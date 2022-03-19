/*
 * Copyright (c) 2005-2022 Xceptance Software Technologies GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xceptance.common.hashcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.xceptance.common.lang.OpenStringBuilder;

/**
 * This class does not implement the CharBuffer of the JDK, but uses the idea of a shared
 * character array with views. This is also a very unsafe implementation with as little
 * as possible boundary checks to achieve the maximum speed possible. To enhance use, we
 * implement CharSequence and hence can also do regex with it now. It also features common
 * string and striingbuffer methods to make it versatile and avoid the typical overhead 
 * when doing conversions back and forth.
 * 
 * @author rschwietzke
 * @since 7.0
 */
public class CharBufferHashCode implements CharSequence, Comparable<CharBufferHashCode>
{
    /**
     * Empty array
     */
    private static final char[] EMPTY_ARRAY = new char[0];

    /**
     * An empty static XltCharBuffer
     */
    public static final CharBufferHashCode EMPTY = new CharBufferHashCode(EMPTY_ARRAY);

    /**
     * The internal buffer, it is shared!
     */
    private final char[] src;

    /**
     * Because we are here dealing with the view of an array, we need a start
     * and a length.
     */
    private final int from;

    /**
     * The length of the view of the buffer
     */
    private final int length;

    /**
     * The hashcode. It is cached to avoid running the same operation again and 
     * again. The hashcode is identical to a hashcode of a String with the same 
     * content.
     */
    private int hashCode;

    /**
     * New buffer from a raw char array
     * 
     * @param src a char array
     */
    public CharBufferHashCode(final char[] src)
    {
        this.src = src == null ? EMPTY_ARRAY : src;
        this.from = 0;
        this.length = this.src.length;
    }

    /**
     * A new buffer from an open string builder, so we can directly 
     * use its buffer and don't have to copy. This is highly unsafe, so 
     * make sure you know what you are doing!
     * 
     * @param src an open string builder
     */
    public CharBufferHashCode(final OpenStringBuilder src)
    {
        this(src.getCharArray(), 0, src.length());
    }

    /**
     * A new buffer from a char array including a view port.
     * 
     * @param src the char array, if is is null, we fix that silently
     * @param from from where to deliver the buffer
     * @param length how long should the buffer be
     */
    public CharBufferHashCode(final char[] src, final int from, final int length)
    {
        if (src != null)
        {
            this.src = src;
            this.from = from;
            this.length = length;
        }
        else
        {
            this.src = EMPTY_ARRAY;
            this.from = 0;
            this.length = 0;
        }
    }

    /**
     * Just returns an empty buffer. This is a static object and not
     * a new buffer every time, so apply caution.
     * 
     * @return the empty buffer
     */
    public static CharBufferHashCode empty()
    {
        return EMPTY;
    }

    /**
     * Return the character at a position. This code does not run any 
     * checks in regards to pos being correct (>= 0, < length). This will
     * automatically apply the view on the underlying array hence incorrect
     * pos values might return something unexpected. So know what you do or else...
     * 
     * @param pos the position to return
     * @return the character at this position.
     */
    public char charAt(final int pos)
    {
        return src[from + pos];
    }

    /**
     * Set a character at this position. Similarly to charAt, this does not 
     * check for correctness of pos in favor of speed.
     * 
     * @param pos the pos to write to 
     * @param c the character to set
     * @return this instance so put can be chained
     */
    public CharBufferHashCode put(final int pos, final char c)
    {
        src[from + pos] = c;

        return this;
    }

    /**
     * Splits up this sequence into sub-sequences at splitChar markers 
     * excluding the marker
     * 
     * @param splitChar the split character
     * @return a list of the sub-sequences
     */
    public List<CharBufferHashCode> split(final char splitChar)
    {
        final List<CharBufferHashCode> result = new ArrayList<>();

        int last = -1;
        for (int i = 0; i < this.length; i++)
        {
            char c = this.charAt(i);
            if (c == splitChar)
            {
                result.add(this.substring(last + 1, i));
                last = i;
            }
        }

        last++;
        // in case there is either nothing done yet or
        // something left
        if (last == 0 || last < this.length)
        {
            result.add(this.substring(last, this.length));
        }
        else // if (last + 1 == this.length)
        {
            // the else if is not needed, this branch
            // only fires when the del is the last char
            result.add(CharBufferHashCode.empty());
        }

        return result;
    }

