/**
 * 
 */
package com.abimulia.hotel.listing.application.dto.vo;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public record TitleVo(@NotNull(message="Title value must be present") String value) {

}
