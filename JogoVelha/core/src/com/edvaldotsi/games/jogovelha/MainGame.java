package com.edvaldotsi.games.jogovelha;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MainGame extends ApplicationAdapter {

	private ShapeRenderer sr;

	private float largura, altura;
	private int div = 3;

	private char[][] jogos;

	@Override
	public void create () {
		sr = new ShapeRenderer();

		largura = Gdx.graphics.getWidth();
		altura = Gdx.graphics.getHeight();

		jogos = new char[div][div];
		jogos[2][0] = 'x'; jogos[2][1] = 'x'; jogos[2][2] = 'o';
		jogos[1][0] = 'x'; jogos[1][1] = 'o'; jogos[1][2] = 'x';
		jogos[0][0] = 'o'; jogos[0][1] = 'x'; jogos[0][2] = 'x';
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sr.begin(ShapeRenderer.ShapeType.Line);

		float sizeX = largura / div, sizeY = altura / div;

		sr.setColor(1, 0, 0, 1);
		for (int n = 1; n < div; n++) {
			sr.line((sizeX * n), 0, (sizeX * n), altura);
			sr.line(0, (sizeY * n), largura, (sizeY * n));
		}

		for (int n = 0; n < div; n++)
			for (int m = 0; m < div; m++) {
				float x = sizeX / 2 + sizeX * n;
				float y = sizeY / 2 + sizeY * m;

				if (jogos[n][m] == 'x') {
					sr.setColor(1, 1, 0, 1);
					fazerX(x, y, 20);
				} else {
					sr.setColor(1, 0, 1, 1);
					fazerO(x, y, 20);
				}
			}

		sr.end();
	}

	private void fazerX(float x, float y, int size) {

		sr.line(x - size, y - size, x + size, y + size);
		sr.line(x - size, y + size, x + size, y - size);
	}

	private void fazerO(float x, float y, int size) {

		sr.circle(x, y, size);
	}
}
