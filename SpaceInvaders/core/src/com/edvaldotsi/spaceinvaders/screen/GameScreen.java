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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by edvaldo on 03/08/15.
 */
public class GameScreen extends BaseScreen {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage stage;

    private BitmapFont font;
    private Label lbScore;

    private Texture texturePlayer, textureShot;
    private Image player;

    private boolean toLeft, toRight, toUp, toDown;
    private boolean shoting = false;

    private Array<Image> shots = new Array<Image>();
    private Texture textureShoot;

    private Array<Image> enemy1 = new Array<Image>();
    private Array<Image> enemy2 = new Array<Image>();
    private Texture textureEnemy1, textureEnemy2;

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

        initTextures();
        initFont();
        initInformation();
        initPlayer();
    }

    private void initTextures() {
    	textureShot = new Texture("sprites/shot.png");
    	textureEnemy1 = new Texture("sprites/enemy1.png");
    	textureEnemy2 = new Texture("sprites/enemy2.png");
    }

    /**
     * Instancia os objetos do jogador e adiciona no palco
     */
    private void initPlayer() {

        texturePlayer = new Texture("spaceship/player.jpg");
        player = new Image(texturePlayer);

        float x = camera.viewportWidth / 2 - player.getWidth() / 2;
        float y = 10;
        player.setPosition(x, y);

        stage.addActor(player);
    }

    /**
     * Instancia as informações escritas na tela
     */
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
        updateShots(delta);
        updateEnemy(delta);

        // Atualiza a situação do palco
        stage.act(delta);

        // Desenha o palco na tela
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

        // Verifica se o jogador está dentro da tela
        if (toUp)
            y = player.getY() + speed * delta;
        else if (toDown)
            y = player.getY() - speed * delta;

        if (y <= 0 || (y + player.getHeight()) >= camera.viewportHeight)
            y = player.getY();

        player.setPosition(x, y);
    }

    private void controls() {

        toLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        toRight = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        toUp = Gdx.input.isKeyPressed(Input.Keys.UP);
        toDown = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        shoting = Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }

    private float shotInterval = 0; // Tempo acumulado entre os tiros
    private final float MIN_SHOT_INTERVAL = 0.2f; // Minimo de tempo entre os tiros

    private void updateShots(float delta) {
    	float speed = 600;

    	shotInterval += delta; // Acumula o tempo percorrido
    	if (shoting) {
    		// Verifica se o tempo mínimo foi atingido
    		if (shotInterval >= MIN_SHOT_INTERVAL) { // Percorre todos os tiros existentes
    			Image shot = new Image(textureShot);
	    		float x = (player.getX() + player.getWidth() / 2 - shot.getWidth() / 2), y = (player.getY() + player.getHeight());
	    		shot.setPosition(x, y);
	    		shots.add(shot);
	    		stage.addActor(shot);
	    		shotInterval = 0;
    		}
    	}

    	for (Image shot : shots) { // Movimenta o tiro em direção ao topo
    		float x = shot.getX(), y = shot.getY() + speed * delta;
    		shot.setPosition(x, y);

    		// Remove os tiros que sairam da tela
    		if (shot.getY() > camera.viewportHeight) {
                shots.removeValue(shot, true); // Remove da lista
                shot.remove(); // Remove do palco
            }
    	}
    }

    private float enemyInterval = 0; // Tempo acumulado entre os tiros
    private final float MIN_ENEMY_INTERVAL = 0.2f; // Minimo de tempo entre os tiros

    private void updateEnemy(float delta) {
    	int tipo = MathUtils.random(1, 3);
        float speed = 200;

        enemyInterval += delta;
        if (enemyInterval >= MIN_ENEMY_INTERVAL) {
            if (tipo == 1) {
                // Cria meteoro 1
                Image img = new Image(textureEnemy1);
                float x = MathUtils.random(0, camera.viewportWidth - img.getWidth());
                float y = MathUtils.random(camera.viewportHeight, camera.viewportHeight * 2);
                img.setPosition(x, y);
                enemy1.add(img);
                stage.addActor(img);
            } else {
                // Cria meteoro 2
                Image img = new Image(textureEnemy2);
                float x = MathUtils.random(0, camera.viewportWidth - img.getWidth());
                float y = MathUtils.random(camera.viewportHeight, camera.viewportHeight * 2);
                img.setPosition(x, y);
                enemy2.add(img);
                stage.addActor(img);
            }
            enemyInterval = 0;
        }

    	for (Image enemy : enemy1) {
    		float x = enemy.getX();
    		float y = enemy.getY() - speed * delta;
    		enemy.setPosition(x, y);
    	}

        for (Image enemy : enemy2) {
            float x = enemy.getX();
            float y = enemy.getY() - speed * delta;
            enemy.setPosition(x, y);
        }
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
        textureShot.dispose();
        textureEnemy1.dispose();
        textureEnemy2.dispose();;
    }
}
