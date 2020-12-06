package com.xceptance.common.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

/**
 * Checks that a page has at least one closing HTML tag. Does not check, that this tag is the only one. It uses a
 * regular expression.
 * <p>
 * HTML comments are permitted after the closing HTML tag. Anything else will be logged as warning.
 * </p>
 * <p>
 * It also assumes a lower-case closing HTML tag according to the HTML/XHTML standard.
 * </p>
 * 
 * @author René Schwietzke (Xceptance Software Technologies GmbH)
 */
public class HtmlEndTagValidator
{
    /**
     * Regular expression that matches the closing HTML tag followed by any content.
     */
    private static final String CL_HTML_REGEX = "(?ism)</(?:body|frameset)>(\\s|<!--.*?-->)*</html>(.*)$";

    /**
     * Regular expression used to check for regular trailing content.
     */
    private static final String REGULAR_TRAILING_CONTENT_REGEX = "(?sm)(\\s|<!--.*?-->)*";

    /**
     * The pattern to be use on the page.
     */
    private final Pattern pattern;

    /**
     * Pattern used to check for regular trailing content.
     */
    private final Pattern trailingContentPattern;

    /**
     * Constructor.
     * <p>
     * Declared as private to prevent external instantiation.
     * </p>
     */
    private HtmlEndTagValidator()
    {
        pattern = Pattern.compile(CL_HTML_REGEX);
        trailingContentPattern = Pattern.compile(REGULAR_TRAILING_CONTENT_REGEX);
    }

    /**
     * The validation as plain string method to be tested and used independently from the page.
     * 
     * @param content
     *            a snippet of HTML code to be checked
     */
    public void validate(final String content)
    {
        // parameter validation
        Assert.assertNotNull("The page content is null", content);
        Assert.assertTrue("The page is empty", content.length() > 0);

        // try a small size first to save time
        final String truncated = content.substring((int) (content.length() * 0.90));

        Matcher matcher = pattern.matcher(truncated);

        if (!matcher.find())
        {
            // try the long version
            matcher = pattern.matcher(content);
            Assert.assertTrue("html endtag not found", matcher.find());
        }

        final String trailingContent = matcher.group(2);
        matcher = trailingContentPattern.matcher(trailingContent);
        if (!matcher.matches())
        {
            System.out.println("Only whitespace and XML comments are allowed after closing HTML tag.");
        }
    }

    /**
     * Returns the singleton instance.
     * 
     * @return the singleton instance
     */
    public static HtmlEndTagValidator getInstance()
    {
        return HtmlEndTagValidator_Singleton.instance;
    }

    /**
     * Singleton implementation of {@link HtmlEndTagValidator}.
     */
    private static class HtmlEndTagValidator_Singleton
    {
        /**
         * Singleton instance.
         */
        private static final HtmlEndTagValidator instance;

        // static initializer (synchronized by class loader)
        static
        {
            instance = new HtmlEndTagValidator();
        }
    }
}
