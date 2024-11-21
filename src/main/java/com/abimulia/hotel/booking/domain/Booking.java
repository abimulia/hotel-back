/**
 * 
 */
package com.abimulia.hotel.booking.domain;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.abimulia.hotel.sharedkernel.domain.AbstractAuditingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "booking")
public class Booking extends AbstractAuditingEntity<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8316433871921671659L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookingSequenceGenerator")
    @SequenceGenerator(name = "bookingSequenceGenerator", sequenceName = "booking_generator", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @UuidGenerator
    @Column(name = "public_id", nullable = false)
    private UUID publicId;

    @Column(name = "start_date", nullable = false)
    private OffsetDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private OffsetDateTime endDate;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "nb_of_travelers", nullable = false)
    private int numberOfTravelers;

    @Column(name = "fk_tenant", nullable = false)
    private UUID fkTenant;

    @Column(name = "fk_listing", nullable = false)
    private UUID fkListing;

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
	 * @return the startDate
	 */
	public OffsetDateTime getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(OffsetDateTime startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public OffsetDateTime getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(OffsetDateTime endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the totalPrice
	 */
	public int getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the numberOfTravelers
	 */
	public int getNumberOfTravelers() {
		return numberOfTravelers;
	}

	/**
	 * @param numberOfTravelers the numberOfTravelers to set
	 */
	public void setNumberOfTravelers(int numberOfTravelers) {
		this.numberOfTravelers = numberOfTravelers;
	}

	/**
	 * @return the fkTenant
	 */
	public UUID getFkTenant() {
		return fkTenant;
	}

	/**
	 * @param fkTenant the fkTenant to set
	 */
	public void setFkTenant(UUID fkTenant) {
		this.fkTenant = fkTenant;
	}

	/**
	 * @return the fkListing
	 */
	public UUID getFkListing() {
		return fkListing;
	}

	/**
	 * @param fkListing the fkListing to set
	 */
	public void setFkListing(UUID fkListing) {
		this.fkListing = fkListing;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endDate, fkListing, fkTenant, numberOfTravelers, startDate, totalPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		return Objects.equals(endDate, other.endDate) && Objects.equals(fkListing, other.fkListing)
				&& Objects.equals(fkTenant, other.fkTenant) && numberOfTravelers == other.numberOfTravelers
				&& Objects.equals(startDate, other.startDate) && totalPrice == other.totalPrice;
	}

	@Override
	public String toString() {
		return "Booking [startDate=" + startDate + ", endDate=" + endDate + ", totalPrice=" + totalPrice
				+ ", numberOfTravelers=" + numberOfTravelers + ", fkTenant=" + fkTenant + ", fkListing=" + fkListing
				+ "]";
	}
    
	
}
