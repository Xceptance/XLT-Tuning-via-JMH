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

import com.xceptance.common.util.XltCharBuffer;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class ParseNumbersBenchmark
{
    private Random r = new Random(7L);
    private static int SIZE = 10000;
    private List<String> longs = new ArrayList<>();
    private List<String> doubles = new ArrayList<>();
    private List<char[]> doublesChar = new ArrayList<>();
    private List<XltCharBuffer> doublesXltCharBuffer = new ArrayList<>();

    private List<String> ints = new ArrayList<>();
    private List<char[]> longsChar = new ArrayList<>();
    private List<char[]> intsChar = new ArrayList<>();
    private List<XltCharBuffer> longsXltCharBuffer = new ArrayList<>();
    private List<XltCharBuffer> intsXltCharBuffer = new ArrayList<>();
    
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
            
            final String s = String.valueOf(r.nextInt(1000) + afterDecimals[r.nextInt(afterDecimals.length)]);
            doubles.add(s);
            doublesChar.add(s.toCharArray());
            doublesXltCharBuffer.add(new XltCharBuffer(s.toCharArray()));
            
            longsXltCharBuffer.add(new XltCharBuffer(lo.toCharArray()));
            longsChar.add(lo.toCharArray());
            intsXltCharBuffer.add(new XltCharBuffer(in.toCharArray()));
            intsChar.add(in.toCharArray());
        }
    }
    
    @Benchmark
    public long longDefault()
    {
        long c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = Long.parseLong(longs.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark
    public int integerDefault()
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
    public double doubleDefault()
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
    public long long00_Custom()
    {
        long c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.orig.ParseNumbers.parseLong(longs.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark
    public int integer00_Custom()
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
    public long long01()
    {
        long c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned1.ParseNumbers.parseLong(longs.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark
    public int integer01()
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
    public long long02()
    {
        long c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned2.ParseNumbers.parseLong(longs.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark
    public int integer02()
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
    public long long03()
    {
        long c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned3.ParseNumbers.parseLong(longs.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public int integer03()
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
    public long long04()
    {
        long c = 0;
        for (int i = 0; i < longs.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned4.ParseNumbers.parseLong(longs.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public int integer04()
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
    public long long05_char_nochecks_shortloop()
    {
        long c = 0;
        for (int i = 0; i < longsChar.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned5_fail.ParseNumbers.parseLong(longsChar.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public int integer05_char_nochecks_shortloop()
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
    public long long06_char()
    {
        long c = 0;
        for (int i = 0; i < longsChar.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned6_fail.ParseNumbers.parseLong(longsChar.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public int integer06_char()
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
    public long long07char_nochecks_longloop()
    {
        long c = 0;
        for (int i = 0; i < longsChar.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned7_fail.FastParseNumbers.fastParseLong(longsChar.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public int integer07_char_nochecks_longloop()
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
    public long long10_char_longloop()
    {
        long c = 0;
        for (int i = 0; i < longsChar.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned10.ParseNumbers.parseLong(longsChar.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public int integer10_char_longloop()
    {
        int c = 0;
        for (int i = 0; i < intsChar.size(); i++)
        {
            int l = com.xceptance.common.parsenumbers.tuned10.ParseNumbers.parseInt(intsChar.get(i));
            c += l;
        }
        
        return c;
    }
    
    
    @Benchmark
    public long long11_char_longloop_onelinecalc()
    {
        long c = 0;
        for (int i = 0; i < longsChar.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned11.ParseNumbers.parseLong(longsChar.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public int integer11_char_longloop_onelinecalc()
    {
        int c = 0;
        for (int i = 0; i < intsChar.size(); i++)
        {
            int l = com.xceptance.common.parsenumbers.tuned11.ParseNumbers.parseInt(intsChar.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark
    public long long12_from6_current_best_charArray()
    {
        long c = 0;
        for (int i = 0; i < longsChar.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned12_current.ParseNumbers.parseLong(longsChar.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark
    public long long12_from6_current_best_XltCharBuffer()
    {
        long c = 0;
        for (int i = 0; i < longsChar.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned12_current.ParseNumbers.parseLong(longsXltCharBuffer.get(i).toCharArray());
            c += l;
        }
        
        return c;
    }

    
    @Benchmark
    public long long12_from6_current_best_StringToCharArray()
    {
        long c = 0;
        for (int i = 0; i < longsChar.size(); i++)
        {
            long l = com.xceptance.common.parsenumbers.tuned12_current.ParseNumbers.parseLong(longs.get(i).toCharArray());
            c += l;
        }
        
        return c;
    }
    
    @Benchmark 
    public int integer12_from6_current_best_charArray()
    {
        int c = 0;
        for (int i = 0; i < intsChar.size(); i++)
        {
            int l = com.xceptance.common.parsenumbers.tuned12_current.ParseNumbers.parseInt(intsChar.get(i));
            c += l;
        }
        
        return c;
    }
    
    @Benchmark 
    public int integer12_from6_current_best_XltCharBuffer()
    {
        int c = 0;
        for (int i = 0; i < intsChar.size(); i++)
        {
            int l = com.xceptance.common.parsenumbers.tuned12_current.ParseNumbers.parseInt(intsXltCharBuffer.get(i).toCharArray());
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public int integer12_from6_current_best_StringToCharArrray()
    {
        int c = 0;
        for (int i = 0; i < intsChar.size(); i++)
        {
            int l = com.xceptance.common.parsenumbers.tuned12_current.ParseNumbers.parseInt(ints.get(i).toCharArray());
            c += l;
        }
        
        return c;
    }
    
    /* ==================================================================
     * Double
     */
    
    @Benchmark 
    public double double08()
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
    public double double09_MultiplierList()
    {
        double c = 0;
        for (int i = 0; i < doubles.size(); i++)
        {
            double l = com.xceptance.common.parsenumbers.tuned9_double.ParseNumbers.parseDouble(doubles.get(i));
            c += l;
        }
        
        return c;
    }
    
    @Benchmark 
    public double double10_MultiplierList_longLoop()
    {
        double c = 0;
        for (int i = 0; i < doubles.size(); i++)
        {
            double l = com.xceptance.common.parsenumbers.tuned10.ParseNumbers.parseDouble(doubles.get(i));
            c += l;
        }
        
        return c;
    }

    @Benchmark 
    public double double11()
    {
        double c = 0;
        for (int i = 0; i < doubles.size(); i++)
        {
            double l = com.xceptance.common.parsenumbers.tuned11.ParseNumbers.parseDouble(doubles.get(i));
            c += l;
        }
        
        return c;
    }
    
    @Benchmark 
    public double double12_from9_current_best_charArray()
    {
        double c = 0;
        for (int i = 0; i < doubles.size(); i++)
        {
            double l = com.xceptance.common.parsenumbers.tuned12_current.ParseNumbers.parseDouble(doublesChar.get(i));
            c += l;
        }
        
        return c;
    }
    
    @Benchmark 
    public double double12_from9_current_best_XltCharBuffer()
    {
        double c = 0;
        for (int i = 0; i < doubles.size(); i++)
        {
            double l = com.xceptance.common.parsenumbers.tuned12_current.ParseNumbers.parseDouble(doublesXltCharBuffer.get(i).toCharArray());
            c += l;
        }
        
        return c;
    }
    
    @Benchmark 
    public double double12_from9_current_best_StringToCharArray()
    {
        double c = 0;
        for (int i = 0; i < doubles.size(); i++)
        {
            double l = com.xceptance.common.parsenumbers.tuned12_current.ParseNumbers.parseDouble(doubles.get(i).toCharArray());
            c += l;
        }
        
        return c;
    }
}
