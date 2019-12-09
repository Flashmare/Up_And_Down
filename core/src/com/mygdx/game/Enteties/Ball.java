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

    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("badlogic.png")));
    private BodyDef ball = new BodyDef();
    private CircleShape shape = new CircleShape();
    private FixtureDef fixtureDef = new FixtureDef();
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
        // Center body is center sprite here
        float hw = sprite.getWidth() / 2.0f;
        float hh = sprite.getHeight() / 2.0f;
        float a = body.getAngle() * MathUtils.radiansToDegrees;
        float x = position.x - hw;
        float y = position.y - hh;

        sprite.setPosition(x, y);
        sprite.setRotation(a);
    }
    public Ball(float x, float y, float r, World world){

        sprite.setPosition(x,y);
        this.world = world;
        ball.type = BodyDef.BodyType.DynamicBody;
        ball.position.set(x,y);

        shape.setRadius(r);

        fixtureDef.shape = shape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.5f;
        body = world.createBody(ball);
        body.createFixture(fixtureDef);
    }

}
