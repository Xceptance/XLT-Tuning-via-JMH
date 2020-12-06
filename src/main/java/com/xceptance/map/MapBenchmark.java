package com.xceptance.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

import com.xceptance.common.util.FastRandom;
import com.xceptance.common.util.RandomUtils;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 3  , timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class MapBenchmark
{
    private String EMPTY = "EMPTY";
    private static final int SIZE = 1000;
    private static final int FACTOR = 10;
    
    private static List<String> strings = new ArrayList<>();
    
    final LRUHashMap<String, String> cmap = new LRUHashMap<>(FACTOR * SIZE);
    final HashMap<String, String> hmap = new HashMap<>(FACTOR * SIZE);
    final FastHashMap<String, String> fmap = new FastHashMap<>(FACTOR * SIZE, 0.5f);
    final LRUFastHashMap<String, String> lrufmap = new LRUFastHashMap<>(FACTOR * SIZE);
    final LRUInPlaceHashMap<String, String> imap = new LRUInPlaceHashMap<>(FACTOR * SIZE);

    @Setup
    public void setup()
    {   
        final List<String> tmp = new ArrayList<>();
        
        final FastRandom r = new FastRandom(7L);
        for (int i = 0; i < SIZE; i++)
        {
            String s = RandomUtils.randomString(r, r.nextInt(5) + 10);
            strings.add(s);
//            cmap.put(s, s);
//            hmap.put(s, s);
//            imap.put(s, s);
//            fmap.put(s, s);
//            lrufmap.put(s, s);
        }
//        
//        for (int i = 0; i < FACTOR; i++)
//        {
//            strings.addAll(tmp);
//        }
    }
    
    @Benchmark
    public int lru()
    {
        int hit = 0;
        int miss = 0;
        
        int sum = 0;
        for (String s : strings)
        {
            String r = cmap.get(s);
            if (r == null)
            {
                miss++;
                r = EMPTY;
                cmap.put(s,  EMPTY);
            }
            else
            {
                hit++;
            }
            sum += r.length();
        }

        return sum;
    }
    
    @Benchmark
    public int lruFast()
    {
        int hit = 0;
        int miss = 0;
        
        int sum = 0;
        for (String s : strings)
        {
            String r = lrufmap.get(s);
            if (r == null)
            {
                miss++;
                r = EMPTY;
                lrufmap.put(s,  EMPTY);
            }
            else
            {
                hit++;
            }
            sum += r.length();
        }

        return sum;
    }

    @Benchmark
    public int fast()
    {
        int hit = 0;
        int miss = 0;
        
        int sum = 0;
        for (String s : strings)
        {
            String r = fmap.get(s);
            if (r == null)
            {
                miss++;
                r = EMPTY;
                fmap.put(s,  EMPTY);
            }
            else
            {
                hit++;
            }
            sum += r.length();
        }

        return sum;
    }
    
    @Benchmark
    public int hashmap()
    {
        int hit = 0;
        int miss = 0;
        
        int sum = 0;
        for (String s : strings)
        {
            String r = hmap.get(s);
            if (r == null)
            {
                miss++;
                r = EMPTY;
                hmap.put(s,  EMPTY);
            }
            else
            {
                hit++;
            }
            sum += r.length();
        }

        return sum;
    }
    
    @Benchmark
    public int inplace()
    {
        int hit = 0;
        int miss = 0;
        
        int sum = 0;
        for (String s : strings)
        {
            String r = imap.get(s);
            if (r == null)
            {
                miss++;
                r = EMPTY;
                imap.put(s,  EMPTY);
            }
            else
            {
                hit++;
            }
            sum += r.length();
        }
        
        return sum;
    }
//    
//    @Benchmark
//    public int basic()
//    {
//        int sum = 0;
//        for (String s : strings)
//        {
//            String r = bmap.get(s);
//            if (r == null)
//            {
//                r = EMPTY;
//                bmap.put(s,  EMPTY);
//            }
//            sum += r.length();
//        }
//        
//        return sum;
//    }
}
