/**
 * 
 */
package com.abimulia.hotel.listing.presentation;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abimulia.hotel.listing.application.TenantService;
import com.abimulia.hotel.listing.application.dto.DisplayCardListingDto;
import com.abimulia.hotel.listing.application.dto.DisplayListingDto;
import com.abimulia.hotel.listing.application.dto.SearchDto;
import com.abimulia.hotel.listing.domain.BookingCategory;
import com.abimulia.hotel.sharedkernel.service.State;
import com.abimulia.hotel.sharedkernel.service.StatusNotification;

import jakarta.validation.Valid;

/**
 * 
 */
@RestController
@RequestMapping("/api/tenant-listing")
public class TenantResource {
	
	private final TenantService tenantService;

	/**
	 * @param tenantService
	 */
	public TenantResource(TenantService tenantService) {
		super();
		this.tenantService = tenantService;
	}
	
	@GetMapping("/get-all-by-category")
    public ResponseEntity<Page<DisplayCardListingDto>> findAllByBookingCategory(Pageable pageable,
                                                                                @RequestParam BookingCategory category) {
        return ResponseEntity.ok(tenantService.getAllByCategory(pageable, category));
    }

    @GetMapping("/get-one")
    public ResponseEntity<DisplayListingDto> getOne(@RequestParam UUID publicId) {
        State<DisplayListingDto, String> displayListingState = tenantService.getOne(publicId);
        if (displayListingState.getStatus().equals(StatusNotification.OK)) {
            return ResponseEntity.ok(displayListingState.getValue());
        } else {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, displayListingState.getError());
            return ResponseEntity.of(problemDetail).build();
        }
    }

    @PostMapping("/search")
    public ResponseEntity<Page<DisplayCardListingDto>> search(Pageable pageable,
                                                              @Valid @RequestBody SearchDto searchDTO) {
        return ResponseEntity.ok(tenantService.search(pageable, searchDTO));
    }

}
