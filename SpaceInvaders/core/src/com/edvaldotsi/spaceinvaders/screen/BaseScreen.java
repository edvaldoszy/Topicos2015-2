package com.edvaldotsi.spaceinvaders.screen;

import com.badlogic.gdx.Screen;
import com.edvaldotsi.spaceinvaders.MainGame;

/**
 * Created by edvaldo on 03/08/15.
 */
public abstract class BaseScreen implements Screen {

    protected MainGame game;

    public BaseScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void hide() {
        dispose();
    }
}
