package com.xceptance.common.csvutils.tuned2;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the {@link CsvUtils} class.
 */

public class CsvUtilsTest
{
    private static final String[] COMMA_DEC =
        {
            "fo,o", "bar", "baz"
        };

    private static final String COMMA_ENC = "\"fo,o\",bar,baz";

    private static final String[] EMPTY_DEC =
        {
            ""
        };

    private static final String EMPTY_ENC = "";

    private static final String[] EMPTY_FIELDS_DEC =
        {
            "", "", ""
        };

    private static final String EMPTY_FIELDS_ENC = ",,";

    private static final String[] DOUBLE_QUOTE_DEC =
        {
            "f\"o\"o", "bar", "baz"
        };

    private static final String DOUBLE_QUOTE_ENC = "\"f\"\"o\"\"o\",bar,baz";

    private static final String[] NORMAL_DEC =
        {
            "foo", "bar", "baz"
        };

    private static final String NORMAL_ENC = "foo,bar,baz";

    private static final String[] NULL_ENTRIES =
        {
            "foo", null, "baz"
        };

    private static final String[] WHITESPACE_DEC =
        {
            "fo\no", " bar", "baz\t"
        };

    private static final String WHITESPACE_ENC = "\"fo\no\", bar,baz\t";

    @Test
    public void testParse()
    {
        Assert.assertArrayEquals(NORMAL_DEC, CsvUtils.parse(NORMAL_ENC));
        Assert.assertArrayEquals(EMPTY_DEC, CsvUtils.parse(EMPTY_ENC));
        Assert.assertArrayEquals(EMPTY_FIELDS_DEC, CsvUtils.parse(EMPTY_FIELDS_ENC));
        Assert.assertArrayEquals(WHITESPACE_DEC, CsvUtils.parse(WHITESPACE_ENC));
        Assert.assertArrayEquals(DOUBLE_QUOTE_DEC, CsvUtils.parse(DOUBLE_QUOTE_ENC));
        Assert.assertArrayEquals(COMMA_DEC, CsvUtils.parse(COMMA_ENC));

        Assert.assertArrayEquals(new String[]{"foo", "bar", "max"}, CsvUtils.parse("foo,b\"a\"r,max"));
        Assert.assertArrayEquals(new String[]{"foo", "ba,r", "max"}, CsvUtils.parse("foo,\"ba,r\",max"));
        Assert.assertArrayEquals(new String[]{"foo", "bar", "max"}, CsvUtils.parse("foo,b\"\"ar,max"));

    }

    @Test
    public void testparse()
    {
        // nothing
        Assert.assertArrayEquals(new String[]{""}, CsvUtils.parse(""));
        Assert.assertArrayEquals(new String[]{" "}, CsvUtils.parse(" "));
        Assert.assertArrayEquals(new String[]{"a"}, CsvUtils.parse("a"));
        Assert.assertArrayEquals(new String[]{"ab"}, CsvUtils.parse("ab"));
        Assert.assertArrayEquals(new String[]{"", ""}, CsvUtils.parse(","));
        Assert.assertArrayEquals(new String[]{"a", "b"}, CsvUtils.parse("a,b"));
        Assert.assertArrayEquals(new String[]{"a", "b", "c"}, CsvUtils.parse("a,b,c"));
        Assert.assertArrayEquals(new String[]{"a", "b", "c", ""}, CsvUtils.parse("a,b,c,"));

        Assert.assertArrayEquals(new String[]{"a"}, CsvUtils.parse("\"a\""));
        Assert.assertArrayEquals(new String[]{"ab"}, CsvUtils.parse("\"ab\""));
        Assert.assertArrayEquals(new String[]{"a\"b"}, CsvUtils.parse("\"a\"\"b\""));
        Assert.assertArrayEquals(new String[]{","}, CsvUtils.parse("\",\""));
        Assert.assertArrayEquals(new String[]{"a,"}, CsvUtils.parse("\"a,\""));
        Assert.assertArrayEquals(new String[]{",b"}, CsvUtils.parse("\",b\""));
        Assert.assertArrayEquals(new String[]{"a,b"}, CsvUtils.parse("\"a,b\""));

        Assert.assertArrayEquals(new String[]{"a", "b"}, CsvUtils.parse("a,\"b\""));
        Assert.assertArrayEquals(new String[]{"a", "b,c"}, CsvUtils.parse("a,\"b,c\""));
        Assert.assertArrayEquals(new String[]{"ab", "bc"}, CsvUtils.parse("\"ab\",bc"));
        Assert.assertArrayEquals(new String[]{"ab", "bc"}, CsvUtils.parse("\"ab\",\"bc\""));

        Assert.assertArrayEquals(NORMAL_DEC, CsvUtils.parse(NORMAL_ENC));
        Assert.assertArrayEquals(EMPTY_DEC, CsvUtils.parse(EMPTY_ENC));
        Assert.assertArrayEquals(EMPTY_FIELDS_DEC, CsvUtils.parse(EMPTY_FIELDS_ENC));
        Assert.assertArrayEquals(WHITESPACE_DEC, CsvUtils.parse(WHITESPACE_ENC));
        Assert.assertArrayEquals(DOUBLE_QUOTE_DEC, CsvUtils.parse(DOUBLE_QUOTE_ENC));
        Assert.assertArrayEquals(COMMA_DEC, CsvUtils.parse(COMMA_ENC));
    }

