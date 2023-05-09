package render.scene.scenes;

import com.raylib.Jaylib;
import math.Vector2;
import render.scene.Element;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.scenes.uiElements.ButtonElement;
import render.scene.scenes.uiElements.TextElement;
import render.scene.scenes.uiElements.ToggleElement;
import util.UiUtil;

public class SettingsScene extends Scene {

    public ElementBatch settingsBodyBatch;
    public ElementBatch settingsApplyBatch;

    public boolean needToApply = false;

    public SettingsScene() {
        super();

        settingsBodyBatch = new ElementBatch(new Element[]{
                new TextElement(
                        new Vector2(UiUtil.getWidthPercent(0),
                                UiUtil.getHeightPercent(0)),
                        new Vector2(UiUtil.getWidthPercent(100),
                                UiUtil.getHeightPercent(15)),
                        "Settings",
                        50f,
                        Jaylib.LIGHTGRAY
                ),
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
                            SceneManager.instance.setActiveScene(TitleScene.class);
                        }
                ),
                new ToggleElement(
                        new Vector2(UiUtil.getWidthPercent(25),
                                UiUtil.getHeightPercent(30)),
                        new Vector2(UiUtil.getWidthPercent(40),
                                UiUtil.getHeightPercent(10)),
                        "Fullscreen",
                        50f,
                        Jaylib.LIGHTGRAY,
                        Jaylib.GRAY,
                        Jaylib.GREEN,
                        Jaylib.RED,
                        () -> {
                            System.out.println("Fullscreen Toggle Pressed");
                            needToApply = true;
                        }
                ),
                new ToggleElement(
                        new Vector2(UiUtil.getWidthPercent(75),
                                UiUtil.getHeightPercent(30)),
                        new Vector2(UiUtil.getWidthPercent(40),
                                UiUtil.getHeightPercent(10)),
                        "Fullscreen",
                        50f,
                        Jaylib.LIGHTGRAY,
                        Jaylib.GRAY,
                        Jaylib.GREEN,
                        Jaylib.RED,
                        () -> {
                            System.out.println("Fullscreen Toggle Pressed");
                            needToApply = true;
                        }
                ),
                new ToggleElement(
                        new Vector2(UiUtil.getWidthPercent(25),
                                UiUtil.getHeightPercent(45)),
                        new Vector2(UiUtil.getWidthPercent(40),
                                UiUtil.getHeightPercent(10)),
                        "Fullscreen",
                        50f,
                        Jaylib.LIGHTGRAY,
                        Jaylib.GRAY,
                        Jaylib.GREEN,
                        Jaylib.RED,
                        () -> {
                            System.out.println("Fullscreen Toggle Pressed");
                            needToApply = true;
                        }
                ),
                new ToggleElement(
                        new Vector2(UiUtil.getWidthPercent(75),
                                UiUtil.getHeightPercent(45)),
                        new Vector2(UiUtil.getWidthPercent(40),
                                UiUtil.getHeightPercent(10)),
                        "Fullscreen",
                        50f,
                        Jaylib.LIGHTGRAY,
                        Jaylib.GRAY,
                        Jaylib.GREEN,
                        Jaylib.RED,
                        () -> {
                            System.out.println("Fullscreen Toggle Pressed");
                            needToApply = true;
                        }
                ),
        }, true);

        settingsApplyBatch = new ElementBatch(new Element[]{
                // Apply Button
                new ButtonElement(
                        new Vector2(UiUtil.getWidthPercent(50),
                                UiUtil.getHeightPercent(90)),
                        new Vector2(UiUtil.getWidthPercent(40),
                                UiUtil.getHeightPercent(10)),
                        "Apply",
                        50f,
                        Jaylib.LIGHTGRAY,
                        Jaylib.BLANK,
                        Jaylib.RED,
                        Jaylib.GREEN,
                        () -> {
                            System.out.println("Apply Button Pressed");
                            needToApply = false;
                        }
                ),
        }, false);

        addElement(settingsBodyBatch);
        addElement(settingsApplyBatch);
    }

    @Override
    public void update() {
        // Update the settings apply batch if the settings have been changed
      settingsApplyBatch.setVisibility(needToApply);

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
