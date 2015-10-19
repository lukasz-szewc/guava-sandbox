package org.luksze.cache;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.ArrayList;

import static com.google.common.cache.CacheBuilder.newBuilder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CacheLoaderUsageTest {

    @Test
    public void callableIsCalledForNotExistingKey() throws Exception {
        //given
        StringCacheLoader loader = new StringCacheLoader();
        LoadingCache<String, Object> loadingCache = newBuilder().build(loader);
        String notExistentKey = "notExistentKey";

        //when
        Object returnedValue = loadingCache.getUnchecked(notExistentKey);

        //then
        assertTrue(loader.hasAskedForKey(notExistentKey));
        assertEquals(StringCacheLoader.VALUE, returnedValue);
    }

    private static class StringCacheLoader extends CacheLoader<String, Object> {

        public static final String VALUE = "value";
        private final ArrayList<Object> list = new ArrayList<>();

        @Override
        public Object load(String key) throws Exception {
            list.add(key);
            return VALUE;
        }

        public boolean hasAskedForKey(String key) {
            return list.contains(key);
        }
    }
}
