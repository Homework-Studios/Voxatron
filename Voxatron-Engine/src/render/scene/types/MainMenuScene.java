package render.scene.types;

import input.Input;
import input.map.Mapping;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.SceneType;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

public class MainMenuScene extends Scene {

    public MainMenuScene() {
        super(SceneType.MAIN_MENU);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

        if(Input.instance.isKeyPressed(Mapping.TOGGLE_SCENE)){
            SceneManager.instance.setCurrentScene(SceneType.CREDITS);
        }

       DrawText("Main Menu!", 10, 10, 20, WHITE);
    }
}
