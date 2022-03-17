package com.xceptance.common.math;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import com.xceptance.common.util.ArrayUtils;

import it.unimi.dsi.util.FastRandom;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class PowOrSquare
{
    int SIZE = 256;
    final FastRandom r = new FastRandom(2912817L);
    final int[] data = new int[SIZE];
    
    @Setup
    @Before
    public void setup()
    {
        for (int i = 0; i < SIZE; i++)
        {
            data[i] = r.nextInt(0, 30000);
        }
    }

    @Benchmark
    public double mul()
    {
        double sum = 0;
        for (int i = 0; i < SIZE; i++)
        {
            sum += data[i] * data[i];;
        }
        
        return sum;
    }    

    @Benchmark
    public double pow()
    {
        double sum = 0;
        for (int i = 0; i < SIZE; i++)
        {
            sum += Math.pow(data[i], 2);
        }
        
        return sum;
    }  
    
}