package org.luksze.builder;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

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

}
