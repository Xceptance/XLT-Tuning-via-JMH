package com.xceptance.common.parsenumbers.tuned13_vectored;

import org.junit.Assert;
import org.junit.Test;

import com.xceptance.common.util.XltCharBuffer;

/**
 * Test for parsing longs and ints.
 * 
 * @author René Schwietzke (Xceptance Software Technologies GmbH)
 */
public class ParseNumbersTest
{
    /**
     * Test method for {@link com.xceptance.common.ParseNumbers.FastParseNumbers#fastParseLong(java.lang.String)}.
     */
    @Test
    public final void testParseLong()
    {
        var longs = new String[]{"0", "1", "12", "123", "1234", "12345", "123456", "1234567", "12345678", "987654321", String.valueOf(System.currentTimeMillis())};
        
        for (var l : longs)
        {
            Assert.assertEquals(Long.valueOf(l).longValue(), ParseNumbers.parseLong(XltCharBuffer.valueOf(l)));
        }
    }
}
