package com.xceptance.common.parsenumbers.tuned14;

/**
 * This is a small helper class for parsing strings and converting them into longs. This implementation is optimized for
 * speed not for functionality. It is only able to parse plain numbers with base 10, e.g. 100828171 (see
 * String.parseLong())
 *
 * @author René Schwietzke
 */
public final class ParseNumbers
{
    private static final int DIGIT_0 = '0';
    private static final int DIGIT_9 = '9';

    public static long parseLong1(final CharSequence s)
    {
        // determine length
        final int length = s.length();

        if (length == 0)
        {
            throw new NumberFormatException("length = 0");
        }

        // that is safe, we already know that we are > 0
        char c = s.charAt(0);

        // turn the compare around to allow the compiler and cpu
        // to run the next code most of the time
        if (c < '0' || c > '9')
        {
            return Long.parseLong(String.valueOf(s));
        }

        long value = c - DIGIT_0;

        for (int i = 1; i < length; i++)
        {
            c = s.charAt(i);

            value = ((value << 3) + (value << 1));
            value += (c - DIGIT_0);

            if (c < '0' || c > '9')
            {
                return Long.parseLong(String.valueOf(s));
            }
        }

        return value;
    }

    public static long parseLong2(final CharSequence s)
    {
        // determine length
        final int length = s.length();

        if (length == 0)
        {
            throw new NumberFormatException("length = 0");
        }

        // that is safe, we already know that we are > 0
        int c = s.charAt(0) - DIGIT_0;

        // turn the compare around to allow the compiler and cpu
        // to run the next code most of the time
        if (c > 9 || c < 0)
        {
            return Long.parseLong(String.valueOf(s));
        }

        long value = c;
        int i = 1;

        while (i < length)
        {
            value = ((value << 3) + (value << 1));
            c = s.charAt(i) - DIGIT_0;
            value += c;

            i++;

            if (c > 9 || c < 0)
            {
                return Long.parseLong(String.valueOf(s));
            }
        }

        return value;
    }

    public static long parseLong3(final CharSequence s)
    {
        // determine length
        final int length = s.length();

        if (length == 0)
        {
            throw new NumberFormatException("length = 0");
        }

        // that is safe, we already know that we are > 0
        int c = s.charAt(0) - DIGIT_0;

        // turn the compare around to allow the compiler and cpu
        // to run the next code most of the time
        if (c > 9 || c < 0)
        {
            return Long.parseLong(String.valueOf(s));
        }

        long value = c;
        int i = 1;

        while (i < length)
        {
            value = ((value << 3) + (value << 1));
            c = s.charAt(i) - DIGIT_0;
            value += c;

            i++;
        }

        return value;
    }

    public static long parseLong4(final CharSequence s)
    {
        // determine length
        final int length = s.length();

        if (length == 0)
        {
            throw new NumberFormatException("length = 0");
        }

        // that is safe, we already know that we are > 0
        int c = s.charAt(0) - DIGIT_0;

        // turn the compare around to allow the compiler and cpu
        // to run the next code most of the time
        if (c > 9 || c < 0)
        {
            return Long.parseLong(String.valueOf(s));
        }

        long value = c;
        int i = 1;

        while (i < length)
        {
            value *= 10;
            c = s.charAt(i) - DIGIT_0;
            value += c;

            i++;
        }

        return value;
    }
}
