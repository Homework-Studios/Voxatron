package render.scene.scenes;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import math.Vector2;
import render.scene.Element;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.scenes.uiElements.ButtonElement;
import render.scene.scenes.uiElements.SliderElement;
import render.scene.scenes.uiElements.TextElement;
import render.scene.scenes.uiElements.ToggleElement;
import util.UiUtil;
import window.SettingsManager;
import window.Window;

import java.io.IOException;

public class SettingsScene extends Scene {

    public ElementBatch settingsBodyBatch;
    public ElementBatch settingsApplyBatch;

    public boolean needToApply = false;

    public SettingsScene() {
        super();

        boolean aa = SettingsManager.instance.getSetting("aa").equals("1");

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
                        false,
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
                        "Particles",
                        50f,
                        false,
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
                        "Anti-Aliasing",
                        50f,
                        aa,
                        Jaylib.LIGHTGRAY,
                        Jaylib.GRAY,
                        Jaylib.GREEN,
                        Jaylib.RED,
                        () -> {
                            System.out.println("Fullscreen Toggle Pressed");
                            needToApply = true;
                            if(!aa){
                                SettingsManager.instance.setSetting("aa", "1");
                                Window.instance.reopenWindow();
                            }else{
                                SettingsManager.instance.setSetting("aa", "0");
                                Window.instance.reopenWindow();
                            }
                        }
                ),
                new ToggleElement(
                        new Vector2(UiUtil.getWidthPercent(75),
                                UiUtil.getHeightPercent(45)),
                        new Vector2(UiUtil.getWidthPercent(40),
                                UiUtil.getHeightPercent(10)),
                        "Screen Shake",
                        50f,
                        false,
                        Jaylib.LIGHTGRAY,
                        Jaylib.GRAY,
                        Jaylib.GREEN,
                        Jaylib.RED,
                        () -> {
                            System.out.println("Fullscreen Toggle Pressed");
                            needToApply = true;
                        }
                ),
                // Text Elements define the area of where the text should be centered in
                new TextElement(
                        new Vector2(UiUtil.getWidthPercent(5),
                                UiUtil.getHeightPercent(55)),
                        new Vector2(UiUtil.getWidthPercent(10),
                                UiUtil.getHeightPercent(10)),
                        "Volume",
                        50f,
                        Jaylib.LIGHTGRAY
                ),
                new SliderElement(
                        new Vector2(UiUtil.getWidthPercent(30),
                                UiUtil.getHeightPercent(60)),
                        new Vector2(UiUtil.getWidthPercent(27),
                                UiUtil.getHeightPercent(10)),
                        new Vector2(UiUtil.getWidthPercent(2),
                                UiUtil.getHeightPercent(12)),
                        0,
                        Jaylib.LIGHTGRAY,
                        Jaylib.GRAY,
                        Jaylib.GREEN,
                        Jaylib.RED,
                        () -> {
                            System.out.println("Volume Slider Changed");
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
                            try {
                                SettingsManager.instance.saveSettings();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
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
