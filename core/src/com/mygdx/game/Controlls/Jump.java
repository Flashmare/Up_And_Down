package com.mygdx.game.Controlls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;



public class Jump extends Actor {

    private World world;
    private Viewport gamePort;
    private Vector2 position;
    private Table table;
    boolean jumpPressed, shootPressed;

    public Jump(World world, Viewport gamePort, Stage stage){
        this.world = world;
        this.gamePort = gamePort;
        Gdx.input.setInputProcessor(stage);
        table= new Table();
        table.setPosition( gamePort.getWorldWidth()*0.85f,gamePort.getWorldHeight()*0.2f);
        Image jump = new Image(new Texture("Jump_up.png"));
        Image shoot = new Image(new Texture("Shoot_up.png"));
        jump.setSize(7,7);
        jump.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                jumpPressed = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                jumpPressed = false;
            }

        });
        shoot.setSize(7,7);
        shoot.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                shootPressed = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                shootPressed = false;
            }

        });

        table.add();
        table.add(jump).size(jump.getWidth(),jump.getHeight());
        table.row().pad(1,1,1,1);
        table.add(shoot).size(shoot.getWidth(),shoot.getHeight());
        table.add();

    }
    public Actor getTable(){
        return table;
    }

    public boolean isJumpPressed() {
        return jumpPressed;
    }

    public boolean isShootPressed() {
        return shootPressed;
    }
}
