package com.xceptance.common.csvutils.tuned501;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the {@link CsvUtilsDecode} class.
 */

public class CsvUtilsDecodeTest
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

    private static final String NORMAL_ENC = "foo,bar,baz";
    private static final String[] NORMAL_DEC = {"foo", "bar", "baz"};


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
    public void testDecodeNormal()
    {
        Assert.assertArrayEquals(NORMAL_DEC, CsvUtilsDecode.decode(NORMAL_ENC).toArray());
    }
    @Test
    public void testDecodeEmpty()
    {
        Assert.assertArrayEquals(EMPTY_DEC, CsvUtilsDecode.decode(EMPTY_ENC).toArray());
    }
    @Test
    public void testDecodeEmptyFields()
    {
        Assert.assertArrayEquals(EMPTY_FIELDS_DEC, CsvUtilsDecode.decode(EMPTY_FIELDS_ENC).toArray());
    }
    @Test
    public void testDecodeWhiteSpace()
    {
        Assert.assertArrayEquals(WHITESPACE_DEC, CsvUtilsDecode.decode(WHITESPACE_ENC).toArray());
    }
    @Test
    public void testDecodeDoubleQuote()
    {
        Assert.assertArrayEquals(DOUBLE_QUOTE_DEC, CsvUtilsDecode.decode(DOUBLE_QUOTE_ENC).toArray());
    }
    @Test
    public void testDecodeQuotedComma()
    {
        Assert.assertArrayEquals(COMMA_DEC, CsvUtilsDecode.decode(COMMA_ENC).toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeWithNull()
    {
        CsvUtilsDecode.decode(null);
    }
    
    /**
     * Decode field as null
     */
    @Test
    public void decodeField_Null()
    {
        Assert.assertNull(CsvUtilsDecode.decodeField(null));
    }

    /**
     * Decode field with too many quotes
     */
    @Test(expected = IllegalArgumentException.class)
    public void decodeField_SingleQuote()
    {
        CsvUtilsDecode.decodeField("\"foob\"ar\"");
    }

    /**
     * Encode and decode fields
     */
    @Test
    public void encodeDecodeField()
    {
        Assert.assertEquals("foobar", CsvUtilsDecode.decodeField("foobar"));
        
        Assert.assertEquals("foo", CsvUtilsDecode.decodeField("\"foo\""));
        
        String s = "";
        Assert.assertEquals(s, CsvUtilsDecode.decodeField(s));
        
        s = "foobar";
        Assert.assertEquals(s, CsvUtilsDecode.decodeField(CsvUtils.encodeField(s)));
        s = "foo,bar";
        Assert.assertEquals(s, CsvUtilsDecode.decodeField(CsvUtils.encodeField(s)));
        s = "foo,";
        Assert.assertEquals(s, CsvUtilsDecode.decodeField(CsvUtils.encodeField(s)));
        s = ",foo";
        Assert.assertEquals(s, CsvUtilsDecode.decodeField(CsvUtils.encodeField(s)));
        s = "\"foo,bar\"";
        Assert.assertEquals(s, CsvUtilsDecode.decodeField(CsvUtils.encodeField(s)));
        s = "foo\"bar";
        Assert.assertEquals(s, CsvUtilsDecode.decodeField(CsvUtils.encodeField(s)));
        s = "\"";
        Assert.assertEquals(s, CsvUtilsDecode.decodeField(CsvUtils.encodeField(s)));
        s = "\"\"";
        Assert.assertEquals(s, CsvUtilsDecode.decodeField(CsvUtils.encodeField(s)));
        s = "\"\"\"";
        Assert.assertEquals(s, CsvUtilsDecode.decodeField(CsvUtils.encodeField(s)));
    }
}
