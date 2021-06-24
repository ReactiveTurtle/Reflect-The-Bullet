package ru.reactiveturtle.reflectthebullet.toolkit;

public final class Objects {
    private Objects() {
    }

    public static <T> T requireNonNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        } else {
            return obj;
        }
    }
}
