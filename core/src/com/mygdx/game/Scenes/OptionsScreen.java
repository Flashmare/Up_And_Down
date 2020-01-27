package com.mygdx.game.Scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ScreenManager;
import com.mygdx.game.UpAndDown;

public class OptionsScreen implements Screen{

    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    private UpAndDown game;
    private Skin skin;
    private Table topTable;
    private Table mainTable;
    public Preferences prefs;

    public OptionsScreen(UpAndDown game)
    {
        atlas = new TextureAtlas("rustyrobotui/rusty-robot-ui.atlas");
        skin = new Skin(Gdx.files.internal("rustyrobotui/rusty-robot-ui.json"), atlas);
        background = new Texture("background_main.png");
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
        topTable = new Table();
        mainTable = new Table();
        prefs = Gdx.app.getPreferences("Options");
        prefs.putFloat("Volume",1f);
        topTable.setFillParent(true);
        topTable.right().top();

        mainTable.setSkin(skin);
        mainTable.setFillParent(true);
        mainTable.center();

        TextButton exitButton = new TextButton("Back", skin);
        TextButton applyButton = new TextButton("Apply",skin);
        final Slider volume = new Slider(0,1,0.05f,false,skin);
        volume.setValue(prefs.getFloat("Volume"));
        final Label volumeValue = new Label("Volume:  "+String.valueOf(Math.round(volume.getValue()*100f)/100f),skin);

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.setMainScreen();

            }
        });

       applyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefs.putFloat("Volume",volume.getValue());
                prefs.flush();
            }
        });

        volume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                volumeValue.setText("Volume:  "+String.valueOf(Math.round(volume.getValue()*100f)/100f));
            }
        });



        topTable.add(exitButton);
        topTable.row();
        topTable.add(applyButton);

        mainTable.add(volumeValue);
        mainTable.row();
        mainTable.add(volume);
        mainTable.row();
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addActor(mainTable);
        stage.addActor(topTable);
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