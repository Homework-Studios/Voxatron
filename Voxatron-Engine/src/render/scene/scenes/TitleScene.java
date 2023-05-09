package render.scene.scenes;

import com.raylib.Jaylib;
import math.Vector2;
import render.scene.Element;
import render.scene.Scene;
import render.scene.scenes.titleElements.VTBannerElement;
import render.scene.scenes.uiElements.ButtonElement;
import render.scene.scenes.uiElements.ImageElement;
import util.UiUtil;

public class TitleScene extends Scene {

    public TitleScene(){
        super();

        addElement(new ImageElement(
                Jaylib.LoadImage("Voxatron/Assets/MainMenu/VTBanner/VTBanner.png"),
                new Vector2(
                    UiUtil.getWidthPercent(50),
                    UiUtil.getHeightPercent(30)
                ),
                new Vector2(
                    UiUtil.getWidthPercent(50),
                    UiUtil.getHeightPercent(50)
                )
        ));
        // new ButtonElement below the VTBannerElement
        addElement(new ButtonElement(
                new Vector2(UiUtil.getWidthPercent(50),
                UiUtil.getHeightPercent(60)),
                new Vector2(UiUtil.getWidthPercent(40),
                UiUtil.getHeightPercent(10)),
                "Play",
                50f,
                Jaylib.LIGHTGRAY,
                Jaylib.BLANK,
                Jaylib.RED,
                Jaylib.GREEN
        ));
        // new ButtonElement below the previous ButtonElement
        addElement(new ButtonElement(
                new Vector2(UiUtil.getWidthPercent(50),
                UiUtil.getHeightPercent(72)),
                new Vector2(UiUtil.getWidthPercent(40),
                UiUtil.getHeightPercent(10)),
                "Settings",
                50f,
                Jaylib.LIGHTGRAY,
                Jaylib.BLANK,
                Jaylib.RED,
                Jaylib.GREEN
        ));
        // new ButtonElement below the previous ButtonElement
        addElement(new ButtonElement(
                new Vector2(UiUtil.getWidthPercent(50),
                UiUtil.getHeightPercent(84)),
                new Vector2(UiUtil.getWidthPercent(40),
                UiUtil.getHeightPercent(10)),
                "Quit",
                50f,
                Jaylib.LIGHTGRAY,
                Jaylib.BLANK,
                Jaylib.RED,
                Jaylib.GREEN
        ));

        // add a mute button to the top right corner
        addElement(new ButtonElement(
                new Vector2(UiUtil.getWidthPercent(94),
                UiUtil.getHeightPercent(5)),
                new Vector2(UiUtil.getWidthPercent(10),
                UiUtil.getHeightPercent(7)),
                "Mute",
                40f,
                Jaylib.LIGHTGRAY,
                Jaylib.BLANK,
                Jaylib.RED,
                Jaylib.GREEN
        ));
        // and a credits button below the mute button
        addElement(new ButtonElement(
                new Vector2(UiUtil.getWidthPercent(94),
                UiUtil.getHeightPercent(14)),
                new Vector2(UiUtil.getWidthPercent(10),
                UiUtil.getHeightPercent(7)),
                "Credits",
                40f,
                Jaylib.LIGHTGRAY,
                Jaylib.BLANK,
                Jaylib.RED,
                Jaylib.GREEN
        ));
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
