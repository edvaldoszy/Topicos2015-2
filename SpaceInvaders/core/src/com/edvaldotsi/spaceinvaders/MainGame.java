package com.edvaldotsi.spaceinvaders;

import com.badlogic.gdx.Game;
import com.edvaldotsi.spaceinvaders.screen.GameScreen;

public class MainGame extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}
}
