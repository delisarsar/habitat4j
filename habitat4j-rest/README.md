# Habitat4j REST Client

This is an implementation of the habitat4j API that consumes the REST services exposed by Nest.

Refer to the [Nest overview documentation](https://developers.nest.com/documentation/cloud/rest-quick-guide/) for some guidance on how the Nest REST API works.

Once you have [created a product](https://developers.nest.com/documentation/cloud/rest-quick-guide/#create-a-product) to get your OAuth client ID and secret and then have [obtained your authorization code](https://developers.nest.com/documentation/cloud/rest-quick-guide/#get-an-authorization-code), you can assemble your client code thusly:

```java
package app.example;

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

public class MyApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyApp.class);

    public static void run() throws Exception {
        final NestUrls nestUrls = new NestUrls();

        // You will have to figure out how the client information provider is provisioned
        final ClientInformationProvider clientInformationProvider = getClientInformationProvider();
        final javax.ws.rs.client.Client jaxRsClient = ClientBuilder.newClient();
        try {
            jaxRsClient.register(JacksonJsonProvider.class);
            final AccessTokenProvider accessTokenProvider = new CachingAccessTokenProvider(new JaxRsAccessTokenProvider(jaxRsClient, nestUrls, clientInformationProvider);
            final Client client = new RestClient(jaxRsClient, accessTokenProvider, nestUrls);
            LOGGER.info("The following thermostat information was returned: {}", client.getThermostats());
        } finally {
            jaxRsClient.close();
        }
    }
}
```

This will print out something like the following:

```
[main] INFO app.example.MyApp - The following thermostat information was returned: [com.github.jrh3k5.habitat4j.rest.client.JsonThermostat@6dd7b5a3[deviceId=Zjf8923k-dskfs_wc23,ambientTemperatureFarenheit=79.0,ambientTemperatureCelsius=25.5]]
```