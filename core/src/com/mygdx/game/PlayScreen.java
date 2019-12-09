package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Enteties.Ball;
import com.mygdx.game.Enteties.Ground;

public class PlayScreen implements Screen {

    private UpAndDown game;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Texture img;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private ShapeRenderer shapeRenderer;
    private Vector3 touchCoordinates;
    private boolean touched = false;
    private Sprite sprite;
    private BitmapFont font;
    private Vector2 gravity = new Vector2(0, -98.10f);
    private Ground ground;

    private FixtureDef fixtureDef;
    private float TIME_STEP = 1/60f;
    private int VELOCITZITERATIONS = 8;
    private int POSITIONITERATIONS = 3;
    private float time = 0;
    private Ball ball;
    private Stage stage;


    public PlayScreen(UpAndDown game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),gamecam);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        touchCoordinates = new Vector3();
        world = new World(gravity,true);
        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.setDrawVelocities(true);
        //ballDef = new BodyDef();

        fixtureDef = new FixtureDef();
        stage = new Stage();

        ground = new Ground(world);


    }
    public void handleInput(float dt){
    }

    public void update(float dt){
        handleInput(dt);
        gamecam.update();
        touchCoordinates.x = Gdx.input.getX();
        touchCoordinates.y = Gdx.input.getY();
        gamePort.unproject(touchCoordinates);
        if(Gdx.input.isTouched() && time > 0.5){
            time = 0;
            stage.addActor(new Ball(touchCoordinates.x, touchCoordinates.y, 125, world));
            //world.createBody(ballDef).createFixture(fixtureDef);
        }



        for(Actor actor : stage.getActors())
        {
            if(time > 2)
            {
                actor.remove();
            }
        }
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(Body body: bodies)
        {
            if(time > 2 )
            {
                world.destroyBody(body);
            }
        }

        time += dt;
    }

    @Override
    public void show() {

        /*

        ballDef.type = BodyDef.BodyType.DynamicBody;
        ballDef.position.set(5,5);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);


        fixtureDef.shape = shape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.5f;*/




    }

    @Override
    public void render(float dt) {
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world,gamecam.combined);
        //font.draw(game.batch, touchCoordinates.toString(), 200, 200);
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
    }
}
