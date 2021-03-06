package com.xceptance.common.minmaxvalue.tune1;

/**
 * A {@link MinMaxValue} stores the minimum/maximum/sum/count of all the sample values added, but can also reproduce a
 * rough approximation of the distinct values added.
 */
public class MinMaxValue
{
    private long accumulatedValue;

    private int maximum = Integer.MIN_VALUE;

    private int minimum = Integer.MAX_VALUE;

    private int valueCount;

    /**
     * Holds an approximation of the distinct values added to this min-max value.
     */
    private final IntLowPrecisionValueSet valueSet = new IntLowPrecisionValueSet();

    /**
     * Constructor.
     * 
     * @param value
     *            the first value to add
     */
    public MinMaxValue(final int value)
    {
        valueSet.addValue(value);

        accumulatedValue = value;
        maximum = value;
        minimum = value;
        valueCount = 1;
    }

    /**
     * Returns the sum of the values added to this min-max value.
     * 
     * @return the accumulated value
     */
    public long getAccumulatedValue()
    {
        return accumulatedValue;
    }

    /**
     * Returns the average of the values added to this min-max value.
     * 
     * @return the average value
     */
    public int getAverageValue()
    {
        return (int) (accumulatedValue / valueCount);
    }

    /**
     * Returns the maximum of the values added to this min-max value.
     * 
     * @return the maximum value
     */
    public int getMaximumValue()
    {
        return maximum == Integer.MIN_VALUE ? 0 : maximum;
    }

    /**
     * Returns the minimum of the values added to this min-max value.
     * 
     * @return the minimum value
     */
    public int getMinimumValue()
    {
        return minimum == Integer.MAX_VALUE ? 0 : minimum;
    }

    /**
     * Returns the average of the values added to this min-max value.
     * 
     * @return the average value
     */
    public int getValue()
    {
        return getAverageValue();
    }

    /**
     * Returns an approximation of the distinct values added to this min-max value.
     * 
     * @return the values
     */
    public double[] getValues()
    {
        return valueSet.getValues();
    }

    /**
     * Returns the number of values added to this min-max value.
     * 
     * @return the value count
     */
    public int getValueCount()
    {
        return valueCount;
    }

    /**
     * Merges the data of the given min-max value into this min-max value.
     * 
     * @param item
     *            the other value
     */
    MinMaxValue merge(final MinMaxValue item)
    {
        if (item != null)
        {
            // only, if we already have counted something
            maximum = Math.max(maximum, item.maximum);
            minimum = Math.min(minimum, item.minimum);
    
            accumulatedValue += item.accumulatedValue;
            valueCount += item.valueCount;
    
            valueSet.merge(item.valueSet);
        }
            
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return "" + getValue() + "/" + getAccumulatedValue() + "/" + getMinimumValue() + "/" + getMaximumValue() + "/" + getValueCount();
    }

    /**
     * Adds the given sample value to this min-max value.
     * 
     * @param sample
     *            the sample to add
     */
    public void updateValue(final int sample)
    {
        valueSet.addValue(sample);

        maximum = Math.max(maximum, sample);
        minimum = Math.min(minimum, sample);

        accumulatedValue += sample;
        valueCount++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final MinMaxValue other = (MinMaxValue) obj;
        if (accumulatedValue != other.accumulatedValue)
        {
            return false;
        }
        if (valueSet == null)
        {
            if (other.valueSet != null)
            {
                return false;
            }
        }
        else if (!valueSet.equals(other.valueSet))
        {
            return false;
        }
        if (maximum != other.maximum)
        {
            return false;
        }
        if (minimum != other.minimum)
        {
            return false;
        }
        if (valueCount != other.valueCount)
        {
            return false;
        }
        return true;
    }
}
