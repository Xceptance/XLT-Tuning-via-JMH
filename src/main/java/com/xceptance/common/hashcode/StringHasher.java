package com.xceptance.common.hashcode;

import org.junit.Assert;
import org.junit.Test;

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
    public static int hashStringUntil(final String s, final char limitingChar)
    {
        int hash = 0;
        
        for (int i = 0; i < s.length(); i++) 
        {
            final char c = s.charAt(i);
            
            if (c != limitingChar)
            {
                hash = 31 * hash + c;
            }
            else
            {
                // early end reached
                break;
            }
        }
        
        return hash;
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
    }

}
