package com.mygdx.game.Controlls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GyroControlls extends Actor {

    private float rotation;


    public GyroControlls(){
        rotation = 0;
    }

    public void update() {
        if(rotation < 30 && rotation > -30) {
            rotation += Gdx.input.getGyroscopeZ();
        }
        else if(rotation < 30){
            rotation += 0.1;
        }
        else{
            rotation += -0.1;
        }
    }

    public float getPosition(boolean orientation){
        update();
        return orientation ? rotation: -rotation;
    }
}
