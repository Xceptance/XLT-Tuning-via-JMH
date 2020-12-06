package com.xceptance.common.csvutils;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
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

import com.xceptance.common.util.SimpleArrayList;
import com.xceptance.common.util.XltCharBuffer;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class CsvUtilsBenchmarkParse
{
    @Param({"10", "2", "5", "1"})
    private int FACTOR;
    
    private List<String> slines = new ArrayList<>();
    private List<String> squotedLines = new ArrayList<>();

    private List<char[]> clines = new ArrayList<>();
    private List<char[]> cquotedLines = new ArrayList<>();  

    
    @Setup
    public void setup()
    {
        String[] lines = new String[21];
        String[] quotedLines = new String[21];

        lines[0] = "R,Homepage.1,1474304849886,250,false,323,24527,200,http://production-anything-ab.anyhoster.xyz/,text/html,2,0,244,3,246,249,,,,,0";
        lines[1] = "R,Homepage.2,1474304850162,26,false,955,3130,200,http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/Newsletter-DialogForm?dialog=true&format=ajax,text/html,0,0,24,0,24,24,,,,,0";
        lines[2] = "R,Homepage.3,1474304850310,14,false,1206,377,200,http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2F&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=Cole+Haan%3A+Shoes%2C+Bags+%26+Accessories+for+Men%2C+Women+%26+Kids&fake=10001197739313&ref=,image/gif,0,0,14,0,14,14,,,,,0";
        lines[3] = "A,Homepage,1474304849886,439,false";
        lines[4] = "R,GoToSignUp.1,1474304855967,46,false,891,3540,200,http://production-anything-ab.anyhoster.xyz/s/ColeHaan_US/account-register,text/html,2,0,43,0,45,45,,,,,0";
        lines[5] = "R,GoToSignUp.2,1474304856128,13,false,1261,377,200,http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2F&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=Cole+Haan%3A+Shoes%2C+Bags+%26+Accessories+for+Men%2C+Women+%26+Kids&fake=10007015123408&ref=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2F,image/gif,0,0,12,0,12,12,,,,,0";
        lines[6] = "A,GoToSignUp,1474304855967,175,false";
        lines[7] = "R,Register.1,1474304862192,2519,false,2001,4858,302,https://production-anything-ab.anyhoster.xyz/s/ColeHaan_US/account-register?dwcont=C1182483558,text/html,1,11,0,2511,8,2519,,,,,0";
        lines[8] = "R,Register.2,1474304864712,397,false,868,23149,200,http://production-anything-ab.anyhoster.xyz/,text/html,3,0,389,4,392,396,,,,,0";
        lines[9] = "R,Register.3,1474304865124,52,false,967,800,200,http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/Account-ThankYou,text/html,0,0,51,0,51,51,,,,,0";
        lines[10] = "R,Register.4,1474304865266,12,false,1302,377,200,http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2F&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=Cole+Haan%3A+Shoes%2C+Bags+%26+Accessories+for+Men%2C+Women+%26+Kids&fake=10016153348536&ref=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2F,image/gif,0,0,11,0,11,11,,,,,0";
        lines[11] = "A,Register,1474304862191,3087,false";
        lines[12] = "R,Logout.1,1474304870277,67,false,929,2033,302,http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/Login-Logout,text/html,2,0,63,0,65,65,,,,,0";
        lines[13] = "R,Logout.2,1474304870344,466,false,1387,24820,200,https://production-anything-ab.anyhoster.xyz/s/ColeHaan_US/account,text/html,1,6,0,460,5,465,,,,,0";
        lines[14] = "R,Logout.3,1474304870950,12,false,1413,437,200,https://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=https%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Faccount&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=Cole+Haan+Account+Login+%7C+Cole+Haan&fake=10021837570387&ref=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2F,image/gif,0,0,11,0,11,11,,,,,0";
        lines[15] = "A,Logout,1474304870276,686,false";
        lines[16] = "R,SelectCategory.1,1474304875451,1080,false,870,38427,200,http://production-anything-ab.anyhoster.xyz/s/ColeHaan_US/womens-whats-new-new-shoes,text/html,2,0,1070,7,1072,1079,,,,,0";
        lines[17] = "R,SelectCategory.2,1474304876694,14,false,1345,377,200,http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Fwomens-whats-new-new-shoes&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=New+Womens+Shoes+%3A+Sneakers%2C+Flats+%26+More+%7C+Cole+Haan&fake=10027581275695&ref=https%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Faccount,image/gif,0,0,13,0,13,13,,,,,0";
        lines[18] = "A,SelectCategory,1474304875450,1258,false";
        lines[19] = "R,RefineByCategory.1,1474304880577,460,false,879,34633,200,http://production-anything-ab.anyhoster.xyz/s/ColeHaan_US/womens-2zerogrand,text/html,0,0,458,0,458,458,,,,,0";
        lines[20] = "R,RefineByCategory.2,1474304881165,13,false,1327,377,200,http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Fwomens-2zerogrand&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=Womens+2.ZEROGRAND+Collection+%7C+Cole+Haan&fake=10032052865709&ref=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Fwomens-whats-new-new-shoes,image/gif,0,0,12,0,12,12,,,,,0";
    
        quotedLines[0] = "R,Homepage.1,1474304849886,250,false,323,24527,200,\"http://production-anything-ab.anyhoster.xyz/\",text/html,2,0,244,3,246,249,,,,,0";
        quotedLines[1] = "R,Homepage.2,1474304850162,26,false,955,3130,200,\"http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/Newsletter-DialogForm?dialog=true&format=ajax\",text/html,0,0,24,0,24,24,,,,,0";
        quotedLines[2] = "R,Homepage.3,1474304850310,14,false,1206,377,200,\"http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2F&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=Cole+Haan%3A+Shoes%2C+Bags+%26+Accessories+for+Men%2C+Women+%26+Kids&fake=10001197739313&ref=,image/gif,0,0,14,0,14,14,,,,,0";
        quotedLines[3] = "A,Homepage,1474304849886,439,false";
        quotedLines[4] = "R,GoToSignUp.1,1474304855967,46,false,891,3540,200,\"http://production-anything-ab.anyhoster.xyz/s/ColeHaan_US/account-register\",text/html,2,0,43,0,45,45,,,,,0";
        quotedLines[5] = "R,GoToSignUp.2,1474304856128,13,false,1261,377,200,\"http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2F&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=Cole+Haan%3A+Shoes%2C+Bags+%26+Accessories+for+Men%2C+Women+%26+Kids&fake=10007015123408&ref=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2F,image/gif,0,0,12,0,12,12,,,,,0";
        quotedLines[6] = "A,GoToSignUp,1474304855967,175,false";
        quotedLines[7] = "R,Register.1,1474304862192,2519,false,2001,4858,302,\"https://production-anything-ab.anyhoster.xyz/s/ColeHaan_US/account-register?dwcont=C1182483558\",text/html,1,11,0,2511,8,2519,,,,,0";
        quotedLines[8] = "R,Register.2,1474304864712,397,false,868,23149,200,\"http://production-anything-ab.anyhoster.xyz/\",text/html,3,0,389,4,392,396,,,,,0";
        quotedLines[9] = "R,Register.3,1474304865124,52,false,967,800,200,\"http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/Account-ThankYou\",text/html,0,0,51,0,51,51,,,,,0";
        quotedLines[10] = "R,Register.4,1474304865266,12,false,1302,377,200,\"http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2F&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=Cole+Haan%3A+Shoes%2C+Bags+%26+Accessories+for+Men%2C+Women+%26+Kids&fake=10016153348536&ref=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2F,image/gif,0,0,11,0,11,11,,,,,0";
        quotedLines[11] = "A,Register,1474304862191,3087,false";
        quotedLines[12] = "R,Logout.1,1474304870277,67,false,929,2033,302,\"http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/Login-Logout\",text/html,2,0,63,0,65,65,,,,,0";
        quotedLines[13] = "R,Logout.2,1474304870344,466,false,1387,24820,200,\"https://production-anything-ab.anyhoster.xyz/s/ColeHaan_US/account\",text/html,1,6,0,460,5,465,,,,,0";
        quotedLines[14] = "R,Logout.3,1474304870950,12,false,1413,437,200,\"https://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=\"https%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Faccount&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=Cole+Haan+Account+Login+%7C+Cole+Haan&fake=10021837570387&ref=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2F,image/gif,0,0,11,0,11,11,,,,,0";
        quotedLines[15] = "A,Logout,1474304870276,686,false";
        quotedLines[16] = "R,SelectCategory.1,1474304875451,1080,false,870,38427,200,\"http://production-anything-ab.anyhoster.xyz/s/ColeHaan_US/womens-whats-new-new-shoes\",text/html,2,0,1070,7,1072,1079,,,,,0";
        quotedLines[17] = "R,SelectCategory.2,1474304876694,14,false,1345,377,200,\"http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=\"http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Fwomens-whats-new-new-shoes&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=New+Womens+Shoes+%3A+Sneakers%2C+Flats+%26+More+%7C+Cole+Haan&fake=10027581275695&ref=https%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Faccount,image/gif,0,0,13,0,13,13,,,,,0";
        quotedLines[18] = "A,SelectCategory,1474304875450,1258,false";
        quotedLines[19] = "R,RefineByCategory.1,1474304880577,460,false,879,34633,200,\"http://production-anything-ab.anyhoster.xyz/s/ColeHaan_US/womens-2zerogrand\",text/html,0,0,458,0,458,458,,,,,0";
        quotedLines[20] = "R,RefineByCategory.2,1474304881165,13,false,1327,377,200,\"http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=\"http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Fwomens-2zerogrand&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=Womens+2.ZEROGRAND+Collection+%7C+Cole+Haan&fake=10032052865709&ref=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Fwomens-whats-new-new-shoes,image/gif,0,0,12,0,12,12,,,,,0";
    
        for (int x = 0; x < FACTOR; x++)
            for (int i = 0; i < lines.length; i++)
            {
                slines.add(new String(lines[i]));
                squotedLines.add(new String(lines[i]));
                
                clines.add(lines[i].toCharArray());
                cquotedLines.add(quotedLines[i].toCharArray());
            }
    }

    @Benchmark
    public int original()
    { 
        int sum = 0;
        for (String s : slines)
        {
            String[] result = com.xceptance.common.csvutils.original411.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    }
    @Benchmark
    public int originalQuoted()
    { 
        int sum = 0;
        for (String s : squotedLines)
        {
            String[] result = com.xceptance.common.csvutils.original411.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    }

    @Benchmark
    public int tuned412()
    { 
        int sum = 0;
        for (String s : slines)
        {
            String[] result = com.xceptance.common.csvutils.tuned412.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    }
    @Benchmark
    public int tuned412Quoted()
    { 
        int sum = 0;
        for (String s : squotedLines)
        {
            String[] result = com.xceptance.common.csvutils.tuned412.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    }   

    @Benchmark
    public int tuned4121()
    { 
        int sum = 0;
        for (String s : slines)
        {
            String[] result = com.xceptance.common.csvutils.tuned4121.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    }
    @Benchmark
    public int tuned4121Quoted()
    { 
        int sum = 0;
        for (String s : squotedLines)
        {
            String[] result = com.xceptance.common.csvutils.tuned4121.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    }   
    
    @Benchmark
    public int tuned500()
    { 
        int sum = 0;
        for (String s : slines)
        {
            List<String> result = com.xceptance.common.csvutils.tuned500.CsvUtils.decode(s);
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned500Quoted()
    { 
        int sum = 0;
        for (String s : squotedLines)
        {
            List<String> result = com.xceptance.common.csvutils.tuned500.CsvUtils.decode(s);
            sum += result.size();
        }
        
        return sum;
    }  

    @Benchmark
    public int tuned501()
    { 
        int sum = 0;
        for (String s : slines)
        {
            List<String> result = com.xceptance.common.csvutils.tuned501.CsvUtils.decode(s);
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned501Quoted()
    { 
        int sum = 0;
        for (String s : squotedLines)
        {
            List<String> result = com.xceptance.common.csvutils.tuned501.CsvUtils.decode(s);
            sum += result.size();
        }
        
        return sum;
    } 
    
//    @Benchmark
//    public int tuned501Decode()
//    { 
//        int sum = 0;
//        for (String s : lines)
//        {
//            List<String> result = com.xceptance.common.csvutils.tuned501.CsvUtilsDecode.decode(s);
//            sum += result.size();
//        }
//        
//        return sum;
//    }
//    
//    @Benchmark
//    public int tuned501DecodeQuoted()
//    { 
//        int sum = 0;
//        for (String s : quotedLines)
//        {
//            List<String> result = com.xceptance.common.csvutils.tuned501.CsvUtilsDecode.decode(s);
//            sum += result.size();
//        }
//        
//        return sum;
//    } 
    
    @Benchmark
    public int tuned502()
    { 
        int sum = 0;
        for (String s : slines)
        {
            String[] result = com.xceptance.common.csvutils.tuned502.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned502Quoted()
    { 
        int sum = 0;
        for (String s : squotedLines)
        {
            String[] result = com.xceptance.common.csvutils.tuned502.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    } 
    
    
    @Benchmark
    public int tuned503()
    { 
        int sum = 0;
        for (char[] s : clines)
        {
            List<String> result = com.xceptance.common.csvutils.tuned503_rewrite.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned503Quoted()
    { 
        int sum = 0;
        for (char[] s : cquotedLines)
        {
            List<String> result = com.xceptance.common.csvutils.tuned503_rewrite.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned504()
    { 
        int sum = 0;
        for (char[] s : clines)
        {
            final SimpleArrayList<String> result = com.xceptance.common.csvutils.tuned504_simplelist.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned504Quoted()
    { 
        int sum = 0;
        for (char[] s : cquotedLines)
        {
            final SimpleArrayList<String> result = com.xceptance.common.csvutils.tuned504_simplelist.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }

    @Benchmark
    public int tuned505()
    { 
        int sum = 0;
        for (char[] s : clines)
        {
            final List<char[]> result = com.xceptance.common.csvutils.tuned505_tuned503.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned505Quoted()
    { 
        int sum = 0;
        for (char[] s : cquotedLines)
        {
            final List<char[]> result = com.xceptance.common.csvutils.tuned505_tuned503.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned510()
    { 
        int sum = 0;
        for (char[] s : clines)
        {
            final List<String> result = com.xceptance.common.csvutils.tuned510.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned510Quoted()
    { 
        int sum = 0;
        for (char[] s : cquotedLines)
        {
            final List<String> result = com.xceptance.common.csvutils.tuned510.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned510b()
    { 
        int sum = 0;
        for (char[] s : clines)
        {
            final List<String> result = com.xceptance.common.csvutils.tuned510b.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned510bQuoted()
    { 
        int sum = 0;
        for (char[] s : cquotedLines)
        {
            final List<String> result = com.xceptance.common.csvutils.tuned510b.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned511()
    { 
        int sum = 0;
        for (char[] s : clines)
        {
            final List<CharBuffer> result = com.xceptance.common.csvutils.tuned511.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned511Quoted()
    { 
        int sum = 0;
        for (char[] s : cquotedLines)
        {
            final List<CharBuffer> result = com.xceptance.common.csvutils.tuned511.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned512()
    { 
        int sum = 0;
        for (char[] s : clines)
        {
            final List<XltCharBuffer> result = com.xceptance.common.csvutils.tuned512_labels.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned512Quoted()
    { 
        int sum = 0;
        for (char[] s : cquotedLines)
        {
            final List<XltCharBuffer> result = com.xceptance.common.csvutils.tuned512_labels.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned513()
    { 
        int sum = 0;
        for (char[] s : clines)
        {
            final List<String> result = com.xceptance.common.csvutils.tuned513_labels_string.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned513Quoted()
    { 
        int sum = 0;
        for (char[] s : cquotedLines)
        {
            final List<String> result = com.xceptance.common.csvutils.tuned513_labels_string.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
}