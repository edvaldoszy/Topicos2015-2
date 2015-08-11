package com.edvaldotsi.games.jogovelha;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MainGame extends ApplicationAdapter {

	private ShapeRenderer sr;

	private float largura, altura;
	private float sizeX, sizeY;
	private int div = 3;

	private int[][] jogos = {
			{0, 2, 0},
			{0, 0, 0},
			{1, 2, 2}
	};

	int o = 1;

	@Override
	public void create () {

		sr = new ShapeRenderer();

		largura = Gdx.graphics.getWidth();
		altura = Gdx.graphics.getHeight();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glLineWidth(3);

		sr.begin(ShapeRenderer.ShapeType.Line);

		sizeX = largura / div;
		sizeY = altura / div;

		sr.setColor(1, 0, 0, 1);
		for (int n = 1; n < div; n++) {
			sr.line((sizeX * n), 0, (sizeX * n), altura);
			sr.line(0, (sizeY * n), largura, (sizeY * n));
		}

		for (int n = 0; n < div; n++)
			for (int m = 0; m < div; m++) {
				float x = sizeX / 2 + sizeX * n;
				float y = sizeY / 2 + sizeY * (2 - m);

				if (jogos[m][n] == 1) {
					sr.setColor(1, 1, 0, 1);
					fazerX(x, y, 40);
				} else if (jogos[m][n] == 2) {
					sr.setColor(1, 0, 1, 1);
					fazerO(x, y, 40);
				}

			}

		sr.end();

		jogar();
		ganhador();
	}

	private void fazerX(float x, float y, int size) {

		sr.line(x - size, y - size, x + size, y + size);
		sr.line(x - size, y + size, x + size, y - size);
	}

	private void fazerO(float x, float y, int size) {

		sr.circle(x, y, size);
	}

	private void jogar() {

		if (Gdx.input.justTouched()) {
			int posX = Math.abs(Gdx.input.getX() / (int) sizeX);
			int posY = Math.abs(Gdx.input.getY() / (int) sizeY);

			//if (jogos[posY][posX])
            //jogos[posY][posX] = o = !o;
		}
	}

	public void ganhador() {

	}
}
