package com.vanhack.urlshortener.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
* Entity containing the URL index and the time-stamp of its creation.
* 
* @author Bruno Amorim
* 
*/
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class UrlIndex {
	
	/**
	 * The id to the original URL.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**
	 * Hold to the original URL.
	 */
	@NonNull
	private String url;
	
	/**
	 * Hold the time-stamp when the original URL was inserted in the index.
	 */
	private LocalDateTime created = LocalDateTime.now();
	
	/**
	 * Hold the total of calls made to this URL index.
	 */
	@Setter(AccessLevel.NONE) 
	@NonNull
	private Long clicks = 0l;
	
	public UrlIndex incrementClicks() {
    	clicks++;
    	return this;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public Long getClicks() {
		return clicks;
	}

	public void setClicks(Long clicks) {
		this.clicks = clicks;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}	

}
