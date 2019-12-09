package com.mygdx.game.Enteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Ground extends Actor {

    private FixtureDef fixtureDef = new FixtureDef();
    ChainShape groundShape;
    private BodyDef ground = new BodyDef();


    public Ground(World world){
        ground.type = BodyDef.BodyType.StaticBody;

        groundShape = new ChainShape();
        groundShape.createChain(new Vector2[] {new Vector2(0, 0),new Vector2(Gdx.graphics.getWidth(),0)});
        fixtureDef.shape = groundShape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;
        world.createBody(ground).createFixture(fixtureDef);
    }
}
