package com.xceptance.common.validator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class HtmlEndTagValidatorBenchmark
{    
    @Param({"michaels", "soliver"})
    public String customer;
    
    public String content;
    
    @Setup
    public void setup() throws IOException
    {
        content = new String(Files.readAllBytes(Paths.get("/tmp/s3/" + customer + "/index.html")));
        System.out.println(customer + ": " + content.length());
    }

    @Benchmark
    public boolean original()
    {
        try
        {
            HtmlEndTagValidator.getInstance().validate(customer);
            return true;
        }
        catch(Error e)
        {
            return false;
        }
    }
}