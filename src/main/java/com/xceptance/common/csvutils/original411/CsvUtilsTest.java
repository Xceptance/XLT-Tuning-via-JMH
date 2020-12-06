package com.xceptance.common.csvutils.original411;

import org.junit.Assert;
import org.junit.Test;

import com.xceptance.common.csvutils.tuned412.CsvUtils;

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
    public void testDecode()
    {
        Assert.assertArrayEquals(NORMAL_DEC, CsvUtils.decode(NORMAL_ENC));
        Assert.assertArrayEquals(EMPTY_DEC, CsvUtils.decode(EMPTY_ENC));
        Assert.assertArrayEquals(EMPTY_FIELDS_DEC, CsvUtils.decode(EMPTY_FIELDS_ENC));
        Assert.assertArrayEquals(WHITESPACE_DEC, CsvUtils.decode(WHITESPACE_ENC));
        Assert.assertArrayEquals(DOUBLE_QUOTE_DEC, CsvUtils.decode(DOUBLE_QUOTE_ENC));
        Assert.assertArrayEquals(COMMA_DEC, CsvUtils.decode(COMMA_ENC));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeWithNull()
    {
        CsvUtils.decode(null);
    }

    @Test
    public void testEncode()
    {
        Assert.assertEquals(NORMAL_ENC, CsvUtils.encode(NORMAL_DEC));
        Assert.assertEquals(EMPTY_ENC, CsvUtils.encode(EMPTY_DEC));
        Assert.assertEquals(EMPTY_FIELDS_ENC, CsvUtils.encode(EMPTY_FIELDS_DEC));
        Assert.assertEquals(WHITESPACE_ENC, CsvUtils.encode(WHITESPACE_DEC));
        Assert.assertEquals(DOUBLE_QUOTE_ENC, CsvUtils.encode(DOUBLE_QUOTE_DEC));
        Assert.assertEquals(COMMA_ENC, CsvUtils.encode(COMMA_DEC));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodeWithNullArray()
    {
        CsvUtils.encode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodeWithEmptyArray()
    {
        CsvUtils.encode(new String[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodeWithNullArrayEntries()
    {
        CsvUtils.encode(NULL_ENTRIES);
    }

    /**
     * Decode field as null
     */
    @Test
    public void decodeField_Null()
    {
        Assert.assertNull(CsvUtils.decodeField(null));
    }

    /**
     * Decode field with too many quotes
     */
    @Test(expected = IllegalArgumentException.class)
    public void decodeField_SingleQuote()
    {
        CsvUtils.decodeField("\"foob\"ar\"");
    }

    /**
     * Encode and decode fields
     */
    @Test
    public void encodeDecodeField()
    {
        Assert.assertEquals("foobar", CsvUtils.decodeField("foobar"));
        
        String s = "";
        Assert.assertEquals(s, CsvUtils.decodeField(CsvUtils.encodeField(s)));
        s = "foobar";
        Assert.assertEquals(s, CsvUtils.decodeField(CsvUtils.encodeField(s)));
        s = "foo,bar";
        Assert.assertEquals(s, CsvUtils.decodeField(CsvUtils.encodeField(s)));
        s = "foo,";
        Assert.assertEquals(s, CsvUtils.decodeField(CsvUtils.encodeField(s)));
        s = ",foo";
        Assert.assertEquals(s, CsvUtils.decodeField(CsvUtils.encodeField(s)));
        s = "\"foo,bar\"";
        Assert.assertEquals(s, CsvUtils.decodeField(CsvUtils.encodeField(s)));
        s = "foo\"bar";
        Assert.assertEquals(s, CsvUtils.decodeField(CsvUtils.encodeField(s)));
        s = "\"";
        Assert.assertEquals(s, CsvUtils.decodeField(CsvUtils.encodeField(s)));
        s = "\"\"";
        Assert.assertEquals(s, CsvUtils.decodeField(CsvUtils.encodeField(s)));
        s = "\"\"\"";
        Assert.assertEquals(s, CsvUtils.decodeField(CsvUtils.encodeField(s)));
    }

    /**
     * Encode and decode fields
     */
    @Test
    public void encodeField()
    {
        Assert.assertEquals("", CsvUtils.encodeField(""));
        Assert.assertEquals(" ", CsvUtils.encodeField(" "));
        Assert.assertEquals("\"\"\"\"", CsvUtils.encodeField("\""));
        Assert.assertEquals("\"\"\"a\"\"\"", CsvUtils.encodeField("\"a\""));
        Assert.assertEquals("foobar", CsvUtils.encodeField("foobar"));
        Assert.assertEquals("\"foo,bar\"", CsvUtils.encodeField("foo,bar"));
        Assert.assertEquals("\",foo,bar,\"", CsvUtils.encodeField(",foo,bar,"));
        Assert.assertEquals("\"\"\",\"\"\"", CsvUtils.encodeField("\",\""));
    }
}
