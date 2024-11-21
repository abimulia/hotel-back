/**
 * 
 */
package com.abimulia.hotel.listing.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.abimulia.hotel.listing.application.dto.CreatedListingDto;
import com.abimulia.hotel.listing.application.dto.DisplayCardListingDto;
import com.abimulia.hotel.listing.application.dto.DisplayListingDto;
import com.abimulia.hotel.listing.application.dto.ListingCreateBookingDto;
import com.abimulia.hotel.listing.application.dto.SaveListingDto;
import com.abimulia.hotel.listing.application.dto.vo.PriceVo;
import com.abimulia.hotel.listing.domain.Listing;

/**
 * 
 */
@Mapper(componentModel = "spring", uses = {ListingPictureMapper.class})
public interface ListingMapper {
	@Mapping(target = "landlordPublicId", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "pictures", ignore = true)
    @Mapping(target = "title", source = "description.title.value")
    @Mapping(target = "description", source = "description.description.value")
    @Mapping(target = "bedrooms", source = "infos.bedrooms.value")
    @Mapping(target = "guests", source = "infos.guests.value")
    @Mapping(target = "bookingCategory", source = "category")
    @Mapping(target = "beds", source = "infos.beds.value")
    @Mapping(target = "bathrooms", source = "infos.baths.value")
    @Mapping(target = "price", source = "price.value")
    Listing saveListingDTOToListing(SaveListingDto saveListingDTO);

    CreatedListingDto listingToCreatedListingDTO(Listing listing);

    @Mapping(target = "cover", source = "pictures")
    List<DisplayCardListingDto> listingToDisplayCardListingDTOs(List<Listing> listings);

    @Mapping(target = "cover", source = "pictures", qualifiedByName = "extract-cover")
    DisplayCardListingDto listingToDisplayCardListingDTO(Listing listing);

    default PriceVo mapPriceToPriceVO(int price) {
        return new PriceVo(price);
    }

    @Mapping(target = "landlord", ignore = true)
    @Mapping(target = "description.title.value", source = "title")
    @Mapping(target = "description.description.value", source = "description")
    @Mapping(target = "infos.bedrooms.value", source = "bedrooms")
    @Mapping(target = "infos.guests.value", source = "guests")
    @Mapping(target = "infos.beds.value", source = "beds")
    @Mapping(target = "infos.baths.value", source = "bathrooms")
    @Mapping(target = "category", source = "bookingCategory")
    @Mapping(target = "price.value", source = "price")
    DisplayListingDto listingToDisplayListingDTO(Listing listing);

    @Mapping(target = "listingPublicId", source = "publicId")
    ListingCreateBookingDto mapListingToListingCreateBookingDTO(Listing listing);
}
