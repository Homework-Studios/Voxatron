package render.scene.scenes;

import com.raylib.Jaylib;
import math.Vector2;
import render.scene.Element;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.scenes.uiElements.ButtonElement;
import render.scene.scenes.uiElements.ImageElement;
import render.scene.scenes.uiElements.TextElement;
import render.scene.scenes.uiElements.ToggleElement;
import util.UiUtil;
import window.Window;

public class TitleScene extends Scene {

    ElementBatch titleScreenBatch;
    ElementBatch creditsScreenBatch;
    public boolean creditsVisible = false;

    public TitleScene(){
        super();

        titleScreenBatch = new ElementBatch(new Element[]{
            new ImageElement(
                    Jaylib.LoadImage("Voxatron/Assets/MainMenu/VTBanner/VTBanner.png"),
                    new Vector2(
                            UiUtil.getWidthPercent(50),
                            UiUtil.getHeightPercent(30)
                    ),
                    new Vector2(
                            UiUtil.getWidthPercent(50),
                            UiUtil.getHeightPercent(50)
                    )
            ),
            new ButtonElement(
                    new Vector2(UiUtil.getWidthPercent(50),
                            UiUtil.getHeightPercent(60)),
                    new Vector2(UiUtil.getWidthPercent(40),
                            UiUtil.getHeightPercent(10)),
                    "Play",
                    50f,
                    Jaylib.LIGHTGRAY,
                    Jaylib.BLANK,
                    Jaylib.RED,
                    Jaylib.GREEN,
                    () -> {
                        System.out.println("Play Button Pressed");
                    }
            ),
            new ButtonElement(
                    new Vector2(UiUtil.getWidthPercent(50),
                            UiUtil.getHeightPercent(72)),
                    new Vector2(UiUtil.getWidthPercent(40),
                            UiUtil.getHeightPercent(10)),
                    "Settings",
                    50f,
                    Jaylib.LIGHTGRAY,
                    Jaylib.BLANK,
                    Jaylib.RED,
                    Jaylib.GREEN,
                    () -> {
                        SceneManager.instance.setActiveScene(SettingsScene.class);
                    }
            ),
            new ButtonElement(
                    new Vector2(UiUtil.getWidthPercent(50),
                            UiUtil.getHeightPercent(84)),
                    new Vector2(UiUtil.getWidthPercent(40),
                            UiUtil.getHeightPercent(10)),
                    "Quit",
                    50f,
                    Jaylib.LIGHTGRAY,
                    Jaylib.BLANK,
                    Jaylib.RED,
                    Jaylib.GREEN,
                    () -> {
                        Window.instance.stop();
                    }
            ),
            new ButtonElement(
                    new Vector2(UiUtil.getWidthPercent(94),
                            UiUtil.getHeightPercent(5)),
                    new Vector2(UiUtil.getWidthPercent(10),
                            UiUtil.getHeightPercent(7)),
                    "Credits",
                    40f,
                    Jaylib.LIGHTGRAY,
                    Jaylib.BLANK,
                    Jaylib.RED,
                    Jaylib.GREEN,
                    () -> {
                        creditsVisible = !creditsVisible;
                        System.out.println("Credits Button Pressed + " + creditsVisible);
                    }
            ),
            new ToggleElement(
                    new Vector2(UiUtil.getWidthPercent(94),
                            UiUtil.getHeightPercent(14)),
                    new Vector2(UiUtil.getWidthPercent(10),
                            UiUtil.getHeightPercent(7)),
                    "Mute",
                    40f,
                    false,
                    Jaylib.LIGHTGRAY,
                    Jaylib.GRAY,
                    Jaylib.GREEN,
                    Jaylib.RED,
                    () -> {
                        System.out.println("Mute Button Pressed");
                    }
            ),
        }, true);

        // add a credits screen batch but just add the close button for now in the top right corner
        creditsScreenBatch = new ElementBatch(new Element[]{
            new ButtonElement(
                    new Vector2(UiUtil.getWidthPercent(94),
                            UiUtil.getHeightPercent(5)),
                    new Vector2(UiUtil.getWidthPercent(10),
                            UiUtil.getHeightPercent(7)),
                    "Close",
                    40f,
                    Jaylib.LIGHTGRAY,
                    Jaylib.BLANK,
                    Jaylib.RED,
                    Jaylib.GREEN,
                    () -> {
                        creditsVisible = !creditsVisible;
                        System.out.println("Credits Button Pressed + " + creditsVisible);
                    }
            ),
            new TextElement(
                    new Vector2(UiUtil.getWidthPercent(0),
                            UiUtil.getHeightPercent(0)),
                    new Vector2(UiUtil.getWidthPercent(100),
                            UiUtil.getHeightPercent(100)),
                    "This game was developed by Jonas Fabian Windmann and Timon Richter for 10 grade computer sicence class.\n\nDeveloped with the VoxatronEngine in 2023.",
                    30f,
                    Jaylib.LIGHTGRAY
            ),
        }, false);

        addElement(titleScreenBatch);
        addElement(creditsScreenBatch);
    }

    @Override
    public void update() {

        titleScreenBatch.setVisibility(!creditsVisible);
        creditsScreenBatch.setVisibility(creditsVisible);

        for (Element element : getIterableElements()) {
            element.update();
        }
    }

    @Override
    public void render() {
        for (Element element : elements) {
            element.render();
        }
    }
}
