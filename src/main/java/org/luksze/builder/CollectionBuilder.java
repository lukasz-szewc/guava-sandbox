package org.luksze.builder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionBuilder<T> {
    private final T firstElement;

    CollectionBuilder(T firstElement) {
        this.firstElement = firstElement;
    }

    public List<T> immutableList() {
        return ImmutableList.of(firstElement);
    }

    public List<T> mutableList() {
        ArrayList<T> ts = new ArrayList<>();
        ts.add(firstElement);
        return ts;
    }

    public Set<T> immutableSet() {
        return ImmutableSet.of(firstElement);
    }

    public Set<T> mutableSet() {
        HashSet<T> set = new HashSet<>();
        set.add(firstElement);
        return set;
    }

}
