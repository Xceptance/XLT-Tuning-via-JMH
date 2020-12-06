package com.xceptance.common.csvutils;
import java.util.ArrayList;
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

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class CsvUtilsBenchmarkBuild
{
    private String[] lines = new String[21];
    private String[] quotedLines = new String[21];
    private List<String[]> fields = new ArrayList<>();
    private List<String[]> quotedFields = new ArrayList<>();
    
    @Setup
    public void setup()
    {
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
        quotedLines[14] = "R,Logout.3,1474304870950,12,false,1413,437,200,\"https://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=https%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Faccount&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=Cole+Haan+Account+Login+%7C+Cole+Haan&fake=10021837570387&ref=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2F,image/gif,0,0,11,0,11,11,,,,,0";
        quotedLines[15] = "A,Logout,1474304870276,686,false";
        quotedLines[16] = "R,SelectCategory.1,1474304875451,1080,false,870,38427,200,\"http://production-anything-ab.anyhoster.xyz/s/ColeHaan_US/womens-whats-new-new-shoes\",text/html,2,0,1070,7,1072,1079,,,,,0";
        quotedLines[17] = "R,SelectCategory.2,1474304876694,14,false,1345,377,200,\"http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Fwomens-whats-new-new-shoes&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=New+Womens+Shoes+%3A+Sneakers%2C+Flats+%26+More+%7C+Cole+Haan&fake=10027581275695&ref=https%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Faccount,image/gif,0,0,13,0,13,13,,,,,0";
        quotedLines[18] = "A,SelectCategory,1474304875450,1258,false";
        quotedLines[19] = "R,RefineByCategory.1,1474304880577,460,false,879,34633,200,\"http://production-anything-ab.anyhoster.xyz/s/ColeHaan_US/womens-2zerogrand\",text/html,0,0,458,0,458,458,,,,,0";
        quotedLines[20] = "R,RefineByCategory.2,1474304881165,13,false,1327,377,200,\"http://production-anything-ab.anyhoster.xyz/on/demandware.store/Sites-ColeHaan_US-Site/en_US/__Analytics-Tracking?url=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Fwomens-2zerogrand&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&dwac=0.7869769714444649&pcat=new-arrivals&title=Womens+2.ZEROGRAND+Collection+%7C+Cole+Haan&fake=10032052865709&ref=http%3A%2F%2Fproduction-anything-ab.anyhoster.xyz%2Fs%2FColeHaan_US%2Fwomens-whats-new-new-shoes,image/gif,0,0,12,0,12,12,,,,,0";
        
        for (String s : lines)
        {
            fields.add(com.xceptance.common.csvutils.original411.CsvUtils.decode(s));
        }
        for (String s : quotedLines)
        {
            quotedFields.add(com.xceptance.common.csvutils.original411.CsvUtils.decode(s));
        }
    }

    @Benchmark
    public int original()
    { 
        int sum = 0;
        for (String[] f : fields)
        {
            String encoded = com.xceptance.common.csvutils.original411.CsvUtils.encode(f);
            sum += encoded.length();
        }
        
        return sum;
    }

    @Benchmark
    public int originalQuoted()
    { 
        int sum = 0;
        for (String[] f : quotedFields)
        {
            String encoded = com.xceptance.common.csvutils.original411.CsvUtils.encode(f);
            sum += encoded.length();
        }
        
        return sum;
    }

    @Benchmark
    public int tuned()
    { 
        int sum = 0;
        for (String[] f : fields)
        {
            String encoded = com.xceptance.common.csvutils.tuned4121.CsvUtils.encode(f);
            sum += encoded.length();
        }
        
        return sum;
    }

    @Benchmark
    public int tunedQuoted()
    { 
        int sum = 0;
        for (String[] f : quotedFields)
        {
            String encoded = com.xceptance.common.csvutils.tuned4121.CsvUtils.encode(f);
            sum += encoded.length();
        }
        
        return sum;
    }
    
    private final String[] SMALL = new String[]{"foo", "bar"};
    private final String[] SMALLQUOTED = new String[]{"foo", "b\"ar"};
    private final String[] LARGE = new String[]{"fosdf jhsjfd hjhsajdf hkjsad fkjhaskjd hfo", "jasd fjasdjkf jaksjdkfjakjdsfkjaks jdfkjaskdjf kajskdf jlkasjdfj hajsdf jajsdhf kjahsjhf kjahskjf hakjdsf kjhskjdfbar"};
    private final String[] LARGEQUOTED = new String[]{"fooasjhdf jahsdjf asdj jahsdjf hajkshdf jhakjsdfhj", "b\"ards ahsdjfhjshfjhsdjhfjshdjhfsjdhfjhuewzuewzuhjdsahf jkhaskjdhfjhsadf hakjhdsf jhasjdhf asjdf jksad"};
    
    @Benchmark
    public int originalSmall()
    { 
        int sum = 0;
        String encoded = com.xceptance.common.csvutils.original411.CsvUtils.encode(SMALL);
        sum += encoded.length();
        
        return sum;
    }
    
    @Benchmark
    public int tunedSmall()
    { 
        int sum = 0;
        String encoded = com.xceptance.common.csvutils.original411.CsvUtils.encode(SMALL);
        sum += encoded.length();
        
        return sum;
    }

    @Benchmark
    public int originalSmallQuoted()
    { 
        int sum = 0;
        String encoded = com.xceptance.common.csvutils.original411.CsvUtils.encode(SMALLQUOTED);
        sum += encoded.length();
        
        return sum;
    }
    
    @Benchmark
    public int tunedSmallQuoted()
    { 
        int sum = 0;
        String encoded = com.xceptance.common.csvutils.original411.CsvUtils.encode(SMALLQUOTED);
        sum += encoded.length();
        
        return sum;
    }

    @Benchmark
    public int originalLarge()
    { 
        int sum = 0;
        String encoded = com.xceptance.common.csvutils.original411.CsvUtils.encode(LARGE);
        sum += encoded.length();
        
        return sum;
    }
    
    @Benchmark
    public int tunedLarge()
    { 
        int sum = 0;
        String encoded = com.xceptance.common.csvutils.original411.CsvUtils.encode(LARGE);
        sum += encoded.length();
        
        return sum;

    }

    @Benchmark
    public int originalLargeQuoted()
    { 
        int sum = 0;
        String encoded = com.xceptance.common.csvutils.original411.CsvUtils.encode(LARGEQUOTED);
        sum += encoded.length();
        
        return sum;
    }
    
    @Benchmark
    public int tunedLargeQuoted()
    { 
        int sum = 0;
        String encoded = com.xceptance.common.csvutils.original411.CsvUtils.encode(LARGEQUOTED);
        sum += encoded.length();
        
        return sum;
    }

}