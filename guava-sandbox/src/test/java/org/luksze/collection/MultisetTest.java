package org.luksze.collection;

import com.google.common.collect.HashMultiset;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MultisetTest {

    private HashMultiset<String> strings;

    @Before
    public void setUp() throws Exception {
        strings = HashMultiset.create();
    }

    @Test
    public void canAddOneElementManyTimes() throws Exception {
        //when
        strings.add("test", 50);

        //then
        assertThat(strings.count("test"), equalTo(50));
    }

    @Test
    public void canRemoveElementsFromMultiset() throws Exception {
        //when
        strings.add("test", 10);
        strings.remove("test", 3);

        //then
        assertThat(strings.count("test"), equalTo(7));
    }
}
