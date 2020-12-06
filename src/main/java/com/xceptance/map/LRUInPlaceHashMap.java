/**
 * 
 */
package com.xceptance.map;

/**
 * This is a hash map style cache without resizing!
 * All about speed!
 * 
 * @author rschwietzke
 *
 */
public class LRUInPlaceHashMap<K, V>
{
    /**
     * Array that holds our data, we store key and value entries next to each other, hence the type
     * here is very generic
     */
    private Object[] data;

    private int mask;
    private int capacity;
    
    public static final int LRUSIZE = 4; 

    /**
     * Constructor of a default cache
     */
    public LRUInPlaceHashMap(final int capacity)
    {
        this.capacity = ensurePowerOfTwo(capacity);

        // * 2 because key, value next to each other and value is a special helper for LRU operations
        this.data = new Object[(this.capacity * LRUSIZE * 2)];

        this.mask = this.capacity - 1;
    }

    /**
     * Ensure that we have a power of two as capacity
     * 
     * @param value
     * @return
     */
    private int ensurePowerOfTwo(int value)
    {
        final int highestOneBit = Integer.highestOneBit(value);

        if (value == highestOneBit)
        {
            return value;
        }
        return highestOneBit << 1;
    }

    /**
     * Inspired by the JDK version
     * 
     * @param key the key to hash
     * @return the position
     */
    private int pos(int h)
    {
        int rest = h & mask;
        
        int pos = rest * LRUSIZE;
//        System.out.printf("%s [%s] %% %s = %s -> pos = %s\n", key, h, this.capacity, rest, pos);

        return pos;
    }

    /**
     * Run in place lru, but only when not at the first pos
     * 
     * @param key
     * @return
     */
    private void lru(final int start, final int current, final Object key, final Object value)
    {
        if (start == current)
        {
            return;
        }
        
//        System.arraycopy(data, start, data, start + 2, current - start);
//        for (int i = pos + offset + 1; i >= pos + 2; i-=2)
//        {
//            data[i] = data[i - 2];
//            data[i-1] = data[i - 3];
//        }
        
//        final Object keyOld = data[current - 2];
//        final Object keyValue = data[current - 1];
//        
//        data[current - 2] = key;
//        data[current - 1] = value;
//        
//        data[current] = keyOld;
//        data[current + 1] = keyValue;
    }
    
    /**
     * Run in place lru, take out the current pos and put tham at 0
     * 
     * @param key
     * @return
     */
    private void lruUpdate(final int pos, final int offset, final K key, final V value)
    {
        System.arraycopy(data, pos, data, pos + 2, offset);
        
        data[pos] = key;
        data[pos + 1] = value;
    }
    
    public V get(final K key)
    {
        // where is our entry located
        final int h = key.hashCode();
        final int pos = pos(h);

        // get the entry
        for (int i = pos; i < pos + LRUSIZE * 2; i = i + 2)
        {
            final Object readKey = data[i];
            if (readKey != null)
            {
                // are we there yet?
                if (readKey.equals(key))
                {
                    final V value = (V) data[i + 1];

                    //lru(pos, i, readKey, value);
                    
                    return value;
                }
            }
            else
            {
                // stop here, place was empty
                return null;
            }
        }

        // nothing found
        return null;
    }

    public V put(final K key, final V value)
    {
        // where does it belong?
        final int h = key.hashCode();
        final int pos = pos(h);

        // get the entry
        for (int i = 0; i < LRUSIZE * 2; i = i + 2)
        {
            final int current = pos + i;

            final K readKey = (K) data[current];
            if (readKey != null)
            {
                // are we there yet, update the old entry
                if (readKey.equals(key))
                {
                    final V oldValue = (V) data[current + 1];
                    
                    lruUpdate(pos, i, readKey, value);

                    return oldValue;
                }
            }
            else
            {
                lruUpdate(pos, (LRUSIZE - 1) * 2, key, value);
                return null;
            }
        }
         
        // we made it here, so we have not updated it aka have not found an old entry
        // or an empty pos
        lruUpdate(pos, (LRUSIZE - 1) * 2, key, value);

        return null;
    }

//    public V remove(final K key) throws IllegalArgumentException
//    {
//        // where does it belong?
//        final int pos = pos(key);
//
//        // get the entry
//        for (int i = 0; i < ATTEMPTS; i++)
//        {
//            final int current = pos +  (i << 1);
//
//            final K readKey = (K) data[current];
//            if (readKey != null)
//            {
//                // are we there yet? Yes, remove entry and 
//                // move the others
//                if (readKey.equals(key))
//                {
//                    final Entry<K, V> entry = (Entry<K, V>) data[current + 1];
//                    final V oldValue = entry.value;
//
//                    System.arraycopy(data, current + 2, data, current, (ATTEMPTS - i - 1) << 1);
//
//                    // empty last two
//                    final int last = current + ((ATTEMPTS - i - 1) << 1);
//                    data[last] = null;
//                    data[last + 1] = null;
//
//                    size--;
//
//                    return oldValue;
//                }
//            }
//        }
//
//        // not found, don't care
//        return null;
//    }

    public int capacity()
    {
        return capacity;
    }
}
