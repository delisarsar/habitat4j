package com.github.jrh3k5.habitat4j.rest;

import java.util.Objects;

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

/**
 * Definition of the credentials needed to communicate with Nest.
 * 
 * @author jrh3k5
 */

public class ClientInformation {
    private final String code;
    private final String clientId;
    private final String clientSecret;

    /**
     * Creates a client information object.
     * 
     * @param code
     *            The code used to identify the authorization granted to this client.
     * @param clientId
     *            The ID of the client.
     * @param clientSecret
     *            The secret used to verify the identity of this client.
     */
    public ClientInformation(String code, String clientId, String clientSecret) {
        this.code = Objects.requireNonNull(code, "Code cannot be null");
        this.clientId = Objects.requireNonNull(clientId, "Client ID cannot be null");
        this.clientSecret = Objects.requireNonNull(clientSecret, "Client secret cannot be null");
    }

    /**
     * Gets the ID of the client.
     * 
     * @return The ID of the client.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Gets the secret used to verify the identity of this client.
     * 
     * @return The secret used to verify the identity of this client.
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Gets the code used to identify the authorization granted to this client.
     * 
     * @return The code used to identify the authorization granted to this client.
     */
    public String getCode() {
        return code;
    }
}