    /**
     * Replace a character by a character sequence in this charbuffer. This will
     * create a new char array backed charbuffer. 
     * 
     * @param c the character to search for 
     * @param s the charsequence to insert instead of the character
     * @return a new charbuffer with no references ot the old
     */
    public CharBufferHashCode replace(final char c, final CharSequence s)
    {
        final OpenStringBuilder result = new OpenStringBuilder(this.length() + s.length());

        for (int i = 0; i < this.length; i++)
        {
            final char cc = this.charAt(i);
            if (cc == c)
            {
                result.append(s);
            }
            else
            {
                result.append(cc);
            }
        }

        return CharBufferHashCode.valueOf(result);
    }

    /**
     * Looks ahead, otherwise returns 0. Only safety bound against ahead misses, not 
     * any behind misses
     * 
     * @param pos the position to look at
     * @return the content of the peaked pos or 0 if this position does not exist
     */
    public char peakAhead(final int pos)
    {
        return pos < length ? charAt(pos) : 0;
    }

    /**    
     * Returns a new buffer with a view on the current. No copy is made.
     * No runtime checks
     * 
     * @param from start position
     * @param length length of the view port
     * @return a new buffer
     */
    public CharBufferHashCode viewByLength(final int from, final int length)
    {
        return new CharBufferHashCode(this.src, this.from + from, length);
    }

    /**    
     * Returns a new buffer with a view on the current. No copy is made.
     * No runtime checks
     * 
     * @param from start position
     * @param to end position
     * @return a new buffer
     */
    public CharBufferHashCode viewFromTo(final int from, final int to)
    {
        return new CharBufferHashCode(this.src, this.from + from, to - from);
    }

    /**
     * Creates a new buffer similar to a String.substring call
     * @param from first position
     * @param to last position
     * @return
     */
    public CharBufferHashCode substring(final int from, final int to)
    {
        return viewFromTo(from, to);
    }

    /**
     * Creates a new buffer similar to a String.substring call from 
     * a position till the end
     * @param from first position
     * @return
     */    
    public CharBufferHashCode substring(final int from)
    {
        return viewByLength(from, this.length - from);
    }

    /**
     * Append a charbuffer to a stringbuilder. Internal helper.
     * 
     * @param target the target
     * @param src the source
     * @return the passed target for fluid syntax
     */
    private static OpenStringBuilder append(final OpenStringBuilder target, final CharBufferHashCode src)
    {
        target.append(src.src, src.from, src.length);

        return target;
    }

    /**
     * Creates a new char buffer by merging strings
     * 
     * @param s1 string 1
     * @param s2 string 2
     * @return the new charbuffer
     */
    public static CharBufferHashCode valueOf(final String s1, final String s2)
    {
        final OpenStringBuilder sb = new OpenStringBuilder(s1.length() + s2.length());
        sb.append(s1);
        sb.append(s2);

        return new CharBufferHashCode(sb.getCharArray(), 0, sb.length());
    }

    /**
     * Creates a new char buffer by merging XltCharBuffers
     * 
     * @param s1 buffer 1
     * @param s2 buffer 2
     * @return the new charbuffer
     */
    public static CharBufferHashCode valueOf(final CharBufferHashCode s1, final CharBufferHashCode s2)
    {
        final OpenStringBuilder sb = new OpenStringBuilder(s1.length() + s2.length());
        append(sb, s1);
        append(sb, s2);

        return new CharBufferHashCode(sb.getCharArray(), 0, sb.length());
    }

    /**
     * Creates a new char buffer by adding a single char
     * 
     * @param s1
     * @param c
     * @return
     */
    public static CharBufferHashCode valueOf(final CharBufferHashCode s1, final char c)
    {
        // our problem is that a String.toCharArray already creates a copy and we
        // than copy the copy into a new array, hence wasting one full array of 
        // s1 and s2

        // let's instead see if we can run with openstringbuilder nicely
        // more cpu in favour of less memory
        final OpenStringBuilder sb = new OpenStringBuilder(s1.length() + 1);
        append(sb, s1);
        sb.append(c);

        return new CharBufferHashCode(sb.getCharArray(), 0, sb.length());
    }

    /**
     * Creates a new char buffer by merging strings
     * 
     * @param s
     * @return
     */
    public static CharBufferHashCode valueOf(final String s1, final String s2, final String s3)
    {
        // our problem is that a String.toCharArray already creates a copy and we
        // than copy the copy into a new array, hence wasting one full array of 
        // s1 and s2

        // let's instead see if we can run with openstringbuilder nicely
        // more cpu in favour of less memory
        final OpenStringBuilder sb = new OpenStringBuilder(s1.length() + s2.length() + s3.length());
        sb.append(s1);
        sb.append(s2);
        sb.append(s3);

        return new CharBufferHashCode(sb.getCharArray(), 0, sb.length());
    }

