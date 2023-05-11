package render.scene.scenes;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ImageAsset;
import engine.assets.basic.SoundAsset;
import math.Vector2;
import render.scene.Element;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.scenes.uiElements.*;
import window.Window;

public class TitleScene extends Scene {

    public boolean creditsVisible = false;
    ElementBatch titleScreenBatch;
    ElementBatch creditsScreenBatch;

    public TitleScene() {
        super();
    }

    @Override
    public void init() {
        titleScreenBatch = new ElementBatch(new Element[]{
                new SoundElement(
                        new AssetManager<SoundAsset>().getAsset("MainMenu/VTTheme")
                ),
                new ImageElement(
                        new AssetManager<ImageAsset>().getAsset("MainMenu/VTBanner"),
                        Vector2.byScreenPercent(50, 30),
                        Vector2.byScreenPercent(50, 50)
                ),
                new ButtonElement(
                        Vector2.byScreenPercent(50, 60),
                        Vector2.byScreenPercent(40, 10),
                        "Play",
                        50f,
                        Jaylib.LIGHTGRAY,
                        Jaylib.BLANK,
                        Jaylib.WHITE,
                        Jaylib.GREEN
                ) {
                    @Override
                    public void run() {
                        System.out.println("Play Button Pressed");
                    }
                },
                new ButtonElement(
                        Vector2.byScreenPercent(50, 72),
                        Vector2.byScreenPercent(40, 10),
                        "Settings",
                        50f,
                        Jaylib.LIGHTGRAY,
                        Jaylib.BLANK,
                        Jaylib.WHITE,
                        Jaylib.GREEN
                ) {
                    @Override
                    public void run() {
                        SceneManager.instance.setActiveScene(SettingsScene.class);
                    }
                },
                new ButtonElement(
                        Vector2.byScreenPercent(50, 84),
                        Vector2.byScreenPercent(40, 10),
                        "Quit",
                        50f,
                        Jaylib.LIGHTGRAY,
                        Jaylib.BLANK,
                        Jaylib.WHITE,
                        Jaylib.GREEN
                ) {
                    @Override
                    public void run() {
                        Window.instance.stop();
                    }
                },
                new ButtonElement(
                        Vector2.byScreenPercent(94, 5),
                        Vector2.byScreenPercent(10, 7),
                        "Credits",
                        40f,
                        Jaylib.LIGHTGRAY,
                        Jaylib.BLANK,
                        Jaylib.WHITE,
                        Jaylib.GREEN
                ) {
                    @Override
                    public void run() {
                        creditsVisible = !creditsVisible;
                        System.out.println("Credits Button Pressed + " + creditsVisible);
                    }
                },
                new ToggleElement(
                        Vector2.byScreenPercent(94, 14),
                        Vector2.byScreenPercent(10, 7),
                        "Mute",
                        40f,
                        false,
                        Jaylib.LIGHTGRAY,
                        Jaylib.WHITE,
                        Jaylib.GREEN,
                        Jaylib.RED
                ) {
                    @Override
                    public void run() {
                        System.out.println("Mute Button Pressed");
                        if (toggle)
                            Raylib.PauseSound(new AssetManager<SoundAsset>().getAsset("MainMenu/VTTheme").getSound());
                        else
                            Raylib.ResumeSound(new AssetManager<SoundAsset>().getAsset("MainMenu/VTTheme").getSound());
                    }
                },
        }, true);

        // add a credits screen batch but just add the close button for now in the top right corner
        creditsScreenBatch = new ElementBatch(new Element[]{
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
                        creditsVisible = !creditsVisible;
                        System.out.println("Credits Button Pressed + " + creditsVisible);
                    }
                },
                new TextElement(
                        Vector2.byScreenPercent(0, 0),
                        Vector2.byScreenPercent(100, 100),
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
