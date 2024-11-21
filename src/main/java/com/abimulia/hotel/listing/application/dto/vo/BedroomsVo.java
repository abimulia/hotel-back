/**
 * 
 */
package com.abimulia.hotel.listing.application.dto.vo;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public record BedroomsVo(@NotNull(message="Bedroom value must be present") int value) {

}
