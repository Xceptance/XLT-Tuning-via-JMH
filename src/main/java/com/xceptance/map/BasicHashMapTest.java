package com.xceptance.map;

import org.junit.Assert;
import org.junit.Test;

import com.xceptance.common.util.RandomUtils;

import it.unimi.dsi.util.FastRandom;



public class BasicHashMapTest
{
    /**
     * Create a default cache
     */
    @Test 
    public void createDefault()
    { 
        BasicHashMap<?, ?> map = new BasicHashMap<>(8);
        
        Assert.assertEquals(0, map.size());
        Assert.assertEquals(8, map.capacity());
    }
    
    /**
     * Create a default cache and get the next power of two size
     */
    @Test 
    public void createDefaultNonPowerOf2()
    { 
        BasicHashMap<?, ?> map = new BasicHashMap<>(9);
        
        Assert.assertEquals(0, map.size());
        Assert.assertEquals(16, map.capacity());
    }
    
    /**
     * Add and find
     */
    @Test 
    public void addAndFind4()
    { 
        final BasicHashMap<String, String> map = new BasicHashMap<>(4);

        Assert.assertEquals(null, map.put("AK", "AV"));
        Assert.assertEquals("AV", map.get("AK"));
        Assert.assertEquals(1, map.size());

        Assert.assertEquals(null, map.put("BK", "BV"));
        Assert.assertEquals("BV", map.get("BK"));
        Assert.assertEquals(2, map.size());

        Assert.assertEquals(null, map.put("DK", "DV"));
        Assert.assertEquals("DV", map.get("DK"));
        Assert.assertEquals(3, map.size());
        
        Assert.assertEquals(null, map.put("CK", "CV"));
        Assert.assertEquals("DV", map.get("DK"));
        Assert.assertEquals("CV", map.get("CK"));
        Assert.assertEquals("AV", map.get("AK"));
        Assert.assertEquals("BV", map.get("BK"));
        Assert.assertEquals(4, map.size());
    }   

    /**
     * Add and find
     */
    @Test 
    public void addAndFind32()
    { 
        final BasicHashMap<String, String> map = new BasicHashMap<>(21);

        Assert.assertEquals(null, map.put("AK", "AV"));
        Assert.assertEquals("AV", map.get("AK"));
        Assert.assertEquals(1, map.size());

        Assert.assertEquals(null, map.put("BK", "BV"));
        Assert.assertEquals("BV", map.get("BK"));
        Assert.assertEquals(2, map.size());

        Assert.assertEquals(null, map.put("DK", "DV"));
        Assert.assertEquals("DV", map.get("DK"));
        Assert.assertEquals(3, map.size());
        
        Assert.assertEquals(null, map.put("CK", "CV"));
        Assert.assertEquals("DV", map.get("DK"));
        Assert.assertEquals("CV", map.get("CK"));
        Assert.assertEquals("AV", map.get("AK"));
        Assert.assertEquals("BV", map.get("BK"));
        Assert.assertEquals(4, map.size());
    }  
    
    /**
     * Add key with new value
     */
    @Test 
    public void keyAndNewValue()
    { 
        final BasicHashMap<String, String> map = new BasicHashMap<>(8);

        Assert.assertEquals(null, map.put("AK", "AV"));
        Assert.assertEquals("AV", map.put("AK", "AV2"));
    }   

    /**
     * Add key and remove
     */
    @Test 
    public void addAndRemove()
    { 
        final BasicHashMap<String, String> map = new BasicHashMap<>(8);

        Assert.assertEquals(null, map.put("AK", "AV"));
        Assert.assertEquals(null, map.put("BK", "BV"));
        Assert.assertEquals(null, map.put("CK", "CV"));
        Assert.assertEquals(null, map.put("DK", "DV"));
        Assert.assertEquals(null, map.put("EK", "EV"));
        Assert.assertEquals(null, map.put("FK", "FV"));
        Assert.assertEquals(6, map.size());
        
        Assert.assertEquals("AV", map.remove("AK"));
        Assert.assertEquals("BV", map.remove("BK"));
        Assert.assertEquals("CV", map.remove("CK"));
        Assert.assertEquals("DV", map.remove("DK"));
        Assert.assertEquals("EV", map.remove("EK"));
        Assert.assertEquals("FV", map.remove("FK"));
        Assert.assertEquals(0, map.size());
    }  
    
