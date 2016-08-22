/*
 * Knot.x - Reactive microservice assembler
 *
 * Copyright (C) 2016 Cognifide Limited
 *
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
 */
package com.cognifide.knotx.util;

import com.google.gson.Gson;

import com.cognifide.knotx.api.RepositoryRequest;

import io.netty.util.CharsetUtil;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class RepositoryRequestCodec implements MessageCodec<RepositoryRequest, RepositoryRequest> {

    private static final int CONTENT_START_IDX = 4;
    private static final byte SUCCESS_FLAG = 1;
    private static final byte ERROR_FLAG = 0;

    @Override
    public void encodeToWire(Buffer buffer, RepositoryRequest object) {
        byte[] encoded = new Gson().toJson(object.getHeaders()).getBytes(CharsetUtil.UTF_8);
        buffer.appendInt(encoded.length);
        Buffer buff = Buffer.buffer(encoded);
        buffer.appendBuffer(buff);
    }

    @Override
    public RepositoryRequest decodeFromWire(int pos, Buffer buffer) {
        int length = buffer.getInt(pos);
        pos += 4;
        byte[] encoded = buffer.getBytes(pos, pos + length);
        String str = new String(encoded, CharsetUtil.UTF_8);

        return new Gson().fromJson(str, RepositoryRequest.class);
    }

    @Override
    public RepositoryRequest transform(RepositoryRequest message) {
        return message;
    }

    @Override
    public String name() {
        return RepositoryRequest.class.getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
