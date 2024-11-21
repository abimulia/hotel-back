/**
 * 
 */
package com.abimulia.hotel.listing.application.dto.vo;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public record PriceVo(@NotNull(message= "Price value must be present") int value) {

}
