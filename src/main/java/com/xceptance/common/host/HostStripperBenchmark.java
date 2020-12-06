package com.xceptance.common.host;

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

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class HostStripperBenchmark
{
    private String[] hosts;
    
    @Setup
    public void setup()
    {
        hosts = new String[] 
                        { 
                                "https://production-nam-torrid.demandware.net",
                                "https://production-nam-torrid.demandware.net/",
                                "https://production-nam-torrid.demandware.net?test",
                                "https://production-nam-torrid.demandware.net#jshdj haasd fjas jdhfjash jdhfjasfd?test",
                                "https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2F&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=TORRID+%7C+BOGO+50%25+OFF&dwac=0.8276623877741339&r=-3165066457466333638&ref=",
                                "https://production-nam-torrid.demandware.net/s/torrid/search?cgid=Clothing_WeddingShop_WeddingParty&srule=BestSeller&sz=60&start=0&format=ajax",
                                "https://production-nam-torrid.demandware.net/s/torrid/product/blush-faux-leather-metallic-sneaker-wide-width/11679041.html?cgid=ShoesAccessories_Shoes_Sneakers&source=quickview&format=ajax"
                        };
    }
    
    @Benchmark
    public int original()
    {
        int c = 0;
        for (String s : hosts)
        {
            String h = HostStripper.extractHostNameFromUrl(s);
            c += h.length();
        }
        
        return c;
    }

    @Benchmark
    public int retrieveHostFromUrl()
    {
        int c = 0;
        for (String s : hosts)
        {
            String h = HostStripper.retrieveHostFromUrl(s);
            c += h.length();
        }
        
        return c;
    }

    @Benchmark
    public int indexOf()
    {
        int c = 0;
        for (String s : hosts)
        {
            String h = HostStripper.indexOfOnly(s);
            c += h.length();
        }
        
        return c;
    }
}
