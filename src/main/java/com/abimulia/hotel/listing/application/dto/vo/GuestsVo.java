/**
 * 
 */
package com.abimulia.hotel.listing.application.dto.vo;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public record GuestsVo(@NotNull(message="Guest value must be present") int value) {

}
