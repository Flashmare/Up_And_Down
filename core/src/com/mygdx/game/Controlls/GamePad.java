package com.mygdx.game.Controlls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class GamePad{
    private Vector3 Coordinates; //Z = IS TOUCHED IF IS MORE THEN 1 NUMBER IS TIME TOUCHED
    private int radius;

    GamePad(int radius){
        this.Coordinates = new Vector3();
        this.radius = radius;
    }

    public void handleInput(){
        Coordinates.x = Gdx.input.getX();
        Coordinates.y = Gdx.input.getY();
    }




}
