package com.vanhack.urlshortener.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.vanhack.urlshortener.resource.UrlIndexResource;
import com.vanhack.urlshortener.service.UrlShortenerService;

import javassist.NotFoundException;

@RunWith(SpringRunner.class)
@WebMvcTest(UrlShortenerController.class)
public class UrlShortenerControllerTests {
	private final String url = "www.vanhack.com";
	private final LocalDateTime timestamp = LocalDateTime.now();
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
    private UrlShortenerService service;
	
	@Test
    public void shouldReturnShorterUrlJson() throws Exception {
		ResourceSupport resource =
			new UrlIndexResource("n", url, timestamp, 0l);
		
		given(service.getUrlIndexResource(url)).willReturn(resource);
		
        this.mockMvc.perform(get("/shrink?url=www.vanhack.com"))
        	.andDo(print())
        	.andExpect(status().isOk())
        	.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
        	.andExpect(jsonPath("$.longUrl").value(url))
        	.andExpect(jsonPath("$.shortUrl").value("n"))
        	.andExpect(jsonPath("$.clicks").value("0"));
    }
	
	@Test
    public void shouldRedicectToLongUrl() throws Exception {
		given(service.getLongUrl("n")).willReturn(url);
		
        this.mockMvc.perform(get("/redirect/n"))
        	.andDo(print())
        	.andExpect(status().isTemporaryRedirect());
    }
	
	@Test
    public void shouldNotFoundToLongUrl() throws Exception {
		given(service.getLongUrl("n"))
			.willThrow(NotFoundException.class);
		
        this.mockMvc.perform(get("/redirect/n"))
        	.andDo(print())
        	.andExpect(status().isNotFound());
    }

}
