package com.xceptance.common.hashcode;

import java.util.concurrent.TimeUnit;

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

import com.xceptance.common.util.XltCharBuffer;

import static org.junit.Assert.*;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class StringHasherBenchmark
{
    private String DEMO = "http://www.com/foobjshdf hajsfjahsfdh ahsdjfh jahsdjfhjashjfhasjdhfjahsjfhueruweurweurzuwzuw urzwurzuw zuzewur zwuzr uwzeru zwurz uwzruweur ar";
    private String DEMO_LIMITED = DEMO + "#uzwuzuiwer";
    private XltCharBuffer DEMO_XLTCB;
    private XltCharBuffer DEMO_LIMITED_XLTCB;
    
    @Setup(Level.Invocation)
    public void setup()
    {
        DEMO = new String(DEMO.toCharArray());
        DEMO_LIMITED = new String(DEMO_LIMITED.toCharArray());
        
        DEMO_XLTCB = XltCharBuffer.valueOf(DEMO);
        DEMO_LIMITED_XLTCB = XltCharBuffer.valueOf(DEMO_LIMITED);
    }
    
    @Benchmark
    public int originalHashNone()
    {
        return StringHasher.originalHashStringUntil(DEMO, '#');
    }
    @Benchmark
    public int originalHashLimited()
    {
        return StringHasher.originalHashStringUntil(DEMO_LIMITED, '#');
    }

    @Benchmark
    public int hashNone()
    {
        return StringHasher.hashStringUntil(DEMO, '#');
    }
    @Benchmark
    public int hashLimited()
    {
        return StringHasher.hashStringUntil(DEMO_LIMITED, '#');
    }
    
    @Benchmark
    public int hash109None()
    {
        return StringHasher.hashStringUntil109(DEMO, '#');
    }
    @Benchmark
    public int hash109Limited()
    {
        return StringHasher.hashStringUntil109(DEMO_LIMITED, '#');
    }
    
    
    @Benchmark
    public int hashFNP1ANone()
    {
        return StringHasher.hashStringUntilFNP1A(DEMO, '#');
    }
    @Benchmark
    public int hashFNP1ALimited()
    {
        return StringHasher.hashStringUntilFNP1A(DEMO_LIMITED, '#');
    }
    
    @Benchmark
    public int hashShiftingNone()
    {
        return StringHasher.hashShiftingHint(DEMO, '#');
    }
    @Benchmark
    public int hashShiftingLimited()
    {
        return StringHasher.hashShiftingHint(DEMO_LIMITED, '#');
    }

    @Benchmark
    public int hashShiftingFullNone()
    {
        return StringHasher.hashShiftingFull(DEMO_XLTCB, '#');
    }
    @Benchmark
    public int hashShiftingFullLimited()
    {
        return StringHasher.hashShiftingFull(DEMO_LIMITED_XLTCB, '#');
    }

    
//    @Benchmark
//    public int hashShiftingNone_XLTCB()
//    {
//        return StringHasher.hashStringUntilShifting(DEMO_LIMITED, '#');
//    }
//    @Benchmark
//    public int hashShiftingLimited_XLTCB()
//    {
//        return StringHasher.hashStringUntilShifting(DEMO_LIMITED_XLTCB, '#');
//    }
//    
//    @Benchmark
//    public int xcbHashNone()
//    {
//        return StringHasher.hashStringViaXltCharBuffer(DEMO_XLTCB, '#');
//    }
//    @Benchmark
//    public int xcbHashLimited()
//    {
//        return StringHasher.hashStringViaXltCharBuffer(DEMO_LIMITED_XLTCB, '#');
//    }    
//
//    @Benchmark
//    public int xcbLastHashNone()
//    {
//        return StringHasher.hashStringViaXltCharBufferLast(DEMO_XLTCB, '#');
//    }
//    @Benchmark
//    public int xcbLastHashLimited()
//    {
//        return StringHasher.hashStringViaXltCharBufferLast(DEMO_LIMITED_XLTCB, '#');
//    }  
//    
//    @Benchmark
//    public int xcbStrHashNone()
//    {
//        return StringHasher.hashStringViaString(DEMO_XLTCB, '#');
//    }
//    @Benchmark
//    public int xcbStrHashLimited()
//    {
//        return StringHasher.hashStringViaString(DEMO_LIMITED_XLTCB, '#');
//    }    
    
}
