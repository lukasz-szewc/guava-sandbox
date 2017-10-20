package org.luksze.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BasicCacheTest {

    private Cache<String, Object> cache;

    @Before
    public void setUp() throws Exception {
        cache = CacheBuilder.newBuilder().build();
    }

    @Test
    public void assertNewCacheIsEmpty() throws Exception {
        assertEquals(cache.size(), 0);
    }

    @Test
    public void elementCanBeAddedIntoCache() throws Exception {
        //when
        cache.put("firstKey", new Object());

        //then
        assertEquals(cache.size(), 1);
    }

    @Test
    public void valueWillBeReplaced() throws Exception {
        //given
        cache.put("key", "initialValue");

        //when
        cache.put("key", "overrideValue");

        //then
        assertEquals(cache.size(), 1);
        assertEquals("overrideValue", cache.getIfPresent("key"));
    }

    @Test
    public void cacheContentCanBeInvalidated() throws Exception {
        //given
        cache.put("firstKey", "firstValue");
        cache.put("secondKey", "secondValue");

        //when
        cache.invalidateAll();

        //then
        assertEquals(cache.size(), 0);
    }

    @Test
    public void specificKeyCanBeInvalidated() throws Exception {
        //given
        cache.put("firstKey", "firstValue");
        cache.put("secondKey", "secondValue");

        //when
        cache.invalidate("firstKey");

        //then
        assertEquals(cache.size(), 1);
        assertNull(cache.getIfPresent("firstKey"));
        assertEquals("secondValue", cache.getIfPresent("secondKey"));
    }

}
