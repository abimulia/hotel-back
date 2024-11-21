/**
 * 
 */
package com.abimulia.hotel.listing.application.dto.sub;

import com.abimulia.hotel.listing.application.dto.vo.DescriptionVo;
import com.abimulia.hotel.listing.application.dto.vo.TitleVo;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public record DescriptonDto(@NotNull TitleVo title,@NotNull DescriptionVo description) {

}
