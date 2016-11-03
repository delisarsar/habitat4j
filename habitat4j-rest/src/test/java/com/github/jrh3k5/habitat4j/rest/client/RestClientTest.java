package com.github.jrh3k5.habitat4j.rest.client;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.jrh3k5.habitat4j.client.TemperatureUnit;
import com.github.jrh3k5.habitat4j.client.Thermostat;
import com.github.jrh3k5.habitat4j.rest.AccessToken;
import com.github.jrh3k5.habitat4j.rest.AccessTokenProvider;
import com.github.jrh3k5.habitat4j.rest.NestUrls;

/**
 * Unit tests for {@link RestClient}.
 * 
 * @author jrh3k5
 */

@RunWith(MockitoJUnitRunner.class)
public class RestClientTest {
    @Mock
    private NestUrls nestUrls;
    @Mock
    private Client client;
    @Mock
    private AccessTokenProvider accessTokenProvider;
    private RestClient restClient;

    /**
     * Sets up the REST client for each test.
     */
    @Before
    public void setUp() {
        restClient = new RestClient(client, accessTokenProvider, nestUrls);
    }

    /**
     * Test the retrieval of thermostats.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetThermostats() {
        final String accessTokenString = String.format("/accessToken:%s", UUID.randomUUID());
        final AccessToken accessToken = mock(AccessToken.class);
        when(accessToken.getToken()).thenReturn(accessTokenString);
        when(accessTokenProvider.getAccessToken()).thenReturn(accessToken);
        
        final Invocation.Builder headerBuilder = mock(Invocation.Builder.class);
        when(headerBuilder.get(any(Class.class))).then((InvocationOnMock o) -> {
            final Class<?> targetClass = o.getArgument(0);
            final ObjectReader objectReader = new ObjectMapper().readerFor(targetClass);
            try (final InputStream jsonStream = RestClientTest.class.getResourceAsStream("RestClientTest.sampleNestResponse.json")) {
                return objectReader.readValue(jsonStream);
            }
        });
        final Invocation.Builder acceptBuilder = mock(Invocation.Builder.class);
        when(acceptBuilder.header("Authorization", "Bearer " + accessTokenString)).thenReturn(headerBuilder);
        final WebTarget target = mock(WebTarget.class);
        when(target.request(MediaType.APPLICATION_JSON_TYPE)).thenReturn(acceptBuilder);

        final String nestApiUrl = String.format("/nestApiUrl:%s", UUID.randomUUID());
        when(nestUrls.getApiUrl()).thenReturn(nestApiUrl);
        when(client.target(nestApiUrl)).thenReturn(target);

        final List<? extends Thermostat> thermostats = restClient.getThermostats();
        assertThat(thermostats).hasSize(1);

        final Thermostat thermostat = thermostats.get(0);
        assertThat(thermostat.getDeviceId()).isEqualTo("peyiJNo0IldT2YlIVtYaGQ");

        final Thermostat.Temperatures celsiusTemperatures = thermostat.getTemperatureInformation(TemperatureUnit.CELSIUS);
        assertThat(celsiusTemperatures.getTemperatureUnit()).isEqualTo(TemperatureUnit.CELSIUS);
        assertThat(celsiusTemperatures.getAmbientTemperature()).isEqualTo(21.5f);

        final Thermostat.Temperatures farenheitTemperatures = thermostat.getTemperatureInformation(TemperatureUnit.FARENHEIT);
        assertThat(farenheitTemperatures.getTemperatureUnit()).isEqualTo(TemperatureUnit.FARENHEIT);
        assertThat(farenheitTemperatures.getAmbientTemperature()).isEqualTo(72.0f);
    }
}
