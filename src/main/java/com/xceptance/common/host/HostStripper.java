package com.xceptance.common.host;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;


public class HostStripper
{
    public static String extractHostNameFromUrl(final String url)
    {
        String tmp = url;

        // strip protocol if present
        final int startIndex = tmp.indexOf("://");
        if (startIndex != -1)
        {
            tmp = StringUtils.substring(tmp, startIndex + 3);
        }

        // strip path/query/fragment if present (whatever comes first)
        final int endIndex = StringUtils.indexOfAny(tmp, "/?#");
        if (endIndex != -1)
        {
            tmp = StringUtils.substring(tmp, 0, endIndex);
        }

        return tmp;
    }

    final static int MAX_CHAR = Math.max(Math.max('/', '?'), '#');
    
    public static String indexOfOnly(final String url)
    {
        // strip protocol
        int start = url.indexOf("://");
        start = start == -1 ? 0 : start + 3;

        int endIndex = url.indexOf('/', start);
        if (endIndex == -1)
        {
            endIndex = url.indexOf('?', start);
            if (endIndex == -1)
            {
                endIndex = url.indexOf('#', start);
            }

        }

        if (endIndex != -1)
        {
            return url.substring(start, endIndex);
        }
        
        // no end, check if we got a start
        if (start == 0)
        {
            // no start, use the original
            return url;
        }
        else
        {
            // at least we had a start
            return url.substring(start);
        }
    }
    
    /**
     * Gets the host from a url as string as cheaply as possible. Does not pay attention to any special url formats
     * or rules. Mainly meant for report processing 
     * 
     * @param url the url to retrieve the host name from
     * @return the host name in the url or the full url if not host name can be identified
     */
    public static String retrieveHostFromUrl(final String url)
    {
        // strip protocol
        int start = url.indexOf("://");
        start = start == -1 ? 0 : start + 3;

        // strip path/query/fragment if present (whatever comes first)
        final int length = url.length();
        for (int i = start; i < length; i++)
        {
            final char c = url.charAt(i);
            
            // avoid all three comparison by checking for a lot of
            // not relevant chars first
            if (c <= MAX_CHAR && (c == '/' || c == '?' || c == '#'))
            {
                return url.substring(start, i);
            }
        }
        
        // no end, check if we got a start
        if (start == 0)
        {
            // no start, use the original
            return url;
        }
        else
        {
            // at least we had a start
            return url.substring(start);
        }
    }
    
    @Test
    public void testOriginal()
    {
        Assert.assertEquals("www.foo.bar", extractHostNameFromUrl("www.foo.bar"));
        Assert.assertEquals("www.foo.bar", extractHostNameFromUrl("http://www.foo.bar"));
        Assert.assertEquals("www.foo.bar", extractHostNameFromUrl("http://www.foo.bar?"));
        Assert.assertEquals("www.foo.bar", extractHostNameFromUrl("http://www.foo.bar#"));
        Assert.assertEquals("www.foo.bar", extractHostNameFromUrl("http://www.foo.bar/"));
        Assert.assertEquals("www.foo.bar", extractHostNameFromUrl("http://www.foo.bar/?"));
        Assert.assertEquals("www.foo.bar", extractHostNameFromUrl("http://www.foo.bar/test/index.html"));
    }

    @Test
    public void testRegex()
    {
        Assert.assertEquals("www.foo.bar", retrieveHostFromUrl("http://www.foo.bar"));
        Assert.assertEquals("www.foo.bar", retrieveHostFromUrl("http://www.foo.bar?"));
        Assert.assertEquals("www.foo.bar", retrieveHostFromUrl("http://www.foo.bar#"));
        Assert.assertEquals("www.foo.bar", retrieveHostFromUrl("http://www.foo.bar/"));
        Assert.assertEquals("www.foo.bar", retrieveHostFromUrl("http://www.foo.bar/?"));
        Assert.assertEquals("www.foo.bar", retrieveHostFromUrl("http://www.foo.bar/test/index.html"));
        Assert.assertEquals("www.foo.bar", retrieveHostFromUrl("www.foo.bar?81"));
        Assert.assertEquals("www.foo.bar", retrieveHostFromUrl("www.foo.bar#test"));
        Assert.assertEquals("www.foo.bar", retrieveHostFromUrl("www.foo.bar/foo"));
        Assert.assertEquals("www.foo.bar", retrieveHostFromUrl("www.foo.bar"));
        Assert.assertEquals("", retrieveHostFromUrl(""));
        Assert.assertEquals("", retrieveHostFromUrl("/"));
        Assert.assertEquals("", retrieveHostFromUrl("?"));
        Assert.assertEquals("", retrieveHostFromUrl("#"));

        Assert.assertEquals("www", retrieveHostFromUrl("://www"));
    }
    
    @Test
    public void indexOfOnly()
    {
        Assert.assertEquals("www.foo.bar", indexOfOnly("http://www.foo.bar"));
        Assert.assertEquals("www.foo.bar", indexOfOnly("http://www.foo.bar?"));
        Assert.assertEquals("www.foo.bar", indexOfOnly("http://www.foo.bar#"));
        Assert.assertEquals("www.foo.bar", indexOfOnly("http://www.foo.bar/"));
        Assert.assertEquals("www.foo.bar", indexOfOnly("http://www.foo.bar/?"));
        Assert.assertEquals("www.foo.bar", indexOfOnly("http://www.foo.bar/test/index.html"));
        Assert.assertEquals("www.foo.bar", indexOfOnly("www.foo.bar?81"));
        Assert.assertEquals("www.foo.bar", indexOfOnly("www.foo.bar#test"));
        Assert.assertEquals("www.foo.bar", indexOfOnly("www.foo.bar/foo"));
        Assert.assertEquals("www.foo.bar", indexOfOnly("www.foo.bar"));
        Assert.assertEquals("", indexOfOnly(""));
        Assert.assertEquals("", indexOfOnly("/"));
        Assert.assertEquals("", indexOfOnly("?"));
        Assert.assertEquals("", indexOfOnly("#"));

        Assert.assertEquals("www", indexOfOnly("://www"));
    }
}
