package render.scene.types;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import input.Input;
import input.map.Mapping;
import math.LerpUtil;
import org.bytedeco.javacpp.Pointer;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.SceneType;
import render.scene.ui.MainMenuUIScreen;
import render.ui.UIScreen;

import java.awt.*;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Jaylib.*;

public class MainMenuScene extends Scene {

    public UIScreen uiScreen;
    public Texture bgTexture;

    public Shader blurShader;

    public MainMenuScene() {
        super(SceneType.MAIN_MENU);
        uiScreen = new MainMenuUIScreen();

        String shaderPath = System.getProperty("user.dir") + "\\Voxatron-Engine\\src\\shader\\";

        blurShader = LoadShader(shaderPath + "base.vs", shaderPath + "gaussianBlur.fs");
        SetShaderValue(blurShader, GetShaderLocation(blurShader, "width"), Pointer.malloc(GetScreenWidth()), SHADER_ATTRIB_FLOAT);
        SetShaderValue(blurShader, GetShaderLocation(blurShader, "height"), Pointer.malloc(GetScreenHeight()), SHADER_ATTRIB_FLOAT);

        Raylib.Image background = GenImageGradientV(GetScreenWidth() * 2, GetScreenWidth() * 2, BLACK, Jaylib.ColorFromHSV(0, 0, 0.30f));
        bgTexture = LoadTextureFromImage(background);
    }

    @Override
    public void update() {
        uiScreen.update();
    }

    public float rotation = 0;

    @Override
    public void render() {
        // rotate the texture and use DrawTexturePro to draw it in the screen center

        BeginShaderMode(blurShader);
        DrawTexturePro(bgTexture, new Jaylib.Rectangle(0, 0, bgTexture.width(), bgTexture.height()), new Jaylib.Rectangle(GetScreenWidth() / 2, GetScreenHeight() / 2, GetScreenWidth() * 2, GetScreenWidth() * 2), new Jaylib.Vector2(bgTexture.width() / 2, bgTexture.height() / 2), LerpUtil.slerp(0, 360, rotation), WHITE);
        EndShaderMode();

        uiScreen.render();

        if(Input.instance.isKeyPressed(Mapping.TOGGLE_SCENE)){
            //SceneManager.instance.setCurrentScene(SceneType.LEVEL);
        }
        rotation += 0.0001f;
        rotation %= 360;
    }
}
