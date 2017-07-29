package org.luksze.builder;

import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CollectionsTest {

    @Test
    public void mutableListCreationTest() throws Exception {
        //given
        List<String> strings = Collections.of("something").mutableList();

        //then
        assertEquals(strings.size(), 1);
        assertThat(strings, hasItems("something"));

        //when
        strings.add("any");

        //then
        assertEquals(strings.size(), 2);
        assertThat(strings, hasItems("something", "any"));

    }

    @Test(expected = UnsupportedOperationException.class)
    public void immutableListCreationTest() throws Exception {
        //given
        List<String> strings = Collections.of("something").immutableList();

        //when
        strings.add("any");
    }

    @Test
    public void mutableSetCreationTest() throws Exception {
        //given
        Set<Integer> integers = Collections.of(1).mutableSet();

        //when
        integers.add(2);

        //then
        assertEquals(integers.size(), 2);
        assertThat(integers, hasItems(1, 2));
    }
}