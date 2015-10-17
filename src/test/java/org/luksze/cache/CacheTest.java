package org.luksze.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CacheTest {

    private Cache<String, Object> cache;

    @Before
    public void setUp() throws Exception {
        cache = CacheBuilder.newBuilder().build();
    }

    @Test
    public void assertNewCacheIsEmpty() throws Exception {
        Assert.assertEquals(cache.size(), 0);
    }

    @Test
    public void elementCanBeAddedIntoCache() throws Exception {
        cache.put("firstKey", new Object());
        Assert.assertEquals(cache.size(), 1);
    }
}
