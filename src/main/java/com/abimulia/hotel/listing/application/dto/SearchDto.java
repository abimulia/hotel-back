/**
 * 
 */
package com.abimulia.hotel.listing.application.dto;

import com.abimulia.hotel.booking.application.dto.BookedDateDto;
import com.abimulia.hotel.listing.application.dto.sub.ListingInfoDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

/**
 * 
 */
public record SearchDto(@Valid BookedDateDto dates, @Valid ListingInfoDto infos, @NotEmpty String location) {

}
