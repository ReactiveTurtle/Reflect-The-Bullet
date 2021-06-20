package ru.reactiveturtle.reflectthebullet.toolkit;

public class Value<T> {
    private T value;

    public Value() {
        this(null);
    }

    public Value(T value) {
        setValue(value);
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public boolean hasValue() {
        return value != null;
    }
}
