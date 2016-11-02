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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests for {@link CachingAccessTokenProvider}.
 * 
 * @author jrh3k5
 */

@RunWith(MockitoJUnitRunner.class)
public class CachingAccessTokenProviderTest {
    @Mock
    private AccessTokenProvider tokenProvider;
    private CachingAccessTokenProvider cachingProvider;

    /**
     * Sets up the caching provider for each test.
     */
    @Before
    public void setUp() {
        cachingProvider = new CachingAccessTokenProvider(tokenProvider);
    }

    /**
     * Tests the retrieval of the access token.
     * 
     * @throws Exception
     *             If any errors occur during the test run.
     */
    @Test
    public void testGetAccessToken() throws Exception {
        final AccessToken accessToken0 = mock(AccessToken.class);
        // The first times it's queried after creation, it should reflect that it's not yet ready to be refresh - but is on the second inquiry
        when(accessToken0.getExpiration()).thenReturn(LocalDateTime.now().plus(2, ChronoUnit.HOURS), LocalDateTime.now());
        final AccessToken accessToken1 = mock(AccessToken.class);
        when(tokenProvider.getAccessToken()).thenReturn(accessToken0, accessToken1);

        assertThat(cachingProvider.getAccessToken()).isEqualTo(accessToken0);
        // Trigger a refresh evaluation
        assertThat(cachingProvider.getAccessToken()).isEqualTo(accessToken0);
        // Trigger a refresh again - this time, a "new" access token should be created
        assertThat(cachingProvider.getAccessToken()).isEqualTo(accessToken1);
    }
}
