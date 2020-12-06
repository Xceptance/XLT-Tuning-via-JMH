package experiments.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class MonoFile
{   
    public static void main(String[] args) throws IOException, InterruptedException
    {
        final String dir = "/home/rschwietzke/projects/loadtest/test-results/20191022-230434-torrid";
        final ExecutorService pool = Executors.newWorkStealingPool();
        
        try (final Stream<Path> files = Files.find(
                        Paths.get(dir), Integer.MAX_VALUE, 
                        (p, bfa) -> bfa.isRegularFile() && p.getFileName().toString().matches("^timers.csv.*")))
        {
            files.forEach(f -> 
            {
                pool.submit(() -> 
                {
                    int charCount = 0;
                    try (final BufferedReader br = Files.newBufferedReader(f))
                    {
                        String line;
                        while ((line = br.readLine()) != null)
                        {
                            charCount += line.length();
                        }
                    }
                    catch (IOException e)
                    {
                    }
                });
            });
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);
    }
}
