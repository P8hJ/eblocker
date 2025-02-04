/*
 * Copyright 2020 eBlocker Open Source UG (haftungsbeschraenkt)
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be
 * approved by the European Commission - subsequent versions of the EUPL
 * (the "License"); You may not use this work except in compliance with
 * the License. You may obtain a copy of the License at:
 *
 *   https://joinup.ec.europa.eu/page/eupl-text-11-12
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.eblocker.server.common.blacklist;

import org.eblocker.server.common.collections.ConcurrentFixedSizeCache;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CachingFilter implements DomainFilter<String> {

    @Nonnull
    private final CacheMode cacheMode;
    @Nonnull
    private final Map<String, FilterDecision<String>> cache;
    @Nonnull
    private final DomainFilter<String> filter;

    CachingFilter(int size, @Nonnull CacheMode cacheMode, @Nonnull DomainFilter<String> filter) {
        this.cache = new ConcurrentFixedSizeCache<>(size, true);
        this.cacheMode = cacheMode;
        this.filter = filter;
    }

    @Nullable
    @Override
    public Integer getListId() {
        return filter.getListId();
    }

    @Nonnull
    @Override
    public String getName() {
        return "(cache " + filter.getName() + ")";
    }

    @Override
    public int getSize() {
        return filter.getSize();
    }

    @Nonnull
    @Override
    public Stream<String> getDomains() {
        return filter.getDomains();
    }

    @Nonnull
    @Override
    public FilterDecision<String> isBlocked(String domain) {
        FilterDecision<String> decision = cache.get(domain);
        if (decision == null) {
            decision = filter.isBlocked(domain);
            if (cacheMode == CacheMode.ALL
                    || !decision.isBlocked() && cacheMode == CacheMode.NON_BLOCKED
                    || decision.isBlocked() && cacheMode == CacheMode.BLOCKED) {
                cache.put(domain, decision);
            }
        }

        return decision;
    }

    @Nonnull
    @Override
    public List<DomainFilter<?>> getChildFilters() {
        return Collections.singletonList(filter);
    }

    public enum CacheMode {NON_BLOCKED, BLOCKED, ALL}
}
