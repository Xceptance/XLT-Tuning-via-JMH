package com.xceptance.common.encodenumbers.v01;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringifyNumbers
{
    final static int MAXLONGLENGTH = Long.toString(Long.MAX_VALUE).length() + 1;
    final static int MAXINTLENGTH = Long.toString(Integer.MAX_VALUE).length() + 1;
    
    static final char[] LOWER = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    };
    private static final int DIGITOFFSET = 48;
    static final char[] UPPER = new char[100];
    static
    {
        for (int i = 0; i < 100; i++)
        {
            UPPER[i] = Character.valueOf((char) ((i / 10) + DIGITOFFSET));
        }
    }

    private static final String MIN_VALUE_STR = Long.toString(Long.MIN_VALUE);
    
    public static String toString(final long l)
    {
        long t = l;
        
        if (l <= 0)
        {
            if (l == 0)
            {
                return "0";
            }
            if (l == Long.MIN_VALUE)
            {
                return MIN_VALUE_STR;
            }
            t = -l;
        }
        
//        int length = t <= Integer.MAX_VALUE ? MAXINTLENGTH : MAXLONGLENGTH;
        final char[] c = new char[MAXLONGLENGTH];
        int last = MAXLONGLENGTH;

        while (t >= 10)
        {
            final long nT = (t / 100);
            final int rest = (int) (t - nT * 100);
            t = nT;

            c[--last] = LOWER[rest];
            c[--last] = UPPER[rest];
        }
        if (t > 0)
        {
            c[--last] = LOWER[(int) t];
        }


        if (l < 0)
        {
            c[--last] = '-';
        }

        return new String(c, last, MAXLONGLENGTH - last);
    }

    @Test
    public void testLong()
    {
        assertEquals(Long.toString(0), toString(0));
        assertEquals(Long.toString(9), toString(9));
        assertEquals(Long.toString(10), toString(10));
        assertEquals(Long.toString(19), toString(19));
        assertEquals(Long.toString(-100L), toString(-100L));
        assertEquals(Long.toString(100L), toString(100L));
        assertEquals(Long.toString(1645996349752L), toString(1645996349752L));
        assertEquals(Long.toString(-1645996349752L), toString(-1645996349752L));

        assertEquals(Long.toString(Long.MAX_VALUE), toString(Long.MAX_VALUE));
        assertEquals(Long.toString(Long.MIN_VALUE), toString(Long.MIN_VALUE));
}
}
