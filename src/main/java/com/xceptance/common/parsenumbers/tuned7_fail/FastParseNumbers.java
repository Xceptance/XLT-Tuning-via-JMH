package com.xceptance.common.parsenumbers.tuned7_fail;

/**
 * This is a small helper class for parsing strings and converting them into longs. This implementation is optimized for
 * speed not for functionality. It is only able to parse plain numbers with base 10, e.g. 100828171 (see
 * String.parseLong())
 * 
 * @author René Schwietzke
 */
public final class FastParseNumbers
{
    private static final int DIGITOFFSET = 48;
    
    /**
     * Parses the string and returns the result as long. Due to conversion limitations, 
     * the content of s might be larger than a long, precision might be
     * inaccurate.
     * 
     * This version does not check for any problems neither in content nor length.
     * 
     * @param s
     *            the string to parse
     * @return the converted string as int
     * @throws java.lang.NumberFormatException
     */
    public static long fastParseLong(final char[] s)
    {
        // determine length
        final int length = s.length;
        
        long value = 0;
        for (int i = 0; i < length; i++)
        {
            final int d = s[i];

            value = ((value << 3) + (value << 1));
            value += (d - DIGITOFFSET);
        }

        return value;
    }

    /**
     * Parses the string and returns the result as int. Due to conversion limitations, 
     * the content of s might be larger than an int, precision might be
     * inaccurate.
     * 
     * This version does not check for any problems neither in content nor length.
     * 
     * @param s
     *            the string to parse
     * @return the converted string as int
     * @throws java.lang.NumberFormatException
     */
    public static int fastParseInt(final char[] s)
    {
        // determine length
        final int length = s.length;

        int value = 0;
        for (int i = 0; i < length; i++)
        {
            final int d = s[i];

            value = ((value << 3) + (value << 1));
            value += (d - DIGITOFFSET);
        }

        return value;
    }
}
