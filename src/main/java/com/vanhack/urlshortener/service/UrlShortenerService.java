package com.vanhack.urlshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Service;

import com.vanhack.urlshortener.model.UrlIndex;
import com.vanhack.urlshortener.repository.UrlIndexRepository;
import com.vanhack.urlshortener.resource.UrlIndexResource;

import io.seruco.encoding.base62.Base62;
import javassist.NotFoundException;

/**
* UrlShortenerService is the main service layer class we'll be using to
* create new URL index in database and return the original URL.
* 
* @author Bruno Amorim
* 
*/
@Service
public class UrlShortenerService {
	
	/**
	 * The UrlIndexRepository hold call methods in the repository layer.
	 */
	private final UrlIndexRepository urlIndexRepo;
	private final Base62 base62 = Base62.createInstance();
	
	@Autowired
	public UrlShortenerService(UrlIndexRepository repo) {
		this.urlIndexRepo = repo;
	}
	
	/**
	 * <p>
	 * This method uses an Id in Base62 to find the original URL.
	 * </p>
	 * @param idBase62 String representing an number in Base62.
	 * @throws NotFoundException if the id in Base62 was not found in database.
	 * @return original URL.
	 * @since 1.0
	 */
	public String getLongUrl(String idBase62) throws NotFoundException {
		Long id = decodeBase62(idBase62);
		
		UrlIndex urlIndex = urlIndexRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Short URL not found."))
				.incrementClicks();
		
		return urlIndexRepo.save(urlIndex).getUrl();
	}

	/**
	 * <p>
	 * This method uses the original URL to find or create
	 * in the database a new URL index.
	 * </p>
	 * @param url long URL.
	 * @return UrlShortenerResource ResourceSupport containing the id in Base62.
	 * @since 1.0
	 */
	public ResourceSupport getUrlIndexResource(String url) {
		UrlIndex urlIndex = urlIndexRepo.findByUrl(url).orElseGet(() -> urlIndexRepo.save(new UrlIndex(url)));
		
		return new UrlIndexResource(
			encodeBase62(urlIndex.getId()),
			urlIndex.getUrl(),
			urlIndex.getCreated(),
			urlIndex.getClicks()
		);
	}
	
	/**
	 * <p>
	 * This method encode a Long object into a String object representing a Base62 number.
	 * </p>
	 * @param id Long object
	 * @return String object representing a Base62 number
	 * @since 1.0
	 */
	private String encodeBase62(Long id) {
		return new String(base62.encode(String.valueOf(id).getBytes()));		
	}
	
	/**
	 * <p>
	 * This method decode a String object representing a Base62 number into
	 * a Long object.
	 * </p>
	 * @param id String object representing a Base62 number
	 * @return Long object equivalent to then received Base62 number
	 * @since 1.0
	 */
	private Long decodeBase62(String id) {
		return Long.valueOf(new String(base62.decode(id.getBytes())));
	}
}
