package com.xceptance.common.doubleminmaxvalue.tune1;

import org.junit.Assert;
import org.junit.Test;

public class DoubleMinMaxValueTest
{
    @Test
    public final void testDoubleMinMaxValue()
    {
        final DoubleMinMaxValue set = new DoubleMinMaxValue(0);
        Assert.assertEquals(0, set.getAccumulatedValue(), 0.0);
        Assert.assertEquals(0, set.getAverageValue(), 0.0);
        Assert.assertEquals(0, set.getMaximumValue(), 0.0);
        Assert.assertEquals(0, set.getMinimumValue(), 0.0);
        Assert.assertEquals(0, set.getValue(), 0.0);
        Assert.assertEquals(1, set.getValueCount());
    }

    @Test
    public final void testDoubleMinMaxValueInt_0()
    {
        final DoubleMinMaxValue set = new DoubleMinMaxValue(0);
        Assert.assertEquals(0, set.getAccumulatedValue(), 0.0);
        Assert.assertEquals(0, set.getAverageValue(), 0.0);
        Assert.assertEquals(0, set.getMaximumValue(), 0.0);
        Assert.assertEquals(0, set.getMinimumValue(), 0.0);
        Assert.assertEquals(0, set.getValue(), 0.0);
        Assert.assertEquals(1, set.getValueCount());
    }

    @Test
    public final void testDoubleMinMaxValueInt_1()
    {
        final DoubleMinMaxValue set = new DoubleMinMaxValue(1);
        Assert.assertEquals(1, set.getAccumulatedValue(), 0.0);
        Assert.assertEquals(1, set.getAverageValue(), 0.0);
        Assert.assertEquals(1, set.getMaximumValue(), 0.0);
        Assert.assertEquals(1, set.getMinimumValue(), 0.0);
        Assert.assertEquals(1, set.getValue(), 0.0);
        Assert.assertEquals(1, set.getValueCount());
    }

    @Test
    public final void testDoubleMinMaxValueInt_neg2()
    {
        final DoubleMinMaxValue set = new DoubleMinMaxValue(-2);
        Assert.assertEquals(-2, set.getAccumulatedValue(), 0.0);
        Assert.assertEquals(-2, set.getAverageValue(), 0.0);
        Assert.assertEquals(-2, set.getMaximumValue(), 0.0);
        Assert.assertEquals(-2, set.getMinimumValue(), 0.0);
        Assert.assertEquals(-2, set.getValue(), 0.0);
        Assert.assertEquals(1, set.getValueCount());
    }

    @Test
    public final void testUpdateValue()
    {
        final DoubleMinMaxValue set = new DoubleMinMaxValue(0);
        set.updateValue(2.0);

        Assert.assertTrue(2.0 == set.getAccumulatedValue());
        Assert.assertTrue(1.0 == set.getAverageValue());
        Assert.assertTrue(2.0 == set.getMaximumValue());
        Assert.assertTrue(0.0 == set.getMinimumValue());
        Assert.assertTrue(1.0 == set.getValue());
        Assert.assertEquals(2, set.getValueCount());

        // next max
        set.updateValue(10);

        Assert.assertTrue(12.0 == set.getAccumulatedValue());
        Assert.assertTrue(4.0 == set.getAverageValue());
        Assert.assertTrue(10.0 == set.getMaximumValue());
        Assert.assertTrue(0.0 == set.getMinimumValue());
        Assert.assertTrue(4.0 == set.getValue());
        Assert.assertEquals(3, set.getValueCount());

        // next min
        set.updateValue(-2);

        Assert.assertTrue(10 == set.getAccumulatedValue());
        Assert.assertEquals(2.5, set.getAverageValue(), 0.0);
        Assert.assertTrue(10  == set.getMaximumValue());
        Assert.assertTrue(-2 == set.getMinimumValue());
        Assert.assertEquals(2.5, set.getValue(), 0.0);
        Assert.assertEquals(4, set.getValueCount());

        // next middle value
        set.updateValue(2);
        Assert.assertTrue(12 == set.getAccumulatedValue());
        Assert.assertEquals(2.4,  set.getAverageValue(), 0.0);
        Assert.assertTrue(10 == set.getMaximumValue());
        Assert.assertTrue(-2 == set.getMinimumValue());
        Assert.assertEquals(2.4, set.getValue(), 0.0);
        Assert.assertEquals(5, set.getValueCount());
    }

    @Test
    public final void testMerge()
    {
        final DoubleMinMaxValue set1 = new DoubleMinMaxValue(10);
        final DoubleMinMaxValue set2 = new DoubleMinMaxValue(20);
        final DoubleMinMaxValue set = new DoubleMinMaxValue(0);

        set.merge(set1);
        Assert.assertEquals(10, set.getAccumulatedValue(), 0.0);
        Assert.assertEquals(5, set.getAverageValue(), 0.0);
        Assert.assertEquals(10, set.getMaximumValue(), 0.0);
        Assert.assertEquals(0, set.getMinimumValue(), 0.0);
        Assert.assertEquals(5, set.getValue(), 0.0);
        Assert.assertEquals(2, set.getValueCount());

        set.merge(set2);
        Assert.assertEquals(30, set.getAccumulatedValue(), 0.0);
        Assert.assertEquals(10, set.getAverageValue(), 0.0);
        Assert.assertEquals(20, set.getMaximumValue(), 0.0);
        Assert.assertEquals(0, set.getMinimumValue(), 0.0);
        Assert.assertEquals(10, set.getValue(), 0.0);
        Assert.assertEquals(3, set.getValueCount());
    }

    @Test
    public final void testToString()
    {
        final DoubleMinMaxValue set = new DoubleMinMaxValue(88);
        Assert.assertEquals("88.0/88.0/88.0/88.0/1", set.toString());

        set.updateValue(12);
        Assert.assertEquals("50.0/100.0/12.0/88.0/2", set.toString());
    }

    @Test
    public final void testEquals()
    {
        final DoubleMinMaxValue set = new DoubleMinMaxValue(76210);
        Assert.assertFalse(set.equals(null));
        Assert.assertFalse(set.equals("Foo"));
        Assert.assertTrue(set.equals(set));
        Assert.assertTrue(set.equals(new DoubleMinMaxValue(76210)));
        Assert.assertFalse(set.equals(new DoubleMinMaxValue(6210)));
    }

}
