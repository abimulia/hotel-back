/**
 * 
 */
package com.abimulia.hotel.listing.application.dto.vo;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public record DescriptionVo(@NotNull(message="Description value must be present") String value) {

}
