package com.vanhack.urlshortener.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.urlshortener.model.UrlIndex;
import com.vanhack.urlshortener.repository.UrlIndexRepository;
import com.vanhack.urlshortener.resource.UrlIndexResource;

import javassist.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UrlShortenerServiceTests {
	private final String url = "www.vanhack.com";
	private final LocalDateTime timestamp = LocalDateTime.now();
	
	@Autowired
	private UrlShortenerService service;
	
	@MockBean
    private UrlIndexRepository repo;
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
    public void shouldGetLongUrlReturnLongUrl() throws Exception {
		Long id = 1l;
		
		UrlIndex urlIndex = new UrlIndex(url);
		urlIndex.setId(id);
		urlIndex.setCreated(timestamp);
		Optional<UrlIndex> index = Optional.of(urlIndex);
		
		given(repo.findById(id)).willReturn(index);
		urlIndex.incrementClicks();
		given(repo.save(urlIndex)).willReturn(urlIndex);
		
		assertThat(service.getLongUrl("n")).isEqualTo(url);
	}
	
	@Test
    public void shouldGetLongUrlReturnHttpNotFound() throws Exception {
		Optional<UrlIndex> index = Optional.empty();
		given(repo.findById(1l)).willReturn(index);
		
		exceptionRule.expect(NotFoundException.class);
	    exceptionRule.expectMessage("Short URL not found.");
		service.getLongUrl("n");
	}
	
	@Test
	public void shouldGetUrlIndexResourceReturnFoundResource() throws Exception {
		UrlIndex urlIndex = new UrlIndex(url);
		urlIndex.setId(1l);
		urlIndex.setCreated(timestamp);
		Optional<UrlIndex> index = Optional.of(urlIndex);
		
		given(repo.findByUrl(url)).willReturn(index);
		
		ResourceSupport resource =
			new UrlIndexResource("n", url, timestamp, 0l);
		
		assertThat(service.getUrlIndexResource(url)).isEqualTo(resource);
	}
	
	@Test
	public void shouldGetUrlIndexResourceReturnNewResource() throws Exception {
		Optional<UrlIndex> index = Optional.empty();
		given(repo.findByUrl(url)).willReturn(index);
		
		UrlIndex urlIndex = new UrlIndex(url);
		urlIndex.setId(1l);
		urlIndex.setCreated(timestamp);
		given(repo.save(new UrlIndex(url))).willReturn(urlIndex);
		
		ResourceSupport resource =
			new UrlIndexResource("n", url, timestamp, 0l);
		
		assertThat(service.getUrlIndexResource(url)).isEqualTo(resource);
	}
}
