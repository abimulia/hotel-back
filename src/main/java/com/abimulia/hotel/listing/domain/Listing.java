/**
 * 
 */
package com.abimulia.hotel.listing.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.abimulia.hotel.sharedkernel.domain.AbstractAuditingEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "listing")
public class Listing extends AbstractAuditingEntity<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6609383224838955797L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listingSequenceGenerator")
	@SequenceGenerator(name = "listingSequenceGenerator", sequenceName = "listing_generator", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@UuidGenerator
	@Column(name = "public_id", nullable = false)
	private UUID publicId;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "guests")
	private int guests;
	@Column(name = "bedrooms")
	private int bedrooms;
	@Column(name = "beds")
	private int beds;
	@Column(name = "bathrooms")
	private int bathrooms;

	@Column(name = "price")
	private int price;

	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	private BookingCategory bookingCategory;

	@Column(name = "location")
	private String location;

	@Column(name = "landlord_public_id")
	private UUID landlordPublicId;

	@OneToMany(mappedBy = "listing", cascade = CascadeType.REMOVE)
	private Set<ListingPicture> pictures = new HashSet<>();

	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the publicId
	 */
	public UUID getPublicId() {
		return publicId;
	}

	/**
	 * @param publicId the publicId to set
	 */
	public void setPublicId(UUID publicId) {
		this.publicId = publicId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the guests
	 */
	public int getGuests() {
		return guests;
	}

	/**
	 * @param guests the guests to set
	 */
	public void setGuests(int guests) {
		this.guests = guests;
	}

	/**
	 * @return the bedrooms
	 */
	public int getBedrooms() {
		return bedrooms;
	}

	/**
	 * @param bedrooms the bedrooms to set
	 */
	public void setBedrooms(int bedrooms) {
		this.bedrooms = bedrooms;
	}

	/**
	 * @return the beds
	 */
	public int getBeds() {
		return beds;
	}

	/**
	 * @param beds the beds to set
	 */
	public void setBeds(int beds) {
		this.beds = beds;
	}

	/**
	 * @return the bathrooms
	 */
	public int getBathrooms() {
		return bathrooms;
	}

	/**
	 * @param bathrooms the bathrooms to set
	 */
	public void setBathrooms(int bathrooms) {
		this.bathrooms = bathrooms;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the bookingCategory
	 */
	public BookingCategory getBookingCategory() {
		return bookingCategory;
	}

	/**
	 * @param bookingCategory the bookingCategory to set
	 */
	public void setBookingCategory(BookingCategory bookingCategory) {
		this.bookingCategory = bookingCategory;
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
	 * @return the landlordPublicId
	 */
	public UUID getLandlordPublicId() {
		return landlordPublicId;
	}

	/**
	 * @param landlordPublicId the landlordPublicId to set
	 */
	public void setLandlordPublicId(UUID landlordPublicId) {
		this.landlordPublicId = landlordPublicId;
	}

	/**
	 * @return the pictures
	 */
	public Set<ListingPicture> getPictures() {
		return pictures;
	}

	/**
	 * @param pictures the pictures to set
	 */
	public void setPictures(Set<ListingPicture> pictures) {
		this.pictures = pictures;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bathrooms, bedrooms, beds, bookingCategory, description, guests, landlordPublicId, location,
				price, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Listing other = (Listing) obj;
		return bathrooms == other.bathrooms && bedrooms == other.bedrooms && beds == other.beds
				&& bookingCategory == other.bookingCategory && Objects.equals(description, other.description)
				&& guests == other.guests && Objects.equals(landlordPublicId, other.landlordPublicId)
				&& Objects.equals(location, other.location) && price == other.price
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Listing [title=" + title + ", description=" + description + ", guests=" + guests + ", bedrooms="
				+ bedrooms + ", beds=" + beds + ", bathrooms=" + bathrooms + ", price=" + price + ", bookingCategory="
				+ bookingCategory + ", location=" + location + ", landlordPublicId=" + landlordPublicId + "]";
	}

}
