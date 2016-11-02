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

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jrh3k5.habitat4j.client.TemperatureUnit;
import com.github.jrh3k5.habitat4j.client.Thermostat;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonThermostat implements Thermostat {
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("ambient_temperature_f")
    private float ambientTemperatureFarenheit;
    @JsonProperty("ambient_temperature_c")
    private float ambientTemperatureCelsius;

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public Temperatures getTemperatureInformation(TemperatureUnit temperatureUnit) {
        switch(temperatureUnit) {
        case CELSIUS:
            return new SimpleTemperatures(temperatureUnit, ambientTemperatureCelsius);
        case FARENHEIT:
            return new SimpleTemperatures(temperatureUnit, ambientTemperatureFarenheit);
        default:
            throw new IllegalArgumentException("Unexpected temperature unit: " + temperatureUnit);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    private static final class SimpleTemperatures implements Thermostat.Temperatures {
        private final TemperatureUnit temperatureUnit;
        private final float ambientTemperature;

        private SimpleTemperatures(TemperatureUnit temperatureUnit, float ambientTemperature) {
            this.temperatureUnit = Objects.requireNonNull(temperatureUnit);
            this.ambientTemperature = ambientTemperature;
        }

        @Override
        public float getAmbientTemperature() {
            return ambientTemperature;
        }

        @Override
        public TemperatureUnit getTemperatureUnit() {
            return temperatureUnit;
        }
    }
}
