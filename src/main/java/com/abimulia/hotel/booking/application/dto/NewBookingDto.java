/**
 * 
 */
package com.abimulia.hotel.booking.application.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public record NewBookingDto(@NotNull OffsetDateTime startDate, @NotNull OffsetDateTime endDate, @NotNull UUID listingPublicId) {

}
