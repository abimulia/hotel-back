/**
 * 
 */
package com.abimulia.hotel.listing.application;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.abimulia.hotel.listing.application.dto.sub.PictureDto;
import com.abimulia.hotel.listing.domain.Listing;
import com.abimulia.hotel.listing.domain.ListingPicture;
import com.abimulia.hotel.listing.mapper.ListingPictureMapper;
import com.abimulia.hotel.listing.repository.ListingPictureRepository;

/**
 * 
 */
@Service
public class PictureService {
	private final ListingPictureRepository listingPictureRepository;
	private final ListingPictureMapper listingPictureMapper;
	/**
	 * @param listingPictureRepository
	 * @param listingPictureMapper
	 */
	public PictureService(ListingPictureRepository listingPictureRepository,
			ListingPictureMapper listingPictureMapper) {
		this.listingPictureRepository = listingPictureRepository;
		this.listingPictureMapper = listingPictureMapper;
	}
	
	public List<PictureDto> saveAll(List<PictureDto> pictures, Listing listing) {
        Set<ListingPicture> listingPictures = listingPictureMapper.pictureDTOsToListingPictures(pictures);

        boolean isFirst = true;

        for (ListingPicture listingPicture : listingPictures) {
            listingPicture.setCover(isFirst);
            listingPicture.setListing(listing);
            isFirst = false;
        }

        listingPictureRepository.saveAll(listingPictures);
        return listingPictureMapper.listingPictureToPictureDTO(listingPictures.stream().toList());
    }
	

}
