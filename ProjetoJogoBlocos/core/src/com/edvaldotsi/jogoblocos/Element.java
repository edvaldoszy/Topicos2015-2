package com.edvaldotsi.jogoblocos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Edvaldo on 18/08/2015.
 */
public class Element  {

    private SpriteBatch batch;
    private Texture[] textures;
    private float x, y;

    private int pos = 0;

    public Element(SpriteBatch batch, Texture[] textures) {
        this.batch = batch;
        this.textures = textures;
    }

    public void draw() {
        batch.draw(textures[pos], x, y);
    }

    public int getWidth() {
        return textures[pos].getWidth();
    }

    public int getHeight() {
        return textures[pos].getHeight();
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean contains(float x, float y) {
        return (this.x >= (x - getWidth()) && this.x <= x + getWidth()) && (this.y >= (y - getHeight()) && this.y <= (y + getHeight()));
    }

    public boolean change() {
        if (++pos > 2)
            return false;

        return true;
    }
}
