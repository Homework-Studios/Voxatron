package game.visuals.scenes;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ImageAsset;
import engine.assets.basic.SoundAsset;
import engine.math.Vector2;
import engine.render.scene.Element;
import engine.render.scene.ElementBatch;
import engine.render.scene.Scene;
import engine.render.scene.SceneManager;
import engine.render.scene.elements.*;
import engine.window.Window;
import game.visuals.elements.uiElements.main.VTBannerElement;

import static engine.util.ColorPalette.ColorPalettes.STANDARD_BUTTON;

public class TitelSzene extends Scene {

    public boolean creditsSichtbar = false;
    ElementBatch titelBildschirmPaket;
    ElementBatch creditsBildschirmPaket;
    ScrollingBackgroundElement scrollingHintergrundElement;

    public TitelSzene() {
        super();
    }

    @Override
    public void init() {
        titelBildschirmPaket = new ElementBatch(new Element[]{
                new SoundElement(
                        new AssetManager<SoundAsset>().getAsset("MainMenu/VTTheme"),
                        true
                ),
                new VTBannerElement(
                        new AssetManager<ImageAsset>().getAsset("MainMenu/VTBanner"),
                        Vector2.byScreenPercent(50, 27),
                        Vector2.byScreenPercent(50, 50)
                ),
                new ButtonElement(
                        Vector2.byScreenPercent(50, 60),
                        Vector2.byScreenPercent(40, 10),
                        "Spielen",
                        50f,
                        STANDARD_BUTTON
                ) {
                    @Override
                    public void run() {
                        SceneManager.instance.setActiveScene(EbenenAuswaehlerSzene.class);
                    }
                },
                new ButtonElement(
                        Vector2.byScreenPercent(50, 72),
                        Vector2.byScreenPercent(40, 10),
                        "Einstellungen",
                        50f,
                        STANDARD_BUTTON
                ) {
                    @Override
                    public void run() {
                        SceneManager.instance.setActiveScene(EinstellungsSzene.class);
                    }
                },
                new ButtonElement(
                        Vector2.byScreenPercent(50, 84),
                        Vector2.byScreenPercent(40, 10),
                        "Verlassen",
                        50f,
                        STANDARD_BUTTON
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
                        STANDARD_BUTTON
                ) {
                    @Override
                    public void run() {
                        creditsSichtbar = !creditsSichtbar;
                        System.out.println("Credits Button Pressed + " + creditsSichtbar);
                    }
                },
                new ToggleElement(
                        Vector2.byScreenPercent(94, 14),
                        Vector2.byScreenPercent(10, 7),
                        "Stumm",
                        40f,
                        false,
                        STANDARD_BUTTON
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
        creditsBildschirmPaket = new ElementBatch(new Element[]{
                new ButtonElement(
                        Vector2.byScreenPercent(94, 5),
                        Vector2.byScreenPercent(10, 7),
                        "Schließen",
                        40f,
                        STANDARD_BUTTON
                ) {
                    @Override
                    public void run() {
                        creditsSichtbar = !creditsSichtbar;
                        System.out.println("Credits Button Pressed + " + creditsSichtbar);
                    }
                },
                new TextElement(
                        Vector2.byScreenPercent(0, 0),
                        Vector2.byScreenPercent(100, 100),
                        "Das Spiel wurde von Jonas Windmann und Timon Richter im Rahmen eines Schulprojektes angefertigt\nDieses Spiel wurde mit der hauseigenen Voxatron-Engine entwickelt",
                        30f,
                        Jaylib.LIGHTGRAY
                ),
        }, false);

        addElement(titelBildschirmPaket);
        addElement(creditsBildschirmPaket);

        scrollingHintergrundElement = new ScrollingBackgroundElement(
                Jaylib.GenImageGradientV(Raylib.GetScreenWidth(), Raylib.GetScreenHeight(), Jaylib.BLACK, new Jaylib.Color(0, 40, 0, 255)),
                Vector2.byScreenPercent(50, 50),
                Vector2.byScreenPercent(100, 100),
                50,
                true
        );
    }

    @Override
    public void update() {
        titelBildschirmPaket.setVisibility(!creditsSichtbar);
        creditsBildschirmPaket.setVisibility(creditsSichtbar);

        for (Element element : getIterableElements()) {
            element.update();
        }

        scrollingHintergrundElement.update();
    }

    @Override
    public void render() {
        scrollingHintergrundElement.render();

        for (Element element : elements) {
            element.render();
        }
    }
}
