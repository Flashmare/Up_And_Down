package com.mygdx.game.Enteties;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;


public class Ground extends Actor {

    private World world;
    private Batch batch;
    private Viewport gamePort;
    private Texture texture;
    private Texture background;
    private TextureRegion imgTextureRegion;
    private Platform[] platforms;
    private Json json;
    private ArrayList<JsonValue> list;

    public Ground(World world, Batch batch, Viewport gamePort){
        texture = new Texture((Gdx.files.internal("platform.png")));
        background = new Texture((Gdx.files.internal("background.png")));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        imgTextureRegion = new TextureRegion(background);
        imgTextureRegion.setRegion(0,0,background.getWidth()*50,background.getHeight()*50);

        json = new Json();
        list = json.fromJson(ArrayList.class,Gdx.files.internal("Platforms.json"));
        platforms = new Platform[list.size()];
        int i = 0;
        for (JsonValue v : list) {
            platforms[i] = new Platform(world,json.readValue("hx",Float.class,v),json.readValue("hy",Float.class,v),json.readValue("x",Float.class,v),json.readValue("y",Float.class,v));
            i+=1;
        }
        this.batch = batch;
        this.world = world;
        this.gamePort = gamePort;


    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.draw(imgTextureRegion,0,0,background.getWidth(),background.getHeight());
        for(Platform platform: platforms) {
            if (platform != null) {
                batch.draw(texture, platform.getX() - platform.getHx(), platform.getY() - platform.getHy(), platform.getHx() * 2, platform.getHy() * 2);
            }
        }

    }
}

