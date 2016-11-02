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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.mutable.MutableObject;

/**
 * An {@link AccessTokenProvider} that caches its tokens.
 * 
 * @author jrh3k5
 */

public class CachingAccessTokenProvider implements AccessTokenProvider {
    private static final long EXPIRATION_WINDOW = TimeUnit.MINUTES.toSeconds(1);
    private final MutableObject<AccessToken> accessTokenStore = new MutableObject<AccessToken>();
    private final ReadWriteLock storeLock = new ReentrantReadWriteLock();
    private final AccessTokenProvider sourceProvider;

    /**
     * Creates a caching access token provider.
     * 
     * @param sourceProvider
     *            An {@link AccessTokenProvider} used to provision access tokens.
     */
    public CachingAccessTokenProvider(AccessTokenProvider sourceProvider) {
        this.sourceProvider = Objects.requireNonNull(sourceProvider, "Source token provider cannot be null.");
    }

    @Override
    public AccessToken getAccessToken() {
        final Optional<AccessToken> currentAccessToken = getCurrentAccessToken();
        if (!currentAccessToken.isPresent() || needsRefresh(currentAccessToken.get())) {
            return getNewAccessToken();
        }

        return currentAccessToken.get();
    }

    /**
     * Gets the current, if any, cached access token.
     * 
     * @return An {@link Optional} containing the access token that may be cached; this will be empty if no access token is currently cached.
     */
    private Optional<AccessToken> getCurrentAccessToken() {
        final Lock readLock = storeLock.readLock();
        readLock.lock();
        try {
            return Optional.ofNullable(accessTokenStore.getValue());
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Gets a new access token.
     * 
     * @return An {@link AccessToken} representing the new access token.
     */
    private AccessToken getNewAccessToken() {
        final Lock writeLock = storeLock.writeLock();
        writeLock.lock();
        try {
            final AccessToken currentAccessToken = accessTokenStore.getValue();
            if (currentAccessToken != null && !needsRefresh(currentAccessToken)) {
                return currentAccessToken;
            }
            
            final AccessToken newAccessToken = sourceProvider.getAccessToken();
            accessTokenStore.setValue(newAccessToken);
            return newAccessToken;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Determines whether or not the given access token is in need of being refreshed and evicted from the cache.
     * 
     * @param accessToken
     *            The {@link AccessToken} to be evaluated for eviction from the cache.
     * @return {@code true} if the given access token requires a refresh; {@code false} if not.
     */
    private boolean needsRefresh(AccessToken accessToken) {
        final long expirationDiff = accessToken.getExpiration().toEpochSecond(ZoneOffset.UTC) - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        return expirationDiff <= EXPIRATION_WINDOW;
    }
}
