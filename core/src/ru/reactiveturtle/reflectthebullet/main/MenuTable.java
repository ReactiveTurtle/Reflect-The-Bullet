package ru.reactiveturtle.reflectthebullet.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.Drawable;
import ru.reactiveturtle.reflectthebullet.level.LevelType;

import static ru.reactiveturtle.reflectthebullet.general.GameData.GAME_FONT;

public class MenuTable implements Drawable {
    private Label mMenuLabel;

    public MenuTable(DisplayMetrics displayMetrics) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal(GAME_FONT));
        labelStyle.fontColor = Color.WHITE;
        labelStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("table.png"))));
        labelStyle.font.getData().setScale(displayMetrics.widthPixels() / 1400f);
        mMenuLabel = new Label("\n\nГлавное меню", labelStyle);
        mMenuLabel.setAlignment(Align.center);
        mMenuLabel.setSize(displayMetrics.widthPixels() / 2.25f, displayMetrics.widthPixels() / 3f);
        mMenuLabel.setPosition(displayMetrics.widthPixels() / 2f - displayMetrics.widthPixels() / 4.5f, displayMetrics.heightPixels() - mMenuLabel.getHeight());
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        mMenuLabel.draw(spriteBatch, 1f);
    }

    public void setText(Text text) {
        switch (text) {
            case MAIN_MENU:
                mMenuLabel.setText("\n\nГлавное меню");
                break;
            case LEVELS:
                mMenuLabel.setText("\n\nУровни");
                break;
            case SETTINGS:
                mMenuLabel.setText("\n\nНастройки");
                break;
            case DESERT:
                mMenuLabel.setText("\n\nПустыня");
                break;
            case CELT:
                mMenuLabel.setText("\n\nРавнина");
                break;
            default:
                throw new EnumConstantNotPresentException(Text.class, text.name());
        }
    }

    public enum Text {
        MAIN_MENU,
        LEVELS,
        SETTINGS,
        DESERT,
        CELT
    }
}
