package com.mygdx.game.Enteties;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Platform {
    private FixtureDef fixtureDef = new FixtureDef();
    private PolygonShape shape = new PolygonShape();
    private BodyDef ground = new BodyDef();
    private World world;
    private float hx,hy,x,y;
    public Platform(World world,float hx, float hy, float x, float y){
        ground.type = BodyDef.BodyType.StaticBody;
        fixtureDef.shape = shape;
        fixtureDef.density = 100f;
        fixtureDef.friction = 0.5f;
        shape.setAsBox(hx,hy);
        ground.position.x = x;
        ground.position.y = y;
        world.createBody(ground).createFixture(fixtureDef);
        this.x = x;
        this.y = y;
        this.hx = hx;
        this.hy = hy;
    }

    public float getHx() {
        return hx;
    }

    public float getHy() {
        return hy;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}