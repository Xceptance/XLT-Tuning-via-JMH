package com.xceptance.common.lang;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
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

import com.xceptance.common.util.RandomUtils;
import com.xceptance.common.util.XltCharBuffer;

import it.unimi.dsi.util.FastRandom;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class OpenStringBuilderFastAppend
{
    
    @Param({"10", "100", "500"})
    int length;
    String s1;
    String s2;
    
    @Setup
    @Before
    public void setup()
    {
        final FastRandom r = new FastRandom(13L);
        s1 = RandomUtils.randomString(r, length, length);
        s2 = RandomUtils.randomString(r, length, length);
    }
    
    @Benchmark
    public XltCharBuffer append()
    {
        var o = new OpenStringBuilder(length);
        o.append(s1);
        o.append(s2);
        
        return XltCharBuffer.valueOf(o);
    }
    
    @Benchmark
    public XltCharBuffer fastAppend()
    {
        var o = new OpenStringBuilder(length);
        o.fastAppend(s1);
        o.fastAppend(s2);
        
        return XltCharBuffer.valueOf(o);
    }
}
