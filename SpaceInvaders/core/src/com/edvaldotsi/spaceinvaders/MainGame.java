package com.edvaldotsi.spaceinvaders;

import com.badlogic.gdx.Game;
import com.edvaldotsi.spaceinvaders.screen.GameScreen;
import com.edvaldotsi.spaceinvaders.screen.MenuScreen;

public class MainGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
