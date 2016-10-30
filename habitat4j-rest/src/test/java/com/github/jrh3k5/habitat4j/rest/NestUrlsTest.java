package com.github.jrh3k5.habitat4j.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Unit tests for {@link NestUrls}.
 * 
 * @author jrh3k5
 */

public class NestUrlsTest {
    private final NestUrls nestUrls = new NestUrls();

    @Test
    public void testGetOAuth2Url() {
        assertThat(nestUrls.getOauth2Url()).isEqualTo("https://api.home.nest.com/oauth2/access_token");
    }

}
