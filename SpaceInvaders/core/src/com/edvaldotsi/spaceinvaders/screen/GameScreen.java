package com.edvaldotsi.spaceinvaders.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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

    private Texture texturePlayer, texturePlayerLeft, texturePlayerRight;
    private Image player;

    private boolean toLeft, toRight, toUp, toDown;

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
        initPlayer();
    }

    private void initPlayer() {

        texturePlayer = new Texture("spaceship/player.jpg");
        player = new Image(texturePlayer);

        float x = camera.viewportWidth / 2 - player.getWidth() / 2;
        float y = 10;
        player.setPosition(x, y);

        stage.addActor(player);
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
        Gdx.gl.glClearColor(0.023f, 0.039f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        lbScore.setPosition(20, camera.viewportHeight - 30);

        controls();
        updatePlayer(delta);

        stage.act(delta);
        stage.draw();
    }

    private void updatePlayer(float delta) {
        float speed = 200; // Velocidade de movimento do jogador
        float x = player.getX(), y = player.getY();

        if (toLeft) {
            x = player.getX() - speed * delta;
        } else if (toRight)
            x = player.getX() + speed * delta;

        if (x <= 0 || (x + player.getWidth()) >= camera.viewportWidth)
            x = player.getX();

        /*
        if (toLeft)
            player.setDrawable(new SpriteDrawable(new Sprite(texturePlayerLeft)));
        else if (toRight)
            player.setDrawable(new SpriteDrawable(new Sprite(texturePlayerRight)));
        else
            player.setDrawable(new SpriteDrawable(new Sprite(texturePlayer)));
        */

        if (toUp)
            y = player.getY() + speed * delta;
        else if (toDown)
            y = player.getY() - speed * delta;

        if (y <= 0 || (y + player.getHeight()) >= camera.viewportHeight)
            y = player.getY();

        player.setPosition(x, y);
    }

    private void controls() {

        toLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT) ? true : false;
        toRight = Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? true : false;
        toUp = Gdx.input.isKeyPressed(Input.Keys.UP) ? true : false;
        toDown = Gdx.input.isKeyPressed(Input.Keys.DOWN) ? true : false;
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
        texturePlayer.dispose();
        //texturePlayerLeft.dispose();
        //texturePlayerRight.dispose();
    }
}
