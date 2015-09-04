package org.zalando.problem.springweb.advice;

/*
 * #%L
 * problem-handling
 * %%
 * Copyright (C) 2015 Zalando SE
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


import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_ATOM_XML;
import static org.springframework.http.MediaType.APPLICATION_ATOM_XML_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MediaTypeNotSupportedIT extends AdviceIT {

    @Override
    protected Object advice() {
        return new MediaTypeNotSupported() {
        };
    }

    @Test
    public void unsupportedMediaType() throws Exception {
        mvc.perform(request(PUT, URI_HANDLER_PUT)
                .contentType(APPLICATION_ATOM_XML))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(header().string("Accept", containsString(APPLICATION_JSON_VALUE)))
                .andExpect(jsonPath("$.title", is(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())))
                .andExpect(jsonPath("$.status", is(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())))
                .andExpect(jsonPath("$.detail", containsString(APPLICATION_ATOM_XML_VALUE)));
    }

}