    /**
     * Add key and remove
     */
    @Test 
    public void addAndRemoveOrder2()
    { 
        final BasicHashMap<String, String> map = new BasicHashMap<>(8);

        Assert.assertEquals(null, map.put("AK", "AV"));
        Assert.assertEquals(null, map.put("BK", "BV"));
        Assert.assertEquals(null, map.put("CK", "CV"));
        Assert.assertEquals(null, map.put("DK", "DV"));
        Assert.assertEquals(null, map.put("EK", "EV"));
        Assert.assertEquals(null, map.put("FK", "FV"));
        
        Assert.assertEquals("FV", map.remove("FK"));
        Assert.assertEquals("EV", map.remove("EK"));
        Assert.assertEquals("DV", map.remove("DK"));
        Assert.assertEquals("CV", map.remove("CK"));
        Assert.assertEquals("BV", map.remove("BK"));
        Assert.assertEquals("AV", map.remove("AK"));
    }  

    /**
     * Add key and remove
     */
    @Test 
    public void addAndRemoveOrder3()
    { 
        final BasicHashMap<String, String> map = new BasicHashMap<>(8);

        Assert.assertEquals(null, map.put("AK", "AV"));
        Assert.assertEquals(null, map.put("BK", "BV"));
        Assert.assertEquals(null, map.put("CK", "CV"));
        Assert.assertEquals(null, map.put("DK", "DV"));
        Assert.assertEquals(null, map.put("EK", "EV"));
        Assert.assertEquals(null, map.put("FK", "FV"));
        Assert.assertEquals(6, map.size());
        
        Assert.assertEquals("EV", map.remove("EK"));
        Assert.assertEquals(5, map.size());
        Assert.assertEquals("FV", map.remove("FK"));
        Assert.assertEquals(4, map.size());
        Assert.assertEquals("AV", map.remove("AK"));
        Assert.assertEquals(3, map.size());
        Assert.assertEquals("DV", map.remove("DK"));
        Assert.assertEquals(2, map.size());
        Assert.assertEquals("BV", map.remove("BK"));
        Assert.assertEquals(1, map.size());
        Assert.assertEquals("CV", map.remove("CK"));
        Assert.assertEquals(0, map.size());

    }  

    /**
     * Push out
     */
    @Test 
    public void lru1()
    { 
        final BasicHashMap<String, String> map = new BasicHashMap<>(4);

        Assert.assertEquals(null, map.put("K1", "V1"));
        Assert.assertEquals(null, map.put("K2", "V2"));
        Assert.assertEquals(null, map.put("K3", "V3"));
        Assert.assertEquals(null, map.put("K4", "V4"));
        Assert.assertEquals(4, map.size());
        
        Assert.assertEquals(null, map.put("K5", "V5"));
        Assert.assertEquals(4, map.size());
        
        Assert.assertEquals("V2", map.get("K2"));
        Assert.assertEquals("V3", map.get("K3"));
        Assert.assertEquals("V4", map.get("K4"));
        Assert.assertEquals("V5", map.get("K5"));
        Assert.assertEquals(4, map.size());
    }  

    /**
     * LRU touch all in same order
     */
    @Test 
    public void lruOrderDoesNotChange()
    { 
        final BasicHashMap<String, String> map = new BasicHashMap<>(4);

        Assert.assertEquals(null, map.put("K1", "V1"));
        Assert.assertEquals(null, map.put("K2", "V2"));
        Assert.assertEquals(null, map.put("K3", "V3"));
        Assert.assertEquals(null, map.put("K4", "V4"));
        Assert.assertEquals(4, map.size());
        
        Assert.assertEquals("V1", map.get("K1"));
        Assert.assertEquals("V2", map.get("K2"));
        Assert.assertEquals("V3", map.get("K3"));
        Assert.assertEquals("V4", map.get("K4"));

        Assert.assertEquals(null, map.put("K5", "V5"));
        Assert.assertEquals(4, map.size());
        
        Assert.assertEquals("V2", map.get("K2"));
        Assert.assertEquals("V3", map.get("K3"));
        Assert.assertEquals("V4", map.get("K4"));
        Assert.assertEquals("V5", map.get("K5"));
        Assert.assertEquals(4, map.size());
    } 
    
    /**
     * LRU touch all in different order
     */
    @Test 
    public void lruOrderReversed()
    { 
        final BasicHashMap<String, String> map = new BasicHashMap<>(4);

        Assert.assertEquals(null, map.put("K1", "V1"));
        Assert.assertEquals(null, map.put("K2", "V2"));
        Assert.assertEquals(null, map.put("K3", "V3"));
        Assert.assertEquals(null, map.put("K4", "V4"));
        Assert.assertEquals(4, map.size());
        
        Assert.assertEquals("V4", map.get("K4"));
        Assert.assertEquals("V3", map.get("K3"));
        Assert.assertEquals("V2", map.get("K2"));
        Assert.assertEquals("V1", map.get("K1"));

        Assert.assertEquals(null, map.put("K5", "V5"));
        Assert.assertEquals(4, map.size());
        
        Assert.assertEquals("V1", map.get("K1"));
        Assert.assertEquals("V2", map.get("K2"));
        Assert.assertEquals("V3", map.get("K3"));
        Assert.assertEquals("V5", map.get("K5"));
        Assert.assertEquals(4, map.size());
    } 
    
