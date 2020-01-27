package com.mygdx.game.Controlls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSound {
    private Sound jump;
    private Sound shoot;
    private Sound walk;
    private float walkingSoundTime;
    private float shootTimeout;

    public GameSound(){
        walk = Gdx.audio.newSound(Gdx.files.internal("walking.wav"));
        jump = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        shoot = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        walkingSoundTime=0;
        shootTimeout = 0;
    }
    public void update(){
        walkingSoundTime-=Gdx.graphics.getDeltaTime();
        shootTimeout-=Gdx.graphics.getDeltaTime();
    }
    public void Walk(boolean play,float time,float volume,float pan){
        if(play){
            if(walkingSoundTime <= 0){
                walk.play(volume,1f,pan);
                walkingSoundTime = time;
            }
        }
        else{
            walk.stop();
        }

    }
    public void Jump(boolean play,float volume,float pan){
        if(play) {
                jump.play(volume,1f,pan);
        }
        else{
            jump.stop();
        }
    }
    public void Shoot(boolean play,float volume){
        if(play) {
            shoot.play(volume,1f,0);
        }
        else{
            shoot.stop();
        }
    }


    public void dispose() {
        jump.dispose();
        shoot.dispose();
        walk.dispose();
    }

}
