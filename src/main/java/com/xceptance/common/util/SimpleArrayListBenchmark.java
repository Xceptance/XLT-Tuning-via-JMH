package com.xceptance.common.util;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;


@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class SimpleArrayListBenchmark
{
    private static int SIZE = 10000;
    
    @Setup
    public void setup()
    {
    }
    
    @Benchmark
    public int classicAdd()
    {
        String x = "Just a string";

        final ArrayList<String> list = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++)
        {
            list.add(x);
        }

        return list.size();
    }
    
    @Benchmark
    public int simpleAdd()
    {
        String x = "Just a string";

        final SimpleArrayList<String> list = new SimpleArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++)
        {
            list.add(x);
        }
        
        return list.size();
    }
    
    @Benchmark
    public int simpleAddCache()
    {
        String x = "Just a string";

        final SimpleCacheAwareArrayList<String> list = new SimpleCacheAwareArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++)
        {
            list.add(x);
        }
        
        return list.size();
    }
    
    @Benchmark
    public int arrayAdd()
    {
        String x = "Just a string";
        
        final String[] list = new String[SIZE];
        for (int i = 0; i < SIZE; i++)
        {
            list[i] = x;
        }
        
        return list.length;
    }
}
