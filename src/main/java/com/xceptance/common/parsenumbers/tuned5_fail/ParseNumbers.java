package com.xceptance.common.parsenumbers.tuned5_fail;

/**
 * This is a small helper class for parsing strings and converting them into longs. This implementation is optimized for
 * speed not for functionality. It is only able to parse plain numbers with base 10, e.g. 100828171 (see
 * String.parseLong())
 * 
 * @author René Schwietzke
 */
public final class ParseNumbers
{
    private static final int DIGITOFFSET = 48;
    
    /**
     * Parses the string and returns the result as int. Raises a NumberFormatException in case of an non-convertable
     * string. Due to conversion limitations, the content of s might be larger than an int, precision might be
     * inaccurate.
     * 
     * @param s
     *            the string to parse
     * @return the converted string as int
     * @throws java.lang.NumberFormatException
     */
    public static long parseLong(final char[] s)
    {
        // determine length
        final int length = s.length;
        
        // that is safe, we already know that we are > 0
        final int digit = s[0];
        
        long value = digit - DIGITOFFSET;
        
        for (int i = 1; i < length; i++)
        {
            final int d = s[i];

            value = ((value << 3) + (value << 1));
            value += (d - DIGITOFFSET);
        }

        return value;
    }

    /**
     * Parses the string and returns the result as int. Raises a NumberFormatException in case of an non-convertable
     * string. Due to conversion limitations, the content of s might be larger than an int, precision might be
     * inaccurate.
     * 
     * @param s
     *            the string to parse
     * @return the converted string as int
     * @throws java.lang.NumberFormatException
     */
    public static int parseInt(final char[] s)
    {
        // determine length
        final int length = s.length;
        
        // that is safe, we already know that we are > 0
        final int digit = s[0];
        int value = digit - DIGITOFFSET;
        
        for (int i = 1; i < length; i++)
        {
            final int d = s[i];

            value = ((value << 3) + (value << 1));
            value += (d - DIGITOFFSET);
        }

        return value;
    }
}
