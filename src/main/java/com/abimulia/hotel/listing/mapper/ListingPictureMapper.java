/**
 * 
 */
package com.abimulia.hotel.listing.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.abimulia.hotel.listing.application.dto.sub.PictureDto;
import com.abimulia.hotel.listing.domain.ListingPicture;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ListingPictureMapper {
	
	Set<ListingPicture> pictureDTOsToListingPictures(List<PictureDto> pictureDTOs);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "listing", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "cover", source = "isCover")
    ListingPicture pictureDTOToListingPicture(PictureDto pictureDTO);

    List<PictureDto> listingPictureToPictureDTO(List<ListingPicture> listingPictures);

    @Mapping(target = "isCover", source = "cover")
    PictureDto convertToPictureDTO(ListingPicture listingPicture);

    @Named("extract-cover")
    default PictureDto extractCover(Set<ListingPicture> pictures) {
        return pictures.stream().findFirst().map(this::convertToPictureDTO).orElseThrow();
    }

}
