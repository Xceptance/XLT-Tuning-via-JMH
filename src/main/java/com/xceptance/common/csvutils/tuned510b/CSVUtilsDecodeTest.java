package com.xceptance.common.csvutils.tuned510b;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CSVUtilsDecodeTest
{
    private void test(String s, String... expected) 
    {
        String _s = s.replace("'", "\"");
        
        List<String> result = CsvUtilsDecode.parse(_s);
        
//        System.out.printf("%s - %s - %s%n", _s, 
//                        Arrays.stream(expected).collect(Collectors.joining("][", "[", "]")), 
//                        Arrays.stream(result.toArray()).map(c -> c.toString()).collect(Collectors.joining("][", "[", "]")));
        
        Assert.assertArrayEquals(expected, result.toArray());
    }

    /**
     * All test cases use ' for definition but will run them with ", just
     * to aid the visuals here
     */
    
    @Test
    public void empty() 
    {
        test(" ", " ");
        test("", "");
    }

    @Test
    public void col1() 
    {
        test("a", "a");
        test("ab", "ab");
        test("abc", "abc");
        test("abc def", "abc def");
    }

    @Test
    public void emptyStart() 
    {
        test(",a", "", "a");
        test(",a,b", "", "a", "b");
    }

    @Test
    public void emptyEnd() 
    {
        test("a,", "a", "");
        test("a,b,", "a", "b", "");
        test("aaa,bbb,", "aaa", "bbb", "");
    }
    
    @Test
    public void happy() 
    {
        test("a,foo,bar,123,1232,7,true", "a", "foo", "bar", "123", "1232", "7", "true");
    }

    @Test
    public void happyAndEmpty() 
    {
        test("a,foo,bar,123,1232,,,7,true,,", "a", "foo", "bar", "123", "1232", "", "", "7", "true", "", "");
    }

    
    @Test
    public void col1_long() 
    {
        test("foobar", "foobar");
    }
    @Test
    public void col1Quoted() 
    {
        test("\"a\"", "a");
    }
    @Test
    public void col1Quoted_long() 
    {
        test("\"foobar\"", "foobar");
    }
    @Test
    public void col2() 
    {
        test("a,b", "a", "b");
        test("aa,bb", "aa", "bb");
    }
    @Test
    public void col2Quoted() 
    {
        test("\"a\",\"b\"", "a", "b");
        test("\"aa\",\"bb\"", "aa", "bb");
    }
    @Test
    public void quotedQuotes() 
    {
        test("'a''a'", "a\"a");
//        test("a''a", "a\"a"); // invalid
        test("''", "");
        test("''''", "\"");
    }

    @Test
    public void emptyCols() 
    {
        test("a,,,b,", "a", "", "", "b", "");
        test("a,,b", "a", "", "b");
        test("a,,,b", "a", "", "", "b");
        test(",", "", "");
    }


}
