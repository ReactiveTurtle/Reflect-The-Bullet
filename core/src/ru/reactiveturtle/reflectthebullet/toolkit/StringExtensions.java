package ru.reactiveturtle.reflectthebullet.toolkit;

public final class StringExtensions {
    private StringExtensions() {
    }

    public static String join(String separator, String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            String str = strings[i];
            stringBuilder.append(str);
            if (i == strings.length - 1){
                stringBuilder.append(separator);
            }
        }
        return stringBuilder.toString();
    }
}