    /**
     * Creates a new char buffer by merging strings
     * 
     * @param s
     * @return
     */
    public static CharBufferHashCode valueOf(final CharBufferHashCode s1, final CharBufferHashCode s2, final CharBufferHashCode s3)
    {
        // our problem is that a String.toCharArray already creates a copy and we
        // than copy the copy into a new array, hence wasting one full array of 
        // s1 and s2

        // let's instead see if we can run with openstringbuilder nicely
        // more cpu in favour of less memory
        final OpenStringBuilder sb = new OpenStringBuilder(s1.length() + s2.length() + s3.length());
        append(sb, s1);
        append(sb, s2);
        append(sb, s3);

        // getCharArray does not create a copy, hence OpenStringBuilder from now on should not be used anymore, because it would modify
        // the XltCharBuffer as well. Speed over luxury.
        return new CharBufferHashCode(sb.getCharArray(), 0, sb.length());
    }

    /**
     * Creates a new char buffer by merging strings
     * 
     * @param s1 string 1
     * @param s2 string 2
     * @param s3 string 3
     * @param more more strings
     * @return a new char buffer
     */
    public static CharBufferHashCode valueOf(final String s1, final String s2, final String s3, final String... more)
    {
        // shortcut 
        if (more == null || more.length == 0)
        {
            return valueOf(s1, s2, s3);
        }

        // new total size
        int newSize = s1.length() + s2.length() + s3.length();
        for (int i = 0; i < more.length; i++)
        {
            newSize += more[i].length();
        }

        final OpenStringBuilder sb = new OpenStringBuilder(newSize);
        sb.append(s1);
        sb.append(s2);
        sb.append(s3);

        for (int i = 0; i < more.length; i++)
        {
            sb.append(more[i]);
        }

        return new CharBufferHashCode(sb.getCharArray(), 0, sb.length());
    }

    /**
     * Create a new char buffer from a char array without copying it. It assume that the 
     * full array is valid and because we don't copy, we don't have immutability!
     * 
     * @param s the char array to use
     * @return a new charbuffer instance
     */
    public static CharBufferHashCode valueOf(final char[] s)
    {
        return new CharBufferHashCode(s);
    }

    /**
     * Create a new char buffer from a string. Because a string does provide array access, 
     * we use the returned copy by toCharArray to set up the char buffer.
     * 
     * @param s the string to use
     * @return a new charbuffer instance
     */
    public static CharBufferHashCode valueOf(final String s)
    {
        return new CharBufferHashCode(s.toCharArray());
    }

    /**
     * A new charbuffer from an open string builder. We don't copy the underlying array,
     * hence string builder and char buffer refer to the same underlying data!
     * 
     * @param s the builder to get the array from 
     * @return a new charbuffer instance
     */
    public static CharBufferHashCode valueOf(final OpenStringBuilder s)
    {
        return new CharBufferHashCode(s.getCharArray(), 0, s.length());
    }

    /**
     * Returns the length of this buffer
     */
    public int length()
    {
        return length;
    }

    /**
     * Assume we are not mutating... if we mutate, we have to reset the hashCode
     * 
     * @return the hashcode, similar to what a normal string would deliver
     */
    public int hashCodeV0()
    {
        final int last = length + from;

        int h = 0;
        int i0 = from;
        int i1 = from + 1;
        while (i1 < last) {
            h = h * (31 * 31) + src[i0] * 31 + src[i1];

            i0 = i0 + 2;
            i1 = i1 + 2;
        }
        if (i0 < last) {
            h = h * 31 + src[i0];
        }

        return h; 
    }

    public int hashCodeV0_for()
    {
        final int last = from + length;

        int h = 0;
        int i0 = from;
        int i1 = from + 1;
        for (; i1 < last; i0 = i0 + 2) 
        {
            h = h * (31 * 31) + src[i0] * 31 + src[i1];

            //i0 = i0 + 2;
            i1 = i1 + 2;
        }
        if (i0 < last) {
            h = h * 31 + src[i0];
        }

        return h; 
    }

    /**
     * Assume we are not mutating... if we mutate, we have to reset the hashCode
     * 
     * @return the hashcode, similar to what a normal string would deliver
     */
    public int hashCodeV1()
    {
        final int last = length + from;

        int h = 0;
        int i0 = from;
        int i1 = from + 1;
        int i2 = from + 2;
        while (i2 < last) {
            h = h * (31 * 31 * 31) + src[i0] * (31 * 31) + src[i1] * 31 + src[i2];

            i0 = i2 + 1;
            i1 = i0 + 1;
            i2 = i1 + 1;
        }
        if (i0 < last) {
            h = h * 31 + src[i0];
        }
        if (i1 < last) {
            h = h * 31 + src[i1];
        }

        return h; 
    }


