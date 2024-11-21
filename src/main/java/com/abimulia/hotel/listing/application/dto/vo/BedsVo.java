/**
 * 
 */
package com.abimulia.hotel.listing.application.dto.vo;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public record BedsVo(@NotNull(message="Bed value must be present") int value) {

}
