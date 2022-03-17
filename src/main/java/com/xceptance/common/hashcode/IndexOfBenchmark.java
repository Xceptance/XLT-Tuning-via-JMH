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
public class IndexOfBenchmark
{
    private String DEMO;
    private String DEMO_NONE;
    
    private XltCharBuffer XDEMO_NONE;
    private XltCharBuffer XDEMO;
    
    @Setup(Level.Invocation)
    public void setup()
    {
        var s = "http://www.com/" + System.currentTimeMillis() + "hajsfjr";
        s += "092838298398928398 3898293892893849289nc23n9829nc89n2839nc8293894c2893489nc28394c829389c289348c29n849c2938";
        s += "092838298398928398 3898293892893849289nc23n9829nc89n2839nc8293894c2893489nc28394c829389c289348c29n849c2938";
        s += "092838298398928398 3898293892893849289nc23n9829nc89n2839nc8293894c2893489nc28394c829389c289348c29n849c2938";
        s += "092838298398928398 3898293892893849289nc23n9829nc89n2839nc8293894c2893489nc28394c829389c289348c29n849c2938";
        s += "092838298398928398 3898293892893849289nc23n9829nc89n2839nc8293894c2893489nc28394c829389c289348c29n849c2938";
        s += "092838298398928398 3898293892893849289nc23n9829nc89n2839nc8293894c2893489nc28394c829389c289348c29n849c2938";
        var h = "#9876546789876";
        
        DEMO_NONE = new String(s);
        DEMO = new String(s + h);
        
        XDEMO_NONE = XltCharBuffer.valueOf(s);
        XDEMO = XltCharBuffer.valueOf(s + h);
    }
    
    @Benchmark
    public int noneString()
    {
        return DEMO_NONE.indexOf('#');
    }
    @Benchmark
    public int string()
    {
        return DEMO.indexOf('#');
    }

    @Benchmark
    public int noneString_l()
    {
        return DEMO_NONE.lastIndexOf('#');
    }
    @Benchmark
    public int string_l()
    {
        return DEMO.lastIndexOf('#');
    }

    @Benchmark
    public int noneX()
    {
        return XDEMO_NONE.indexOf('#');
    }
    @Benchmark
    public int X()
    {
        return XDEMO.indexOf('#');
    }

    @Benchmark
    public int noneX_l()
    {
        return XDEMO_NONE.lastIndexOf('#');
    }
    @Benchmark
    public int X_l()
    {
        return XDEMO.lastIndexOf('#');
    }
    
}
