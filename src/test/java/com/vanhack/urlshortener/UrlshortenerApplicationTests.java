package com.vanhack.urlshortener;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.urlshortener.controller.UrlShortenerController;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UrlshortenerApplicationTests {
	@LocalServerPort
    private int port;
	
	@Autowired
	private UrlShortenerController controller;
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}
	
	@Test
    public void shrinkShouldJsonWithlongUrl() throws Exception {
		String url = new StringBuilder("http://localhost:")
				.append(port)
				.append("/shrink?url=www.vanhack.com")
				.toString();
		
        assertThat(restTemplate.getForObject(url, String.class))
        	.contains("\"longUrl\":\"www.vanhack.com\"");
    }

}
