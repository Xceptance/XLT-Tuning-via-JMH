package com.xceptance.common.parsenumbers.tuned14;

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
public class ParseNumbersBenchmark
{
    private Random r = new Random(7L);
    private static int SIZE = 1000;
    private List<String> longs = new ArrayList<>();

    @Setup
    @Before
    public void setup()
    {
        for (int i = 0; i < SIZE; i++)
        {
            String lo = String.valueOf(Math.abs(r.nextLong()));
            longs.add(lo);
        }
    }

    @Benchmark
    public long longDefault()
    {
        long c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = Long.parseLong(longs.get(i));
            c += l;
        }

        return c;
    }

    @Benchmark
    public long long12_Current_XLT_7()
    {
        long c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned12_current_charsequence.ParseNumbersCharSequence.parseLong(longs.get(i));
            c += l;
        }

        return c;
    }

    @Benchmark
    public long long14_1()
    {
        long c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned14.ParseNumbers.parseLong1(longs.get(i));
            c += l;
        }

        return c;
    }

    @Benchmark
    public long long14_2()
    {
        long c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned14.ParseNumbers.parseLong2(longs.get(i));
            c += l;
        }

        return c;
    }

    @Benchmark
    public long long14_3()
    {
        long c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned14.ParseNumbers.parseLong3(longs.get(i));
            c += l;
        }

        return c;
    }

    @Benchmark
    public long long14_4()
    {
        long c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned14.ParseNumbers.parseLong4(longs.get(i));
            c += l;
        }

        return c;
    }
}

