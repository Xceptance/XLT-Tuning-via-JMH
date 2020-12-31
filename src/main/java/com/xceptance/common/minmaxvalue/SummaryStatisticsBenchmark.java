package com.xceptance.common.minmaxvalue;

import java.util.concurrent.TimeUnit;

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

import it.unimi.dsi.util.FastRandom;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class SummaryStatisticsBenchmark
{
    int SIZE = 10000;
    int[] values = new int[SIZE];
    
    @Setup
    public void setup()
    {
        final FastRandom r = new FastRandom(13L);
        for (int i = 0; i < values.length; i++)
        {
            values[i] = r.nextInt(30000);
        }
    }
    
    @Setup(Level.Invocation)
    public void setup2()
    {
    }
    
    @Benchmark
    public int orig()
    {
        final com.xceptance.common.minmaxvalue.orig.SummaryStatistics stat = new com.xceptance.common.minmaxvalue.orig.SummaryStatistics();
        
        for (int i = 0; i < values.length; i++)
        {
            stat.addValue(values[i]);
        }
        
        return stat.getMaximum();
    }
    
    @Benchmark
    public int tune1()
    {
        final com.xceptance.common.minmaxvalue.tune1.SummaryStatistics stat = new com.xceptance.common.minmaxvalue.tune1.SummaryStatistics();

        for (int i = 0; i < values.length; i++)
        {
            stat.addValue(values[i]);
        }
        
        return stat.getMaximum();
    }
}