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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A data object representing the JSON response from the Nest API.
 * 
 * @author jrh3k5
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class JsonResponse {
    @JsonProperty("devices")
    private JsonDevices devices = new JsonDevices();

    /**
     * Gets the devices exposed via the JSON API.
     * 
     * @return A {@link JsonDevices} object representing the list of devices.
     */
    JsonDevices getDevices() {
        return devices;
    }
}