package com.xceptance.common.csvutils.tuned2;

import java.util.ArrayList;

/**
 * The {@link CsvUtils} class provides helper methods to encode and decode values to/from the CSV format. Note that we
 * define the "C" in "CSV" to stand for "comma", so other characters are not allowed as field separator.
 */
public final class CsvUtils
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

    // parser states
    private static final int CLEAR = 0;
    private static final int IN_QUOTES = 1;
    private static final int EXPECT_QUOTE = 2;
   
    /**
     * Default constructor. Declared private to prevent external instantiation.
     */
    private CsvUtils()
    {
    }

    /**
     * Parses a CSV line and also unquotes the fields if needed using a comma
     * as field seperator.
     * 
     * @param line
     *            the CSV line
     * @return the parsed and unquoted data records
     */
    public static String[] parse(final String line)
    {
        return splitAndUnquote(line, COMMA);
    }
    
    /**
     * Builds a CSV line based on these fields using a comma
     * as separator and quoting the field if needed
     * 
     * @param fields
     *            the plain fields
     * @return the CSV-encoded data record
     */
    public static String build(final String[] fields)
    {
        return build(fields, COMMA);
    }    
    
     /**
     * Unquotes a field if needed
     * 
     * @param s
     *            the quoted field
     * @return the unquoted field
     */
    public static String unquote(final String s)
    {
        if (s == null)
        {
            return s;
        }

        if (s.length() < 2)
        {
            return s;
        }

        final int length = s.length();

        if (s.charAt(0) != QUOTE_CHAR || s.charAt(length - 1) != QUOTE_CHAR)
        {
            return s;
        }
        
        // source and target
        final char[] buffer = s.toCharArray();

        // iterate from second up to second last character
        int target = 0;

        for (int src = 1; src < length - 1; src++)
        {
            final char c = buffer[src];

            if (c == QUOTE_CHAR)
            {
                // the next character must be a quote character as well
                src++;

                if (src >= length - 1 || buffer[src] != QUOTE_CHAR)
                {
                    throw new IllegalArgumentException("Parameter '" + s + "' is not properly CSV-encoded.");
                }
            }
            
            buffer[target] = c;
            target++;
        }

        // no reason for a copy if we had no quotes
        return target < length ? new String(buffer, 0, target) : s;
    }

    
    /**
     * Quotes a field when the comma is part of the data to 
     * correctly preserve it
     * 
     * @param s
     *            the plain field
     * @return the quoted field
     */
    public static String quote(final String s)
    {
        return quote(s, COMMA);
    }    
    
    /**
     * Quotes a field when the comma is part of the data to 
     * correctly preserve it
     * 
     * @param s
     *            the plain field
     * @return the quoted field
     */
    public static String quote(final String s, final char fieldSeparator)
    {
        if (s == null || s.length() == 0)
        {
            return s;
        }

        final char[] sourceChars = s.toCharArray();
        final int sourceLength = sourceChars.length;

        // check whether we have to quote at all
        boolean needsQuoting = false;
        int quotesRead = 0;
        for (int i = 0; i < sourceLength; i++)
        {
            final char c = sourceChars[i];
            if (c == QUOTE_CHAR)
            {
                quotesRead++;
            }

            if (!needsQuoting && needsQuote(c, fieldSeparator))
            {
                needsQuoting = true;
            }
        }

        if (!needsQuoting)
        {
            return s;
        }

        // quote
        final char[] targetChars = new char[sourceLength + quotesRead + 2];

        int j = 1;
        for (int i = 0; i < sourceLength; i++, j++)
        {
            final char c = sourceChars[i];

            if (c == QUOTE_CHAR)
            {
                // add another quote
                targetChars[j++] = c;
            }

            targetChars[j] = c;
        }

        targetChars[0] = QUOTE_CHAR;
        targetChars[j] = QUOTE_CHAR;

        return new String(targetChars);
    }

    /**
     * Encodes the given fields to a CSV-encoded data record.
     * 
     * @param fields
     *            the plain fields
     * @param fieldSeparator
     *            the field separator to use
     * @return the CSV-encoded data record
     */
    public static String build(final String[] fields, final char fieldSeparator)
    {
        // parameter validation
        if (fields == null)
        {
            throw new IllegalArgumentException("Parameter fields must not be null");
        }
        
        if (fields.length == 0)
        {
            throw new IllegalArgumentException("Parameter fields must not be empty");
        }
        
        final StringBuilder result = new StringBuilder(256);
        boolean isFirstField = true;

        for (String field : fields)
        {
            if (field == null)
            {
                throw new IllegalArgumentException("Array entry must not be null.");
            }

            // first append the separator except for the first entry
            if (isFirstField)
            {
                isFirstField = false;
            }
            else
            {
                result.append(fieldSeparator);
            }

            // now add the encoded field
            field = quote(field, fieldSeparator);
            result.append(field);
        }

        return result.toString();
    }

    /**
     * Splits the given string into parts at field boundaries. Field separators occurring inside quoted fields are
     * ignored. Most efficient implementation when entire lines have to be parsed and unquoted at the same time.
     * 
     * @param s
     *            a CSV line with quoting if needed
     * @param fieldSeparator
     *            the field separator used
     * @return the unquoted line
     */
    public static String[] splitAndUnquote(final String s, final char fieldSeparator)
    {
        if (s == null)
        {
            throw new IllegalArgumentException("s must not be null");
        }
        
        int length = s.length();
        if (length == 0)
        {
            return new String[]{""};
        }

        final ArrayList<String> target = new ArrayList<>(30);
        final char[] src = s.toCharArray();

        int pos = 0;
        int state = CLEAR;
        int begin = 0;
        
        for (int i = 0; i < length; i++)
        {
            // read next char
            final char c = src[i];
            
            if (c == QUOTE_CHAR)
            {
                // if we are not in the quote mode, set it
                if (state == CLEAR)
                {
                    state = IN_QUOTES;
                    // don't save character
                }
                else if (state == IN_QUOTES)
                {
                    state = EXPECT_QUOTE;
                    // ignore that character
                }
                else if (state == EXPECT_QUOTE)
                {
                    // a quoted quote
                    state = IN_QUOTES;
                    
                    // save the quote
                    src[pos] = c; 
                    pos++;
                }
                
                continue;
            }

            if (state != IN_QUOTES)
            {
                // we did not get another quote, so that was the
                // end quote before, hence this is a regular character 
                // or a separator
                state = CLEAR;

                if (c == fieldSeparator)
                {
                    // finish string
                    final int l = pos - begin;
                    target.add(l == 0 ? "" : new String(src, begin, l));

                    begin = pos + 1; // increase below 
                }
            }
            
            // copy over, aka shift the character to "forget" what we don't need
            src[pos] = c; 
            pos++;

        }
        
        // ok, do we have anything left?
        if (begin <= pos)
        {
            final int l = pos - begin;
            target.add(l == 0 ? "" : new String(src, begin, l));
        }
        
        // return a sub array with only the fields found
        // the new array for type safety is stack local and cheaper
        return target.toArray(new String[target.size()]);
    }
    
    /**
     * Determines whether or not the given character needs to be quoted.
     * 
     * @param c
     *            character to be checked
     * @param separatorChar
     *            the field separator character
     * @return <code>true</code> if the given character needs to be quoted, <code>false</code> otherwise
     */
    private static boolean needsQuote(final char c, final char separatorChar)
    {
        return c == QUOTE_CHAR || c == LF || c == CR || c == separatorChar;
    }
}
