/**
 * 
 */
package com.abimulia.hotel.user.application.dto;

import java.util.Set;
import java.util.UUID;

/**
 * 
 */
public record ReadUserDto(UUID publicId, String firstName, String lastName, String email, String imageUrl, Set<String> authorities) {

}
