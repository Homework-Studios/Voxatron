package render.scene.types;

import input.Input;
import input.map.Mapping;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.SceneType;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

public class CreditsScene extends Scene {

    public CreditsScene() {
        super(SceneType.CREDITS);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

        if(Input.instance.isKeyPressed(Mapping.TOGGLE_SCENE)){
            SceneManager.instance.setCurrentScene(SceneType.MAIN_MENU);
        }

        DrawText("Credits!", 10, 10, 20, WHITE);
    }
}
