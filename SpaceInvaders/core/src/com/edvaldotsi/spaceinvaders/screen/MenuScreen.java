package com.edvaldotsi.spaceinvaders.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.edvaldotsi.spaceinvaders.MainGame;

/**
 * Created by Edvaldo on 14/09/2015.
 */
public class MenuScreen extends BaseScreen {

    private OrthographicCamera camera;
    private Stage stage;

    private ImageButton startButton;
    private Label lbTitle;
    private Label lbScore;

    private BitmapFont titleFont;
    private BitmapFont buttonFont;

    private Texture buttonTexture;
    private Texture hoverButtonTexture;

    public MenuScreen(MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(new FillViewport(camera.viewportWidth, camera.viewportHeight, camera));
        Gdx.input.setInputProcessor(stage);

        initFonts();
        initLabels();
    }

    private void initLabels() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = titleFont;

        lbTitle = new Label("SPACE INVADERS", style);
        stage.addActor(lbTitle);
    }

    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 48;
        params.color = new Color(.25f, .25f, .85f, 1);
        params.shadowOffsetX = 0;
        params.shadowOffsetY = 0;
        params.shadowColor = Color.BLACK;

        titleFont = generator.generateFont(params);
        generator.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateLabels();

        stage.act(delta);
        stage.draw();
    }

    private void updateLabels() {
        float x = camera.viewportWidth / 2 - lbTitle.getPrefWidth() / 2;
        float y = camera.viewportHeight - 200;
        lbTitle.setPosition(x, y);
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        titleFont.dispose();
    }
}
