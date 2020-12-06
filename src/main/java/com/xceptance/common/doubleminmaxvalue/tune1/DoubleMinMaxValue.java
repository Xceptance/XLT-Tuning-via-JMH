package com.xceptance.common.doubleminmaxvalue.tune1;

/**
 * A {@link DoubleMinMaxValue} stores the minimum/maximum/sum/count of all the sample values added, but can also
 * reproduce a rough approximation of the distinct values added.
 * 
 * @see IntMinMaxValue
 * @author Hartmut Arlt (Xceptance Software Technologies GmbH)
 */
public class DoubleMinMaxValue
{
    private double accumulatedValue;

    private double maximum = Double.MIN_VALUE;
    private double minimum = Double.MAX_VALUE;

    private int valueCount;

    /**
     * Holds an approximation of the distinct values added to this min-max value.
     */
    private final DoubleLowPrecisionValueSet valueSet = new DoubleLowPrecisionValueSet();

    /**
     * Constructor.
     * 
     * @param value
     *            the first value to add
     */
    public DoubleMinMaxValue(final double value)
    {
        valueSet.addValue(value);
        accumulatedValue = value;
        maximum = value;
        minimum = value;
        valueCount = 1;
    }

    /**
     * @return the accumulatedValue
     */
    public double getAccumulatedValue()
    {
        return accumulatedValue;
    }

    /**
     * @return the average value
     */
    public double getAverageValue()
    {
        return accumulatedValue / valueCount;
    }

    /**
     * @return the maximum
     */
    public double getMaximumValue()
    {
        return maximum;
    }

    /**
     * @return the minimum
     */
    public double getMinimumValue()
    {
        return minimum;
    }

    /**
     * @return the average value
     */
    public double getValue()
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
     * @return the valueCount
     */
    public int getValueCount()
    {
        return valueCount;
    }

    /**
     * @param item
     */
    DoubleMinMaxValue merge(final DoubleMinMaxValue item)
    {
        if (item != null)
        {
            maximum = Math.max(maximum, item.maximum);
            minimum = Math.min(minimum, item.minimum);

            accumulatedValue += item.accumulatedValue;
            valueCount += item.valueCount;

            valueSet.merge(item.valueSet);
        }

        return this;
    }

    /**
     */
    @Override
    public String toString()
    {
        return "" + getValue() + "/" + getAccumulatedValue() + "/" + getMinimumValue() + "/" + getMaximumValue() + "/" + getValueCount();
    }

    /**
     * @param sample
     */
    public void updateValue(final double sample)
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
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(accumulatedValue);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(maximum);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(minimum);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + valueCount;
        result = prime * result + ((valueSet == null) ? 0 : valueSet.hashCode());
        return result;
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
        DoubleMinMaxValue other = (DoubleMinMaxValue) obj;
        if (Double.doubleToLongBits(accumulatedValue) != Double.doubleToLongBits(other.accumulatedValue))
        {
            return false;
        }
        if (Double.doubleToLongBits(maximum) != Double.doubleToLongBits(other.maximum))
        {
            return false;
        }
        if (Double.doubleToLongBits(minimum) != Double.doubleToLongBits(other.minimum))
        {
            return false;
        }
        if (valueCount != other.valueCount)
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
        return true;
    }
}
