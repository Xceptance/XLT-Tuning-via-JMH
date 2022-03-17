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
import org.openjdk.jmh.annotations.Param;
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
public class XltCharBufferBenchmarkCtr
{
    private final static int count = 200;
    char[] s = "aksdjfa jsdfsadfjashjdf hjashjdfhj ashdkf".toCharArray();
    
    List<char[]> clist = new ArrayList<>(count);

    @Setup
    public void setup()
    {
        final FastRandom r = new FastRandom(13L);
        for (int i = 0; i < count; i++)
        {
            final String s = RandomUtils.randomString(r, 10, 200);
            clist.add(s.toCharArray());
        }
    }

    @Benchmark
    public XltCharBuffer ctr()
    {
        return new XltCharBuffer(s);
    }
    
    @Benchmark
    public XltCharBuffer valueOf()
    {
        return XltCharBuffer.valueOf(s);

    }
}
