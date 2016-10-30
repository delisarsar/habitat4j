package com.github.jrh3k5.habitat4j.rest.jaxrs;

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

import java.time.LocalDateTime;
import java.util.Objects;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jrh3k5.habitat4j.rest.AccessToken;
import com.github.jrh3k5.habitat4j.rest.AccessTokenProvider;
import com.github.jrh3k5.habitat4j.rest.ClientInformation;
import com.github.jrh3k5.habitat4j.rest.NestUrls;

/**
 * A JAX-RS backed implementation of {@link AccessTokenProvider.
 * 
 * @author jrh3k5
 */

public class JaxRsAccessTokenProvider implements AccessTokenProvider {
    private final Client client;
    private final NestUrls nestUrls;
    private final ClientInformation clientInformation;

    /**
     * Creates an access token provider.
     * 
     * @param client
     *            The JAX-RS {@link Client} to be used to execute the request.
     * @param nestUrls
     *            A {@link NestUrls} object describing the location of Nest resources.
     * @param clientInformation
     *            A {@link ClientInformation} object describing identifying information of the client.
     * @throws NullPointerException
     *             If any of the given objects are {@code null}.
     */
    public JaxRsAccessTokenProvider(Client client, NestUrls nestUrls, ClientInformation clientInformation) {
        this.client = Objects.requireNonNull(client);
        this.nestUrls = nestUrls;
        this.clientInformation = Objects.requireNonNull(clientInformation);
    }

    @Override
    public AccessToken getAccessToken() {
        final LocalDateTime startDateTime = LocalDateTime.now();
        final AccessTokenResponse response = client.target(nestUrls.getOauth2Url()).request(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(Entity.form(toFormBody(clientInformation)),
                AccessTokenResponse.class);
        // Be pessimistic about the expiration - better to get a new token a bit ahead of time rather than risk expiration
        final LocalDateTime expiration = startDateTime.plusSeconds(response.getLifeMillis() / 1000);
        return new SimpleAccessToken(response.getAccessToken(), expiration);
    }

    /**
     * Creates a form body for the given client information for a request to retrieve an access token.
     * 
     * @param clientInformation
     *            The {@link ClientInformation} to identify the client when retrieving the OAuth token.
     * @return A {@link Form} built out of the given client information to be used to request an access token.
     */
    private Form toFormBody(ClientInformation clientInformation) {
        final Form form = new Form();
        form.param("code", clientInformation.getCode());
        form.param("client_id", clientInformation.getClientId());
        form.param("client_secret", clientInformation.getClientSecret());
        form.param("grant_type", "authorization_code");
        return form;
    }

    /**
     * A bean representing the access token response expected from the Nest API.
     * 
     * @author jrh3k5
     */
    private static final class AccessTokenResponse {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("expires_in")
        private long lifeMillis;

        /**
         * Gets the access token returned by Nest.
         * 
         * @return The access token returned by Nest.
         */
        public String getAccessToken() {
            return accessToken;
        }

        /**
         * Gets the length of life of the token, expressed in milliseconds.
         * 
         * @return The length of life of the token, expressed in milliseconds.
         */
        public long getLifeMillis() {
            return lifeMillis;
        }
    }

    /**
     * A simple implementation of {@link AccessToken}.
     * 
     * @author jrh3k5
     */
    private static final class SimpleAccessToken implements AccessToken {
        private final String token;
        private final LocalDateTime expiration;

        /**
         * Creates an access token.
         * 
         * @param token
         *            The access token.
         * @param expiration
         *            A {@link LocalDateTime} expressing when the token will expire.
         */
        private SimpleAccessToken(String token, LocalDateTime expiration) {
            this.token = token;
            this.expiration = expiration;
        }

        @Override
        public String getToken() {
            return token;
        }

        @Override
        public LocalDateTime getExpiration() {
            return expiration;
        }
    }
}
