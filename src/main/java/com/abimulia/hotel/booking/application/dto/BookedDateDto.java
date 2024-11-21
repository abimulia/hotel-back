/**
 * 
 */
package com.abimulia.hotel.booking.application.dto;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;

/**
 * 
 */
public record BookedDateDto(@NotNull OffsetDateTime startDate, @NotNull OffsetDateTime endDate) {

}
