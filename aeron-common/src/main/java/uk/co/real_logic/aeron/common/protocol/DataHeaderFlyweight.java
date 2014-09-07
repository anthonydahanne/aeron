/*
 * Copyright 2014 Real Logic Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.real_logic.aeron.common.protocol;

import uk.co.real_logic.aeron.common.concurrent.AtomicBuffer;

import java.nio.ByteOrder;

import static java.nio.ByteOrder.LITTLE_ENDIAN;

/**
 * HeaderFlyweight for Data Header
 * <p>
 * <a href="https://github.com/real-logic/Aeron/wiki/Protocol-Specification#data-frame">Data Frame</a>
 */
public class DataHeaderFlyweight extends HeaderFlyweight
{
    /** Size of the Data Header */
    public static final int HEADER_LENGTH = 24;

    /** Begin Flag */
    public static final short BEGIN_FLAG = 0x80;

    /** End Flag */
    public static final short END_FLAG = 0x40;

    /** Begin and End Flags */
    public static final short BEGIN_AND_END_FLAGS = BEGIN_FLAG | END_FLAG;

    /** Default header for a Data Frame (for ease of use with LogAppender) */
    public static final byte[] DEFAULT_HEADER_NULL_IDS =
        {
            HeaderFlyweight.CURRENT_VERSION, 0, HeaderFlyweight.HDR_TYPE_DATA, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
        };

    private static final int TERM_OFFSET_FIELD_OFFSET = 8;
    private static final int SESSION_ID_FIELD_OFFSET = 12;
    private static final int STREAM_ID_FIELD_OFFSET = 16;
    private static final int TERM_ID_FIELD_OFFSET = 20;
    private static final int DATA_OFFSET = 24;

    /**
     * return session id field
     * @return session id field
     */
    public int sessionId()
    {
        return atomicBuffer().getInt(offset() + SESSION_ID_FIELD_OFFSET, LITTLE_ENDIAN);
    }

    /**
     * set session id field
     * @param sessionId field value
     * @return flyweight
     */
    public DataHeaderFlyweight sessionId(final int sessionId)
    {
        atomicBuffer().putInt(offset() + SESSION_ID_FIELD_OFFSET, sessionId, LITTLE_ENDIAN);

        return this;
    }

    /**
     * return stream id field
     *
     * @return stream id field
     */
    public int streamId()
    {
        return atomicBuffer().getInt(offset() + STREAM_ID_FIELD_OFFSET, LITTLE_ENDIAN);
    }

    /**
     * set stream id field
     *
     * @param streamId field value
     * @return flyweight
     */
    public DataHeaderFlyweight streamId(final int streamId)
    {
        atomicBuffer().putInt(offset() + STREAM_ID_FIELD_OFFSET, streamId, LITTLE_ENDIAN);

        return this;
    }

    /**
     * return term id field
     *
     * @return term id field
     */
    public int termId()
    {
        return atomicBuffer().getInt(offset() + TERM_ID_FIELD_OFFSET, LITTLE_ENDIAN);
    }

    /**
     * set term id field
     *
     * @param termId field value
     * @return flyweight
     */
    public DataHeaderFlyweight termId(final int termId)
    {
        atomicBuffer().putInt(offset() + TERM_ID_FIELD_OFFSET, termId, LITTLE_ENDIAN);

        return this;
    }

    /**
     * return term offset field
     *
     * @return term offset field
     */
    public int termOffset()
    {
        return atomicBuffer().getInt(offset() + TERM_OFFSET_FIELD_OFFSET, LITTLE_ENDIAN);
    }

    /**
     * set term offset field
     *
     * @param termOffset field value
     * @return flyweight
     */
    public DataHeaderFlyweight termOffset(final int termOffset)
    {
        atomicBuffer().putInt(offset() + TERM_OFFSET_FIELD_OFFSET, termOffset, LITTLE_ENDIAN);

        return this;
    }

    /**
     * Return offset in buffer for data
     *
     * @return offset of data in the buffer
     */
    public int dataOffset()
    {
        return offset() + DATA_OFFSET;
    }

    /**
     * Return a customized default Data Frame Header suitable for use with
     * {@link uk.co.real_logic.aeron.common.concurrent.logbuffer.LogAppender}
     *
     * @param sessionId for the header
     * @param streamId for the header
     * @param termId for the header
     * @return byte array containing the header
     */
    public static byte[] createDefaultHeader(final int sessionId, final int streamId, final int termId)
    {
        final byte[] hdr = new byte[HEADER_LENGTH];
        final AtomicBuffer buffer = new AtomicBuffer(hdr);

        buffer.wrap(hdr);
        buffer.putBytes(0, DEFAULT_HEADER_NULL_IDS);
        buffer.putInt(SESSION_ID_FIELD_OFFSET, sessionId, ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(STREAM_ID_FIELD_OFFSET, streamId, ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(TERM_ID_FIELD_OFFSET, termId, ByteOrder.LITTLE_ENDIAN);

        return hdr;
    }
}
