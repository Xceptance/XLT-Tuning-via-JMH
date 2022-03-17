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
public class XltCharBufferBenchmarkHashCode
{
    private final static int count = 200;
    
    @Param({"50"})
//    @Param({"5", "10", "32", "50", "100", "200"})
    private int length = 0;

    List<XltCharBuffer> stringList = new ArrayList<>(count);

    @Setup
    public void setup()
    {
        final FastRandom r = new FastRandom(113L);
        for (int i = 0; i < count; i++)
        {
            final String s = RandomUtils.randomString(r, length, length);
            stringList.add(XltCharBuffer.valueOf(s));
        }
    }

    @Benchmark
    public long hashCode_Classic()
    {
        long sum = 0;

        for (int i = 0; i < count; i++)
        {
            sum += stringList.get(i).hashCodeClassic();
        }

        return sum;
    }
    
//    @Benchmark
//    public long hashCode_NoMull()
//    {
//        long sum = 0;
//
//        for (int i = 0; i < count; i++)
//        {
//            sum += stringList.get(i).hashCodeNoMul();
//        }
//
//        return sum;
//    }
//
//    @Benchmark
//    public long hashCode_v0()
//    {
//        long sum = 0;
//
//        for (int i = 0; i < count; i++)
//        {
//            sum += stringList.get(i).hashCodeV0();
//        }
//
//        return sum;
//    }
//    
//    @Benchmark
//    public long hashCode_v0_for()
//    {
//        long sum = 0;
//
//        for (int i = 0; i < count; i++)
//        {
//            sum += stringList.get(i).hashCodeV0_for();
//        }
//
//        return sum;
//    }
//    
//    @Benchmark
//    public long hashCode_v1()
//    {
//        long sum = 0;
//
//        for (int i = 0; i < count; i++)
//        {
//            sum += stringList.get(i).hashCodeV1();
//        }
//
//        return sum;
//    }
//    
//    @Benchmark
//    public long hashCode_v2()
//    {
//        long sum = 0;
//
//        for (int i = 0; i < count; i++)
//        {
//            sum += stringList.get(i).hashCodeV2();
//        }
//
//        return sum;
//    }

//    @Benchmark
//    public long hashCode_vectored()
//    {
//        long sum = 0;
//
//        for (int i = 0; i < count; i++)
//        {
//            sum += stringList.get(i).hashCodeVectored();
//        }
//
//        return sum;
//    }

    @Benchmark
    public long hashCode_vectored_JDK19()
    {
        long sum = 0;

        for (int i = 0; i < count; i++)
        {
            sum += stringList.get(i).hashCodeVectoredJDK19();
        }

        return sum;
    }

    @Benchmark
    public long hashCode_vectored_JDK19_adjusted()
    {
        long sum = 0;

        for (int i = 0; i < count; i++)
        {
            sum += stringList.get(i).hashCodeVectoredJDK19Adjusted();
        }

        return sum;
    }
}
