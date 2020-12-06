package com.xceptance.common.util;

public class ArrayUtils
{
    // Generic function to randomize a list in Java using Fisher–Yates shuffle
    public static<T> void shuffle(final FastRandom random, final long[] list)
    {
        // start from end of the list
        for (int i = list.length - 1; i >= 1; i--)
        {
            // get a random index j such that 0 <= j <= i
            int j = random.nextInt(i + 1);
 
            // swap element at i'th position in the list with element at
            // randomly generated index j
            long obj = list[i];
            list[i] = list[j];
            list[j] = obj;
        }
    }
}
