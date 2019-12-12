package com.mygdx.game.Enteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Ball extends Actor {

    private Sprite sprite;
    private BodyDef ball;
    private CircleShape shape;
    private FixtureDef fixtureDef;
    private World world;
    private Body body;
    private Vector2 position;



    @Override
    public void draw (Batch batch, float parentAlpha) {
        update();
        sprite.draw(batch);

    }

    public void update() {
        position = body.getPosition();
        sprite.setPosition(position.x - sprite.getWidth() / 2.0f, position.y - sprite.getHeight() / 2.0f);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }
    public Ball(float x, float y, float r, World world){
        //Definitons
        sprite = new Sprite(new Texture(Gdx.files.internal("badlogic.png")));
        ball = new BodyDef();
        shape = new CircleShape();
        fixtureDef = new FixtureDef();
        this.world = world;
        position = new Vector2(x,y);

        //Set sprite position and scale
        sprite.setPosition(x,y);
        sprite.setScale(1/100f,1/100f);

        ball.type = BodyDef.BodyType.DynamicBody;
        ball.position.set(x,y);

        shape.setRadius(r);

        fixtureDef.shape = shape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.5f;

        this.body = world.createBody(ball);
        this.body.createFixture(fixtureDef);


    }
    public void applyForce(Vector2 touch){
        this.body.setLinearVelocity(touch);
    }
    public Vector2 getPosition(){return position;}
}
