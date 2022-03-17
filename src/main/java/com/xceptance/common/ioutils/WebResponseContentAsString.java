package com.xceptance.common.ioutils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
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

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class WebResponseContentAsString
{
    InputStream in;
    private final static Charset UTF8 = Charset.forName("UTF-8");
    private final static String PATH = WebResponseContentAsString.class.getPackageName().replace('.', '/');

    @Setup(Level.Invocation)
    @Before
    public void setup()
    {
        try
        {
            in = this.getClass().getClassLoader().getResourceAsStream(PATH + "/bbw.html");
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Benchmark
    public int v4()
    { 
        try
        {
            return IOUtils2.toString(in, UTF8).length();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return 0;
    }
    
    @Benchmark
    public int v3()
    { 
        try
        {
            return IOUtils2.toString(in, UTF8).length();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return 0;
    }
    
    @Benchmark
    public int v2()
    { 
        try
        {
            return IOUtils.toString(in, UTF8).length();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return 0;
    }

    @Benchmark
    public int v1()
    { 
        try
        {
            return org.apache.commons.io.IOUtils.toString(in, UTF8).length();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return 0;
    }
}
