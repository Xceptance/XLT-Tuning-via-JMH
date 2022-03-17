package com.xceptance.common.encodenumbers.v01;

import org.junit.Test;

import static org.junit.Assert.*;

import static java.lang.String.*;

public class JDKLong
{
    static int stringSize(long x) {
        int d = 1;
        if (x >= 0) {
            d = 0;
            x = -x;
        }
        long p = -10;
        for (int i = 1; i < 19; i++) {
            if (x > p)
                return i + d;
            p = 10 * p;
        }
        return 19 + d;
    }

    static final byte[] DigitTens = {
            '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
            '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
            '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
            '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
            '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
            '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
            '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
            '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
            '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
            '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
            } ;

        static final byte[] DigitOnes = {
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
            } ;
    
        /**
         * Places characters representing the long i into the
         * character array buf. The characters are placed into
         * the buffer backwards starting with the least significant
         * digit at the specified index (exclusive), and working
         * backwards from there.
         *
         * @implNote This method converts positive inputs into negative
         * values, to cover the Long.MIN_VALUE case. Converting otherwise
         * (negative to positive) will expose -Long.MIN_VALUE that overflows
         * long.
         *
         * @param i     value to convert
         * @param index next index, after the least significant digit
         * @param buf   target buffer, Latin1-encoded
         * @return index of the most significant digit or minus sign, if present
         */
        static int getChars(long i, int index, byte[] buf) {
            long q;
            int r;
            int charPos = index;

            boolean negative = (i < 0);
            if (!negative) {
                i = -i;
            }

            // Get 2 digits/iteration using longs until quotient fits into an int
            while (i <= Integer.MIN_VALUE) {
                q = i / 100;
                r = (int)((q * 100) - i);
                i = q;
                buf[--charPos] = DigitOnes[r];
                buf[--charPos] = DigitTens[r];
            }

            // Get 2 digits/iteration using ints
            int q2;
            int i2 = (int)i;
            while (i2 <= -100) {
                q2 = i2 / 100;
                r  = (q2 * 100) - i2;
                i2 = q2;
                buf[--charPos] = DigitOnes[r];
                buf[--charPos] = DigitTens[r];
            }

            // We know there are at most two digits left at this point.
            q2 = i2 / 10;
            r  = (q2 * 10) - i2;
            buf[--charPos] = (byte)('0' + r);

            // Whatever left is the remaining digit.
            if (q2 < 0) {
                buf[--charPos] = (byte)('0' - q2);
            }

            if (negative) {
                buf[--charPos] = (byte)'-';
            }
            return charPos;
        }

    public static String toString(long i)
    {
        int size = stringSize(i);
        byte[] buf = new byte[size];
        
        int pos = getChars(i, size, buf);
        return new String(buf);
    }    

    @Test
    public void testLong()
    {
        assertEquals(Long.toString(100L), toString(100L));
        assertEquals(Long.toString(10), toString(10));
        assertEquals(Long.toString(0), toString(0));
        assertEquals(Long.toString(9), toString(9));
        assertEquals(Long.toString(19), toString(19));
        assertEquals(Long.toString(-100L), toString(-100L));
        assertEquals(Long.toString(1645996349752L), toString(1645996349752L));
        assertEquals(Long.toString(-1645996349752L), toString(-1645996349752L));
    }
}
