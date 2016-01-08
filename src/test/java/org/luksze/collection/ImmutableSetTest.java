package org.luksze.collection;

import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Test;

public class ImmutableSetTest {

    @Test
    public void defensiveCopyEqualsInitialSet() throws Exception {
        //given
        ImmutableSet<String> initialSet = ImmutableSet.of("red", "yellow", "blue");

        //when
        ImmutableSet<String> copy = ImmutableSet.copyOf(initialSet.asList());

        //then
        Assert.assertEquals(copy, initialSet);
    }
}
