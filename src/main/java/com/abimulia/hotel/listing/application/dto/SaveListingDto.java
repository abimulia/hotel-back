/**
 * 
 */
package com.abimulia.hotel.listing.application.dto;

import java.util.List;

import com.abimulia.hotel.listing.application.dto.sub.DescriptonDto;
import com.abimulia.hotel.listing.application.dto.sub.ListingInfoDto;
import com.abimulia.hotel.listing.application.dto.sub.PictureDto;
import com.abimulia.hotel.listing.application.dto.vo.PriceVo;
import com.abimulia.hotel.listing.domain.BookingCategory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public class SaveListingDto {
	
	@NotNull
	BookingCategory category;
	
	@NotNull String location;
	
	@NotNull @Valid ListingInfoDto infos;
	
	@NotNull @Valid DescriptonDto description;
	
	@NotNull @Valid PriceVo price;
	
	@NotNull List<PictureDto> pictures;

	/**
	 * @return the category
	 */
	public BookingCategory getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(BookingCategory category) {
		this.category = category;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the infos
	 */
	public ListingInfoDto getInfos() {
		return infos;
	}

	/**
	 * @param infos the infos to set
	 */
	public void setInfos(ListingInfoDto infos) {
		this.infos = infos;
	}

	/**
	 * @return the description
	 */
	public DescriptonDto getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(DescriptonDto description) {
		this.description = description;
	}

	/**
	 * @return the price
	 */
	public PriceVo getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(PriceVo price) {
		this.price = price;
	}

	/**
	 * @return the pictures
	 */
	public List<PictureDto> getPictures() {
		return pictures;
	}

	/**
	 * @param pictures the pictures to set
	 */
	public void setPictures(List<PictureDto> pictures) {
		this.pictures = pictures;
	}
	
	

}