/**
 * 
 */
package com.abimulia.hotel.listing.presentation;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.abimulia.hotel.infrastructure.config.SecurityUtils;
import com.abimulia.hotel.listing.application.LandlordService;
import com.abimulia.hotel.listing.application.dto.CreatedListingDto;
import com.abimulia.hotel.listing.application.dto.DisplayCardListingDto;
import com.abimulia.hotel.listing.application.dto.SaveListingDto;
import com.abimulia.hotel.listing.application.dto.sub.PictureDto;
import com.abimulia.hotel.sharedkernel.service.State;
import com.abimulia.hotel.sharedkernel.service.StatusNotification;
import com.abimulia.hotel.user.application.UserException;
import com.abimulia.hotel.user.application.UserService;
import com.abimulia.hotel.user.application.dto.ReadUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

/**
 * 
 */
@RestController
@RequestMapping("/api/landlord-listing")
public class LandlordResource {
	
	private final LandlordService landlordService;
	private final Validator validator;
	private final UserService userService;
	private ObjectMapper objectMapper = new ObjectMapper();
	/**
	 * @param landlordService
	 * @param validator
	 * @param userService
	 */
	public LandlordResource(LandlordService landlordService, Validator validator, UserService userService) {
		super();
		this.landlordService = landlordService;
		this.validator = validator;
		this.userService = userService;
	}
	
	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreatedListingDto> create(
            MultipartHttpServletRequest request,
            @RequestPart(name = "dto") String saveListingDTOString
    ) throws IOException {
        List<PictureDto> pictures = request.getFileMap()
                .values()
                .stream()
                .map(mapMultipartFileToPictureDTO())
                .toList();

        SaveListingDto saveListingDTO = objectMapper.readValue(saveListingDTOString, SaveListingDto.class);
        saveListingDTO.setPictures(pictures);

        Set<ConstraintViolation<SaveListingDto>> violations = validator.validate(saveListingDTO);
        if (!violations.isEmpty()) {
            String violationsJoined = violations.stream()
                    .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                    .collect(Collectors.joining());

            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, violationsJoined);
            return ResponseEntity.of(problemDetail).build();
        } else {
            return ResponseEntity.ok(landlordService.create(saveListingDTO));
        }
    }


    private static Function<MultipartFile, PictureDto> mapMultipartFileToPictureDTO() {
        return multipartFile -> {
            try {
                return new PictureDto(multipartFile.getBytes(), multipartFile.getContentType(), false);
            } catch (IOException ioe) {
                throw new UserException(String.format("Cannot parse multipart file: %s", multipartFile.getOriginalFilename()));
            }
        };
    }

    @GetMapping(value = "/get-all")
    @PreAuthorize("hasAnyRole('" + SecurityUtils.ROLE_LANDLORD + "')")
    public ResponseEntity<List<DisplayCardListingDto>> getAll() {
        ReadUserDto connectedUser = userService.getAuthenticatedUserFromSecurityContext();
        List<DisplayCardListingDto> allProperties = landlordService.getAllProperties(connectedUser);
        return ResponseEntity.ok(allProperties);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('" + SecurityUtils.ROLE_LANDLORD + "')")
    public ResponseEntity<UUID> delete(@RequestParam UUID publicId) {
        ReadUserDto connectedUser = userService.getAuthenticatedUserFromSecurityContext();
        State<UUID, String> deleteState = landlordService.delete(publicId, connectedUser);
        if (deleteState.getStatus().equals(StatusNotification.OK)) {
            return ResponseEntity.ok(deleteState.getValue());
        } else if (deleteState.getStatus().equals(StatusNotification.UNAUTHORIZED)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
