package com.vanhack.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vanhack.urlshortener.service.UrlShortenerService;

import javassist.NotFoundException;

/**
* UrlShortenerController is the main controller class we'll be using to
* create new shortener URL and redirect to original long URL.
* 
* @author Bruno Amorim
* 
*/
@RestController
public class UrlShortenerController {
	
	/**
	 * The urlShortenerService hold business logic
	 * and call method in repository layer.
	 */
	private final UrlShortenerService urlShortenerService;
	
	@Autowired
	public UrlShortenerController(UrlShortenerService service) {
		this.urlShortenerService = service;
	}
	
	/**
	 * <p>
	 * This method redirects the short URL call to the original URL
	 * or sent HTTP NOT_FOUND error.
	 * </p>
	 * @param idBase62 id in Base 62 to the original URL.
	 * @return ResponseEntity redirecting to original long URL.
	 * @since 1.0
	 */
	@RequestMapping("/redirect/{idBase62}")
	public HttpEntity<ResourceSupport> redirectToExternalUrl(
		@PathVariable("idBase62") String idBase62
	) {
		try {
			String url = new StringBuilder("http://")
					.append(urlShortenerService.getLongUrl(idBase62))
					.toString();
			
			return ResponseEntity
				    	.status(HttpStatus.TEMPORARY_REDIRECT)
				    	.header(HttpHeaders.LOCATION, url)
				    	.build();
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * <p>
	 * This method redirects the short URL call to the original URL
	 * or sent HTTP NOT_FOUND error.
	 * </p>
	 * @param url long URL.
	 * @return JSON with original long URL, the shortener URL
	 *			and the date and time of its creating.
	 * @since 1.0
	 */
	@GetMapping("/shrink")
	public HttpEntity<ResourceSupport> getShorterUrl(
			@RequestParam("url") String url) {
		
		ResourceSupport resource = urlShortenerService.getUrlIndexResource(url);
		return ResponseEntity.ok(resource);
	}

}
