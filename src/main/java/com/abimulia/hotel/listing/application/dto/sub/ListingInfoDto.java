/**
 * 
 */
package com.abimulia.hotel.listing.application.dto.sub;

import com.abimulia.hotel.listing.application.dto.vo.BathsVo;
import com.abimulia.hotel.listing.application.dto.vo.BedroomsVo;
import com.abimulia.hotel.listing.application.dto.vo.BedsVo;
import com.abimulia.hotel.listing.application.dto.vo.GuestsVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public record ListingInfoDto(@NotNull @Valid GuestsVo guests, @NotNull @Valid BedroomsVo bedrooms,
		@NotNull @Valid BedsVo beds, @NotNull @Valid BathsVo baths) {

}
