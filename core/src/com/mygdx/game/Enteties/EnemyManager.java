package com.mygdx.game.Enteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.Random;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class EnemyManager extends Actor {

    private Vector2[] positions;
    private Ball enemy;
    private World world;
    private Random rand;
    public int Score;
    private Json json;
    private ArrayList<JsonValue> list;
    public EnemyManager(World world){
        this.world=world;
        Score = -1;
        json = new Json();
        list = json.fromJson(ArrayList.class, Gdx.files.internal("Platforms.json"));
        positions = new Vector2[list.size()];
        int i = 0;
        for (JsonValue v : list) {
            positions[i] = new Vector2(json.readValue("x",Float.class,v),json.readValue("y",Float.class,v)+5);
            i+=1;
        }
        rand = new Random();
        newBall();
    }
    private void newBall(){
        if(enemy != null){
            enemy.dispose();
        }
        Score+=1;
        Vector2 vec = positions[rand.nextInt(list.size())];
        enemy = new Ball(vec.x,vec.y, 1.5f, world);
    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
        update();
        enemy.draw(batch);
    }
    public void update() {
        if(enemy.getPosition().y < -10){
            newBall();
        }
    }

}
