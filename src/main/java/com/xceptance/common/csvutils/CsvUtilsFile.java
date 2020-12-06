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

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class CsvUtilsFile
{
    BufferedReader br;
    File FILE = new File("/tmp/ramdisk/20191022-230434-torrid-small/ac002_us-east1-b_01/TAddToCart/119/timers.csv");

    @Setup(Level.Invocation)
    @Before
    public void setup()
    {
        try
        {
            br = new BufferedReader(new FileReader(FILE));
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @TearDown(Level.Invocation)
    public void teardown()
    {
        try
        {
            br.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Benchmark
    public int original() throws IOException
    { 
        int sum = 0;
        String line;
        while ((line = br.readLine()) != null)
        {
            String[] result = com.xceptance.common.csvutils.original411.CsvUtils.decode(line);
            sum += result.length;
        }

        return sum;
    }

    @Benchmark
    public int tuned510() throws IOException
    { 
        int sum = 0;
        String line;
        while ((line = br.readLine()) != null)
        {
            List<String> result = com.xceptance.common.csvutils.tuned510.CsvUtilsDecode.parse(line);
            sum += result.size();
        }

        return sum;
    }

    @Benchmark
    public int tuned510b() throws IOException
    { 
        int sum = 0;
        String line;
        while ((line = br.readLine()) != null)
        {
            List<String> result = com.xceptance.common.csvutils.tuned510b.CsvUtilsDecode.parse(line);
            sum += result.size();
        }

        return sum;
    }

}