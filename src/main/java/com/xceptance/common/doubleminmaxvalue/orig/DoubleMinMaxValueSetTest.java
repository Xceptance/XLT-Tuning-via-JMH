package com.xceptance.common.doubleminmaxvalue.orig;

import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests the {@link DoubleMinMaxValueSet} class.
 */
public class DoubleMinMaxValueSetTest
{
    private int minIndex;

    @Before
    public void setUp()
    {
        minIndex = Integer.MAX_VALUE;
    }

    @Test
    public void testBasics()
    {
        final DoubleMinMaxValueSet minMaxValueSet = new DoubleMinMaxValueSet();

        minMaxValueSet.addOrUpdateValue(10000, 1);
        minMaxValueSet.addOrUpdateValue(11000, 1);
        minMaxValueSet.addOrUpdateValue(12000, 1);
        minMaxValueSet.addOrUpdateValue(1034000, 1);
        minMaxValueSet.addOrUpdateValue(1034000, 1);
        minMaxValueSet.addOrUpdateValue(1034000, 5);
        minMaxValueSet.addOrUpdateValue(1000, 1);
        minMaxValueSet.addOrUpdateValue(1034000, 1);
    }

    @Test
    public void testRandomWithDefaultSize()
    {
        testRandom(DoubleMinMaxValueSet.DEFAULT_SIZE);
    }

    @Test
    public void testRandomWithSpecifiedSize()
    {
        testRandom(12345);
    }

    @Test
    public void testShrinking()
    {
        final DoubleMinMaxValueSet minMaxValueSet = new DoubleMinMaxValueSet();
        final double[] values = new double[100000];

        for (int i = 0; i < values.length; i++)
        {
            final long time = i * 1000;

            minMaxValueSet.addOrUpdateValue(time, 1);
            updateValue(values, time);
        }

        final double[] condensedValues = condenseValues(values, minMaxValueSet.getSize(), minMaxValueSet.getScale());
        assertEquals(condensedValues, minMaxValueSet);
    }

    @Test
    public void testShiftingAndShrinking()
    {
        final DoubleMinMaxValueSet minMaxValueSet = new DoubleMinMaxValueSet();
        final double[] values = new double[100000];

        for (int i = values.length - 1; i >= 0; i--)
        {
            final long time = i * 1000;

            minMaxValueSet.addOrUpdateValue(time, 1);
            updateValue(values, time);
        }

        final double[] condensedValues = condenseValues(values, minMaxValueSet.getSize(), minMaxValueSet.getScale());
        assertEquals(condensedValues, minMaxValueSet);
    }

    private void assertEquals(final double[] values, final DoubleMinMaxValueSet minMaxValueSet)
    {
        final DoubleMinMaxValue[] minMaxValues = minMaxValueSet.getValues();

        for (int i = 0; i < minMaxValues.length; i++)
        {
            final double v1 = values[i];
            final double v2 = (minMaxValues[i] == null) ? 0 : minMaxValues[i].getAccumulatedValue();

            Assert.assertTrue(v1 == v2);
        }
    }

    private double[] condenseValues(final double[] values, final int size, final int scale)
    {
        final double[] v = new double[2 * size];

        minIndex = minIndex / scale * scale;

        for (int i = minIndex, j = 0; j < v.length; i = i + scale, j++)
        {
            for (int k = 0; k < scale; k++)
            {
                if ((i + k) < values.length)
                {
                    v[j] += values[i + k];
                }
            }
        }

        return v;
    }

    private void testRandom(final int size)
    {
        final DoubleMinMaxValueSet minMaxValueSet = new DoubleMinMaxValueSet(size);
        final double[] values = new double[100000];
        final Random rng = new Random();

        for (int i = 0; i < values.length; i++)
        {
            final long time = rng.nextInt(values.length * 1000);

            minMaxValueSet.addOrUpdateValue(time, 1);
            updateValue(values, time);
        }

        final double[] condensedValues = condenseValues(values, minMaxValueSet.getSize(), minMaxValueSet.getScale());
        assertEquals(condensedValues, minMaxValueSet);
    }

    private void updateValue(final double[] values, final long time)
    {
        final int index = (int) (time / 1000);

        values[index]++;

        if (index < minIndex)
        {
            minIndex = index;
        }
    }

    @Test
    public void testConstructor()
    {
        final DoubleMinMaxValueSet set = new DoubleMinMaxValueSet(128);
        Assert.assertEquals(1, set.getScale());
        Assert.assertEquals(128, set.getSize());
    }

