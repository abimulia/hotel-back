/**
 * 
 */
package com.abimulia.hotel.listing.application.dto;

import java.util.UUID;

import com.abimulia.hotel.listing.application.dto.vo.PriceVo;

/**
 * 
 */
public record ListingCreateBookingDto(UUID listingPublicId, PriceVo price) {

}
