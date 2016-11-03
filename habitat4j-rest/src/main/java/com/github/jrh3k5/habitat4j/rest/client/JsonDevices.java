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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A data object representing the {@code devices} response from the Nest API.
 * 
 * @author jrh3k5
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class JsonDevices {
    @JsonProperty("thermostats")
    private final Map<String, JsonThermostat> thermostats = new HashMap<String, JsonThermostat>();

    /**
     * Gets a list of thermostats.
     * 
     * @return A {@link List} of {@link JsonThermostat} objects representing the known thermostats.
     */
    List<JsonThermostat> getThermostats() {
        return Collections.unmodifiableList(new ArrayList<>(thermostats.values()));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
