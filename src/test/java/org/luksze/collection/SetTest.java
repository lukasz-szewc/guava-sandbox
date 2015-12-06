package org.luksze.collection;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import org.junit.Test;

import static java.lang.Boolean.TRUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SetTest {
    @Test
    public void canUnionTwoSets() throws Exception {
        //given
        ImmutableSet<String> firstSet = ImmutableSet.of("first", "second");
        ImmutableSet<String> secondSet = ImmutableSet.of("third", "fourth");

        //when
        SetView<String> union = Sets.union(firstSet, secondSet);

        //then
        assertThat(union.containsAll(firstSet), equalTo(TRUE));
        assertThat(union.containsAll(secondSet), equalTo(TRUE));
    }

    @Test
    public void canIntersectTwoSets() throws Exception {
        //given
        ImmutableSet<String> firstSet = ImmutableSet.of("first", "second");
        ImmutableSet<String> secondSet = ImmutableSet.of("second", "third");

        //when
        SetView<String> intersection = Sets.intersection(firstSet, secondSet);

        //then
        assertThat(intersection.contains("second"), equalTo(TRUE));
        assertThat(intersection.size(), equalTo(1));
    }
}