    public int hashCodeV2()
    {
        final int last = length + from;

        int h = 0;
        int i0 = from;
        int i1 = from + 1;
        int i2 = from + 2;
        while (i2 < last) {
            h = h * (31 * 31 * 31) + src[i0] * (31 * 31) + src[i1] * 31 + src[i2];

            i0 = i2 + 1;
            i1 = i2 + 2;
            i2 = i2 + 3;
        }
        if (i0 < last) {
            h = h * 31 + src[i0];
        }
        if (i1 < last) {
            h = h * 31 + src[i1];
        }

        return h; 
    }


    public int hashCodeVectored()
    {
        final int last = length + from;

        int h = 0;
        int i0 = from;
        int i1 = from + 1;
        int i2 = from + 2;
        int i3 = from + 3;
        while (i3 < last) {
            h = h * (31 * 31 * 31 * 31) + 
                            src[i0] * (31 * 31 * 31) + 
                            src[i1] * (31 * 31) + 
                            src[i2] * (31) + 
                            src[i3];

            i0 = i3 + 1;
            i1 = i3 + 2;
            i2 = i3 + 3;
            i3 = i3 + 4;
        }
        if (i2 < last) {
            h = h * (31 * 31 * 31) + src[i0] * (31 * 31) + src[i1] * (31) + src[i2];
        }
        else if (i1 < last) {
            h = h * (31 * 31) + src[i0] * (31) + src[i1];
        }
        else if (i0 < last) {
            h = h * 31 + src[i0];
        }

        return h; 
    }

    public int hashCodeVectoredJDK19_v0()
    {
        int h = 0;
        int i = from;
        int l, l2;
        l = l2 = (length + from);
        l = l & ~(8 - 1);

        for (; i < l; i += 8) {
            h = -1807454463 * h +
                            1742810335 * src[i+0] +
                            887503681 * src[i+1] +
                            28629151 * src[i+2] +
                            923521 * src[i+3] +
                            29791 * src[i+4] +
                            961 * src[i+5] +
                            31 * src[i+6] +
                            1 * src[i+7];
        }

        for (; i < l2; i++) {
            h = 31 * h + src[i];
        }

        return h;        
    }

    public int hashCodeVectoredJDK19_v1()
    {
        int h = 0;
        int i = from;
        int l, l2;
        l = l2 = (length + from);
        l = l & ~(8 - 1);

        for (; i < l; i += 8) {
            h = -1807454463 * h;
            int h1 = 1742810335 * src[i+0] +
                            887503681 * src[i+1] +
                            28629151 * src[i+2] +
                            923521 * src[i+3] +
                            29791 * src[i+4] +
                            961 * src[i+5] +
                            31 * src[i+6] +
                            1 * src[i+7];

            h += h1;

        }

        for (; i < l2; i++) {
            h = (src[i] - h) + (h << 5);
        }

        return h;        
    }


    /**
     * Assume we are not mutating... if we mutate, we have to reset the hashCode
     * 
     * @return the hashcode, similar to what a normal string would deliver
     */
    public int hashCodeClassic()
    {
        final int last = length + from;

        int h = 0;
        for (int i = from; i < last; i++) 
        {
            h = 31 * h + src[i];
        }

        return h; 
    }

    /*
     * Returns a {@code CharSequence} that is a subsequence of this sequence.
     * The subsequence starts with the {@code char} value at the specified index and
     * ends with the {@code char} value at index {@code end - 1}.  The length
     * (in {@code char}s) of the
     * returned sequence is {@code end - start}, so if {@code start == end}
     * then an empty sequence is returned.
     *
     * @param   start   the start index, inclusive
     * @param   end     the end index, exclusive
     *
     * @return  the specified subsequence
     * */
    @Override
    public CharSequence subSequence(int start, int end)
    {
        return substring(start, end);
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
     * for all {@code x} and {@code y}.  (This
     * implies that {@code x.compareTo(y)} must throw an exception iff
     * {@code y.compareTo(x)} throws an exception.)
     * 
     * @param other the buffer to compare to
     * @retuen -1, if this is smaller than other, 0 if the same, 1 if this is larger
     */
    @Override
    public int compareTo(CharBufferHashCode other)
    {
        return Arrays.compare(this.src, from, from + length, 
                        other.src, other.from, other.from + other.length);
    }

    public String toDebugString()
    {
        return String.format("Base=%s\nCurrent=%s\nfrom=%d, length=%d", new String(src), this, from, length);
    }
}
