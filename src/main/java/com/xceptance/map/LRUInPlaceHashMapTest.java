package com.xceptance.map;

import org.junit.Assert;
import org.junit.Test;

import com.xceptance.common.util.RandomUtils;

import it.unimi.dsi.util.FastRandom;


public class LRUInPlaceHashMapTest
{
    /**
     * Create a default cache
     */
    @Test 
    public void createDefault()
    { 
        LRUInPlaceHashMap<?, ?> map = new LRUInPlaceHashMap<>(8);
        Assert.assertEquals(8, map.capacity());
    }
    
    /**
     * Create a default cache and get the next power of two size
     */
    @Test 
    public void createDefaultNonPowerOf2()
    { 
        LRUInPlaceHashMap<?, ?> map = new LRUInPlaceHashMap<>(9);
        Assert.assertEquals(16, map.capacity());
    }
    
    /**
     * Add and find
     */
    @Test 
    public void addAndFind4()
    { 
        final LRUInPlaceHashMap<String, String> map = new LRUInPlaceHashMap<>(4);

        Assert.assertEquals(null, map.put("AK", "AV"));
        Assert.assertEquals("AV", map.get("AK"));

        Assert.assertEquals(null, map.put("BK", "BV"));
        Assert.assertEquals("BV", map.get("BK"));

        Assert.assertEquals(null, map.put("DK", "DV"));
        Assert.assertEquals("DV", map.get("DK"));
        
        Assert.assertEquals(null, map.put("CK", "CV"));
        Assert.assertEquals("DV", map.get("DK"));
        Assert.assertEquals("CV", map.get("CK"));
        Assert.assertEquals("AV", map.get("AK"));
        Assert.assertEquals("BV", map.get("BK"));
    }   

    /**
     * Don't find it
     */
    @Test 
    public void getNoFind()
    { 
        final LRUInPlaceHashMap<String, String> map = new LRUInPlaceHashMap<>(4);

        Assert.assertEquals(null, map.put("AK", "AV"));
        Assert.assertEquals(null, map.get("none"));
    }   
    
    /**
     * Update
     */
    @Test 
    public void putUpdate()
    { 
        final LRUInPlaceHashMap<String, String> map = new LRUInPlaceHashMap<>(4);

        Assert.assertEquals(null, map.put("AK", "AV"));
        Assert.assertEquals("AV", map.get("AK"));

        Assert.assertEquals("AV", map.put("AK", "AV2"));
        Assert.assertEquals("AV2", map.get("AK"));
    }   
    
    
    /**
     * LRU, we run simple modulo, hence we know where it ends up
     */
    @Test 
    public void lru()
    { 
        final LRUInPlaceHashMap<HashCheater, String> map = new LRUInPlaceHashMap<>(4);

        for (int i = 0; i < map.LRUSIZE; i++)
        {
            Assert.assertEquals(null, map.put(HashCheater.get(0, "AK" + i), "AV" + i));
            Assert.assertEquals("AV" + i, map.get(HashCheater.get(0, "AK" + i)));
        }
        
        for (int i = 0; i < map.LRUSIZE; i++)
        {
            Assert.assertEquals("AV" + i, map.get(HashCheater.get(0, "AK" + i)));
        }
        
        // now we push the oldest out
        map.put(HashCheater.get(0, "AKNEW"), "AVNEW");
        Assert.assertEquals("AVNEW", map.get(HashCheater.get(0, "AKNEW")));

        // 0 is fone
        Assert.assertEquals(null, map.get(HashCheater.get(0, "AK0")));
        
        // rest still there
        for (int i = 1; i < map.LRUSIZE; i++)
        {
            Assert.assertEquals("AV" + i, map.get(HashCheater.get(0, "AK" + i)));
        }
        Assert.assertEquals("AVNEW", map.get(HashCheater.get(0, "AKNEW")));
    }  
    
    /**
     * LRU,
     */
    @Test 
    public void lruGetTouches()
    { 
        final LRUInPlaceHashMap<HashCheater, String> map = new LRUInPlaceHashMap<>(4);

        for (int i = 0; i < map.LRUSIZE; i++)
        {
            Assert.assertEquals(null, map.put(HashCheater.get(0, "AK" + i), "AV" + i));
            Assert.assertEquals("AV" + i, map.get(HashCheater.get(0, "AK" + i)));
        }
        
        for (int i = map.LRUSIZE - 1; i >= 0; i--)
        {
            Assert.assertEquals("AV" + i, map.get(HashCheater.get(0, "AK" + i)));
        }
        
        // now we push the oldest out
        map.put(HashCheater.get(0, "AKNEW"), "AVNEW");
        Assert.assertEquals("AVNEW", map.get(HashCheater.get(0, "AKNEW")));

        // map.LRUSIZE - 1 is fone
        Assert.assertEquals(null, map.get(HashCheater.get(0, "AK" + (map.LRUSIZE - 1))));
        
        // rest is still there
        for (int i = 0; i < map.LRUSIZE - 1; i++)
        {
            Assert.assertEquals("AV" + i, map.get(HashCheater.get(0, "AK" + i)));
        }
        Assert.assertEquals("AVNEW", map.get(HashCheater.get(0, "AKNEW")));

    }  
    
    @Test
    public void addPlenty()
    {
        int SIZE = 1111;
        final LRUInPlaceHashMap<String, String> map = new LRUInPlaceHashMap<>(SIZE);

        final FastRandom r = new FastRandom(7L);
        for (int i = 0; i < 10 * SIZE; i++)
        {
            String s = RandomUtils.randomString(r, 5);
            map.put(s, s + "extra");

            // we can always find it shortly after
            Assert.assertEquals(s + "extra", map.get(s));
        }
    }

   /**
     * A helper class with predictable hash codes
     * @author rschwietzke
     */
    static class HashCheater
    {
        private final int hashCode;
        private final String realKey;
        
        public HashCheater(final int hashCode, final String realKey)
        {
            this.hashCode = hashCode;
            this.realKey = realKey;
        }
        
        public static HashCheater get(final int hashCode, final String realKey)
        {
            return new HashCheater(hashCode, realKey);
        }
        
        @Override
        public int hashCode()
        {
            return hashCode;
        }
        
        @Override
        public boolean equals(Object o)
        {
            // behaves like string, same content, same "instance"
            return o.hashCode() == hashCode() && ((HashCheater)o).realKey.equals(realKey);
        }
    }
}