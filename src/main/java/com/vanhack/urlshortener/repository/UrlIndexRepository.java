package com.vanhack.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vanhack.urlshortener.model.UrlIndex;

/**
* Repository of URL index objects.
* 
* @author Bruno Amorim
* 
*/
@Repository
public interface UrlIndexRepository extends CrudRepository<UrlIndex, Long> {
	
	Optional<UrlIndex> findByUrl(String url);
}
