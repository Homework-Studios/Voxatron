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

    public SettingsManager sm;
    public ElementBatch settingsBodyBatch;
    public ElementBatch settingsApplyBatch;
    public ElementBatch settingsRestartBatch;

    public boolean needToApply = false;
    public boolean needToRestart = false;

    public SettingsScene() {
        super();
    }

    @Override
    public void update() {
        // Update the settings apply batch if the settings have been changed
        settingsApplyBatch.setVisibility(needToApply);
        settingsRestartBatch.setVisibility(needToRestart);

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
        sm = SettingsManager.instance;
        boolean aa = sm.getSetting("aa").equals("1");
        boolean fullscreen = sm.getSetting("fullscreen").equals("1");

        if (Jaylib.IsWindowFullscreen() != fullscreen)
            Jaylib.ToggleFullscreen();

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
                        Jaylib.WHITE,
                        Jaylib.GREEN
                ) {
                    @Override
                    public void run() {
                        SceneManager.instance.setActiveScene(TitleScene.class);
                    }
                },
                new ToggleElement(
                        Vector2.byScreenPercent(25, 30),
                        Vector2.byScreenPercent(40, 10),
                        "Fullscreen",
                        50f,
                        fullscreen,
                        Jaylib.LIGHTGRAY,
                        Jaylib.WHITE,
                        Jaylib.GREEN,
                        Jaylib.RED
                ) {
                    @Override
                    public void run() {
                        System.out.println("Fullscreen Toggle Pressed");
                        sm.setSetting("fullscreen", fullscreen ? "0" : "1");
                        needToApply = true;
                    }
                },
                new ToggleElement(
                        Vector2.byScreenPercent(75, 30),
                        Vector2.byScreenPercent(40, 10),
                        "Particles",
                        50f,
                        false,
                        Jaylib.LIGHTGRAY,
                        Jaylib.WHITE,
                        Jaylib.GREEN,
                        Jaylib.RED

                ) {
                    @Override
                    public void run() {
                        System.out.println("Particles Toggle Pressed");
                        needToApply = true;
                    }
                },
                new ToggleElement(
                        Vector2.byScreenPercent(25, 45),
                        Vector2.byScreenPercent(40, 10),
                        "Anti-Aliasing",
                        50f,
                        aa,
                        Jaylib.LIGHTGRAY,
                        Jaylib.WHITE,
                        Jaylib.GREEN,
                        Jaylib.RED
                ) {
                    @Override
                    public void run() {
                        System.out.println("Anti-Aliasing Toggle Pressed");
                        sm.setSetting("aa", aa ? "0" : "1");
                        needToRestart = true;
                    }
                },
                new ToggleElement(
                        Vector2.byScreenPercent(75, 45),
                        Vector2.byScreenPercent(40, 10),
                        "Screen Shake",
                        50f,
                        false,
                        Jaylib.LIGHTGRAY,
                        Jaylib.WHITE,
                        Jaylib.GREEN,
                        Jaylib.RED
                ) {
                    @Override
                    public void run() {
                        System.out.println("Screen Shake Toggle Pressed");
                        needToApply = true;
                    }
                },
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
                        Jaylib.WHITE,
                        Jaylib.GREEN,
                        Jaylib.RED
                ) {
                    @Override
                    public void onRelease() {
                        System.out.println("Volume Slider Changed");
                        sm.setSetting("volume", String.valueOf(sliderValue));
                        needToApply = true;
                        //TODO: change the volume
                    }

                    @Override
                    public void onValueChange() {
                        AudioManager.masterVolume = sliderValue;
                    }
                },
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
                        Jaylib.WHITE,
                        Jaylib.GREEN
                ) {
                    @Override
                    public void run() {
                        System.out.println("Apply Button Pressed");
                        needToApply = false;
                        try {
                            sm.saveSettings();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        //TODO: fix
                        if (Jaylib.IsWindowFullscreen() != sm.getSetting("fullscreen").equals("1"))
                            Jaylib.ToggleFullscreen();

                    }
                },
        }, false);

        settingsRestartBatch = new ElementBatch(new Element[]{
                // Restart Button
                new ButtonElement(
                        Vector2.byScreenPercent(50, 90),
                        Vector2.byScreenPercent(40, 10),
                        "Restart",
                        50f,
                        Jaylib.LIGHTGRAY,
                        Jaylib.BLANK,
                        Jaylib.WHITE,
                        Jaylib.GREEN
                ) {
                    @Override
                    public void run() {
                        System.out.println("Restart Button Pressed");
                        needToApply = false;
                        needToRestart = false;
                        try {
                            sm.saveSettings();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (Jaylib.IsWindowFullscreen() != sm.getSetting("fullscreen").equals("1"))
                            Jaylib.ToggleFullscreen();
                        Window.instance.reopenWindow();
                        reload();
                    }
                },
        }, false);

        addElement(settingsBodyBatch);
        addElement(settingsApplyBatch);
        addElement(settingsRestartBatch);
    }
}
