package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {

    private UpAndDown game;
    Texture texture;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthoCachedTiledMapRenderer maprenderer;
    private ShapeRenderer shapeRenderer;
    private Vector3 touchCoordinates;
    private boolean touched = false;

    public PlayScreen(UpAndDown game) {
        this.game = game;
        texture = new Texture("badlogic.jpg");
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),gamecam);
        maploader = new TmxMapLoader();
        map = maploader.load("Map.tmx");
        maprenderer = new OrthoCachedTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        shapeRenderer = new ShapeRenderer();
        touchCoordinates = new Vector3();

    }
    public void handleInput(float dt){
        touchCoordinates.x = Gdx.input.getX();
        touchCoordinates.y = Gdx.input.getY();
        gamePort.unproject(touchCoordinates);
        if(touchCoordinates.x+150 > Gdx.graphics.getWidth()/2){
            touchCoordinates.x = (Gdx.graphics.getWidth()/2)-150;
        }
        else if(touchCoordinates.x < 150.){
            touchCoordinates.x = 150;
        }
        if(touchCoordinates.y+150 > Gdx.graphics.getHeight()){
            touchCoordinates.y = Gdx.graphics.getHeight()-150;
        }
        else if(touchCoordinates.y < 150){
            touchCoordinates.y = 150;
        }

        if(Gdx.input.isTouched()){
            touched = true;
        }
        else{
            touched = false;
        }
    }

    public void update(float dt){
        handleInput(dt);
        gamecam.update();
        maprenderer.setView(gamecam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        maprenderer.render();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(touched){
            shapeRenderer.circle(touchCoordinates.x,touchCoordinates.y, 150);
        }
        shapeRenderer.end();


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

    }

    @Override
    public void dispose() {

    }
}
