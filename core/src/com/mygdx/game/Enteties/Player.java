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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Controlls.GameSound;
import com.mygdx.game.Controlls.GyroControlls;

public class Player extends Actor implements Character{
    private BodyDef square;
    private CircleShape circle;
    private PolygonShape shape;
    private FixtureDef fixtureDef;
    private World world;
    private Body body;
    private GameSound sound;
    private float volume;

    private TextureAtlas run, idle,jump,runGun,idleGun,jumpGun;
    private Animation animation,animationGun;
    private float elapsedTime = 0f;
    private boolean orientation;
    private Vector2 speed;
    private float w, h;
    private Movement movement;
    public int m_jumpTimeout;
    public Preferences prefs;
    private GyroControlls gyroControlls;
    private Bullets bullets;
    private Viewport gamePort;
    private float screenMiddle;

    public enum Movement {
        IDLE,
        RUN,
        JUMP ;
    }

    public Player(float x, float y, float w, float h, World world, Viewport gamePort){
        this.w = w;
        this.h = h;
        run = new TextureAtlas(Gdx.files.internal("player/runPlayer.atlas"));
        idle = new TextureAtlas(Gdx.files.internal("player/idlePlayer.atlas"));
        jump = new TextureAtlas(Gdx.files.internal("player/jumpPlayer.atlas"));
        runGun = new TextureAtlas(Gdx.files.internal("gun/runGun.atlas"));
        idleGun = new TextureAtlas(Gdx.files.internal("gun/idleGun.atlas"));
        jumpGun = new TextureAtlas(Gdx.files.internal("gun/jumpGun.atlas"));
        sound = new GameSound();
        animationGun = new Animation(1f/12f,idleGun.getRegions());
        animation = new Animation(1f/12f,idle.getRegions());
        square = new BodyDef();
        circle = new CircleShape();
        shape = new PolygonShape();
        fixtureDef = new FixtureDef();
        this.world = world;
        speed = new Vector2(0,0);
        orientation = false;
        prefs = Gdx.app.getPreferences("Options");
        volume = prefs.getFloat("Volume",1f);
        this.gamePort = gamePort;
        this.screenMiddle = gamePort.getWorldWidth()/2;


        gyroControlls = new GyroControlls();
        bullets = new Bullets(world,this);

        //Set sprite position and scale
        square.type = BodyDef.BodyType.DynamicBody;
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
        sound.update();
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
                sound.Walk(true,2f,volume,((body.getPosition().x-screenMiddle)/gamePort.getWorldWidth())*1.8f);
            }
            else if(Math.abs(body.getLinearVelocity().x) < 6){
                animationGun = new Animation(  1f/6f,runGun.getRegions());
                animation = new Animation(  1f/6f,run.getRegions());
                sound.Walk(true,0.85f,volume,((body.getPosition().x-screenMiddle)/gamePort.getWorldWidth())*1.8f);
            }
            else if(Math.abs(body.getLinearVelocity().x) < 9){
                animationGun = new Animation(  1f/9f,runGun.getRegions());
                animation = new Animation(  1f/9f,run.getRegions());
                sound.Walk(true,0.7f,volume,((body.getPosition().x-screenMiddle)/gamePort.getWorldWidth())*1.8f);
            }
            else{
                animationGun = new Animation(  1f/12f,runGun.getRegions());
                animation = new Animation(  1f/12f,run.getRegions());
                sound.Walk(true,0.5f,volume,((body.getPosition().x-screenMiddle)/gamePort.getWorldWidth())*1.8f);
            }
        }
        else if(movement == Movement.JUMP){
            animationGun = new Animation(1f/20f,jumpGun.getRegions());
            animation = new Animation(1f/20f,jump.getRegions());
            if(m_jumpTimeout < 0){
                movement = Movement.IDLE;
            }
        }
        else if(movement == Movement.IDLE){
            animationGun = new Animation(1f/12f,idleGun.getRegions());
            animation = new Animation(1f/12f,idle.getRegions());
        }
        m_jumpTimeout--;
    }


    public void applyForce(Vector2 touch){
        this.speed = touch;
        orientation = this.speed.x >= 0 ? false:true;
        this.body.setLinearVelocity(touch);
    }
    public void jump(){
        if(m_jumpTimeout < 0){
            sound.Jump(true,volume,((body.getPosition().x-screenMiddle)/gamePort.getWorldWidth())*1.8f);
            movement = Movement.JUMP;
            applyForce(new Vector2(0, 9f*h));
            m_jumpTimeout = Math.round(9*(h+1));
        }
    }
    public Vector2 getValocity(){return this.body.getLinearVelocity();}
    public boolean getOrientation(){return orientation;}

    public float getW(){return orientation ? -this.w : this.w;}
    public Movement getMovement(){return this.movement;}
    public Vector2 getPosition(){return body.getPosition();}
    public void setMovement(Movement mov){this.movement = mov;}


    public void shoot(){
        bullets.shoot();
    }
    public float getRotation(){
        return gyroControlls.getPosition(this.orientation);
    }
    public PlayerPosition getPlayerPosition(){
        if(movement == Movement.JUMP){
            return new PlayerPosition(this.getPosition(),this.getOrientation(),gyroControlls.getPosition(this.orientation),0);
        }
        else if(movement == Movement.RUN){
            return new PlayerPosition(this.getPosition(),this.getOrientation(),gyroControlls.getPosition(this.orientation),1);
        }
        else{
            return new PlayerPosition(this.getPosition(),this.getOrientation(),gyroControlls.getPosition(this.orientation),3);
        }
    }

}
