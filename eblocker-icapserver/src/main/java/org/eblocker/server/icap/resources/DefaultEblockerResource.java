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
package org.eblocker.server.icap.resources;

import java.nio.charset.Charset;

public enum DefaultEblockerResource implements EblockerResource {

    //
    // Static resources, which should normally change only by on update of the software
    //
    ONE_PIXEL_SVG("classpath:block-artefacts/1px.svg"),
    ONE_PIXEL_PNG("classpath:block-artefacts/1px.png"),
    MAC_PREFIXES("classpath:mac-prefixes.txt"),
    MAC_PREFIXES_DISABLED_BY_DEFAULT("classpath:mac-prefixes-disabled-by-default.txt"),
    ;

    private String path;

    private Charset charset = Charset.forName("UTF-8");

    private DefaultEblockerResource(String path) {
        this.path = path;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    @Override
    public String toString() {
        return "DefaultEblockerResource{path='" + path + "'}";
    }
}
