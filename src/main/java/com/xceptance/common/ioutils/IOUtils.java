package com.xceptance.common.ioutils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class IOUtils
{
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int EOF = -1;

    public static String toString(final InputStream input, final Charset charset) throws IOException 
    {
        final LazyByteBuffer buffers = new LazyByteBuffer();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int n;

        while (EOF != (n = input.read(buffer, 0, DEFAULT_BUFFER_SIZE))) {
            buffers.add(buffer, n);
            buffer = new byte[DEFAULT_BUFFER_SIZE];
        }

        return new String(buffers.get(), charset);
    }
    
    static class LazyByteBuffer 
    {
        private List<ByteBufferStorageUnit> buffers = new ArrayList<>();
        private int totalBytes;
        
        public void add(final byte[] buffer, final int size)
        {
            buffers.add(new ByteBufferStorageUnit(buffer, size));
            totalBytes += size;
        }
        
        public byte[] get()
        {
            // total size
            final byte[] buffer = new byte[totalBytes];
            
            int pos = 0;
            for (int i = 0; i < buffers.size(); i++)
            {
                final ByteBufferStorageUnit b = buffers.get(i); 
                System.arraycopy(b.buffer, 0, buffer, pos, b.size);
                pos += b.size;
            }
            
            return buffer;
        }
        
        public int size()
        {
            return totalBytes;
        }
        
        static class ByteBufferStorageUnit
        {
            public final byte[] buffer;
            public final int size;
            
            public ByteBufferStorageUnit(final byte[] buffer, final int size)
            {
                this.buffer = buffer;
                this.size = size;
            }
        }
    }
}
