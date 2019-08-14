package org.luksze.builder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionBuilder<T> {
    private final T[] elements;

    CollectionBuilder(T ... elements) {
        this.elements = elements;
    }

    public List<T> immutableList() {
        return ImmutableList.copyOf(elements);
    }

    public List<T> mutableList() {
        ArrayList<T> ts = new ArrayList<>();
        ts.addAll(Arrays.asList(elements));
        return ts;
    }

    public Set<T> immutableSet() {
        return ImmutableSet.copyOf(elements);
    }

    public Set<T> mutableSet() {
        HashSet<T> set = new HashSet<>();
        set.addAll(Arrays.asList(elements));
        return set;
    }

    public static <T> CollectionBuilder<T> of(T ... elements) {
        return new CollectionBuilder<T>(elements);
    }

}
