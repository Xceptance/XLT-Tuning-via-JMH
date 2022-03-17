package com.xceptance.common.ioutils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class IOUtils3
{
    public static final int DEFAULT_BUFFER_SIZE = 8192/2;
    public static final int EOF = -1;

    public static String toString(final InputStream input, final Charset charset) throws IOException 
    {
        final Reader reader = new InputStreamReader(input, charset);
        
        final LazyByteBuffer buffers = new LazyByteBuffer();
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        int n;

        while (EOF != (n = reader.read(buffer, 0, DEFAULT_BUFFER_SIZE))) {
            buffers.add(buffer, n);
            buffer = new char[DEFAULT_BUFFER_SIZE];
        }

        return new String(buffers.get());
    }
    
    static class LazyByteBuffer 
    {
        private List<CharBufferStorageUnit> buffers = new ArrayList<>();
        private int totalBytes;
        
        public void add(final char[] buffer, final int size)
        {
            buffers.add(new CharBufferStorageUnit(buffer, size));
            totalBytes += size;
        }
        
        public char[] get()
        {
            // total size
            final char[] buffer = new char[totalBytes];
            
            int pos = 0;
            for (int i = 0; i < buffers.size(); i++)
            {
                final CharBufferStorageUnit b = buffers.get(i); 
                System.arraycopy(b.buffer, 0, buffer, pos, b.size);
                pos += b.size;
            }
            
            return buffer;
        }
        
        public int size()
        {
            return totalBytes;
        }
        
        static class CharBufferStorageUnit
        {
            public final char[] buffer;
            public final int size;
            
            public CharBufferStorageUnit(final char[] buffer, final int size)
            {
                this.buffer = buffer;
                this.size = size;
            }
        }
    }
}
