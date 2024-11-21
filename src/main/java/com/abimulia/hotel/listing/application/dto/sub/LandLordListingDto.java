/**
 * 
 */
package com.abimulia.hotel.listing.application.dto.sub;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public record LandLordListingDto(@NotNull String firstname,@NotNull String imageUrl) {

}
