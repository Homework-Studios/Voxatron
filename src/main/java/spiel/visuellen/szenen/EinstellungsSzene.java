package spiel.visuellen.szenen;

import com.raylib.Jaylib;
import engine.math.Vector2;
import engine.render.scene.Element;
import engine.render.scene.ElementBatch;
import engine.render.scene.Scene;
import engine.render.scene.SceneManager;
import engine.render.scene.elements.ButtonElement;
import engine.render.scene.elements.SliderElement;
import engine.render.scene.elements.TextElement;
import engine.render.scene.elements.ToggleElement;
import engine.window.SettingsManager;
import engine.window.Window;

import java.io.IOException;

import static engine.util.ColorPalette.ColorPalettes.STANDARD_BUTTON;

public class EinstellungsSzene extends Scene {

    public SettingsManager sm;
    public ElementBatch einstellungsKoerperPaket;
    public ElementBatch einstellungsAnwendenPaket;
    public ElementBatch einstellugnsNeustartenPaket;

    public boolean mussAnwenden = false;
    public boolean mussNeustarten = false;

    public EinstellungsSzene() {
        super();
    }

    @Override
    public void update() {
        // Update the settings apply batch if the settings have been changed
        einstellungsAnwendenPaket.setVisibility(mussAnwenden);
        einstellugnsNeustartenPaket.setVisibility(mussNeustarten);

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
        boolean vollbild = sm.getSetting("vollbild").equals("1");

        if (Jaylib.IsWindowFullscreen() != vollbild)
            Jaylib.ToggleFullscreen();

        einstellungsKoerperPaket = new ElementBatch(new Element[]{
                new TextElement(
                        Vector2.byScreenPercent(0, 0),
                        Vector2.byScreenPercent(100, 15),
                        "Einstellungen",
                        50f,
                        Jaylib.LIGHTGRAY
                ),
                new ButtonElement(
                        Vector2.byScreenPercent(94, 5),
                        Vector2.byScreenPercent(10, 7),
                        "Schließen",
                        40f,
                        STANDARD_BUTTON
                ) {
                    @Override
                    public void run() {
                        SceneManager.instance.setActiveScene(TitelSzene.class);
                    }
                },
                new ToggleElement(
                        Vector2.byScreenPercent(25, 30),
                        Vector2.byScreenPercent(40, 10),
                        "Vollbild",
                        50f,
                        vollbild,
                        STANDARD_BUTTON
                ) {
                    @Override
                    public void run() {
                        sm.setSetting("fullscreen", vollbild ? "0" : "1");
                        mussAnwenden = true;
                    }
                },
                new ToggleElement(
                        Vector2.byScreenPercent(75, 30),
                        Vector2.byScreenPercent(40, 10),
                        "Partikel",
                        50f,
                        false,
                        STANDARD_BUTTON

                ) {
                    @Override
                    public void run() {
                        System.out.println("Particles Toggle Pressed");
                        mussAnwenden = true;
                    }
                },
                new ToggleElement(
                        Vector2.byScreenPercent(25, 45),
                        Vector2.byScreenPercent(40, 10),
                        "Anti-Aliasing",
                        50f,
                        aa,
                        STANDARD_BUTTON
                ) {
                    @Override
                    public void run() {
                        System.out.println("Anti-Aliasing Toggle Pressed");
                        sm.setSetting("aa", aa ? "0" : "1");
                        mussNeustarten = true;
                    }
                },
                new ToggleElement(
                        Vector2.byScreenPercent(75, 45),
                        Vector2.byScreenPercent(40, 10),
                        "Bildschirm Effekte",
                        50f,
                        false,
                        STANDARD_BUTTON
                ) {
                    @Override
                    public void run() {
                        System.out.println("Screen Shake Toggle Pressed");
                        mussAnwenden = true;
                    }
                },
// Text Elements define the area of where the text should be centered in
                new TextElement(
                        Vector2.byScreenPercent(5, 55),
                        Vector2.byScreenPercent(10, 10),
                        "Lautstärke",
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
                        mussAnwenden = true;
                        //TODO: change the volume
                    }

                    @Override
                    public void onValueChange() {

                    }
                },
        }, true);

        einstellungsAnwendenPaket = new ElementBatch(new Element[]{
                // Apply Button
                new ButtonElement(
                        Vector2.byScreenPercent(50, 90),
                        Vector2.byScreenPercent(40, 10),
                        "Anwenden",
                        50f,
                        STANDARD_BUTTON
                ) {
                    @Override
                    public void run() {
                        System.out.println("Apply Button Pressed");
                        mussAnwenden = false;
                        try {
                            sm.saveSettings();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        //TODO: fix
                        if (Jaylib.IsWindowFullscreen() != sm.getSetting("fullscreeen").equals("1"))
                            Jaylib.ToggleFullscreen();

                    }
                },
        }, false);

        einstellugnsNeustartenPaket = new ElementBatch(new Element[]{
                // Restart Button
                new ButtonElement(
                        Vector2.byScreenPercent(50, 90),
                        Vector2.byScreenPercent(40, 10),
                        "Neustarten",
                        50f,
                        STANDARD_BUTTON
                ) {
                    @Override
                    public void run() {
                        System.out.println("Restart Button Pressed");
                        mussAnwenden = false;
                        mussNeustarten = false;
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

        addElement(einstellungsKoerperPaket);
        addElement(einstellungsAnwendenPaket);
        addElement(einstellugnsNeustartenPaket);
    }
}
