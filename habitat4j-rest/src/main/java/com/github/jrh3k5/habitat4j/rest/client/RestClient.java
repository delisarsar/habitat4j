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

import java.util.List;
import java.util.Objects;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

import com.github.jrh3k5.habitat4j.client.Thermostat;
import com.github.jrh3k5.habitat4j.rest.AccessToken;
import com.github.jrh3k5.habitat4j.rest.AccessTokenProvider;
import com.github.jrh3k5.habitat4j.rest.NestUrls;

/**
 * A REST implementation of the habitat4j Nest client.
 * 
 * @author jrh3k5
 */

public class RestClient implements com.github.jrh3k5.habitat4j.client.Client {
    private final Client jaxRsClient;
    private final AccessTokenProvider accessTokenProvider;
    private final NestUrls nestUrls;

    /**
     * Create a client.
     * 
     * @param jaxRsClient
     *            A {@link Client} to be used to communicate with the Nest service.
     * @param accessTokenProvider
     *            An {@link AccessTokenProvider} used to provision access tokens.
     * @param nestUrls
     *            A {@link NestUrls} object describing the URLs to be used to interact with Nest.
     * @throws NullPointerException
     *             If any of the given objects are {@code null}.
     */
    public RestClient(Client jaxRsClient, AccessTokenProvider accessTokenProvider, NestUrls nestUrls) {
        this.jaxRsClient = Objects.requireNonNull(jaxRsClient, "JAX-RS client cannot be null");
        this.accessTokenProvider = Objects.requireNonNull(accessTokenProvider, "Access token cannot be null");
        this.nestUrls = Objects.requireNonNull(nestUrls, "Nest URLs data object cannot be null");
    }

    @Override
    public List<? extends Thermostat> getThermostats() {
        final AccessToken accessToken = accessTokenProvider.getAccessToken();
        return jaxRsClient.target(nestUrls.getApiUrl()).request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", "Bearer " + accessToken.getToken()).get(JsonResponse.class).getDevices()
                .getThermostats();
    }
}