    /**
     * LRU get one up again and again
     */
    @Test 
    public void lruGetOneOnly()
    { 
        final BasicHashMap<String, String> map = new BasicHashMap<>(4);

        Assert.assertEquals(null, map.put("K1", "V1"));
        Assert.assertEquals(null, map.put("K2", "V2"));
        Assert.assertEquals(null, map.put("K3", "V3"));
        Assert.assertEquals(null, map.put("K4", "V4"));
        Assert.assertEquals(4, map.size());
        
        Assert.assertEquals("V1", map.get("K1"));
        Assert.assertEquals("V1", map.get("K1"));
        Assert.assertEquals("V1", map.get("K1"));
        Assert.assertEquals("V1", map.get("K1"));

        Assert.assertEquals(null, map.put("K5", "V5"));
        Assert.assertEquals(4, map.size());
        
        Assert.assertEquals(null, map.get("K2"));
        Assert.assertEquals("V1", map.get("K1"));
        Assert.assertEquals("V3", map.get("K3"));
        Assert.assertEquals("V4", map.get("K4"));
        Assert.assertEquals("V5", map.get("K5"));
        Assert.assertEquals(4, map.size());
    } 
    
    /**
     * Touch something which is not here
     */
    @Test
    public void lruOnUnknown()
    {
        final BasicHashMap<String, String> map = new BasicHashMap<>(4);

        Assert.assertEquals(null, map.put("K1", "V1"));
        Assert.assertEquals(null, map.put("K2", "V2"));
        Assert.assertEquals(null, map.put("K3", "V3"));
        Assert.assertEquals(null, map.put("K4", "V4"));
        Assert.assertEquals(4, map.size());
        
        Assert.assertEquals(null, map.get("K10"));

        Assert.assertEquals("V1", map.get("K1"));
        Assert.assertEquals("V2", map.get("K2"));
        Assert.assertEquals("V3", map.get("K3"));
        Assert.assertEquals("V4", map.get("K4"));
        Assert.assertEquals(4, map.size());
    }
    
    /**
     * Remove something and have the entry linger in the queue
     */
    @Test
    public void lruOnLingeringEntries()
    {
        final BasicHashMap<String, String> map = new BasicHashMap<>(4);

        Assert.assertEquals(null, map.put("K1", "V1"));
        Assert.assertEquals(null, map.put("K2", "V2"));
        Assert.assertEquals(null, map.put("K3", "V3"));
        Assert.assertEquals(null, map.put("K4", "V4"));
        Assert.assertEquals(4, map.size());
        
        Assert.assertEquals("V1", map.remove("K1"));
        Assert.assertEquals("V4", map.remove("K4"));
        Assert.assertEquals("V3", map.remove("K3"));
        Assert.assertEquals("V2", map.remove("K2"));
        Assert.assertEquals(0, map.size());

        Assert.assertEquals(null, map.put("K11", "V1"));
        Assert.assertEquals(null, map.put("K21", "V2"));
        Assert.assertEquals(null, map.put("K31", "V3"));
        Assert.assertEquals(null, map.put("K41", "V4"));
        Assert.assertEquals(4, map.size());
        Assert.assertEquals(null, map.put("K51", "V5"));
        Assert.assertEquals(4, map.size());
        
        Assert.assertEquals("V2", map.get("K21"));
        Assert.assertEquals("V3", map.get("K31"));
        Assert.assertEquals("V4", map.get("K41"));
        Assert.assertEquals("V5", map.get("K51"));
        Assert.assertEquals(4, map.size());
    }
    
    @Test
    public void addPlenty()
    {
        int SIZE = 1111;
        final BasicHashMap<String, String> map = new BasicHashMap<>(SIZE);

        final String[] strings = new String[10 * SIZE];
        
        final FastRandom r = new FastRandom(7L);
        for (int i = 0; i < 10 * SIZE; i++)
        {
            strings[i] = RandomUtils.randomString(r, 5);
            
            try 
            {
                map.put(strings[i], "");
            }
            catch(Exception e)
            {
                System.out.println(map.toString());
                System.out.println(i);
                throw e;
            }
        }

        // System.out.println(map.toString());
    }
    
    /**
     * A helper class with predictable hash codes
     * @author rschwietzke
     */
    class HashCheater
    {
        private final int hashCode;
        private final String value;
        
        public HashCheater(int hashCode)
        {
            this.hashCode = hashCode;
            this.value = "";
        }
        
        public HashCheater(int hashCode, String value)
        {
            this.hashCode = hashCode;
            this.value = value;
        }
        
        @Override
        public int hashCode()
        {
            return hashCode;
        }
    }
}