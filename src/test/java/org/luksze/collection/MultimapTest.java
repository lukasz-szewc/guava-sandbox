package org.luksze.collection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MultimapTest {

    @Test
    public void hashMultimapDoesNotAcceptsDuplication() throws Exception {
        //given
        HashMultimap<String, Integer> multimap = HashMultimap.create();

        //when
        multimap.put("first", 1);
        multimap.put("first", 2);
        multimap.put("first", 1);

        //then
        assertThat(multimap.size(), is(2));
    }

    @Test
    public void arrayListMultimapAcceptsDuplications() throws Exception {
        //given
        ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();

        //when
        multimap.put("first", 1);
        multimap.put("first", 2);
        multimap.put("first", 1);

        //then
        assertThat(multimap.size(), is(3));
    }
}
