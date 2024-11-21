/**
 * 
 */
package com.abimulia.hotel.listing.application.dto;

import java.util.UUID;

import com.abimulia.hotel.listing.application.dto.sub.PictureDto;
import com.abimulia.hotel.listing.application.dto.vo.PriceVo;
import com.abimulia.hotel.listing.domain.BookingCategory;

/**
 * 
 */
public record DisplayCardListingDto(PriceVo price, String location, PictureDto cover, BookingCategory bookingCategory, UUID publicId) {

}
