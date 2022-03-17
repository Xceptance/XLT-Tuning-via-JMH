package com.xceptance.common.hashcode;

import org.junit.Assert;
import org.junit.Test;

import com.xceptance.common.util.XltCharBuffer;

public class StringHasher
{
    /**
     * Hashes a string up to a terminal character excluding it
     */
    public static int originalHashStringUntil(final String s, final char limitingChar)
    {
        // first remove the hash/fragment from the URL if present
        String _s = s;

        final int pos = s.indexOf(limitingChar);
        if (pos > 0)
        {
            _s = s.substring(0, pos);
        }

        return _s.hashCode();
    }

    /**
     * Hashes a string up to a terminal character excluding it
     */
    public static int combinedHashStringUntil(final String s, final char limitingChar)
    {
        final int pos = s.indexOf(limitingChar);
        if (pos > 0)
        {
            return hashStringUntil(s, limitingChar);
        }
        else
        {
            return s.hashCode();
        }
    }    

    /**
     * Hashes a string up to a terminal character excluding it
     */
    public static int hashStringUntil(final CharSequence s, final char limitingChar)
    {
        int hash = 0;

        for (int i = 0; i < s.length(); i++) 
        {
            final char c = s.charAt(i);

            if (c == limitingChar)
            {
                break;
            }
            hash = 31 * hash + c;
        }

        return hash;
    }    

    /**
     * Hashes a string up to a terminal character excluding it
     */
    public static int hashStringUntil109(final CharSequence s, final char limitingChar)
    {
        int hash = 0;

        for (int i = 0; i < s.length(); i++) 
        {
            final char c = s.charAt(i);

            if (c == limitingChar)
            {
                break;
            }
            hash = 109 * hash + c;
        }

        return hash;
    }

    final static int SHIFT = 15;
    final static int BITS = 32; 

    /**
     * Hashes a string up to a terminal character excluding it
     */
    public static int hashShiftingHint(final CharSequence s, final char limitingChar)
    {
        int hash = 0;

        for (int i = 0; i < s.length(); i++) 
        {
            final char c = s.charAt(i);

            if (c != limitingChar)
            {
                int h1 = hash << 5;
                int h2 = c - hash;
                hash = h1 + h2;
            }
            else
            {
                break;
            }
            //            hash = (hash << 5) + c - hash;
        }

        return hash;
    }   

    /**
     * Hashes a string up to a terminal character excluding it
     */
    public static int hashShiftingFull(final CharSequence s, final char limitingChar)
    {
        int hash = 0;

        for (int i = 0; i < s.length(); i++) 
        {
            final char c = s.charAt(i);

            if (c != limitingChar)
            {
                hash = (hash << 5) + c - hash;
            }
            else
            {
                break;
            }
        }

        return hash;
    } 

    private static final int FNV1_32_INIT = 0x811c9dc5;
    private static final int FNV1_PRIME_32 = 16777619;

    /**
     * Hashes a string up to a terminal character excluding it
     */
    public static int hashStringUntilFNP1A(final CharSequence s, final char limitingChar)
    {
        int hash = FNV1_32_INIT;

        for (int i = 0; i < s.length(); i++) 
        {
            final char c = s.charAt(i);

            if (c == limitingChar)
            {
                break;
            }
            hash ^= c;
            hash *= FNV1_PRIME_32;
        }

        return hash;
    }   

    /**
     * Hashes a string up to a terminal character excluding it
     */
    public static int hashStringViaXltCharBuffer(final XltCharBuffer s, final char limitingChar)
    {
        int pos = s.indexOf(limitingChar);

        return pos == -1 ? s.hashCodeVectoredJDK19() : s.substring(0, pos).hashCodeVectoredJDK19();
    } 

    /**
     * Hashes a string up to a terminal character excluding it
     */
    public static int hashStringViaXltCharBufferLast(final XltCharBuffer s, final char limitingChar)
    {
        int pos = s.lastIndexOf(limitingChar);

        return pos == -1 ? s.hashCodeVectoredJDK19() : s.substring(0, pos).hashCodeVectoredJDK19();
    } 

