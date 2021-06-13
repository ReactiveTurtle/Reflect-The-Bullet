package ru.reactiveturtle.reflectthebullet.toolkit;

public class Value<T> {
    public T value;

    public Value() {
        this(null);
    }

    public Value(T value) {
        this.value = value;
    }
}
