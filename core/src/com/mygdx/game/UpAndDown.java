package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class UpAndDown extends Game {
	public SpriteBatch batch;


	@Override
	public void create () {
		batch = new SpriteBatch();
		ScreenManager.initialize(this);
		ScreenManager.setMainScreen();
	}

	@Override
	public void render () {
		super.render();

	}
	@Override
	public void dispose () {
		batch.dispose();
	}

}
