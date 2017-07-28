package org.luksze.builder;

public class Collections<T> {
    public static <T> CollectionBuilder<T> of(T firstElement) {
        return new CollectionBuilder<T>(firstElement);
    }
}
