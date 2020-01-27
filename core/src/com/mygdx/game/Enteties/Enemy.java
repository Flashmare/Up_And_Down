package com.mygdx.game.Enteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor implements Character {
    private BodyDef square;
    private CircleShape circle;
    private PolygonShape shape;
    private FixtureDef fixtureDef;
    private World world;
    private Body body;

    private TextureAtlas run, idle,jump,runGun,idleGun,jumpGun;
    private Animation animation,animationGun;
    private float elapsedTime = 0f;
    private boolean orientation;
    private float rotation,w,h;
    private Movement movement;
    private Bullets bullets;



    public enum Movement {
        IDLE,
        RUN,
        JUMP ;
    }

    public Enemy(float x, float y, float w, float h, World world){
        this.w = w;
        this.h = h;
        run = new TextureAtlas(Gdx.files.internal("player/runPlayer.atlas"));
        idle = new TextureAtlas(Gdx.files.internal("player/idlePlayer.atlas"));
        jump = new TextureAtlas(Gdx.files.internal("player/jumpPlayer.atlas"));
        runGun = new TextureAtlas(Gdx.files.internal("gun/runGun.atlas"));
        idleGun = new TextureAtlas(Gdx.files.internal("gun/idleGun.atlas"));
        jumpGun = new TextureAtlas(Gdx.files.internal("gun/jumpGun.atlas"));
        animationGun = new Animation(1f/12f,idleGun.getRegions());
        animation = new Animation(1f/12f,idle.getRegions());
        rotation = 0;
        this.world = world;
        bullets = new Bullets(world, this);

        square = new BodyDef();
        circle = new CircleShape();
        shape = new PolygonShape();
        fixtureDef = new FixtureDef();
        orientation = false;


        //Set sprite position and scale
        square.type = BodyDef.BodyType.KinematicBody;
        square.position.set(x,y/2);
        square.fixedRotation = true;

        circle.setRadius(w/4);
        circle.setPosition(new Vector2(0,-h/4));


        shape.setAsBox(w/4,h/4);

        fixtureDef.shape = shape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f;
        this.body = world.createBody(square);
        this.body.createFixture(fixtureDef);
        fixtureDef.shape = circle;
        this.body.createFixture(fixtureDef);


    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        update();
        bullets.draw(batch,parentAlpha);
        batch.draw((TextureRegion) animationGun.getKeyFrame(elapsedTime, true),orientation ? (body.getPosition().x - this.w / 2.0f) + this.w : (body.getPosition().x - this.w / 2.0f),body.getPosition().y - this.h / 2.0f,orientation ? -this.w/2 : this.w/2,this.h/2,orientation ? -this.w : this.w,this.h,getScaleX(),getScaleY(),this.getRotation());
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime, true), orientation ? (body.getPosition().x - this.w / 2.0f) + this.w : (body.getPosition().x - this.w / 2.0f), body.getPosition().y - this.h / 2.0f, orientation ? -this.w : this.w, this.h);
    }

    public void update() {
        if(movement == Movement.RUN){
            if(Math.abs(body.getLinearVelocity().x) < 3){
                animationGun = new Animation(  1f/3f,runGun.getRegions());
                animation = new Animation(  1f/3f,run.getRegions());
            }
            else if(Math.abs(body.getLinearVelocity().x) < 6){
                animationGun = new Animation(  1f/6f,runGun.getRegions());
                animation = new Animation(  1f/6f,run.getRegions());
            }
            else if(Math.abs(body.getLinearVelocity().x) < 9){
                animationGun = new Animation(  1f/9f,runGun.getRegions());
                animation = new Animation(  1f/9f,run.getRegions());
            }
            else{
                animationGun = new Animation(  1f/12f,runGun.getRegions());
                animation = new Animation(  1f/12f,run.getRegions());
            }
        }
        else if(movement == Movement.JUMP){
            animationGun = new Animation(1f/20f,jumpGun.getRegions());
            animation = new Animation(1f/20f,jump.getRegions());
        }
        else if(movement == Movement.IDLE){
            animationGun = new Animation(1f/12f,idleGun.getRegions());
            animation = new Animation(1f/12f,idle.getRegions());
        }

    }

    public void setPlayerPosition(PlayerPosition ps){
        this.rotation = ps.rotation;
        if(ps.movement == 0){
            this.movement = Movement.JUMP;
        }
        else if(ps.movement ==  1){
            this.movement = Movement.RUN;
        }
        else{
            this.movement = Movement.IDLE;
        }
        this.body.setTransform(ps.position,0);
        this.orientation = ps.orientation;
    }

    public float getRotation(){
        return this.rotation;
    }
    @Override
    public Vector2 getPosition() {
        return this.body.getPosition();
    }

    @Override
    public float getW() {
        return this.w;
    }

    @Override
    public boolean getOrientation() {
        return this.orientation;
    }

}