    @Test
    public void testSimpleTest()
    {
        final long from = 10000000L;
        final int size = 128;
        final DoubleMinMaxValueSet set = new DoubleMinMaxValueSet(size);

        for (int i = 0; i < size; i++)
        {
            set.addOrUpdateValue(from + (i * 1000L), 10);
        }

        Assert.assertEquals((from / 1000L * 1000L), set.getFirstSecond());
        Assert.assertEquals(from, set.getMinimumTime());
        Assert.assertEquals(from + (size - 1) * 1000L, set.getMaximumTime());
        Assert.assertEquals(128, set.getValueCount());

        final DoubleMinMaxValue[] actual = set.getValues();
        Assert.assertEquals(size, actual.length);

        final DoubleMinMaxValue[] expected = new DoubleMinMaxValue[size];
        for (int i = 0; i < size; i++)
        {
            expected[i] = new DoubleMinMaxValue(10);
        }
        Assert.assertArrayEquals(expected, actual);
    }

    /**
     * TODO: Ignored until we decide whether DoubleMinMaxValueSet.getValues() can return up to size or 2x size values.
     */
    @Test
    @Ignore
    public void testSimpleTest_Scale2_128values_64target()
    {
        final int scale = 2;
        final long from = 10000000L;
        final int size = 128;
        final DoubleMinMaxValueSet set = new DoubleMinMaxValueSet(size / scale);

        for (int i = 0; i < size; i++)
        {
            set.addOrUpdateValue(from + (i * 1000L), 10);
        }

        Assert.assertEquals((from / 1000L * 1000L), set.getFirstSecond());
        Assert.assertEquals(from, set.getMinimumTime());
        Assert.assertEquals(from + (size - 1) * 1000L, set.getMaximumTime());
        Assert.assertEquals(128, set.getValueCount());

        final DoubleMinMaxValue[] actual = set.getValues();
        Assert.assertEquals(size / scale, actual.length);

        final DoubleMinMaxValue[] expected = new DoubleMinMaxValue[size / scale];
        for (int i = 0; i < size / scale; i++)
        {
            expected[i] = new DoubleMinMaxValue(10);
            expected[i].merge(new DoubleMinMaxValue(10));
        }
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testSimpleTest_Scale2_256values_128target()
    {
        final long from = 10000000L;
        final int size = 128;
        final DoubleMinMaxValueSet set = new DoubleMinMaxValueSet(size);

        for (int i = 0; i < size; i++)
        {
            set.addOrUpdateValue(from + (i * 1000L), 10);
            set.addOrUpdateValue(from + (i * 1000L) + 10, 12); // new value with 10 ms offset
        }

        Assert.assertEquals((from / 1000L * 1000L), set.getFirstSecond());
        Assert.assertEquals(from, set.getMinimumTime());
        Assert.assertEquals(from + (size - 1) * 1000L + 10, set.getMaximumTime());
        Assert.assertEquals(256, set.getValueCount());

        final DoubleMinMaxValue[] actual = set.getValues();
        Assert.assertEquals(size, actual.length);

        final DoubleMinMaxValue[] expected = new DoubleMinMaxValue[size];
        for (int i = 0; i < size; i++)
        {
            expected[i] = new DoubleMinMaxValue(10);
            expected[i].merge(new DoubleMinMaxValue(12));
        }
        Assert.assertArrayEquals(expected, actual);
    }

    /**
     * TODO: Ignored until we decide whether DoubleMinMaxValueSet.getValues() can return up to size or 2x size values.
     */
    @Test
    @Ignore
    public void testSimpleTest_CauseCompress()
    {
        final long from = 10000000L;
        final int size = 128;
        final DoubleMinMaxValueSet set = new DoubleMinMaxValueSet(size);

        for (int i = 0; i < size; i++)
        {
            set.addOrUpdateValue(from + (i * 1000L), 10);
        }
        for (int i = size; i < 2 * size; i++)
        {
            set.addOrUpdateValue(from + (i * 1000L) + 10, 30);
        }

        Assert.assertEquals((from / 1000L * 1000L), set.getFirstSecond());
        Assert.assertEquals(from, set.getMinimumTime());
        Assert.assertEquals(from + (2 * size - 1) * 1000L + 10, set.getMaximumTime());
        Assert.assertEquals(256, set.getValueCount());

        final DoubleMinMaxValue[] actual = set.getValues();
        Assert.assertEquals(size, actual.length);

        final DoubleMinMaxValue[] expected = new DoubleMinMaxValue[size];
        for (int i = 0; i < size / 2; i++)
        {
            expected[i] = new DoubleMinMaxValue(10);
            expected[i].merge(new DoubleMinMaxValue(10));
        }
        for (int i = size / 2; i < size; i++)
        {
            expected[i] = new DoubleMinMaxValue(30);
            expected[i].merge(new DoubleMinMaxValue(30));
        }
        Assert.assertArrayEquals(expected, actual);
    }
}
