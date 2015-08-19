package com.edvaldotsi.jogoblocos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
    Texture[] textures;

    private BitmapFont font;

	Array<Element> list = new Array<Element>();

	@Override
	public void create () {
		batch = new SpriteBatch();
        textures = new Texture[]{
                new Texture("square.png"),
                new Texture("circle.png"),
                new Texture("triangle.png")
        };

        font = new BitmapFont();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		controls();

		batch.begin();
		String str = "Elementos: " + list.size;
		font.draw(batch, str, 20, Gdx.graphics.getHeight() - 20);

		for (Element e : list)
            e.draw();

		batch.end();
	}

	public void controls() {
		if (Gdx.input.justTouched()) {
			float x = Gdx.input.getX();
			float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            for (Element e : list)
                if (e.contains(x, y)) {
                    if (!e.change())
                        list.removeValue(e, true);
                    return;
                }

			Element e = new Element(batch, textures);
			e.setPos((x - e.getWidth() / 2), (y - e.getHeight() / 2));
			list.add(e);
		}
	}
}
