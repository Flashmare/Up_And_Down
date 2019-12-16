package com.mygdx.game.Controlls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Enteties.Player;

public class GyroControlls extends Actor {

    private Sprite sprite;
    private World world;
    private float position;
    private Player player;
    private Viewport gamePort;

    public GyroControlls(World world,  Player player){
        sprite = new Sprite(new Texture(Gdx.files.internal("badlogic.png")));
        this.world = world;
        this.gamePort = gamePort;
        this.player = player;
        position = 0;

    }

    public void draw (Batch batch, float parentAlpha) {
        update();
        sprite.draw(batch);

    }

    public void update() {
        position+=Gdx.input.getGyroscopeZ();
        sprite.setPosition(player.getX(),player.getY());
        sprite.setRotation(position);

    }

    public float getPosition(){
        update();
        return position;
    }
}
