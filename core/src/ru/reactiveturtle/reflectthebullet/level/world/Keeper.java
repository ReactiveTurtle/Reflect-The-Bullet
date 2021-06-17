package ru.reactiveturtle.reflectthebullet.level.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactListener;

import java.util.List;

import ru.reactiveturtle.reflectthebullet.base.Renderable;
import ru.reactiveturtle.reflectthebullet.game.objects.StaticObject;

public interface Keeper {
    void putBackground(List<Sprite> backList);

    void addVisualManager(Renderable renderable);

    void setScoreTableParams(Color textColor, int y);

    void putStaticObjects(List<StaticObject> reflectors);

    void putContactListener(ContactListener contactListener);

    void resetBullet(String id);

    void putScore(int score);

    void playSound(String name);

    void showRikoshetSparks(Vector2 collisionPoint);
}
