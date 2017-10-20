package org.luksze.builder;

public class Collections<T> {
    public static <T> CollectionBuilder<T> of(T ... elements) {
        return new CollectionBuilder<T>(elements);
    }
}
