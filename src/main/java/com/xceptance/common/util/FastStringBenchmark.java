package com.xceptance.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import it.unimi.dsi.util.FastRandom;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class FastStringBenchmark
{
    private final static int count = 10000;
    List<String> list = new ArrayList<>();
    List<FastString> fastList = new ArrayList<>();
    private final static String DEMO = "http://www.urezurzr.com/foobjshdf/";
    
    @Setup
    public void setup()
    {
        final FastRandom r = new FastRandom(13L);
        for (int i = 0; i < count; i++)
        {
            final String s = DEMO + RandomUtils.randomString(r, 15, 25);
            list.add(s);
            fastList.add(new FastString(s));
        }
    }
    
    @Benchmark
    public int string()
    {
        final Map<String, String> map = new HashMap<>();
        
        for (int i = 0; i < count; i++)
        {
            map.put(list.get(i), DEMO);
        }
        for (int i = 0; i < count; i++)
        {
            map.put(list.get(i), DEMO);
        }        
        return map.size();
    }
  
    @Benchmark
    public int fastString()
    {
        final Map<FastString, String> map = new HashMap<>();
        
        for (int i = 0; i < count; i++)
        {
            map.put(fastList.get(i), DEMO);
        }
        for (int i = 0; i < count; i++)
        {
            map.put(fastList.get(i), DEMO);
        }        
        return map.size();
    }
}
