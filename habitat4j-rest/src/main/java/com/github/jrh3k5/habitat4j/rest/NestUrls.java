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

/**
 * A helper class used to provision URLs for known Nest resources.
 * 
 * @author jrh3k5
 */

public class NestUrls {
    private static final String DEFAULT_OAUTH2_URL = "https://api.home.nest.com/oauth2/access_token";
    private static final String DEFAULT_API_URL = "https://developer-api.nest.com";

    /**
     * Gets the URL of the Nest API.
     * 
     * @return The URL of the Nest API.
     */
    public String getApiUrl() {
        return DEFAULT_API_URL;
    }

    /**
     * Gets the URL used to retrieve OAuth 2.0 access tokens.
     * 
     * @return The URL used to retrieve OAuth 2.0 access tokens.
     */
    public String getOauth2Url() {
        return DEFAULT_OAUTH2_URL;
    }
}
