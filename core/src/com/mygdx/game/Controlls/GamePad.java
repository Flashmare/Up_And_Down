package com.mygdx.game.Controlls;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Batch;
        import com.badlogic.gdx.graphics.g2d.Sprite;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.scenes.scene2d.Actor;
        import com.badlogic.gdx.utils.viewport.Viewport;

public class GamePad extends Actor {

    private Sprite sprite, spriteStill;
    private Vector2 position;
    private Vector2 startPosition;
    private Viewport gamePort;
    private boolean firstTouch;
    private boolean touchOne, touchTwo;
    private static float MAX_SPEED = 80f;

    public GamePad(Viewport gamePort){
        sprite = new Sprite(new Texture(Gdx.files.internal("controller.png")));
        spriteStill = new Sprite(new Texture(Gdx.files.internal("controller_still.png")));
        spriteStill.setScale(1/100f,1/100f);
        sprite.setScale(1/100f,1/100f);
        this.gamePort = gamePort;
        position = new Vector2();
        startPosition = new Vector2();
        firstTouch = true;

    }

    public void draw (Batch batch, float parentAlpha) {
        update();
        if(Gdx.input.isTouched()){
            sprite.draw(batch);
            spriteStill.draw(batch);
        }

    }

    public void update() {
        touchOne = (Gdx.input.isTouched(0) && Gdx.input.getX(0) < Gdx.graphics.getWidth()*0.6f);
        touchTwo = (Gdx.input.isTouched(1) && Gdx.input.getX(1) < Gdx.graphics.getWidth()*0.6f);
        if(touchOne){
            position = new Vector2(Gdx.input.getX(0),Gdx.input.getY(0));
            gamePort.unproject(position);
        }
        else if(touchTwo){
            position = new Vector2(Gdx.input.getX(1),Gdx.input.getY(1));
            gamePort.unproject(position);
        }

        if(touchOne && firstTouch){
            startPosition = new Vector2(Gdx.input.getX(0),Gdx.input.getY(0));
            gamePort.unproject(startPosition);
            firstTouch = false;
            spriteStill.setPosition(position.x - sprite.getWidth() / 2.0f, position.y - sprite.getHeight() / 2.0f);
        }
        else if(touchTwo && firstTouch){
            startPosition = new Vector2(Gdx.input.getX(1),Gdx.input.getY(1));
            gamePort.unproject(startPosition);
            firstTouch = false;
            spriteStill.setPosition(position.x - sprite.getWidth() / 2.0f, position.y - sprite.getHeight() / 2.0f);
        }

        if( touchOne || touchTwo) {
            sprite.setPosition(position.x - sprite.getWidth() / 2.0f, position.y - sprite.getHeight() / 2.0f);
        }
        else{
            sprite.setPosition(-10,-10);
            spriteStill.setPosition(-10,-10);
            startPosition = position;
            firstTouch = true;
        }

    }

    public Vector2 getPosition(){
        update();
        return new Vector2((position.x - startPosition.x)/gamePort.getWorldWidth()*MAX_SPEED,0);
    }





}