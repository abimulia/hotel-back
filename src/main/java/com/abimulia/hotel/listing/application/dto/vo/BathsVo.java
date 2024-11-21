/**
 * 
 */
package com.abimulia.hotel.listing.application.dto.vo;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public record BathsVo(@NotNull(message = "Bath value must be present") int value) {

}
