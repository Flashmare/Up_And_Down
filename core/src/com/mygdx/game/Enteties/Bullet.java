package com.mygdx.game.Enteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Bullet {
    private final float SPEED = 350f;
    private final float TTL = 0.5f;
    private static Sprite sprite;
    private Vector2 position;
    private float angle;
    private World world;
    public boolean remove = false;
    private Body body;
    private BodyDef square;
    private PolygonShape shape;
    private FixtureDef fixtureDef;
    private boolean orientation;
    private float deltaTime;

    public Bullet(float x, float y, float angle,World world,boolean orientation){
        position = new Vector2(x,orientation? (y-angle/24): (y+angle/24));
        fixtureDef = new FixtureDef();
        square = new BodyDef();
        shape = new PolygonShape();
        this.angle = angle;
        this.orientation = orientation;
        sprite = new Sprite(new Texture(Gdx.files.internal("bullet.png")));
        sprite.setPosition(position.x,position.y);
        sprite.setScale(0.05f);
        sprite.setFlip(orientation,false);
        square.type = BodyDef.BodyType.DynamicBody;
        shape.setAsBox(0.2f,0.2f);
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 100f;
        fixtureDef.restitution = 0.1f;
        this.world = world;
        this.body = world.createBody(square);
        this.body.isBullet();
        this.body.createFixture(fixtureDef);
        this.body.setTransform(position.x,position.y,angle);

    }
    public void update(){
        deltaTime += Gdx.graphics.getDeltaTime();
        this.body.applyForceToCenter(new Vector2(orientation? -(float)(SPEED*Math.cos(angle*Math.PI/180)):(float)(SPEED*Math.cos(angle*Math.PI/180)),orientation? -(float)(SPEED*Math.sin(angle*Math.PI/180)):(float)(SPEED*Math.sin(angle*Math.PI/180))),false);
        sprite.setPosition(body.getPosition().x-sprite.getWidth()/2,body.getPosition().y-sprite.getHeight()/2);
        sprite.setRotation(body.getAngle());
        if(deltaTime > TTL){this.remove=true;}
    }

    public void draw (Batch batch) {
        if(!this.remove){
        update();
        sprite.draw(batch);
        }
    }

    public void dispose(){
        this.world.destroyBody(this.body);
    }

}
