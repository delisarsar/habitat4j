package com.github.jrh3k5.habitat4j.client;

import java.util.List;

/*-
 * #%L
 * Nest Client API
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
 * Definition of a client used to interact with Nest.
 * 
 * @author jrh3k5
 */

public interface Client {
    /**
     * Gets the thermostats.
     * 
     * @return A {@link List} of {@link Thermostat} objects representing all thermostats exposed by Nest to this client.
     */
    List<? extends Thermostat> getThermostats();
}
