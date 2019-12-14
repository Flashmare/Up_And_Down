package com.mygdx.game.Controlls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GamePad extends Actor {

    private Sprite sprite;
    private World world;
    private Vector2 position;
    private Viewport gamePort;



    public void draw (Batch batch, float parentAlpha) {
        update();
        if(Gdx.input.isTouched() && position.x < gamePort.getWorldWidth()/3){
            sprite.draw(batch);
        }

    }

    public void update() {
        position = new Vector2(Gdx.input.getX(),Gdx.input.getY());

        gamePort.unproject(position);
        if(Gdx.input.isTouched()) {
            sprite.setPosition(position.x - sprite.getWidth() / 2.0f, position.y - sprite.getHeight() / 2.0f);
        }
        else{
            sprite.setPosition(-10,-10);
        }
    }
    public GamePad(World world, Viewport gamePort){
        sprite = new Sprite(new Texture(Gdx.files.internal("badlogic.png")));
        sprite.setScale(1/100f,1/100f);
        this.world = world;
        this.gamePort = gamePort;

    }
    public Vector2 getPosition(){
        update();
        if(Gdx.input.isTouched() && position.x < gamePort.getWorldWidth()/2){
            return new Vector2 (((position.x-(gamePort.getWorldWidth()/4))/2), 0);
        }
        else{
            return new Vector2 (0,0);
        }
    }




}
