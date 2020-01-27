package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ScreenManager;
import com.mygdx.game.UpAndDown;



public class MultiplayerScreen implements Screen{

    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    private UpAndDown game;
    private Skin skin;

    public MultiplayerScreen(UpAndDown game)
    {
        atlas = new TextureAtlas("rustyrobotui/rusty-robot-ui.atlas");
        skin = new Skin(Gdx.files.internal("rustyrobotui/rusty-robot-ui.json"), atlas);
        game = game;
        background = new Texture("background_main.png");
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        Table topTable = new Table();
        topTable.setFillParent(true);
        topTable.right().top();
        mainTable.setFillParent(true);
        mainTable.center();

        final TextField IP = new TextField("",skin);
        TextButton serverButton = new TextButton("Start Server", skin);
        TextButton connectButton = new TextButton("Connect", skin);
        TextButton exitButton = new TextButton("Back", skin);

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.setMainScreen();

            }
        });
        serverButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.setServerScreen();
            }
        });
        connectButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.setClientScreen(IP.getText());
            }
        });
        mainTable.add(IP);
        mainTable.row();
        mainTable.add(connectButton);
        mainTable.row();
        mainTable.add(serverButton);
        topTable.add(exitButton);

        stage.addActor(topTable);
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.752f,0.639f,0.482f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background,0,0,viewport.getWorldWidth(),viewport.getWorldHeight());
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
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
        skin.dispose();
        atlas.dispose();
    }
}