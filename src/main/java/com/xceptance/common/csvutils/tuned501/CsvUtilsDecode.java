package com.xceptance.common.csvutils.tuned501;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link CsvUtilsDecode} class provides helper methods to encode and decode values to/from the CSV format. Note that we
 * define the "C" in "CSV" to stand for "comma", so other characters are not allowed as field separator.
 */
public final class CsvUtilsDecode
{
    /**
     * Character constant representing a comma.
     */
    private static final char COMMA = ',';

    /**
     * Character constant representing a double quote.
     */
    private static final char QUOTE_CHAR = '"';

    /**
     * Character constant representing a line feed.
     */
    private static final char LF = '\n';

    /**
     * Character constant representing a carriage return.
     */
    private static final char CR = '\r';

    /**
     * Default constructor. Declared private to prevent external instantiation.
     */
    private CsvUtilsDecode()
    {
    }

    /**
     * Decodes the given CSV-encoded data record and returns the plain unquoted fields 
     * using a comma as separator
     * 
     * @param s
     *            the CSV-encoded data record
     * @return the plain fields
     */
    public static List<String> decode(final String s)
    {
        return decode(s, COMMA);
    }

    /**
     * Decodes the given fields to a CSV-encoded data record using the given field separator.
     * 
     * @param s
     *            the plain fields
     * @param fieldSeparator
     *            the field separator to use
     * @return the CSV-encoded data record
     */
    public static List<String> decode(final String s, final char fieldSeparator)
    {
        ParameterCheckUtils.isNotNull(s, "s");
        
        return splitAndDecode(s, fieldSeparator);
    }

    /**
     * Splits the given string into parts at field boundaries. Field separators occurring inside quoted fields are
     * ignored.
     * 
     * @param s
     *            the encoded data record
     * @param fieldSeparator
     *            the field separator used
     * @return the encoded fields
     */
    private static List<String> splitAndDecode(final String s, final char fieldSeparator)
    {
        // allocate no more than three cache lines but also don't waste too much of a line
        final char[] src = s.toCharArray();
        final int length = src.length;

        final ArrayList<String> target = new ArrayList<>(32);

        int beginIndex = 0;
        boolean insideQuotes = false;
        
        for (int i = 0; i < length; i++)
        {
            final char c = src[i];

            if (c == fieldSeparator)
            {
                if (insideQuotes == false)
                {
                    target.add(decodeField(src, beginIndex, i));
                    beginIndex = i + 1;
                }
            }
            else if (c == QUOTE_CHAR)
            {
                insideQuotes = !insideQuotes;
            }
        }

        // add the last field
        target.add(decodeField(src, beginIndex, length));

        return target;
    }

    /**
     * Decodes a string that might contain quotes and encoded quotes. Returns a 
     * ready to use string.
     * 
     * @param s
     *            the encoded field
     * @return the plain field
     */
    public static String decodeField(final String s)
    {
        if (s == null)
        {
            return null;
        }
        return decodeField(s.toCharArray(), 0, s.length());
    }
    
    /**
     * Decodes a string that might contain quotes and encoded quotes. Returns a 
     * ready to use string.
     * 
     * @param s
     *            the encoded field
     * @return the plain field
     */
    private static String decodeField(final char[] src, int from, int to)
    {
        final int length = to - from;

        if (length < 2)
        {
            return new String(src, from, length);
        }

        if (src[from] != QUOTE_CHAR || src[to - 1] != QUOTE_CHAR)
        {
            return new String(src, from, length);
        }
        
        // iterate from second up to second last character
        int target = 0;
        final char[] buffer = new char[length - 2];
        
        for (int i = from + 1; i < length - 1; i++)
        {
            final char c = src[i];

            if (c == QUOTE_CHAR)
            {
                // the next character must be a quote character as well
                // because we are inside the string and if this is ok, we 
                // decode it, otherwise complain
                i++;

                if (i > length - 1|| src[i] != QUOTE_CHAR)
                {
                    throw new IllegalArgumentException("Parameter '" + i + "' is not properly CSV-encoded.");
                }
            }
            
            buffer[target] = c;
            target++;
        }

        return target < length - 1 ? new String(buffer, 0, target) : new String(src, from, length - 1);
    }    
}
