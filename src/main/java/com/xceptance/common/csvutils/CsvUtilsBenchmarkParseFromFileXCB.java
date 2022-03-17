package com.xceptance.common.csvutils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
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
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import com.xceptance.common.util.SimpleArrayList;
import com.xceptance.common.util.XltCharBuffer;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class CsvUtilsBenchmarkParseFromFileXCB
{
    SimpleArrayList<String> lines;
    SimpleArrayList<XltCharBuffer> linesXCB;

    @Setup(Level.Invocation)
    @Before
    public void setup()
    {
        File FILE = new File("/tmp/timers.csv");
        lines = new SimpleArrayList<>(100000);
        linesXCB = new SimpleArrayList<>(100000);

        try (BufferedReader br = new BufferedReader(new FileReader(FILE)))
        {
            String s; while ((s = br.readLine()) != null)
            {
                lines.add(s);
                linesXCB.add(XltCharBuffer.valueOf(s));
            }
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @TearDown(Level.Invocation)
    public void teardown()
    {
        lines = null;
    }
    
    @Benchmark
    public int original() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final String s = lines.get(i);
            
            String[] result = com.xceptance.common.csvutils.original411.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    }

    @Benchmark
    public int tuned514a() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < linesXCB.size(); i++)
        {
            final XltCharBuffer s = linesXCB.get(i);
            final List<XltCharBuffer> result = com.xceptance.common.csvutils.tuned514a.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned514b() throws IOException
    { 
        int sum = 0;
        final SimpleArrayList<XltCharBuffer> list = new SimpleArrayList<XltCharBuffer>(32);
        
        for (int i = 0; i < linesXCB.size(); i++)
        {
            final XltCharBuffer s = linesXCB.get(i);
            final List<XltCharBuffer> result = com.xceptance.common.csvutils.tuned514b.CsvUtilsDecode.parse(list, s, ',');
            list.clear();
            
            sum += result.size();
        }
        
        return sum;
    }
}