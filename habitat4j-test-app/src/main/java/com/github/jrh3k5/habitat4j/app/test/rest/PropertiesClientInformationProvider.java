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
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.github.jrh3k5.habitat4j.rest.ClientInformation;

public class PropertiesClientInformationProvider {
    public ClientInformation getClientInformation(File propertiesFile) throws IOException {
        try (final InputStream inputStream = FileUtils.openInputStream(propertiesFile)) {
            final Properties properties = new Properties();
            properties.load(inputStream);

            return new ClientInformation(properties.getProperty("client.code"), properties.getProperty("client.id"), properties.getProperty("client.secret"));
        }
    }
}
