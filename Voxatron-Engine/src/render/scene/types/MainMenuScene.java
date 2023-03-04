package render.scene.types;

import input.Input;
import input.map.Mapping;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.SceneType;
import render.scene.ui.MainMenuUIScreen;
import render.ui.UIScreen;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

public class MainMenuScene extends Scene {

    public UIScreen uiScreen;

    public MainMenuScene() {
        super(SceneType.MAIN_MENU);
        uiScreen = new MainMenuUIScreen();
    }

    @Override
    public void update() {
        uiScreen.update();
    }

    @Override
    public void render() {
        uiScreen.render();

        if(Input.instance.isKeyPressed(Mapping.TOGGLE_SCENE)){
            //SceneManager.instance.setCurrentScene(SceneType.LEVEL);
        }
    }
}
