package com.xceptance.common.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
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

import com.xceptance.common.util.SimpleArrayList;
import com.xceptance.lang.OpenStringBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class NonStringBufferedReader_Benchmark
{
    InputStream in;
    
    @Setup(Level.Invocation)
    @Before
    public void setup()
    {
//        in = this.getClass().getClassLoader().getResourceAsStream("com/xceptance/common/io/big10k.txt");
        try
        {
            in = new FileInputStream("/tmp/big10k.txt");
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Benchmark
    public int o1riginal()
    { 
        final SimpleArrayList<String> result = new SimpleArrayList<>(10000);
        try (final BufferedReader r = new BufferedReader(new InputStreamReader(in)))
        {
            String s = null;
            while ((s = r.readLine()) != null)
            {
                result.add(s);
            }
        }
        catch (IOException e)
        {
            Assert.fail();
        }
        
        return result.size();
    }

    @Benchmark
    public int o2ptimized()
    { 
        final SimpleArrayList<OpenStringBuilder> result = new SimpleArrayList<>(10000);
        try (final NonStringBufferedReader r = new NonStringBufferedReader(new InputStreamReader(in)))
        {
            OpenStringBuilder osb = null;
            while ((osb = r.readLine()) != null)
            {
                result.add(osb);
            }
        }
        catch (IOException e)
        {
            Assert.fail();
        }
        
        return result.size();
    }
}
