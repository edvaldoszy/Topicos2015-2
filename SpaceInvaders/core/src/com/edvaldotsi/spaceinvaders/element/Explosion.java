package com.edvaldotsi.spaceinvaders.element;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Edvaldo on 31/08/2015.
 */
public class Explosion {

    public static float animationSpeed = .5f / 17f;

    private float time = 0;
    private int state = 0;
    private Array<Texture> textures;
    private Image actor;

    public Explosion(Image actor, Array<Texture> textures) {
        this.actor = actor;
        this.textures = textures;
    }

    /**
     * Calcula o tempo acumulado e realiza a troca do estado da explosão
     *
     * @param delta
     */
    public void update(float delta) {
        time += delta;
        if (time >= animationSpeed) {
            time = 0;
            state++;
            Texture texture = textures.get(state);
            actor.setDrawable(new SpriteDrawable(new Sprite(texture)));
        }
    }

    public int getState() {
        return state;
    }

    public Image getActor() {
        return actor;
    }
}
