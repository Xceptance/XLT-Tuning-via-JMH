package com.xceptance.common.hashcode;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
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
public class CharBufferHashCodeBenchmark
{
    private String DEMO = "http://www.com/foobjshdf hajsfjahsfdh ahsdjfh jahsdjfhjashjfhasjdhfjahsjfhueruweurweurzuwzuw urzwurzuw zuzewur zwuzr uwzeru zwurz uwzruweur ar";
    private CharBufferHashCode XB;
    private String STR;
    
    @Setup(Level.Invocation)
    public void setup()
    {
        STR = new String(STR.toCharArray());
        XB = CharBufferHashCode.valueOf(STR);
    }
    
    @Benchmark
    public int original()
    {
        return XB.hashCodeClassic();
    }
    
    @Test
    public void test()
    {
        String s1 = "http://www.com/foobar";
        CharBufferHashCode x1 = CharBufferHashCode.valueOf(s1);
        
        Assert.assertEquals(s1.hashCode(), x1.hashCodeClassic());
    }
}
