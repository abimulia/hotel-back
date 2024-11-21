/**
 * 
 */
package com.abimulia.hotel.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.abimulia.hotel.booking.application.dto.BookedDateDto;
import com.abimulia.hotel.booking.application.dto.NewBookingDto;
import com.abimulia.hotel.booking.domain.Booking;

/**
 * 
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {

	Booking newBookingToBooking(NewBookingDto newBookingDto);

	BookedDateDto bookingToCheckAvailability(Booking booking);

}
