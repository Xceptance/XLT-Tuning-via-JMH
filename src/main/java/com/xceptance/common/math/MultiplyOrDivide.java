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
public class MultiplyOrDivide
{
    int SIZE = 256;
    final FastRandom r = new FastRandom(2912817L);
    final long[] data = new long[SIZE];
    
    @Setup
    @Before
    public void setup()
    {
        long NOW = System.currentTimeMillis();
        
        for (int i = 0; i < SIZE; i++)
        {
            data[i] = NOW + r.nextInt(3600 * 1000);
        }
        
        ArrayUtils.shuffle(r, data);
    }

    @Benchmark
    public long _1mul()
    {
        long sum = 0;
        for (int i = 0; i < SIZE; i++)
        {
            long r = (long) (data[i] * 0.001);
            sum = sum + r;
        }
        
        return sum;
    }    

    @Benchmark
    public long _2div()
    {
        long sum = 0;
        for (int i = 0; i < SIZE; i++)
        {
            long r = data[i] / 1000L;
            sum = sum + r;
        }
        
        return sum;
    }  
    
}