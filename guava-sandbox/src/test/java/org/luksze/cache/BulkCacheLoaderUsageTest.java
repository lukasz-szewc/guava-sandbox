package org.luksze.cache;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.cache.CacheBuilder.newBuilder;
import static org.junit.Assert.assertEquals;

public class BulkCacheLoaderUsageTest {

    @Test
    public void callableIsCalledForNotExistingKey() throws Exception {
        //given
        LoadingCache<String, Object> cache = newBuilder().build(new BulkCacheLoader());

        //when
        ImmutableMap<String, Object> map = cache.getAll(Arrays.asList("firstKey", "secondKey"));

        //then
        assertEquals("firstValue", map.get("firstKey"));
        assertEquals("secondValue", map.get("secondKey"));
    }

    private static class BulkCacheLoader extends CacheLoader<String, Object> {

        @Override
        public Map<String, Object> loadAll(Iterable<? extends String> keys) throws Exception {
            HashMap<String, Object> map = new HashMap<>();
            map.put("firstKey", "firstValue");
            map.put("secondKey", "secondValue");
            return map;
        }

        @Override
        public Object load(String key) throws Exception {
            throw new IllegalStateException();
        }
    }
}
