/**
 * 
 */
package com.abimulia.hotel.booking.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abimulia.hotel.booking.application.dto.BookedDateDto;
import com.abimulia.hotel.booking.application.dto.BookedListingDTO;
import com.abimulia.hotel.booking.application.dto.NewBookingDto;
import com.abimulia.hotel.booking.domain.Booking;
import com.abimulia.hotel.booking.mapper.BookingMapper;
import com.abimulia.hotel.booking.repository.BookingRepository;
import com.abimulia.hotel.infrastructure.config.SecurityUtils;
import com.abimulia.hotel.listing.application.LandlordService;
import com.abimulia.hotel.listing.application.dto.DisplayCardListingDto;
import com.abimulia.hotel.listing.application.dto.ListingCreateBookingDto;
import com.abimulia.hotel.listing.application.dto.vo.PriceVo;
import com.abimulia.hotel.sharedkernel.service.State;
import com.abimulia.hotel.sharedkernel.service.StateBuilder;
import com.abimulia.hotel.user.application.UserService;
import com.abimulia.hotel.user.application.dto.ReadUserDto;

import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 
 */
@Service
@Slf4j
public class BookingService {
	private final BookingRepository bookingRepository;
	@Autowired
	private final BookingMapper bookingMapper;
	private final UserService userService;
	private final LandlordService landlordService;

	public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper, UserService userService,
			LandlordService landlordService) {
		log.debug("BookingService() BookingRepository: "+ bookingRepository+ " BookingMapper: " + bookingMapper+ " UserService: " + userService +
			" LandlordService: " + landlordService);
		this.bookingRepository = bookingRepository;
		this.bookingMapper = bookingMapper;
		this.userService = userService;
		this.landlordService = landlordService;
	}
	
	@Transactional
    public State<Void, String> create(NewBookingDto newBookingDTO) {
        Booking booking = bookingMapper.newBookingToBooking(newBookingDTO);

        Optional<ListingCreateBookingDto> listingOpt = landlordService.getByListingPublicId(newBookingDTO.listingPublicId());

        if (listingOpt.isEmpty()) {
            return StateBuilder.<Void, String>builder().forError("Landlord public id not found");
        }

        boolean alreadyBooked = bookingRepository.bookingExistsAtInterval(newBookingDTO.startDate(), newBookingDTO.endDate(), newBookingDTO.listingPublicId());

        if (alreadyBooked) {
            return StateBuilder.<Void, String>builder().forError("One booking already exists");
        }

        ListingCreateBookingDto listingCreateBookingDTO = listingOpt.get();

        booking.setFkListing(listingCreateBookingDTO.listingPublicId());

        ReadUserDto connectedUser = userService.getAuthenticatedUserFromSecurityContext();
        booking.setFkTenant(connectedUser.publicId());
        booking.setNumberOfTravelers(1);

        long numberOfNights = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        booking.setTotalPrice((int) (numberOfNights * listingCreateBookingDTO.price().value()));

        bookingRepository.save(booking);

        return StateBuilder.<Void, String>builder().forSuccess();
    }

    @Transactional(readOnly = true)
    public List<BookedDateDto> checkAvailability(UUID publicId) {
        return bookingRepository.findAllByFkListing(publicId)
                .stream().map(bookingMapper::bookingToCheckAvailability).toList();
    }

    @Transactional(readOnly = true)
    public List<BookedListingDTO> getBookedListing() {
        ReadUserDto connectedUser = userService.getAuthenticatedUserFromSecurityContext();
        List<Booking> allBookings = bookingRepository.findAllByFkTenant(connectedUser.publicId());
        List<UUID> allListingPublicIDs = allBookings.stream().map(Booking::getFkListing).toList();
        List<DisplayCardListingDto> allListings = landlordService.getCardDisplayByListingPublicId(allListingPublicIDs);
        return mapBookingToBookedListing(allBookings, allListings);
    }

    private List<BookedListingDTO> mapBookingToBookedListing(List<Booking> allBookings, List<DisplayCardListingDto> allListings) {
        return allBookings.stream().map(booking -> {
            DisplayCardListingDto displayCardListingDTO = allListings
                    .stream()
                    .filter(listing -> listing.publicId().equals(booking.getFkListing()))
                    .findFirst()
                    .orElseThrow();
            BookedDateDto dates = bookingMapper.bookingToCheckAvailability(booking);
            return new BookedListingDTO(displayCardListingDTO.cover(),
                    displayCardListingDTO.location(),
                    dates, new PriceVo(booking.getTotalPrice()),
                    booking.getPublicId(), displayCardListingDTO.publicId());
        }).toList();
    }

    @Transactional
    public State<UUID, String> cancel(UUID bookingPublicId, UUID listingPublicId, boolean byLandlord) {
        ReadUserDto connectedUser = userService.getAuthenticatedUserFromSecurityContext();
        int deleteSuccess = 0;

        if (SecurityUtils.hasCurrentUserAnyOfAuthorities(SecurityUtils.ROLE_LANDLORD)
                && byLandlord) {
            deleteSuccess = handleDeletionForLandlord(bookingPublicId, listingPublicId, connectedUser, deleteSuccess);
        } else {
            deleteSuccess = bookingRepository.deleteBookingByFkTenantAndPublicId(connectedUser.publicId(), bookingPublicId);
        }

        if (deleteSuccess >= 1) {
            return StateBuilder.<UUID, String>builder().forSuccess(bookingPublicId);
        } else {
            return StateBuilder.<UUID, String>builder().forError("Booking not found");
        }
    }

    private int handleDeletionForLandlord(UUID bookingPublicId, UUID listingPublicId, ReadUserDto connectedUser, int deleteSuccess) {
        Optional<DisplayCardListingDto> listingVerificationOpt = landlordService.getByPublicIdAndLandlordPublicId(listingPublicId, connectedUser.publicId());
        if (listingVerificationOpt.isPresent()) {
            deleteSuccess = bookingRepository.deleteBookingByPublicIdAndFkListing(bookingPublicId, listingVerificationOpt.get().publicId());
        }
        return deleteSuccess;
    }

    @Transactional(readOnly = true)
    public List<BookedListingDTO> getBookedListingForLandlord() {
        ReadUserDto connectedUser = userService.getAuthenticatedUserFromSecurityContext();
        List<DisplayCardListingDto> allProperties = landlordService.getAllProperties(connectedUser);
        List<UUID> allPropertyPublicIds = allProperties.stream().map(DisplayCardListingDto::publicId).toList();
        List<Booking> allBookings = bookingRepository.findAllByFkListingIn(allPropertyPublicIds);
        return mapBookingToBookedListing(allBookings, allProperties);
    }

    public List<UUID> getBookingMatchByListingIdsAndBookedDate(List<UUID> listingsId, BookedDateDto bookedDateDTO) {
        return bookingRepository.findAllMatchWithDate(listingsId, bookedDateDTO.startDate(), bookedDateDTO.endDate())
                .stream().map(Booking::getFkListing).toList();
    }
}
