/**
 * 
 */
package com.abimulia.hotel.listing.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abimulia.hotel.listing.application.dto.CreatedListingDto;
import com.abimulia.hotel.listing.application.dto.DisplayCardListingDto;
import com.abimulia.hotel.listing.application.dto.ListingCreateBookingDto;
import com.abimulia.hotel.listing.application.dto.SaveListingDto;
import com.abimulia.hotel.listing.domain.Listing;
import com.abimulia.hotel.listing.mapper.ListingMapper;
import com.abimulia.hotel.listing.repository.ListingRepository;
import com.abimulia.hotel.sharedkernel.service.State;
import com.abimulia.hotel.sharedkernel.service.StateBuilder;
import com.abimulia.hotel.user.application.Auth0Service;
import com.abimulia.hotel.user.application.UserService;
import com.abimulia.hotel.user.application.dto.ReadUserDto;

/**
 * 
 */
@Service
public class LandlordService {
	private final ListingRepository listingRepository;

    private final ListingMapper listingMapper;
    private final UserService userService;
    @Autowired
    private final Auth0Service auth0Service;
    private final PictureService pictureService;
	/**
	 * @param listingRepository
	 * @param listingMapper
	 * @param userService
	 * @param auth0Service
	 * @param pictureService
	 */
	public LandlordService(ListingRepository listingRepository, ListingMapper listingMapper, UserService userService,
			Auth0Service auth0Service, PictureService pictureService) {
		super();
		this.listingRepository = listingRepository;
		this.listingMapper = listingMapper;
		this.userService = userService;
		this.auth0Service = auth0Service;
		this.pictureService = pictureService;
	}
    
	public CreatedListingDto create(SaveListingDto saveListingDTO) {
        Listing newListing = listingMapper.saveListingDTOToListing(saveListingDTO);

        ReadUserDto userConnected = userService.getAuthenticatedUserFromSecurityContext();
        newListing.setLandlordPublicId(userConnected.publicId());

        Listing savedListing = listingRepository.saveAndFlush(newListing);

        pictureService.saveAll(saveListingDTO.getPictures(), savedListing);

        auth0Service.addLandlordRoleToUser(userConnected);

        return listingMapper.listingToCreatedListingDTO(savedListing);
    }
    
	@Transactional(readOnly = true)
    public List<DisplayCardListingDto> getAllProperties(ReadUserDto landlord) {
        List<Listing> properties = listingRepository.findAllByLandlordPublicIdFetchCoverPicture(landlord.publicId());
        return listingMapper.listingToDisplayCardListingDTOs(properties);
    }

    @Transactional
    public State<UUID, String> delete(UUID publicId, ReadUserDto landlord) {
        long deletedSuccessfuly = listingRepository.deleteByPublicIdAndLandlordPublicId(publicId, landlord.publicId());
        if (deletedSuccessfuly > 0) {
            return StateBuilder.<UUID, String>builder().forSuccess(publicId);
        } else {
            return StateBuilder.<UUID, String>builder().forUnauthorized("User not authorized to delete this listing");
        }
    }

    public Optional<ListingCreateBookingDto> getByListingPublicId(UUID publicId) {
        return listingRepository.findByPublicId(publicId).map(listingMapper::mapListingToListingCreateBookingDTO);
    }

    public List<DisplayCardListingDto> getCardDisplayByListingPublicId(List<UUID> allListingPublicIDs) {
        return listingRepository.findAllByPublicIdIn(allListingPublicIDs)
                .stream()
                .map(listingMapper::listingToDisplayCardListingDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<DisplayCardListingDto> getByPublicIdAndLandlordPublicId(UUID listingPublicId, UUID landlordPublicId) {
        return listingRepository.findOneByPublicIdAndLandlordPublicId(listingPublicId, landlordPublicId)
                .map(listingMapper::listingToDisplayCardListingDTO);
    }
}
