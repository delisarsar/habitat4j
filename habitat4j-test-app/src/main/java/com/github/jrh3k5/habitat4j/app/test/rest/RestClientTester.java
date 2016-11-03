package com.github.jrh3k5.habitat4j.app.test.rest;

/*-
 * #%L
 * Habitat4j Test Application
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

import java.io.File;
import java.io.IOException;

import javax.ws.rs.client.ClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.github.jrh3k5.habitat4j.client.Client;
import com.github.jrh3k5.habitat4j.rest.AccessTokenProvider;
import com.github.jrh3k5.habitat4j.rest.CachingAccessTokenProvider;
import com.github.jrh3k5.habitat4j.rest.ClientInformation;
import com.github.jrh3k5.habitat4j.rest.NestUrls;
import com.github.jrh3k5.habitat4j.rest.client.RestClient;
import com.github.jrh3k5.habitat4j.rest.jaxrs.JaxRsAccessTokenProvider;

public class RestClientTester {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestClientTester.class);

    /**
     * Executes the application.
     * 
     * @param args
     *            The command-line arguments given to the application; this should be one argument that points to the location of the properties file.
     */
    public static void main(String[] args) {
        try {
            run(args[0]);
        } catch (IOException | RuntimeException e) {
            LOGGER.error("Failed to execute the access token provider test.", e);
        }
    }

    /**
     * Runs the application.
     * 
     * @param clientPropertiesLocation
     *            The location of the properties to be read to exercise the access token provider.
     * @throws IOException
     *             If any errors occur while reading the properties.
     */
    private static void run(String clientPropertiesLocation) throws IOException {
        final NestUrls nestUrls = new NestUrls();

        final ClientInformation clientInformation = new PropertiesClientInformationProvider().getClientInformation(new File(clientPropertiesLocation));
        final javax.ws.rs.client.Client jaxRsClient = ClientBuilder.newClient();
        try {
            jaxRsClient.register(JacksonJsonProvider.class);
            final AccessTokenProvider accessTokenProvider = new CachingAccessTokenProvider(new JaxRsAccessTokenProvider(jaxRsClient, nestUrls, () -> {
                return clientInformation;
            }));
            final Client client = new RestClient(jaxRsClient, accessTokenProvider, nestUrls);
            LOGGER.info("The following thermostat information was returned: {}", client.getThermostats());
        } finally {
            jaxRsClient.close();
        }
    }
}
