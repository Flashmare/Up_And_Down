package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.game.Scenes.MainScreen;
import com.mygdx.game.Scenes.MultiplayerClientScreen;
import com.mygdx.game.Scenes.MultiplayerScreen;
import com.mygdx.game.Scenes.MultiplayerServerScreen;
import com.mygdx.game.Scenes.OptionsScreen;
import com.mygdx.game.Scenes.PlayScreen;

public class ScreenManager {

    static Game game;
    static Screen optionsScreen;
    static Screen playScreen;
    static Screen mainScreen;
    static Screen serverScreen;
    static Screen multiplayerScreen;
    static MultiplayerClientScreen clientScreen;


    private ScreenManager() {}

    //This is called by Game from inside the "create()" method.
    public static void initialize(UpAndDown game) {
        ScreenManager.game = game;
        optionsScreen = new OptionsScreen(game);
        playScreen = new PlayScreen(game);
        serverScreen = new MultiplayerServerScreen(game);
        clientScreen = new MultiplayerClientScreen(game);
        mainScreen = new MainScreen(game);
        multiplayerScreen  = new MultiplayerScreen(game);
    }
    public static void setMainScreen() {
        game.setScreen(mainScreen);

    }

    public static void setPlayScreen() {
        game.setScreen(playScreen);
    }

    public static void setMPScreen() {
        game.setScreen(multiplayerScreen);
    }

    public static void setServerScreen() {
        game.setScreen(serverScreen);
    }

    public static void setClientScreen(String IP) {
        clientScreen.setIp(IP);
        game.setScreen(clientScreen);
    }

    public static void setOptionsScreen() {
        game.setScreen(optionsScreen);
    }



}