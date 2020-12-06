package com.xceptance.common.minmaxvalue;

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
import com.xceptance.common.util.FastRandom;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class MinMaxValueSetBenchmark
{
    final FastRandom r = new FastRandom(7L);
    
    static long NOW = 1586014895254L;
    static int SIZE = 1000 * 60 * 60 ;
    long[] times = new long[SIZE];
    int[] values = new int[SIZE];
        
    com.xceptance.common.minmaxvalue.tune1.MinMaxValueSet setTune1;
    com.xceptance.common.minmaxvalue.orig.MinMaxValueSet setOrig ;
    com.xceptance.common.minmaxvalue.orig.MinMaxValueSet setTune2;

    @Setup
    @Before
    public void setup()
    {
        for (int i = 0; i < SIZE; i++)
        {
            times[i] = NOW + r.nextInt(50) - 50;
            values[i] = r.nextInt(60000);
        }
        
        ArrayUtils.shuffle(r, times);

    }
    
    @Setup(Level.Invocation)
    public void setupPerI()
    {
        setTune1 = new com.xceptance.common.minmaxvalue.tune1.MinMaxValueSet();
        setOrig = new com.xceptance.common.minmaxvalue.orig.MinMaxValueSet();
        setTune2 = new com.xceptance.common.minmaxvalue.orig.MinMaxValueSet();
    }
    

    @Benchmark
    public int orig()
    {
        for (int i = 0; i < SIZE; i++)
        {
            setOrig.addOrUpdateValue(times[i], values[i]);
        }
        
        return values[0];
    }

    @Benchmark
    @Test
    public int tune1()
    {
        for (int i = 0; i < SIZE; i++)
        {
            setTune1.addOrUpdateValue(times[i], values[i]);
        }        
        
        return values[0];
    }
    

    @Benchmark
    public int tune2()
    {
        for (int i = 0; i < SIZE; i++)
        {
            setTune2.addOrUpdateValue(times[i], values[i]);
        }             
        return values[0];
    }
    
}