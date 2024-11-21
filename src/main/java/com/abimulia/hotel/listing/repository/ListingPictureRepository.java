package com.abimulia.hotel.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abimulia.hotel.listing.domain.ListingPicture;

public interface ListingPictureRepository extends JpaRepository<ListingPicture, Long> {

}
