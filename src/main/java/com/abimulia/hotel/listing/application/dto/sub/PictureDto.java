/**
 * 
 */
package com.abimulia.hotel.listing.application.dto.sub;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public record PictureDto(@NotNull byte[] file, @NotNull String fileContentType, @NotNull boolean isCover) {

	@Override
	public int hashCode() {
		return Objects.hash(fileContentType, isCover);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PictureDto other = (PictureDto) obj;
		return Objects.equals(fileContentType, other.fileContentType) && isCover == other.isCover;
	}

	@Override
	public String toString() {
		return "PictureDto [fileContentType=" + fileContentType + ", isCover=" + isCover + "]";
	}
	

}
