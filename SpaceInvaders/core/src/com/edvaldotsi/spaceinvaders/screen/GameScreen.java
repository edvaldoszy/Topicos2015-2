package com.edvaldotsi.spaceinvaders.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.edvaldotsi.spaceinvaders.MainGame;

/**
 * Created by edvaldo on 03/08/15.
 */
public class GameScreen extends BaseScreen {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage stage;

    private BitmapFont font;
    private Label lbScore;

    /**
     * Construtor padrão da tela do jogo
     *
     * @param game Referência da classe principal
     */
    public GameScreen(MainGame game) {
        super(game);
    }

    /**
     * Chamado quando a tela é exibida
     */
    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        stage = new Stage(new FillViewport(camera.viewportWidth, camera.viewportHeight, camera));

        initFont();
        initInformation();
    }

    private void initInformation() {
        Label.LabelStyle estilo = new Label.LabelStyle();
        estilo.fontColor = Color.WHITE;
        estilo.font = font;

        lbScore = new Label("Score: 0", estilo);
        stage.addActor(lbScore);
    }

    private void initFont() {
        font = new BitmapFont();
    }

    /**
     * Chamado a todo quadro de atualização do jogo (FPS)
     * @param delta Tempo entre um quadro e outro (em segundos)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        lbScore.setPosition(20, camera.viewportHeight - 30);

        stage.act(delta);
        stage.draw();
    }

    /**
     * É chamado sempre que há uma alteração no tamanho da tela
     * @param width Novo valor da largura da tela
     * @param height Novo valor da altura da tela
     */
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
    }

    /**
     * É chamado sempre que o jogo for minimizado
     */
    @Override
    public void pause() {

    }

    /**
     * É chamado sempre que o jogo voltar para o primeiro plano
     */
    @Override
    public void resume() {

    }

    /**
     * É chamado quando a tela for destruída
     */
    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        font.dispose();
    }
}
