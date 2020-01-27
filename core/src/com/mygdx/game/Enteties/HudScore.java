package com.mygdx.game.Enteties;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;



public class HudScore extends Actor {
    private BitmapFont font;
    private EnemyManager enemyManager;
    private Viewport gamePort;
    public HudScore(Viewport gamePort,EnemyManager enemyManager){
        this.gamePort = gamePort;
        this.font = new BitmapFont();
        this.enemyManager = enemyManager;
        this.font.getData().setScale(0.3f,0.4f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, Integer.toString(enemyManager.Score), gamePort.getWorldWidth()*0.8f, gamePort.getWorldHeight()*0.9f);
    }
}
