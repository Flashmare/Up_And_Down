package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Controlls.GamePad;
import com.mygdx.game.Controlls.Jump;
import com.mygdx.game.Enteties.Ball;
import com.mygdx.game.Enteties.Ground;
import com.mygdx.game.Enteties.Player;

public class PlayScreen implements Screen {

    private UpAndDown game;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Vector2 gravity = new Vector2(0, -100f);
    private Ground ground;
    private float TIME_STEP = 1/60f;
    private int VELOCITZITERATIONS = 8;
    private int POSITIONITERATIONS = 3;
    private float time = 0;
    private Player player;
    private Ball enemy;
    private Stage stage;
    private Vector2 playerSpeed;
    private GamePad gp;
    private Sound sound;
    private Jump jump;


    public PlayScreen(UpAndDown game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/30,gamecam);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        world = new World(gravity,true);
        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.setDrawVelocities(true);
        stage = new Stage();
        playerSpeed = new Vector2();
        stage.setViewport(gamePort);
        gp = new GamePad(world,gamePort);
        jump = new Jump(world,gamePort,stage);
        sound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));


    }
    public void handleInput(float dt){
        player.applyForce(gp.getPosition());

        if(jump.isJumpPressed() && player.getPosition().y < 5){
            player.applyForceToCenter(new Vector2(0f,10000f));
            sound.play(1.0f);
        }
    }

    public void update(float dt){

        handleInput(dt);




        gamecam.update();

        time += dt;
    }

    @Override
    public void show() {
        ground = new Ground(world);
        player = new Player(1,1, 2.5f,4f, world);
        enemy = new Ball(8,1, 1.25f, world);
        stage.addActor(player);
        stage.addActor(enemy);
        stage.addActor(gp);
        stage.addActor(jump.getTable());


    }

    @Override
    public void render(float dt) {
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world,gamecam.combined);
        world.step(TIME_STEP, VELOCITZITERATIONS, POSITIONITERATIONS);

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        sound.dispose();
    }
}