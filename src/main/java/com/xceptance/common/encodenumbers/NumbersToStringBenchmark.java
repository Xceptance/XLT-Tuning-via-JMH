package com.xceptance.common.encodenumbers;

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

import com.xceptance.common.encodenumbers.v01.JDKLong;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class NumbersToStringBenchmark
{
    private Random r = new Random(17L);
    private static int SIZE = 100;
    private long[] longs = new long[SIZE];

    @Setup
    @Before
    public void setup()
    {
        for (int i = 0; i < SIZE; i++)
        {
            longs[i] = r.nextLong(); 
        }
        for (int i = 0; i < SIZE / 2; i++)
        {
            longs[i] = r.nextInt(); 
        }
    }
    
    @Benchmark
    public long longDefault()
    {
        long c = 0;
        for (int i = 0; i < longs.length; i++)
        {
            long l = Long.toString(longs[i]).length();
            c += l;
        }
        
        return c;
    }
//    @Benchmark
//    public long jdkLongDefault()
//    {
//        long c = 0;
//        for (int i = 0; i < longs.length; i++)
//        {
//            long l = JDKLong.toString(longs[i]).length();
//            c += l;
//        }
//        
//        return c;
//    }    
    @Benchmark
    public long v01()
    {
        long c = 0;
        for (int i = 0; i < longs.length; i++)
        {
            long l = com.xceptance.common.encodenumbers.v01.StringifyNumbers.toString(longs[i]).length();
            c += l;
        }
        
        return c;
    }
}
