/**
 * 
 */
package com.abimulia.hotel.user.presentation;

import java.text.MessageFormat;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abimulia.hotel.user.application.UserService;
import com.abimulia.hotel.user.application.dto.ReadUserDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthResource {
	private final UserService userService;
	private final ClientRegistration registration;
	
	public AuthResource(UserService userService, ClientRegistrationRepository registration) {
		log.debug("AuthResource() userService: " + userService + " registration: "+ registration);
		this.userService = userService;
		this.registration = registration.findByRegistrationId("okta");
	}
	
	@GetMapping("/get-authenticated-user")
    public ResponseEntity<ReadUserDto> getAuthenticatedUser(
            @AuthenticationPrincipal OAuth2User user, @RequestParam boolean forceResync) {
		log.debug("getAuthenticatedUser()");
        if(user == null) {
        	log.debug("user is null");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
        	log.debug("user: " + user.getName());
            userService.syncWithIdp(user, forceResync);
            ReadUserDto connectedUser = userService.getAuthenticatedUserFromSecurityContext();
            return new ResponseEntity<>(connectedUser, HttpStatus.OK);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        String issuerUri = registration.getProviderDetails().getIssuerUri();
        String originUrl = request.getHeader(HttpHeaders.ORIGIN);
        Object[] params = {issuerUri, registration.getClientId(), originUrl};
        String logoutUrl = MessageFormat.format("{0}v2/logout?client_id={1}&returnTo={2}", params);
        request.getSession().invalidate();
        return ResponseEntity.ok().body(Map.of("logoutUrl", logoutUrl));
    }

}
