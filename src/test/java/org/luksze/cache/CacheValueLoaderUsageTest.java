package org.luksze.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;

import static com.google.common.cache.CacheBuilder.newBuilder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CacheValueLoaderUsageTest {

    private Cache<Object, Object> cache;

    @Before
    public void setUp() throws Exception {
        cache = newBuilder().build();
    }

    @Test
    public void callableIsCalledForNotExistingKey() throws Exception {
        //given
        ValueLoader valueLoader = new ValueLoader();

        //when
        Object returnedValue = cache.get("notExistentKey", valueLoader);

        //then
        assertTrue(valueLoader.hit);
        assertEquals(ValueLoader.RETURNED_VALUE, returnedValue);
    }

    @Test
    public void callableIsNotCalledForExistingKey() throws Exception {
        //given
        ValueLoader valueLoader = new ValueLoader();
        cache.put("existentKey", "value");

        //when
        Object returnedValue = cache.get("existentKey", valueLoader);

        //then
        assertFalse(valueLoader.hit);
        assertEquals("value", returnedValue);
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
