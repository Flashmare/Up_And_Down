package com.mygdx.game.Enteties;

import com.badlogic.gdx.math.Vector2;

public interface Character {
    Vector2 getPosition();
    float getW();
    float getRotation();
    boolean getOrientation();
}
