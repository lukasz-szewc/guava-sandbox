package org.luksze.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CacheRemovalListenerTest {

    public static final String INITIAL_VALUE = "firstValue";
    public static final String INITIAL_KEY = "firstKey";
    private Cache<String, Object> cache;
    private StringRemovalListener removalListener;

    @Before
    public void setUp() throws Exception {
        removalListener = new StringRemovalListener();
        constructCacheWithOneValue();
    }

    @Test
    public void assertNewCacheIsEmpty() throws Exception {
        //when
        cache.put("secondKey", "secondValue");

        //then
        Assert.assertTrue(removalListener.wasNotifiedAboutRemovalOfInitialElement());
        Assert.assertEquals(1, cache.size());
    }

    private void constructCacheWithOneValue() {
        cache = CacheBuilder.newBuilder().maximumSize(1).removalListener(removalListener).build();
        cache.put(INITIAL_KEY, INITIAL_VALUE);
    }

    private static class StringRemovalListener implements RemovalListener<String, Object> {

        private List<RemovalNotification<String, Object>> list = new ArrayList<>();

        public void onRemoval(RemovalNotification<String, Object> notification) {
            list.add(notification);
        }

        public boolean wasNotifiedAboutRemovalOfInitialElement() {
            for (RemovalNotification<String, Object> notification : list) {
                if (INITIAL_KEY.equals(notification.getKey()) && INITIAL_VALUE.equals(notification.getValue())) {
                    return true;
                }
            }
            return false;
        }
    }
}
