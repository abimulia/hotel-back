/**
 * 
 */
package com.abimulia.hotel.user.mapper;

import org.mapstruct.Mapper;

import com.abimulia.hotel.user.application.dto.ReadUserDto;
import com.abimulia.hotel.user.domain.Authority;
import com.abimulia.hotel.user.domain.User;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
	ReadUserDto readUserDtoToUser(User user);
	
	default String mapAuthoritiesToString(Authority authority) {
		return authority.getName();
	}

}
