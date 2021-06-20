package ru.reactiveturtle.reflectthebullet.level.loader;

public class RectangleReflectorDto extends ReflectorDto{
    private final int x, y;
    public RectangleReflectorDto(String textureName, int x, int y) {
        super(textureName);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
