package com.github.jrh3k5.habitat4j.client;

/*-
 * #%L
 * Habitat4j API
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
 * Definition of a thermostat as exposed by Nest.
 * 
 * @author jrh3k5
 */

public interface Thermostat {
    /**
     * Gets the ID of the device.
     * 
     * @return The ID of the device.
     */
    String getDeviceId();

    /**
     * Gets the temperature information of the thermostat.
     * 
     * @param temperatureUnit
     *            The {@link TemperatureUnit} for which the returned temperatures are to be measured.
     * @return A {@link Temperatures} object containing the desired temperature information.
     */
    Temperatures getTemperatureInformation(TemperatureUnit temperatureUnit);

    /**
     * Definition of temperature information available for a thermostat.
     * 
     * @author jrh3k5
     */
    interface Temperatures {
        /**
         * Gets the ambient temperature.
         * 
         * @return The ambient temperature.
         */
        float getAmbientTemperature();

        /**
         * Gets the temperature unit in which all temperatures expressed by this object are measured.
         * 
         * @return The temperature unit in which all temperatures expressed by this object are measured.
         */
        TemperatureUnit getTemperatureUnit();
    }
}
