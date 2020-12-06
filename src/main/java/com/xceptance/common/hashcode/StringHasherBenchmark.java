package com.xceptance.common.hashcode;

import java.util.concurrent.TimeUnit;

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

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class StringHasherBenchmark
{
    private String DEMO = "http://www.com/foobjshdf hajsfjahsfdh ahsdjfh jahsdjfhjashjfhasjdhfjahsjfhueruweurweurzuwzuw urzwurzuw zuzewur zwuzr uwzeru zwurz uwzruweur ar";
    private String DEMO_LIMITED = "http://www.com/foojsdhjfh asjhfj ahsjfhjasdhfjhasdjfhjsahf jhsdjfh jsadhf jdsjfhsajdhfjdshfjashfjhdsjfhuehruwezruewzruwzeurzweurzuewzruewrbar#uzwuzuiwer";
    
    @Setup(Level.Invocation)
    public void setup()
    {
        DEMO = new String(DEMO.toCharArray());
        DEMO_LIMITED = new String(DEMO_LIMITED.toCharArray());
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
    public int combinedHashNone()
    {
        return StringHasher.combinedHashStringUntil(DEMO, '#');
    }
    @Benchmark
    public int combinedHashLimited()
    {
        return StringHasher.combinedHashStringUntil(DEMO_LIMITED, '#');
    }    
}
