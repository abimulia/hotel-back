/**
 * 
 */
package com.abimulia.hotel.listing.domain;

import com.abimulia.hotel.sharedkernel.domain.AbstractAuditingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "listing_picture")
public class ListingPicture extends AbstractAuditingEntity<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7991569986602396659L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listingPictureSequenceGenerator")
	@SequenceGenerator(name = "listingPictureSequenceGenerator", sequenceName = "listing_picture_generator", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "listing_fk", referencedColumnName = "id")
	private Listing listing;

	@Lob
	@Column(name = "file", nullable = false)
	private byte[] file;

	@Column(name = "file_content_type")
	private String fileContentType;

	@Column(name = "is_cover")
	private boolean isCover;

	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the listing
	 */
	public Listing getListing() {
		return listing;
	}

	/**
	 * @param listing the listing to set
	 */
	public void setListing(Listing listing) {
		this.listing = listing;
	}

	/**
	 * @return the file
	 */
	public byte[] getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(byte[] file) {
		this.file = file;
	}

	/**
	 * @return the fileContentType
	 */
	public String getFileContentType() {
		return fileContentType;
	}

	/**
	 * @param fileContentType the fileContentType to set
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * @return the isCover
	 */
	public boolean isCover() {
		return isCover;
	}

	/**
	 * @param isCover the isCover to set
	 */
	public void setCover(boolean isCover) {
		this.isCover = isCover;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