    /**
     * Hashes a string up to a terminal character excluding it
     */
    public static int hashStringViaString(final XltCharBuffer s, final char limitingChar)
    {
        String str = s.toString();
        int pos = str.indexOf(limitingChar);

        return pos == -1 ? s.hashCodeVectoredJDK19() : s.substring(0, pos).hashCodeVectoredJDK19();
    }
    
    /**
     * Hashes a string up to a terminal character excluding it
     */
    public static int hashCodeVectoredJDK19(final XltCharBuffer s, final char limitingChar)
    {
        int pos = s.indexOf(limitingChar);

        return pos == -1 ? s.hashCodeVectoredJDK19() : s.substring(0, pos).hashCodeVectoredJDK19();
    }
    
    /**
     * Hashes a string up to a terminal character excluding it
     */
    public static int hashCodeVectoredJDK19Adjusted(final XltCharBuffer s, final char limitingChar)
    {
        int pos = s.indexOf(limitingChar);

        return pos == -1 ? s.hashCodeVectoredJDK19Adjusted() : s.substring(0, pos).hashCodeVectoredJDK19Adjusted();
    }
    
    @Test
    public void test()
    {
        String s1 = "http://www.com/foobar";
        Assert.assertEquals(s1.hashCode(), originalHashStringUntil(s1, '#'));
        String s2 = "http://www.com/foobar#nothing";
        Assert.assertEquals(s1.hashCode(), originalHashStringUntil(s2, '#'));

        Assert.assertEquals(originalHashStringUntil(s1, '#'), hashStringUntil(s1, '#'));
        Assert.assertEquals(originalHashStringUntil(s2, '#'), hashStringUntil(s2, '#'));

        Assert.assertEquals(originalHashStringUntil(s1, '#'), hashStringViaXltCharBuffer(XltCharBuffer.valueOf(s1), '#'));
        Assert.assertEquals(originalHashStringUntil(s2, '#'), hashStringViaXltCharBuffer(XltCharBuffer.valueOf(s2), '#'));

        Assert.assertEquals(originalHashStringUntil(s1, '#'), hashStringViaString(XltCharBuffer.valueOf(s1), '#'));
        Assert.assertEquals(originalHashStringUntil(s2, '#'), hashStringViaString(XltCharBuffer.valueOf(s2), '#'));

        Assert.assertEquals(originalHashStringUntil(s1, '#'), hashStringViaXltCharBufferLast(XltCharBuffer.valueOf(s1), '#'));
        Assert.assertEquals(originalHashStringUntil(s2, '#'), hashStringViaXltCharBufferLast(XltCharBuffer.valueOf(s2), '#'));

        Assert.assertEquals(originalHashStringUntil(s1, '#'), hashShiftingFull(XltCharBuffer.valueOf(s1), '#'));
        Assert.assertEquals(originalHashStringUntil(s2, '#'), hashShiftingFull(XltCharBuffer.valueOf(s2), '#'));

        Assert.assertEquals(originalHashStringUntil(s1, '#'), hashShiftingHint(XltCharBuffer.valueOf(s1), '#'));
        Assert.assertEquals(originalHashStringUntil(s2, '#'), hashShiftingHint(XltCharBuffer.valueOf(s2), '#'));
        
        Assert.assertEquals(originalHashStringUntil(s1, '#'), hashCodeVectoredJDK19(XltCharBuffer.valueOf(s1), '#'));
        Assert.assertEquals(originalHashStringUntil(s2, '#'), hashCodeVectoredJDK19(XltCharBuffer.valueOf(s2), '#'));

        Assert.assertEquals(originalHashStringUntil(s1, '#'), hashCodeVectoredJDK19Adjusted(XltCharBuffer.valueOf(s1), '#'));
        Assert.assertEquals(originalHashStringUntil(s2, '#'), hashCodeVectoredJDK19Adjusted(XltCharBuffer.valueOf(s2), '#'));
    }

}
