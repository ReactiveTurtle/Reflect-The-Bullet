package ru.reactiveturtle.reflectthebullet.level.loader;

public abstract class ReflectorDto {
    private String textureName;
    public ReflectorDto(String textureName) {
        this.textureName = textureName;
    }

    public String getTextureName() {
        return textureName;
    }
}
