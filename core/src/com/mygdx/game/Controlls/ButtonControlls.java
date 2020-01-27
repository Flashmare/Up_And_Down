package com.mygdx.game.Controlls;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ScreenManager;


public class ButtonControlls extends Actor {

    private Viewport gamePort;
    private final float buttonSize = 0.08f;
    private Table table;
    private boolean jumpPressed, shootPressed;
    private Image jump, shoot,pause;

    public ButtonControlls(Viewport gamePort){
        this.gamePort = gamePort;
        table= new Table();
        table.setPosition( gamePort.getWorldWidth()*0.85f,gamePort.getWorldHeight()*0.2f);

        jump = new Image(new Texture("Jump_up.png"));
        shoot = new Image(new Texture("Shoot_up.png"));

        jump.setSize(gamePort.getWorldWidth()*buttonSize,gamePort.getWorldWidth()*buttonSize);
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
        shoot.setSize(gamePort.getWorldWidth()*buttonSize,gamePort.getWorldWidth()*buttonSize);
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


    public void dispose() {
        
    }
}
