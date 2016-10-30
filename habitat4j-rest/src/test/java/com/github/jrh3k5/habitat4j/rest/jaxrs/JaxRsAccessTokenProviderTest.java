package com.github.jrh3k5.habitat4j.rest.jaxrs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.jrh3k5.habitat4j.rest.AccessToken;
import com.github.jrh3k5.habitat4j.rest.ClientInformation;
import com.github.jrh3k5.habitat4j.rest.NestUrls;

/**
 * Unit tests for {@link JaxRsAccessTokenProvider}.
 * 
 * @author jrh3k5
 */

@RunWith(MockitoJUnitRunner.class)
public class JaxRsAccessTokenProviderTest {
    private final String code = String.format("/code:%s", UUID.randomUUID());
    private final String clientId = String.format("/clientId:%s", UUID.randomUUID());
    private final String clientSecret = String.format("/clientSecret:%s", UUID.randomUUID());
    private final String oauth2Url = String.format("/oauth2Url:%s", UUID.randomUUID());
    @Mock
    private Client client;
    @Mock
    private NestUrls nestUrls;
    @Mock
    private ClientInformation clientInformation;
    private JaxRsAccessTokenProvider tokenProvider;

    /**
     * Sets up the test environment for each test.
     */
    @Before
    public void setUp() {
        when(nestUrls.getOauth2Url()).thenReturn(oauth2Url);
        when(clientInformation.getCode()).thenReturn(code);
        when(clientInformation.getClientId()).thenReturn(clientId);
        when(clientInformation.getClientSecret()).thenReturn(clientSecret);

        tokenProvider = new JaxRsAccessTokenProvider(client, nestUrls, clientInformation);
    }

    /**
     * Test the retrieval of the access token.
     * 
     * @throws Exception
     *             If any errors occur during the test run.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetAccessToken() throws Exception {
        final LocalDateTime startDateTime = LocalDateTime.now();
        final String accessTokenValue = String.format("/accessToken:%s", UUID.randomUUID());
        final long lifeMillis = 100000;

        final WebTarget webTarget = mock(WebTarget.class);
        when(client.target(oauth2Url)).thenReturn(webTarget);
        final Invocation.Builder requestBuilder = mock(Invocation.Builder.class);
        when(webTarget.request(MediaType.APPLICATION_FORM_URLENCODED_TYPE)).thenReturn(requestBuilder);
        
        @SuppressWarnings("rawtypes")
        final ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);
        when(requestBuilder.post(entityCaptor.capture(), any(Class.class))).thenAnswer((InvocationOnMock invocation) -> {
            // The class is, itself, hidden from the test, so use reflections to create an instance of it
            final Class<?> objectClass = invocation.getArgument(1);
            final Constructor<?> objectConstructor = objectClass.getDeclaredConstructor();
            objectConstructor.setAccessible(true);
            final Object newInstance = objectConstructor.newInstance();
            // Inject the values
            final Field accessTokenField = objectClass.getDeclaredField("accessToken");
            accessTokenField.setAccessible(true);
            accessTokenField.set(newInstance, accessTokenValue);
            final Field lifeMillisField = objectClass.getDeclaredField("lifeMillis");
            lifeMillisField.setAccessible(true);
            lifeMillisField.set(newInstance, lifeMillis);

            return newInstance;
        });

        final AccessToken accessToken = tokenProvider.getAccessToken();
        
        final LocalDateTime endDateTime = LocalDateTime.now();
        
        assertThat(accessToken.getToken()).isEqualTo(accessTokenValue);
        // Can't assert startDateTime + lifeMillis / 1000 because of the possibility of second rollover during the testing
        assertThat(accessToken.getExpiration()).isAfter(startDateTime).isBefore(endDateTime.plusSeconds(lifeMillis / 1000));
    }
}
