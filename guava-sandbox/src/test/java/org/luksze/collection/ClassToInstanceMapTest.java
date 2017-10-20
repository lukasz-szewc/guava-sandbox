package org.luksze.collection;

import com.google.common.collect.MutableClassToInstanceMap;
import org.junit.Test;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.assertEquals;

public class ClassToInstanceMapTest {

    @Test
    public void canGetValuesWhereKeyIsTheType() throws Exception {
        MutableClassToInstanceMap<Object> map = MutableClassToInstanceMap.create();

        map.putInstance(String.class, "someValue");
        map.putInstance(Integer.class, 33);

        assertEquals("someValue", map.getInstance(String.class));
        assertEquals(valueOf(33), map.getInstance(Integer.class));
    }
}
