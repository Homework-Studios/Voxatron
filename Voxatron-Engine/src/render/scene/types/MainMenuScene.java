package render.scene.types;

import input.Input;
import input.map.Mapping;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.SceneType;

public class MainMenuScene extends Scene {

    public MainMenuScene() {
        super(SceneType.MAIN_MENU);
    }

    @Override
    public void render() {

        if(Input.instance.isKeyPressed(Mapping.TOGGLE_SCENE)){
            SceneManager.instance.setCurrentScene(SceneType.CREDITS);
        }

       // raylib.text.DrawText("Main Menu!", 10, 10, 20, Color.WHITE);
    }
}
