package com.xceptance.common.parsenumbers.tuned14;

import org.junit.Assert;
import org.junit.Test;

import com.xceptance.common.util.XltCharBuffer;

/**
 * Test for parsing longs and ints.
 *
 * @author René Schwietzke (Xceptance Software Technologies GmbH)
 */
public class ParseNumbersTest3
{
    /**
     * Test method for {@link com.xceptance.common.ParseNumbers.FastParseNumbersIfDigit#fastparseLong3(java.lang.String)}.
     */
    @Test
    public final void testparseLong3()
    {
        {
            final String s = "1670036109465868";
            Assert.assertEquals((long) Long.valueOf(s), ParseNumbers.parseLong3(XltCharBuffer.valueOf(s)));
        }
        {
            final String s = "0";
            Assert.assertEquals((long) Long.valueOf(s), ParseNumbers.parseLong3(XltCharBuffer.valueOf(s)));
        }
        {
            final String s = "1670036";
            Assert.assertEquals((long) Long.valueOf(s), ParseNumbers.parseLong3(XltCharBuffer.valueOf(s)));
        }
    }

    /**
     * Test method for {@link com.xceptance.common.ParseNumbers.FastParseNumbersIfDigit#fastparseLong3(java.lang.String)}.
     */
    @Test
    public final void testparseLong3Fallback()
    {
        {
            final String s = "-1670036109465868";
            Assert.assertEquals((long) Long.valueOf(s), ParseNumbers.parseLong3(XltCharBuffer.valueOf(s)));
        }
        {
            final String s = "-0";
            Assert.assertEquals((long) Long.valueOf(s), ParseNumbers.parseLong3(XltCharBuffer.valueOf(s)));
        }
        {
            final String s = "-1670036";
            Assert.assertEquals((long) Long.valueOf(s), ParseNumbers.parseLong3(XltCharBuffer.valueOf(s)));
        }
    }

    /**
     * Test method for {@link com.xceptance.common.ParseNumbers.FastParseNumbersIfDigit#fastParseInt(java.lang.String)}.
     */
    @Test(expected = NumberFormatException.class)
    public final void testNumberFormatExceptionLong_Empty()
    {
        final String s = "";
        ParseNumbers.parseLong3(XltCharBuffer.valueOf(s));
    }

    /**
     * Test method for {@link com.xceptance.common.ParseNumbers.FastParseNumbersIfDigit#fastParseInt(java.lang.String)}.
     */
    @Test(expected = NumberFormatException.class)
    public final void testNumberFormatExceptionLong_Space()
    {
        final String s = " ";
        ParseNumbers.parseLong3(XltCharBuffer.valueOf(s));
    }

    /**
     * Test method for {@link com.xceptance.common.ParseNumbers.FastParseNumbersIfDigit#fastParseInt(java.lang.String)}.
     */
    @Test(expected = NumberFormatException.class)
    public final void testNumberFormatExceptionLong_WrongCharacter()
    {
        final String s = "aaa";
        ParseNumbers.parseLong3(XltCharBuffer.valueOf(s));
    }
}
