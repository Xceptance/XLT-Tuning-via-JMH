package com.xceptance.common.csvutils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
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

import com.xceptance.common.lang.OpenStringBuilder;
import com.xceptance.common.util.SimpleArrayList;
import com.xceptance.common.util.XltCharBuffer;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class CsvUtilsBenchmarkParseFromFile
{
    SimpleArrayList<String> lines;
    SimpleArrayList<OpenStringBuilder> linesOSB;

    @Setup(Level.Invocation)
    @Before
    public void setup()
    {
        File FILE = new File("/tmp/timers-100k.csv");
        lines = new SimpleArrayList<>(100000);
        linesOSB = new SimpleArrayList<>(100000);

        try (BufferedReader br = new BufferedReader(new FileReader(FILE)))
        {
            String s; while ((s = br.readLine()) != null)
            {
                lines.add(s);
                linesOSB.add(new OpenStringBuilder(s));
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
    public int tuned412() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final String s = lines.get(i);
            String[] result = com.xceptance.common.csvutils.tuned412.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    }

    @Benchmark
    public int tuned4121() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final String s = lines.get(i);
            String[] result = com.xceptance.common.csvutils.tuned4121.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned500() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final String s = lines.get(i);
            List<String> result = com.xceptance.common.csvutils.tuned500.CsvUtils.decode(s);
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned501() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final String s = lines.get(i);
            List<String> result = com.xceptance.common.csvutils.tuned501.CsvUtils.decode(s);
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned502() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final String s = lines.get(i);
            String[] result = com.xceptance.common.csvutils.tuned502.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned503() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final String s = lines.get(i);
            List<String> result = com.xceptance.common.csvutils.tuned503_rewrite.CsvUtilsDecode.parse(s);
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned504() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final String s = lines.get(i);
            final SimpleArrayList<String> result = com.xceptance.common.csvutils.tuned504_simplelist.CsvUtilsDecode.parse(s);
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned505() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final String s = lines.get(i);
            final List<char[]> result = com.xceptance.common.csvutils.tuned505_tuned503.CsvUtilsDecode.parse(s);
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned510() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final String s = lines.get(i);
            final List<String> result = com.xceptance.common.csvutils.tuned510.CsvUtilsDecode.parse(s);
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned510b() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final String s = lines.get(i);
            final List<String> result = com.xceptance.common.csvutils.tuned510b.CsvUtilsDecode.parse(s);
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned511() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final String s = lines.get(i);
            final List<CharBuffer> result = com.xceptance.common.csvutils.tuned511.CsvUtilsDecode.parse(s);
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned512() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final String s = lines.get(i);
            final List<XltCharBuffer> result = com.xceptance.common.csvutils.tuned512_labels.CsvUtilsDecode.parse(s);
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned512OSB() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < linesOSB.size(); i++)
        {
            final OpenStringBuilder s = linesOSB.get(i);
            final List<XltCharBuffer> result = com.xceptance.common.csvutils.tuned512_labels.CsvUtilsDecode.parse(s.getCharArray(), ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned513() throws IOException
    { 
        int sum = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final String s = lines.get(i);
            final List<String> result = com.xceptance.common.csvutils.tuned513_labels_string.CsvUtilsDecode.parse(s);
            sum += result.size();
        }
        
        return sum;
    }
}