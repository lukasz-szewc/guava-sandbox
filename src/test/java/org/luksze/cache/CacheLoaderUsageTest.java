package org.luksze.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CacheLoaderUsageTest {

    @Test
    public void callableIsCalledForNotExistingKey() throws Exception {
        //given
        Cache<Object, Object> cache = CacheBuilder.newBuilder().maximumSize(1).build();
        ValueLoader valueLoader = new ValueLoader();

        //when
        Object returnedValue = cache.get("notExistentKey", valueLoader);

        //then
        assertTrue(valueLoader.hit);
        assertEquals(ValueLoader.RETURNED_VALUE, returnedValue);
    }

    private static class ValueLoader implements Callable<Object> {
        public static final String RETURNED_VALUE = "returnedValue";
        private boolean hit = false;

        @Override
        public Object call() throws Exception {
            hit = true;
            return RETURNED_VALUE;
        }
    }
}
