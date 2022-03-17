package com.xceptance.common.hashcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class VerifyDistribution
{
    public static void main(String[] args) throws IOException
    {
        var file = args.length != 0 ? args[0] : "/home/rschwietzke/tmp/words_alpha.txt";
        
        var hash31 = new HashSet<Integer>(1_000_000);
        //var hash31Full = new HashSet<Integer>(1_000_000);
        var hashFancy = new HashSet<Integer>(1_000_000);
        //var hashFancyFull = new HashSet<Integer>(1_000_000);

        var hash109 = new HashSet<Integer>(1_000_000);
        var hashFNP1A = new HashSet<Integer>(1_000_000);

        // assume correctness
        long total = 0;

        try (var r = Files.newBufferedReader(Path.of(file)))
        {
            String line = null;
            while ((line = r.readLine()) != null)
            {
                total++;
                
                hash31.add(StringHasher.hashStringUntil(line, '#'));
                //hash31Full.add(StringHasher.hashStringUntil(line, '\t'));

                hashFancy.add(StringHasher.hashShiftingHint(line, '#'));
                //hashFancyFull.add(StringHasher.hashStringUntilShifting(line, '\t'));
                
                hash109.add(StringHasher.hashStringUntil109(line, '#'));
                hashFNP1A.add(StringHasher.hashStringUntilFNP1A(line, '#'));

                if (total % 100_000 == 0)
                {
                    System.out.format("Current: %,d%n", total);
                }
            }
        }
        
        var t = (double) total;
        System.out.format("Total                  : %,d%n", total);
//        System.out.format("Hash31 Full      (%.2f): %,d%n", (double) (hash31Full.size() / t), hash31Full.size());
        System.out.format("Hash31  Limited     (%.2f): %,d%n", (double) hash31.size() / t, hash31.size());
        System.out.format("Hash109 Limited     (%.2f): %,d%n", (double) hash109.size() / t, hash109.size());
//        System.out.format("New Hash Full    (%.2f): %,d%n", (double) hashFancyFull.size() / t, hashFancyFull.size());
        System.out.format("Hash FNP-1A Limited (%.2f): %,d%n", (double) (hashFNP1A.size() / t), hashFNP1A.size());
        System.out.format("New Hash Limited   (%.2f): %,d%n", (double) (hashFancy.size() / t), hashFancy.size());
        
    }
}
