package com.mygdx.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.UpAndDown;

public class AndroidLauncher extends AndroidApplication {


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config = new AndroidApplicationConfiguration();
		config.useGyroscope = true;  //default is false

		//you may want to switch off sensors that are on by default if they are no longer needed.
		config.useAccelerometer = false;
		config.useCompass = false;
		initialize(new UpAndDown(), config);

	}
}
