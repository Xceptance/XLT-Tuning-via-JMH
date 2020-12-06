package com.xceptance.common.parsenumbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class ParseNumbersBenchmark
{
    private Random r = new Random(7L);
    private static int SIZE = 1500;
    private List<String> longs = new ArrayList<>();
    private List<String> doubles = new ArrayList<>();
    private List<String> ints = new ArrayList<>();
    private List<char[]> longsChar = new ArrayList<>();
    private List<char[]> intsChar = new ArrayList<>();
    
    private double[] afterDecimals = {0.1, 0.25, 0.5, 0.125, 0.8, 0.9817, 0.34, 0.333, 0.651451, 0.9876543, 0.01, 0.0001, 0.1243, 0.32, 0.8};

    @Setup
    @Before
    public void setup()
    {
        for (int i = 0; i < SIZE; i++)
        {
            String lo = String.valueOf(Math.abs(r.nextLong()));
            String in = String.valueOf(Math.abs(r.nextInt(10000)));
            longs.add(lo);
            ints.add(in);
            
            doubles.add(String.valueOf(r.nextInt(1000) * Math.pow(10, -r.nextInt(3))));
            
            longsChar.add(lo.toCharArray());
            intsChar.add(in.toCharArray());
        }
    }
    
    @Benchmark
    public int defaultLong()
    {
        int c = 0;
        for (String s : longs)
        {
            long l = Long.parseLong(s);
            c += l;
        }
        
        return c;
    }

    @Benchmark
    public int defaultInteger()
    {
        int c = 0;
        for (int i = 0; i < ints.size(); i++)
        {
            int l = Integer.parseInt(ints.get(i));
            c += l;
        }
        
        return c;
    }
    
    @Benchmark
    public double defaultDouble()
    {
        double c = 0;
        for (int i = 0; i < doubles.size(); i++)
        {
            double l = Double.parseDouble(doubles.get(i));
            c += l;
        }
        
        return c;
    }
    
    @Benchmark
    public int customLong()
    {
        int c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.orig.ParseNumbers.parseLong(longs.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark
    public int customInteger()
    {
        int c = 0;
        for (int i = 0; i < ints.size(); i++)
        {
            int l = com.xceptance.common.parsenumbers.orig.ParseNumbers.parseInt(ints.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark
    public int tuned1Long()
    {
        int c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned1.ParseNumbers.parseLong(longs.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark
    public int tuned1Integer()
    {
        int c = 0;
        for (int i = 0; i < ints.size(); i++)
        {
            int l = com.xceptance.common.parsenumbers.tuned1.ParseNumbers.parseInt(ints.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark
    public int tuned2Long()
    {
        int c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned2.ParseNumbers.parseLong(longs.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark
    public int tuned2Integer()
    {
        int c = 0;
        for (int i = 0; i < ints.size(); i++)
        {
            int l = com.xceptance.common.parsenumbers.tuned2.ParseNumbers.parseInt(ints.get(i));
            c += l;
        }
        
        return c;
    }
   
    @Benchmark
    public int tuned3Long()
    {
        int c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned3.ParseNumbers.parseLong(longs.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public int tuned3Integer()
    {
        int c = 0;
        for (int i = 0; i < ints.size(); i++)
        {
            int l = com.xceptance.common.parsenumbers.tuned3.ParseNumbers.parseInt(ints.get(i));
            c += l;
        }
        
        return c;
    }
    
    @Benchmark
    public int tuned4Long()
    {
        int c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned4.ParseNumbers.parseLong(longs.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public int tuned4Integer()
    {
        int c = 0;
        for (int i = 0; i < ints.size(); i++)
        {
            int l = com.xceptance.common.parsenumbers.tuned4.ParseNumbers.parseInt(ints.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark
    public int tuned5Long_char_nochecks_shortloop()
    {
        int c = 0;
        for (int i = 0; i < longsChar.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned5_fail.ParseNumbers.parseLong(longsChar.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public int tuned5Integer_char_nochecks_shortloop()
    {
        int c = 0;
        for (int i = 0; i < intsChar.size(); i++)
        {
            int l = com.xceptance.common.parsenumbers.tuned5_fail.ParseNumbers.parseInt(intsChar.get(i));
            c += l;
        }
        
        return c;
    }
    
    @Benchmark
    public int tuned6Long_char()
    {
        int c = 0;
        for (int i = 0; i < longsChar.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned6_fail.ParseNumbers.parseLong(longsChar.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public int tuned6Integer_char()
    {
        int c = 0;
        for (int i = 0; i < intsChar.size(); i++)
        {
            int l = com.xceptance.common.parsenumbers.tuned6_fail.ParseNumbers.parseInt(intsChar.get(i));
            c += l;
        }
        
        return c;
    }
    
    @Benchmark
    public int tuned7Long_char_nochecks_longloop()
    {
        int c = 0;
        for (int i = 0; i < longsChar.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned7_fail.FastParseNumbers.fastParseLong(longsChar.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public int tuned7Integer_char_nochecks_longloop()
    {
        int c = 0;
        for (int i = 0; i < intsChar.size(); i++)
        {
            int l = com.xceptance.common.parsenumbers.tuned7_fail.FastParseNumbers.fastParseInt(intsChar.get(i));
            c += l;
        }
        
        return c;
    }
    

    @Benchmark 
    public double tuned8Double()
    {
        double c = 0;
        for (int i = 0; i < doubles.size(); i++)
        {
            double l = com.xceptance.common.parsenumbers.tuned8_double.ParseNumbers.parseDouble(doubles.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public double tuned9DoubleMultiplierList()
    {
        double c = 0;
        for (int i = 0; i < doubles.size(); i++)
        {
            double l = com.xceptance.common.parsenumbers.tuned9_double.ParseNumbers.parseDouble(doubles.get(i));
            c += l;
        }
        
        return c;
    }
}
