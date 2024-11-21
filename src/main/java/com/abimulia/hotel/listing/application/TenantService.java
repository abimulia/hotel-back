/**
 * 
 */
package com.abimulia.hotel.listing.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abimulia.hotel.booking.application.BookingService;
import com.abimulia.hotel.listing.application.dto.DisplayCardListingDto;
import com.abimulia.hotel.listing.application.dto.DisplayListingDto;
import com.abimulia.hotel.listing.application.dto.SearchDto;
import com.abimulia.hotel.listing.application.dto.sub.LandLordListingDto;
import com.abimulia.hotel.listing.domain.BookingCategory;
import com.abimulia.hotel.listing.domain.Listing;
import com.abimulia.hotel.listing.mapper.ListingMapper;
import com.abimulia.hotel.listing.repository.ListingRepository;
import com.abimulia.hotel.sharedkernel.service.State;
import com.abimulia.hotel.sharedkernel.service.StateBuilder;
import com.abimulia.hotel.user.application.UserService;
import com.abimulia.hotel.user.application.dto.ReadUserDto;

/**
 * 
 */
@Service
public class TenantService {
	private final ListingRepository listingRepository;
	private final ListingMapper listingMapper;
	private final UserService userService;
	private final BookingService bookingService;
	/**
	 * @param listingRepository
	 * @param listingMapper
	 * @param userService
	 * @param bookingService
	 */
	public TenantService(ListingRepository listingRepository, ListingMapper listingMapper, UserService userService,
			BookingService bookingService) {
		this.listingRepository = listingRepository;
		this.listingMapper = listingMapper;
		this.userService = userService;
		this.bookingService = bookingService;
	}
	
	public Page<DisplayCardListingDto> getAllByCategory(Pageable pageable, BookingCategory category) {
        Page<Listing> allOrBookingCategory;
        if (category == BookingCategory.ALL) {
            allOrBookingCategory = listingRepository.findAllWithCoverOnly(pageable);
        } else {
            allOrBookingCategory = listingRepository.findAllByBookingCategoryWithCoverOnly(pageable, category);
        }

        return allOrBookingCategory.map(listingMapper::listingToDisplayCardListingDTO);
    }

    @Transactional(readOnly = true)
    public State<DisplayListingDto, String> getOne(UUID publicId) {
        Optional<Listing> listingByPublicIdOpt = listingRepository.findByPublicId(publicId);

        if (listingByPublicIdOpt.isEmpty()) {
            return StateBuilder.<DisplayListingDto, String>builder()
                    .forError(String.format("Listing doesn't exist for publicId: %s", publicId));
        }

        DisplayListingDto displayListingDTO = listingMapper.listingToDisplayListingDTO(listingByPublicIdOpt.get());

        ReadUserDto readUserDTO = userService.getByPublicId(listingByPublicIdOpt.get().getLandlordPublicId()).orElseThrow();
        LandLordListingDto landlordListingDTO = new LandLordListingDto(readUserDTO.firstName(), readUserDTO.imageUrl());
        displayListingDTO.setLandlord(landlordListingDTO);

        return StateBuilder.<DisplayListingDto, String>builder().forSuccess(displayListingDTO);
    }


    @Transactional(readOnly = true)
    public Page<DisplayCardListingDto> search(Pageable pageable, SearchDto newSearch) {

        Page<Listing> allMatchedListings = listingRepository.findAllByLocationAndBathroomsGreaterThanEqualAndBedroomsGreaterThanEqualAndGuestsGreaterThanEqualAndBedsGreaterThanEqual(pageable, newSearch.location(),
                newSearch.infos().baths().value(),
                newSearch.infos().bedrooms().value(),
                newSearch.infos().guests().value(),
                newSearch.infos().beds().value());

        List<UUID> listingUUIDs = allMatchedListings.stream().map(Listing::getPublicId).toList();

        List<UUID> bookingUUIDs = bookingService.getBookingMatchByListingIdsAndBookedDate(listingUUIDs, newSearch.dates());
 

        List<DisplayCardListingDto> listingsNotBooked = allMatchedListings.stream().filter(listing -> !bookingUUIDs.contains(listing.getPublicId()))
                .map(listingMapper::listingToDisplayCardListingDTO)
                .toList();

        return new PageImpl<>(listingsNotBooked, pageable, listingsNotBooked.size());
    }
	

}
