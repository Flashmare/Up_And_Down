package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Controlls.GamePad;
import com.mygdx.game.Controlls.ButtonControlls;
import com.mygdx.game.Enteties.Enemy;
import com.mygdx.game.Enteties.EnemyManager;
import com.mygdx.game.Enteties.Ground;
import com.mygdx.game.Enteties.HudScore;
import com.mygdx.game.Enteties.Player;
import com.mygdx.game.Enteties.PlayerPosition;
import com.mygdx.game.UpAndDown;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


public class MultiplayerClientScreen implements Screen {

    private UpAndDown game;
    private World world;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Vector2 gravity = new Vector2(0, -100f);
    private Ground ground;
    private float TIME_STEP = 1/60f;
    private int VELOCITZITERATIONS = 8;
    private int POSITIONITERATIONS = 3;
    private Player player;
    private Enemy enemy;
    private EnemyManager enemyManager;
    private Stage stage;
    private GamePad gamePad;
    private ButtonControlls buttonControlls;
    private boolean gameNotOver;

    private float volume;
    private float PLAYER_WIDTH= 5f;
    private float PLAYER_HEIGHT = 5f;
    private TextureAtlas gameOver;
    private Animation animation;
    private float elapsedTime = 0;
    private HudScore hudScore;
    public Preferences prefs;
    private Socket socket;

    private InputStream networkIn;
    private byte[] networkRead;
    private OutputStream networkOut;
    private Thread network;





    public MultiplayerClientScreen(UpAndDown game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/20,gamecam);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        world = new World(gravity,true);
        stage = new Stage(gamePort,game.batch);
        gamePad = new GamePad(gamePort);
        buttonControlls = new ButtonControlls(gamePort);
        ground = new Ground(world,game.batch,gamePort);
        enemy = new Enemy(50,50, PLAYER_WIDTH,PLAYER_HEIGHT, world);
        player = new Player(50,50, PLAYER_WIDTH,PLAYER_HEIGHT, world,gamePort);
        prefs = Gdx.app.getPreferences("Options");
        gameOver = new TextureAtlas(Gdx.files.internal("endgame/gO.atlas"));
        animation = new Animation(1f / 12f, gameOver.getRegions());
        enemyManager = new EnemyManager(world);
        hudScore = new HudScore(gamePort,enemyManager);
        gameNotOver = true;

    }
    public void handleInput(float dt){
        if((Gdx.input.isTouched(0) && Gdx.input.getX(0) < Gdx.graphics.getWidth()*0.6f) ||(Gdx.input.isTouched(1) && Gdx.input.getX(1) < Gdx.graphics.getWidth()*0.6f)){
            if(player.getMovement() != Player.Movement.JUMP){
                player.setMovement(Player.Movement.RUN);
            }
            player.applyForce(new Vector2(gamePad.getPosition().x,player.getValocity().y));
        }
        else if(player.getMovement() != Player.Movement.JUMP){
            player.setMovement(Player.Movement.IDLE);
        }

        if(buttonControlls.isJumpPressed()){
            player.jump();
        }

        if(buttonControlls.isShootPressed()){
            player.shoot();
        }
    }

    public void update(float dt) {
        handleInput(dt);
        gamecam.update();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        volume = prefs.getFloat("Volume",1f);
        stage.addActor(ground);
        stage.addActor(hudScore);
        stage.addActor(player);
        stage.addActor(enemy);
        stage.addActor(enemyManager);
        stage.addActor(buttonControlls.getTable());
        stage.addActor(gamePad);
    }

    @Override
    public void render(float dt) {
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(0.752f,0.639f,0.482f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(gameNotOver){
            world.step(TIME_STEP, VELOCITZITERATIONS, POSITIONITERATIONS);
            stage.draw();
        }
        if(player.getPosition().y < -10 && enemy.getPosition().y < -10) {
            gameNotOver = false;
            elapsedTime += Gdx.graphics.getDeltaTime();
            game.batch.begin();
            game.batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime, false),0,0,gamePort.getWorldWidth(),gamePort.getWorldHeight());
            game.batch.end();
        }
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
    }

    public void setIp(String IP){

        socket = Gdx.net.newClientSocket(Net.Protocol.TCP, IP, 2555, null);
        try {
            networkOut = new ObjectOutputStream(socket.getOutputStream());
            networkIn = new ObjectInputStream((socket.getInputStream()));
            networkRead = new byte[16];
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread network = new Thread(){
            public void run(){
                while(true){
                networkConnection();
                }
            }
        };
        network.start();
    }

    private void networkConnection(){
        try {
            networkOut.write(player.getPlayerPosition().toBytes());
            networkOut.flush();
            networkIn.read(networkRead,0,16);
            enemy.setPlayerPosition(new PlayerPosition(networkRead));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

