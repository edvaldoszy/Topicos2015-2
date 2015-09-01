package com.edvaldotsi.spaceinvaders.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.edvaldotsi.spaceinvaders.MainGame;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;
import com.edvaldotsi.spaceinvaders.element.Explosion;

/**
 * Created by Edvaldo on 03/08/15.
 */
public class GameScreen extends BaseScreen {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage stage, stageInfo;

    private BitmapFont font;
    private Label lbScore, lbGameOver;

    private Texture texturePlayer, textureShot;
    private Image player;

    private boolean toLeft, toRight, toUp, toDown;
    private boolean shoting = false;

    private Array<Image> shots = new Array<Image>();

    private Array<Image> enemy1 = new Array<Image>();
    private Array<Image> enemy2 = new Array<Image>();
    private Texture textureEnemy1, textureEnemy2;

    private Rectangle recPlayer, recShot, recEnemy;

    private Integer score = 0;
    private boolean gameOver = false;

    private Array<Texture> textureExplosion = new Array<Texture>();
    private Array<Explosion> explosions = new Array<Explosion>();

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
        stageInfo = new Stage(new FillViewport(camera.viewportWidth, camera.viewportHeight, camera));

        initTextures();
        initFont();
        initPlayer();
        initInformation();

        recPlayer = new Rectangle();
        recShot = new Rectangle();
        recShot = new Rectangle();
        recEnemy = new Rectangle();
    }

    private void initTextures() {
    	textureShot = new Texture("sprites/shot.png");
    	textureEnemy1 = new Texture("sprites/enemy1.png");
    	textureEnemy2 = new Texture("sprites/enemy2.png");

        for (int n = 1; n <= 17; n++) {
            Texture t = new Texture("sprites/explosion-" + n + ".png");
            textureExplosion.add(t);
        }
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
        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        style.font = font;

        lbScore = new Label("Score: 0", style);
        stageInfo.addActor(lbScore);

        lbGameOver = new Label("Game Over", style);
        lbGameOver.setVisible(false);
        stageInfo.addActor(lbGameOver);
    }

    private void initFont() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
        p.color = Color.WHITE;
        p.size = 18;
        font = gen.generateFont(p);
        gen.dispose();
    }

    /**
     * Chamado a todo quadro de atualização do jogo (FPS)
     * @param delta Tempo entre um quadro e outro (em segundos)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.023f, 0.039f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!gameOver) {
            controls();

            updateScore();

            updateEnemy(delta);
            updatePlayer(delta);
            updateShots(delta);

            detectCollisions(enemy1, 5);
            detectCollisions(enemy2, 10);
        } else {
            lbGameOver.setVisible(true);
            lbGameOver.setPosition(camera.viewportWidth / 2 - lbGameOver.getWidth() / 2, camera.viewportHeight / 2 - lbGameOver.getHeight() / 2);
        }

        updateExplosions(delta);

        // Atualiza a situação do palco
        stage.act(delta);
        stageInfo.act(delta);

        // Desenha o palco na tela
        stage.draw();
        stageInfo.draw();
    }

    private void updateExplosions(float delta) {
        for (Explosion e : explosions) {
            if (e.getState() >= 16) {
                explosions.removeValue(e, true);
                e.getActor().remove();
            } else {
                e.update(delta);
            }
        }
    }

    private void updateScore() {
        lbScore.setText("Score: " + score.toString());
        lbScore.setPosition(20, camera.viewportHeight - 30);
    }

    private void detectCollisions(Array<Image> list, int score) {
        recPlayer.set(player.getX(), player.getY(), player.getWidth(), player.getHeight());

        for (Image enemy : list) {
            recEnemy.set(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
            for (Image shot : shots) {
                recShot.set(shot.getX(), shot.getY(), shot.getWidth(), shot.getHeight());
                if (recEnemy.overlaps(recShot)) {
                    // Ocorre uma solisão
                    this.score += score;
                    enemy.remove();
                    list.removeValue(enemy, true);
                    shot.remove();
                    shots.removeValue(shot, true);
                    createExplosion(enemy.getX(), enemy.getY());
                }
            }
            if (recEnemy.overlaps(recPlayer)) {
                gameOver = true;
                enemy.remove();
                list.removeValue(enemy, true);
            }
        }
    }

    private void createExplosion(float x, float y) {
        Image actor = new Image(textureExplosion.get(0));
        actor.setPosition(x, y);
        stage.addActor(actor);
        explosions.add(new Explosion(actor, textureExplosion));
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
    private final float MIN_ENEMY_INTERVAL = 0.3f; // Minimo de tempo entre os tiros

    private void updateEnemy(float delta) {
    	int tipo = MathUtils.random(1, 3);
        float speed1 = 200, speed2 = 300;

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
    		float y = enemy.getY() - speed1 * delta;
    		enemy.setPosition(x, y);
            if (enemy.getY() + enemy.getHeight() < 0) {
                enemy.remove();
                enemy1.removeValue(enemy, true);
            }
    	}

        for (Image enemy : enemy2) {
            float x = enemy.getX();
            float y = enemy.getY() - speed2 * delta;
            enemy.setPosition(x, y);
            if (enemy.getY() + enemy.getHeight() < 0) {
                enemy.remove();
                enemy2.removeValue(enemy, true);
            }
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
     *
     */
    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        stageInfo.dispose();
        font.dispose();
        texturePlayer.dispose();
        textureShot.dispose();
        textureEnemy1.dispose();
        textureEnemy2.dispose();

        for (Texture t : textureExplosion) {
            t.dispose();
        }
    }
}
