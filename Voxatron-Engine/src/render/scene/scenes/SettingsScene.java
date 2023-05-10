package render.scene.scenes;

import com.raylib.Jaylib;
import math.Vector2;
import render.scene.Element;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.scenes.uiElements.ButtonElement;
import render.scene.scenes.uiElements.SliderElement;
import render.scene.scenes.uiElements.TextElement;
import render.scene.scenes.uiElements.ToggleElement;
import window.SettingsManager;
import window.Window;

import java.io.IOException;

public class SettingsScene extends Scene {

    public ElementBatch settingsBodyBatch;
    public ElementBatch settingsApplyBatch;

    public boolean needToApply = false;

    public SettingsScene() {
        super();
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

    @Override
    public void init() {
        boolean aa = SettingsManager.instance.getSetting("aa").equals("1");

        settingsBodyBatch = new ElementBatch(new Element[]{
                new TextElement(
                        Vector2.byScreenPercent(0, 0),
                        Vector2.byScreenPercent(100, 15),
                        "Settings",
                        50f,
                        Jaylib.LIGHTGRAY
                ),
                new ButtonElement(
                        Vector2.byScreenPercent(94, 5),
                        Vector2.byScreenPercent(10, 7),
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
                        Vector2.byScreenPercent(25, 30),
                        Vector2.byScreenPercent(40, 10),
                        "Fullscreen",
                        50f,
                        Jaylib.IsWindowFullscreen(),
                        Jaylib.LIGHTGRAY,
                        Jaylib.GRAY,
                        Jaylib.GREEN,
                        Jaylib.RED,
                        () -> {
                            System.out.println("Fullscreen Toggle Pressed");
                            Jaylib.ToggleFullscreen();
                        }
                ),
                new ToggleElement(
                        Vector2.byScreenPercent(75, 30),
                        Vector2.byScreenPercent(40, 10),
                        "Particles",
                        50f,
                        false,
                        Jaylib.LIGHTGRAY,
                        Jaylib.GRAY,
                        Jaylib.GREEN,
                        Jaylib.RED,
                        () -> {
                            System.out.println("Particles Toggle Pressed");
                            needToApply = true;
                        }
                ),
                new ToggleElement(
                        Vector2.byScreenPercent(25, 45),
                        Vector2.byScreenPercent(40, 10),
                        "Anti-Aliasing",
                        50f,
                        aa,
                        Jaylib.LIGHTGRAY,
                        Jaylib.GRAY,
                        Jaylib.GREEN,
                        Jaylib.RED,
                        () -> {
                            System.out.println("Anti-Aliasing Toggle Pressed");
                            if (!aa) {
                                SettingsManager.instance.setSetting("aa", "1");
                                try {
                                    SettingsManager.instance.saveSettings();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                Window.instance.reopenWindow();
                            } else {
                                SettingsManager.instance.setSetting("aa", "0");
                                try {
                                    SettingsManager.instance.saveSettings();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                Window.instance.reopenWindow();
                            }
                        }
                ),
                new ToggleElement(
                        Vector2.byScreenPercent(75, 45),
                        Vector2.byScreenPercent(40, 10),
                        "Screen Shake",
                        50f,
                        false,
                        Jaylib.LIGHTGRAY,
                        Jaylib.GRAY,
                        Jaylib.GREEN,
                        Jaylib.RED,
                        () -> {
                            System.out.println("Screen Shake Toggle Pressed");
                            needToApply = true;
                        }
                ),
// Text Elements define the area of where the text should be centered in
                new TextElement(
                        Vector2.byScreenPercent(5, 55),
                        Vector2.byScreenPercent(10, 10),
                        "Volume",
                        50f,
                        Jaylib.LIGHTGRAY
                ),
                new SliderElement(
                        Vector2.byScreenPercent(30, 60),
                        Vector2.byScreenPercent(27, 10),
                        Vector2.byScreenPercent(2, 12),
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
                        Vector2.byScreenPercent(50, 90),
                        Vector2.byScreenPercent(40, 10),
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
}
