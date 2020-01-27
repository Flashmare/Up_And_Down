package com.mygdx.game.Enteties;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class PlayerPosition implements Serializable {
    public Vector2 position;
    public boolean orientation;
    public float rotation;
    public int movement;
    public PlayerPosition(Vector2 position ,boolean orientation,float rotation,int movement){
        this.movement = movement;
        this.rotation = rotation;
        this.position = position;
        this.orientation = orientation;
    }
    public PlayerPosition(byte[] bytes){
        float r = Float.intBitsToFloat(((bytes[5] & 0xFF)| ((bytes[4] & 0xFF) << 8)| ((bytes[3] & 0xFF) << 16)| ((bytes[2] & 0xFF) << 24)));
        float py = Float.intBitsToFloat(((bytes[9] & 0xFF)| ((bytes[8] & 0xFF) << 8)| ((bytes[7] & 0xFF) << 16)| ((bytes[6] & 0xFF) << 24)));
        float px = Float.intBitsToFloat(((bytes[13] & 0xFF)| ((bytes[12] & 0xFF) << 8)| ((bytes[11] & 0xFF) << 16)| ((bytes[10] & 0xFF) << 24)));
        if((bytes[0] & 0xFF) == 0){
            this.orientation = false;
        }
        else{
            this.orientation = true;
        }
        this.rotation = r;
        this.movement = (bytes[1] & 0xFF);
        this.position = new Vector2(px,py);
        System.out.println(r+"Rotation");
    }
    public byte[] toBytes(){
        int px = Float.floatToIntBits(this.position.x);
        int py = Float.floatToIntBits(this.position.y);
        int r = Float.floatToIntBits(this.rotation);
        return new byte[] {(byte) (this.orientation ? 1 : 0),(byte) movement,(byte) (r >> 24),(byte) (r>> 16),(byte) (r >> 8),(byte) (r),(byte) (py >> 24),(byte) (py >> 16),(byte) (py >> 8),(byte) (py),(byte) (px >> 24), (byte) (px >> 16), (byte) (px >> 8), (byte) (px)};
}



}
