package com.xceptance.common.html4;

import java.util.concurrent.TimeUnit;

import org.apache.commons.text.StringEscapeUtils;
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

import com.google.common.html.HtmlEscapers;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class HTMLEntityEncoding
{
    String[] VALUES = {
            "foo bar test",
            "<>äöü\"",
            "Das ist ein Teest & toll"
    };
    
    @Setup
    @Before
    public void setup()
    {
    }
    
    @Benchmark
    public int apache()
    {
        int sum = 0;
        for (var v: VALUES)
        {
            sum += StringEscapeUtils.escapeHtml4(v).length();
        }
        
        return sum;
    }

    @Benchmark
    public int guava()
    {
        int sum = 0;
        for (var v: VALUES)
        {
            sum += HtmlEscapers.htmlEscaper().escape(v).length();
        }
        
        return sum;
    }
    
}