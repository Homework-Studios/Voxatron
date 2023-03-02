package render.scene.types;

import input.Input;
import input.map.Mapping;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.SceneType;

public class CreditsScene extends Scene {

    public CreditsScene() {
        super(SceneType.CREDITS);
    }

    @Override
    public void render() {

        if(Input.instance.isKeyPressed(Mapping.TOGGLE_SCENE)){
            SceneManager.instance.setCurrentScene(SceneType.MAIN_MENU);
        }

        //raylib.text.DrawText("Credits!", 10, 10, 20, Color.WHITE);
    }
}
