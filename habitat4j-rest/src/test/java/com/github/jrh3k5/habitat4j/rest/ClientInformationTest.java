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

import java.util.UUID;

import org.junit.Test;

/**
 * Unit tests for {@link ClientInformation}.
 * 
 * @author jrh3k5
 */

public class ClientInformationTest {
    private final String clientId = String.format("/clientId:%s", UUID.randomUUID());
    private final String clientSecret = String.format("/clientSecret:%s", UUID.randomUUID());
    private final String code = String.format("/code:%s", UUID.randomUUID());
    private final ClientInformation information = new ClientInformation(code, clientId, clientSecret);

    /**
     * Tests the retrieval of the client ID.
     */
    @Test
    public void testGetClientId() {
        assertThat(information.getClientId()).isEqualTo(clientId);
    }

    /**
     * Tests the retrieval of the client secret.
     */
    @Test
    public void testGetClientSecret() {
        assertThat(information.getClientSecret()).isEqualTo(clientSecret);
    }

    /**
     * Tests the retrieval of the code.
     */
    @Test
    public void testGetCode() {
        assertThat(information.getCode()).isEqualTo(code);
    }
}
