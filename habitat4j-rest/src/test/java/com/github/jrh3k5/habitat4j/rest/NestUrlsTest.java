package com.github.jrh3k5.habitat4j.rest;

/*-
 * #%L
 * Habitat4j REST Client
 * %%
 * Copyright (C) 2016 jrh3k5
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Unit tests for {@link NestUrls}.
 * 
 * @author jrh3k5
 */

public class NestUrlsTest {
    private final NestUrls nestUrls = new NestUrls();

    /**
     * Tests the retrieval of the API URL.
     */
    @Test
    public void testGetApiUrl() {
        assertThat(nestUrls.getApiUrl()).isEqualTo("https://developer-api.nest.com");
    }

    /**
     * Tests the retrieval of the OAuth 2.0 URL.
     */
    @Test
    public void testGetOAuth2Url() {
        assertThat(nestUrls.getOauth2Url()).isEqualTo("https://api.home.nest.com/oauth2/access_token");
    }

}
