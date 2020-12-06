/**
 * 
 */
package com.xceptance.map;

import java.util.StringJoiner;

/**
 * This is a hash map style cache without resizing!
 * All about speed!
 * 
 * @author rschwietzke
 *
 */
public class BasicHashMap<K, V>
{
    /**
     * Array that holds our data, we store key and value entries next to each other, hence the type
     * here is very generic
     */
    private Object[] data;

    private final Entry<K, V> head;
    private final Entry<K, V> tail;

    /**
     * Holds the current data amount aka size
     */
    private int size;

    private final int mask;
    private final int capacity;

    private static final int ATTEMPTS_IN_SHIFT = 2; // 4
    private static final int ATTEMPTS = 1 << ATTEMPTS_IN_SHIFT; // 4

    /**
     * Constructor of a default cache
     */
    public BasicHashMap(final int capacity)
    {
        this.capacity = ensurePowerOfTwo(capacity);

        // * 2 because key, value next to each other and value is a special helper for LRU operations
        this.data = new Object[(this.capacity << ATTEMPTS_IN_SHIFT + 1)];

        this.mask = this.capacity - 1;

        this.size = 0;

        head = new Entry<>(null, null, null, null);
        tail = new Entry<>(null, null, null, null);
        head.next = tail;
        tail.previous = head;
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
    private int pos(K key)
    {
        int h = key.hashCode();
        int rest = h & mask;
        
        int pos = rest << (1 + ATTEMPTS_IN_SHIFT); // we use only every second fields for keys
        System.out.printf("%s [%s] %% %s = %s -> pos = %s\n", key, h, this.capacity, rest, pos);

        return pos;
    }

    public V get(final K key)
    {
        // where is our entry located
        final int pos = pos(key);

        // get the entry
        for (int i = 0; i < ATTEMPTS; i++)
        {
            final int current = pos +  (i << 1);

            final K readKey = (K) data[current];
            if (readKey != null)
            {
                // are we there yet?
                if (readKey.equals(key))
                {
                    final Entry<K, V> entry = (Entry<K, V>) data[current + 1];

                    // get me out of the chain and heal chain
                    Entry<K, V> oldPrevious = entry.previous;
                    Entry<K, V> oldNext = entry.next;
                    
                    oldPrevious.next = oldNext;
                    oldNext.previous = oldPrevious;

                    // move behind head
                    head.next.previous = entry;
                    entry.next = head.next;
                    entry.previous = head;
                    head.next = entry;

                    return entry.value;
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
        final int pos = pos(key);

        // get the entry
        boolean added = false;
        for (int i = 0; i < ATTEMPTS; i++)
        {
            final int current = pos +  (i << 1);

            final K readKey = (K) data[current];
            if (readKey != null)
            {
                // are we there yet, update the old entry
                if (readKey.equals(key))
                {
                    final Entry<K, V> entry = (Entry<K, V>) data[current + 1];

                    // get me out of the chain and heal chain
                    entry.previous.next = entry.next;
                    entry.next.previous = entry.previous;

                    // move behind head
                    entry.next = head.next;
                    entry.previous = head;

                    final V oldValue = entry.value;
                    entry.value = value;

                    return oldValue;
                }
            }
            else
            {
                // empty and so this is our place
                final Entry<K, V> entry = new Entry<>(key, value, head, head.next);
                head.next.previous = entry;
                head.next = entry;

                data[current + 1] = entry;
                data[current] = key;

                size++;

                added = true;

                break;
            }
        }

        if (added == false)
        {
            throw new IllegalArgumentException();
        }

        // are we too big now?
        // loop because they might be old removed entries in the queue
        // and so a remove might silently fail and not change the size
        if (size > capacity)
        {
            removeOldest();

            while (size > capacity)
            {
                removeOldest();
            }
        }

        return null;
    }

    private void removeOldest()
    {
        final Entry<K, V> oldest = tail.previous;

        // can never be head, so don't check

        // remove it from chain and fix chain
        tail.previous = oldest.previous;
        oldest.previous.next = tail;

        // remove the key
        remove(oldest.key);
    }


    public V remove(final K key) throws IllegalArgumentException
    {
        // where does it belong?
        final int pos = pos(key);

        // get the entry
        for (int i = 0; i < ATTEMPTS; i++)
        {
            final int current = pos +  (i << 1);

            final K readKey = (K) data[current];
            if (readKey != null)
            {
                // are we there yet? Yes, remove entry and 
                // move the others
                if (readKey.equals(key))
                {
                    final Entry<K, V> entry = (Entry<K, V>) data[current + 1];
                    final V oldValue = entry.value;

                    System.arraycopy(data, current + 2, data, current, (ATTEMPTS - i - 1) << 1);

                    // empty last two
                    final int last = current + ((ATTEMPTS - i - 1) << 1);
                    data[last] = null;
                    data[last + 1] = null;

                    size--;

                    return oldValue;
                }
            }
        }

        // not found, don't care
        return null;
    }

    public int size()
    {
        return size;
    }

    public int capacity()
    {
        return capacity;
    }

    @Override
    public String toString()
    {
        String s = "BasicHashMap [data=\n";

        for (int i = 0; i < data.length; i = i + (ATTEMPTS << 1))
        {
            StringJoiner sj = new StringJoiner(", ", "[", "]\n");
            for (int j = i; j < i + (ATTEMPTS << 1); j++) 
            {
                sj.add(String.valueOf(data[j]));

            }
            s = s + sj.toString();
        }
        s = s + "]";

        {
            Entry<K, V> entry = head;
            StringJoiner sj = new StringJoiner(" -> ", "HEAD -> ", " -> TAIL");

            while ((entry = entry.next) != tail)
            {
                sj.add(entry.toString());
            }
            s = s + "\n" + sj.toString();
        }

        {
            Entry<K, V> entry = tail;
            StringJoiner sj = new StringJoiner(" -> ", "TAIL -> ", " -> HEAD");

            while ((entry = entry.previous) != head)
            {
                sj.add(entry.toString());
            }
            s = s + "\n" + sj.toString();
        }
        return s;
    }

    /**
     * Our entry as part of the hash map and 
     */
    private static class Entry<KEY, VALUE>
    {
        public final KEY key;
        public VALUE value;

        public Entry<KEY, VALUE> previous;
        public Entry<KEY, VALUE> next;

        /**
         * Constructor
         * 
         * @param key
         *            key to keep
         * @param value
         *            value to keep
         */
        public Entry(final KEY key, final VALUE value, final Entry<KEY, VALUE> previous, final Entry<KEY, VALUE> next)
        {
            this.key = key;
            this.value = value;
            this.previous = previous;
            this.next = next;
        }

        @Override
        public String toString()
        {
            return String.format("Entry [key=%s, value=%s]", key, value);
        }
    }
}
