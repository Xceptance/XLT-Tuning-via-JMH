package com.xceptance.common.parsenumbers.tuned13_vectored;

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
    public static long parseLong(final CharSequence s)
    {
        // determine length
        final int length = s.length();
        
        if (length == 0)
        {
            throw new NumberFormatException("length = 0");
        }
//        
//        if (length == 13)
//        {
//            var v0  = (s.charAt(0) -   DIGITOFFSET) * 1_000_000_000_000L;
//            var v1  = (s.charAt(1) -   DIGITOFFSET) * 100_000_000_000L;
//            var v2  = (s.charAt(2) -   DIGITOFFSET) * 10_000_000_000L;
//            var v3  = (s.charAt(3) -   DIGITOFFSET) * 1_000_000_000L;
//            var v4  = (s.charAt(4) -   DIGITOFFSET) * 100_000_000L;
//            var v5  = (s.charAt(5) -   DIGITOFFSET) * 10_000_000L;
//            var v6  = (s.charAt(6) -   DIGITOFFSET) * 1_000_000L;
//            var v7  = (s.charAt(7) -   DIGITOFFSET) * 100_000L;
//            var v8  = (s.charAt(8) -   DIGITOFFSET) * 10_000L;
//            var v9  = (s.charAt(9) -   DIGITOFFSET) * 1_000L;
//            var v10  = (s.charAt(10) - DIGITOFFSET) * 100L;
//            var v11  = (s.charAt(11) - DIGITOFFSET) * 10L;
//            var v12  = (s.charAt(12) - DIGITOFFSET);
//            
//            var value = v0 + v1 + v2 + v3 + v4 + v5 + v6 + v7 + v8 + v9 + v10 + v11 + v12;
//            
//            return value;
//        }
             
        
        // that is safe, we already know that we are > 0
        long value = s.charAt(0) - DIGITOFFSET;
        
        for (int i = 1; i < length; i++)
        {
            value *= 10;

            final int d = s.charAt(i) - DIGITOFFSET;
            value += d;
        }

        return value;
    }

    public static long parseLongV(final CharSequence s)
    {
        // determine length
        final int length = s.length();
        
        if (length == 0)
        {
            throw new NumberFormatException("length = 0");
        }
        
        long value = 0;
        int i1 = 1;
        
        for (; i1 < length; i1 = i1 + 2)
        {
            value *= 100;

            final int d0 = s.charAt(i1 - 1);
            var v0 = (d0 - DIGITOFFSET);
            v0 *= 10;
                            
            final int d1 = s.charAt(i1);
            var v1 = d1 - DIGITOFFSET;
            
            value += v0 + v1;
        }
        if (i1 - 1  < length)
        {
            final int d = s.charAt(i1 - 1);

            value *= 10;
            value += (d - DIGITOFFSET);            

        }

        return value;
    }
    
}
