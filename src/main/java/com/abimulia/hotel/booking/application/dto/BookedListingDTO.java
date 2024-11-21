/**
 * 
 */
package com.abimulia.hotel.booking.application.dto;

import java.util.UUID;

import com.abimulia.hotel.listing.application.dto.sub.PictureDto;
import com.abimulia.hotel.listing.application.dto.vo.PriceVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public record BookedListingDTO(@Valid PictureDto cover, @NotEmpty String location, @Valid BookedDateDto dates,
		@Valid PriceVo totalPrice, @NotNull UUID bookingPublicId, @NotNull UUID listingPublicId) {

}