    //         Assert.assertArrayEquals(new String[]{"ab"}, CsvUtils.parse("\"ab\""));

    
    @Test(expected = IllegalArgumentException.class)
    public void testParseWithNull()
    {
        CsvUtils.parse(null);
    }

    @Test
    public void testBuild()
    {
        Assert.assertEquals(NORMAL_ENC, CsvUtils.build(NORMAL_DEC));
        Assert.assertEquals(EMPTY_ENC, CsvUtils.build(EMPTY_DEC));
        Assert.assertEquals(EMPTY_FIELDS_ENC, CsvUtils.build(EMPTY_FIELDS_DEC));
        Assert.assertEquals(WHITESPACE_ENC, CsvUtils.build(WHITESPACE_DEC));
        Assert.assertEquals(DOUBLE_QUOTE_ENC, CsvUtils.build(DOUBLE_QUOTE_DEC));
        Assert.assertEquals(COMMA_ENC, CsvUtils.build(COMMA_DEC));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildWithNullArray()
    {
        CsvUtils.build(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildWithEmptyArray()
    {
        CsvUtils.build(new String[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildWithNullArrayEntries()
    {
        CsvUtils.build(NULL_ENTRIES);
    }

    /**
     * Decode field as null
     */
    @Test
    public void unquoteField_Null()
    {
        Assert.assertNull(CsvUtils.unquote((String)null));
    }

    /**
     * Decode field with too many quotes
     */
    @Test(expected = IllegalArgumentException.class)
    public void unquoteField_SingleQuote()
    {
        CsvUtils.unquote("\"foob\"ar\"");
    }

    /**
     * Encode and decode fields
     */
    @Test
    public void quoteUnquote()
    {
        Assert.assertEquals("foobar", CsvUtils.unquote("foobar"));
        
        String s;
        
        s = "";         Assert.assertEquals(s, CsvUtils.unquote(CsvUtils.quote(s)));
        s = "foobar";   Assert.assertEquals(s, CsvUtils.unquote(CsvUtils.quote(s)));
        s = "foo,bar";  Assert.assertEquals(s, CsvUtils.unquote(CsvUtils.quote(s)));
        s = "foo,";     Assert.assertEquals(s, CsvUtils.unquote(CsvUtils.quote(s)));
        s = ",foo";     Assert.assertEquals(s, CsvUtils.unquote(CsvUtils.quote(s)));
        s = ",foo,";     Assert.assertEquals(s, CsvUtils.unquote(CsvUtils.quote(s)));
        s = "bar,foo,";     Assert.assertEquals(s, CsvUtils.unquote(CsvUtils.quote(s)));
        
        s = "\"foo\",\"bar\"";  Assert.assertEquals(s, CsvUtils.unquote(CsvUtils.quote(s)));
        s = "\"foo,bar\"";  Assert.assertEquals(s, CsvUtils.unquote(CsvUtils.quote(s)));
        s = "foo\"bar";     Assert.assertEquals(s, CsvUtils.unquote(CsvUtils.quote(s)));
        s = "\"";       Assert.assertEquals(s, CsvUtils.unquote(CsvUtils.quote(s)));
        s = "\"\"";     Assert.assertEquals(s, CsvUtils.unquote(CsvUtils.quote(s)));
        s = "\"\"\"";   Assert.assertEquals(s, CsvUtils.unquote(CsvUtils.quote(s)));
    }

    /**
     * Encode and decode fields
     */
    @Test
    public void quote()
    {
        Assert.assertEquals("", CsvUtils.quote(""));
        Assert.assertEquals(" ", CsvUtils.quote(" "));
        Assert.assertEquals("\"\"\"\"", CsvUtils.quote("\""));
        Assert.assertEquals("\"\"\"a\"\"\"", CsvUtils.quote("\"a\""));
        Assert.assertEquals("foobar", CsvUtils.quote("foobar"));
        Assert.assertEquals("\"foo,bar\"", CsvUtils.quote("foo,bar"));
        Assert.assertEquals("\",foo,bar,\"", CsvUtils.quote(",foo,bar,"));
        Assert.assertEquals("\"\"\",\"\"\"", CsvUtils.quote("\",\""));
        Assert.assertEquals("\"a\"\"b\"", CsvUtils.quote("a\"b"));
    }
}
