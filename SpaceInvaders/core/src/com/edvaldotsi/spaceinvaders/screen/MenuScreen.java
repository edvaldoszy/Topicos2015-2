package com.edvaldotsi.spaceinvaders.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.edvaldotsi.spaceinvaders.MainGame;

/**
 * Created by Edvaldo on 14/09/2015.
 */
public class MenuScreen extends BaseScreen {

    private OrthographicCamera camera;
    private Stage stage;

    private ImageTextButton startButton;
    private Label lbTitle;
    private Label lbScore;

    private BitmapFont titleFont;
    private BitmapFont buttonFont;

    private Texture buttonTexture;
    private Texture downButtonTexture;

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
        initButtons();
    }

    private void initButtons() {
        buttonTexture = new Texture("buttons/button.png");
        downButtonTexture = new Texture("buttons/button-down.png");

        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.font = buttonFont;
        style.up = new SpriteDrawable(new Sprite(buttonTexture));
        style.down = new SpriteDrawable(new Sprite(downButtonTexture));

        startButton = new ImageTextButton("  Iniciar  ", style);
        stage.addActor(startButton);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Evento do botão
                game.setScreen(new GameScreen(game));
            }
        });
    }

    private void initLabels() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = titleFont;

        lbTitle = new Label("SPACE INVADERS", style);
        stage.addActor(lbTitle);

        Preferences preferences = Gdx.app.getPreferences("SpaceInvaders");
        int highScore = preferences.getInteger("high_score", 0);


        style = new Label.LabelStyle();
        style.font = buttonFont;
        lbScore = new Label("Pontuação máxima " + highScore, style);
        stage.addActor(lbScore);
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

        params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 32;
        params.color = Color.BLACK;
        buttonFont = generator.generateFont(params);
        generator.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateLabels();
        updateButtons();

        stage.act(delta);
        stage.draw();
    }

    private void updateButtons() {
        startButton.setPosition(camera.viewportWidth / 2 - startButton.getPrefWidth() / 2, camera.viewportHeight / 2 - startButton.getPrefHeight() / 2);
    }

    private void updateLabels() {
        float x = camera.viewportWidth / 2 - lbTitle.getPrefWidth() / 2;
        float y = camera.viewportHeight - 200;
        lbTitle.setPosition(x, y);

        lbScore.setPosition(camera.viewportWidth / 2 - lbScore.getPrefWidth() / 2, 40);
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
