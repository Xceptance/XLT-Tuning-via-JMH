package com.xceptance.common.parsenumbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
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

import com.xceptance.common.util.XltCharBuffer;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class ParseNumbersBenchmarkBufferVsSequence
{
    private Random r = new Random(7L);
    private static int SIZE = 1000;

    private List<XltCharBuffer> longsXltCharBuffer = new ArrayList<>();
    private List<XltCharBuffer> intsXltCharBuffer = new ArrayList<>();
    
    @Setup
    @Before
    public void setup()
    {
        for (int i = 0; i < SIZE; i++)
        {
            String lo = String.valueOf(Math.abs(r.nextLong()));
            String in = String.valueOf(Math.abs(r.nextInt(10000)));
            
            longsXltCharBuffer.add(new XltCharBuffer(lo.toCharArray()));
            intsXltCharBuffer.add(new XltCharBuffer(in.toCharArray()));
        }
    }
    
    
    @Benchmark
    public long long_XltCharBuffer()
    {
        long c = 0;
        for (int i = 0; i < longsXltCharBuffer.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned12_current_charsequence.ParseNumbersXltCharBuffer.parseLong(longsXltCharBuffer.get(i));
            c += l;
        }
        
        return c;
    }
    
    @Benchmark
    public long long_CharSequence()
    {
        long c = 0;
        for (int i = 0; i < longsXltCharBuffer.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned12_current_charsequence.ParseNumbersCharSequence.parseLong(longsXltCharBuffer.get(i));
            c += l;
        }
        
        return c;
    }
    
}
