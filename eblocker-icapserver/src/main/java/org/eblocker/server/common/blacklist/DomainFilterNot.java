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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class DomainFilterNot<T> implements DomainFilter<T> {

    @Nonnull
    private final DomainFilter<T> filter;

    DomainFilterNot(@Nonnull DomainFilter<T> filter) {
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
        return "(not " + filter.getName() + ")";
    }

    @Override
    public int getSize() {
        return filter.getSize();
    }

    @Nonnull
    @Override
    public Stream<T> getDomains() {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public FilterDecision<T> isBlocked(T domain) {
        FilterDecision<T> decision = filter.isBlocked(domain);
        return new FilterDecision<>(domain, !decision.isBlocked(), decision.getFilter());
    }

    @Nonnull
    @Override
    public List<DomainFilter<?>> getChildFilters() {
        return Collections.singletonList(filter);
    }
}
