package com.xceptance.common.parseboolean;

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
public class ParseBooleanBenchmark
{
    String[] bool = {"true", "true", "true", "false", "true", "false", "true", "false", "false"};
    XltCharBuffer[] xbool = new XltCharBuffer[bool.length]; 
    
    @Setup
    @Before
    public void setup()
    {
        for (int i = 0; i < bool.length; i++)
        {
            xbool[i] = XltCharBuffer.valueOf(bool[i]);
        }
    }
    
    @Benchmark
    public boolean v00_orig()
    {
        boolean c = true;
        for (int i = 0; i < bool.length; i++)
        {
            c = c & Boolean.parseBoolean(bool[i]);
        }
        
        return c;
    }
    
    @Benchmark
    public boolean v00_xorig()
    {
        boolean c = true;
        for (int i = 0; i < xbool.length; i++)
        {
            c = c & Boolean.parseBoolean(xbool[i].toString());
        }
        
        return c;
    }

    @Benchmark
    public boolean v01_xoptimized()
    {
        boolean c = true;
        for (int i = 0; i < xbool.length; i++)
        {
            c = c & com.xceptance.common.parseboolean.v01.ParseBoolean.parse(xbool[i]);
        }
        
        return c;
    }
    @Benchmark
    public boolean v02_xnoearlyabort()
    {
        boolean c = true;
        for (int i = 0; i < xbool.length; i++)
        {
            c = c & com.xceptance.common.parseboolean.v02.ParseBoolean.parse(xbool[i]);
        }
        
        return c;
    }
    @Benchmark
    public boolean v03_xorder()
    {
        boolean c = true;
        for (int i = 0; i < xbool.length; i++)
        {
            c = c & com.xceptance.common.parseboolean.v03.ParseBoolean.parse(xbool[i]);
        }
        
        return c;
    }
    @Benchmark
    public boolean v04_xIPC()
    {
        boolean c = true;
        for (int i = 0; i < xbool.length; i++)
        {
            c = c & com.xceptance.common.parseboolean.v04.ParseBoolean.parse(xbool[i]);
        }
        
        return c;
    }
}
