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
 * Definition of a provider of access tokens.
 * 
 * @author jrh3k5
 */

public interface AccessTokenProvider {
    /**
     * Gets the access token.
     * 
     * @return A {@link AccessToken} representing a token to make an authorized call into the Nest service.
     */
    AccessToken getAccessToken();
}
