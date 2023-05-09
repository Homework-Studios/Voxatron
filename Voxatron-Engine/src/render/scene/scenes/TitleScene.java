package render.scene.scenes;

import com.raylib.Jaylib;
import math.Vector2;
import render.scene.Element;
import render.scene.Scene;
import render.scene.scenes.uiElements.ButtonElement;
import render.scene.scenes.uiElements.ImageElement;
import render.scene.scenes.uiElements.ToggleElement;
import util.UiUtil;
import window.Window;

public class TitleScene extends Scene {

    public TitleScene(){
        super();

        addElement(new ElementBatch(new Element[]{
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
                        System.out.println("Settings Button Pressed");
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
            new ToggleElement(
                    new Vector2(UiUtil.getWidthPercent(94),
                            UiUtil.getHeightPercent(5)),
                    new Vector2(UiUtil.getWidthPercent(10),
                            UiUtil.getHeightPercent(7)),
                    "Mute",
                    40f,
                    Jaylib.LIGHTGRAY,
                    Jaylib.GRAY,
                    Jaylib.GREEN,
                    Jaylib.RED,
                    () -> {
                        System.out.println("Mute Button Pressed");
                    }
            ),
            new ButtonElement(
                    new Vector2(UiUtil.getWidthPercent(94),
                            UiUtil.getHeightPercent(14)),
                    new Vector2(UiUtil.getWidthPercent(10),
                            UiUtil.getHeightPercent(7)),
                    "Credits",
                    40f,
                    Jaylib.LIGHTGRAY,
                    Jaylib.BLANK,
                    Jaylib.RED,
                    Jaylib.GREEN,
                    () -> {
                        System.out.println("Credits Button Pressed");
                    }
            )
        }, true));
    }

    @Override
    public void update() {
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
