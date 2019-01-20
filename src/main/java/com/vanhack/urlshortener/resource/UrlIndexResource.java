package com.vanhack.urlshortener.resource;

import java.time.LocalDateTime;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* ResourceSupport containing the URL index and the time-stamp of its creation.
* 
* @author Bruno Amorim
* 
*/
@Data
@EqualsAndHashCode(callSuper=false)
public class UrlIndexResource extends ResourceSupport {
	private final String shortUrl;
	private final String longUrl;
	private final LocalDateTime created;
	private final Long clicks;

    @JsonCreator
    public UrlIndexResource(
    	@JsonProperty("shortUrl") String shortUrl,
    	@JsonProperty("longUrl") String longUrl,
    	@JsonProperty("created") LocalDateTime created,
    	@JsonProperty("clicks") Long clicks
    ) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.created = created;
        this.clicks = clicks;
    }
}
