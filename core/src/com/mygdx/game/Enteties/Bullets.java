package com.mygdx.game.Enteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Controlls.GameSound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Bullets extends Actor {
    private World world;
    private List<Bullet> bullet;
    private Character player;
    private GameSound sound;
    private float volume;
    private Preferences prefs;
    private float shootTimeout;

    public Bullets(World world, Character player){
        this.player = player;
        this.world = world;
        this.sound = new GameSound();
        this.bullet = new ArrayList<Bullet>();
        this.prefs = Gdx.app.getPreferences("Options");
        this.volume = prefs.getFloat("Volume",1f);
        this.shootTimeout = 0;
    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
        this.update();
        if(bullet != null){
            for (Bullet b : bullet){
                b.draw(batch);
            }

        }
    }
    private void update(){
        shootTimeout+=Gdx.graphics.getDeltaTime();
        for (Iterator<Bullet> iterator = bullet.iterator(); iterator.hasNext();) {
            if(bullet != null){
                Bullet b = iterator.next();
                b.update();
                if(b.remove){
                    iterator.remove();
                    b.dispose();

                }
            }
        }
    }
    public void shoot(){
        if(shootTimeout >= 0.125) {
            bullet.add(new Bullet(player.getPosition().x+player.getW()/2 , player.getPosition().y , player.getRotation(), world, player.getOrientation()));
            sound.Shoot(true, volume);
            shootTimeout =0;
        }
    }
}
