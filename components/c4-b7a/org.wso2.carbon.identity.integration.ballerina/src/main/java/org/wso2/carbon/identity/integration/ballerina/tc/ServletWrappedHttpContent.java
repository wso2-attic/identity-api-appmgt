/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.integration.ballerina.tc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpContent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * Maps netty Http to Servlet world. Used in msf4j Servlet Bridge.
 */
public class ServletWrappedHttpContent implements HttpContent {

    private Log log = LogFactory.getLog(ServletWrappedHttpContent.class);
    private HttpServletRequest httpServletRequest;
    private InputStream inputStream;

    public ServletWrappedHttpContent(HttpServletRequest httpServletRequest) {

        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public ByteBuf content() {

        if (inputStream == null) {
            try {
                inputStream = httpServletRequest.getInputStream();
            } catch (IOException e) {
                log.error("An error occurred while trying to access input stream.");
            }
        }
        if (inputStream != null) {
            byte[] byteArray = new byte[128];
            try {
                int len = inputStream.read(byteArray);
                if (len > 0) {
                    return Unpooled.wrappedBuffer(byteArray, 0, len);
                }
            } catch (IOException e) {
                log.error("An error occurred while trying to read from stream.");
            }
        }
        return Unpooled.EMPTY_BUFFER;
    }

    @Override
    public HttpContent copy() {

        return null;
    }

    @Override
    public HttpContent duplicate() {

        return null;
    }

    @Override
    public HttpContent retainedDuplicate() {

        return null;
    }

    @Override
    public HttpContent replace(ByteBuf byteBuf) {

        return null;
    }

    @Override
    public int refCnt() {

        return 0;
    }

    @Override
    public HttpContent retain() {

        return null;
    }

    @Override
    public HttpContent retain(int i) {

        return null;
    }

    @Override
    public HttpContent touch() {

        return null;
    }

    @Override
    public HttpContent touch(Object o) {

        return null;
    }

    @Override
    public boolean release() {

        return false;
    }

    @Override
    public boolean release(int i) {

        return false;
    }

    @Override
    public DecoderResult getDecoderResult() {

        return null;
    }

    @Override
    public DecoderResult decoderResult() {

        return null;
    }

    @Override
    public void setDecoderResult(DecoderResult decoderResult) {

    }
}
