package spiel.visuellen.szenen;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ImageAsset;
import engine.math.Vector2;
import engine.render.scene.Element;
import engine.render.scene.ElementBatch;
import engine.render.scene.Scene;
import engine.render.scene.SceneManager;
import engine.render.scene.elements.ButtonElement;
import engine.render.scene.elements.TextElement;
import spiel.SpielManager;
import spiel.visuellen.elemente.biElemente.levelSelector.EbenenAuswaehler;
import spiel.visuellen.elemente.biElemente.levelSelector.EbenenAuswahltTab;

import static engine.util.ColorPalette.ColorPalettes.STANDARD_BUTTON;

public class EbenenAuswaehlerSzene extends Scene {

    public ElementBatch ebenenAuswahlPaket;

    public EbenenAuswaehlerSzene() {
    }

    @Override
    public void init() {
        // Erstellt eine Textur mit einem Schachbrettmuster
        Raylib.Texture basisVorderbild = new AssetManager<ImageAsset>().getAsset("Game/Icons").getTexture("icons");

        // Erstellt eine ElementBatch, die alle Elemente der Szene enthält
        ebenenAuswahlPaket = new ElementBatch(new Element[]{
                // Erstellt einen Text, der die Überschrift der Szene darstellt
                new TextElement(
                        Vector2.byScreenPercent(0, 0),
                        Vector2.byScreenPercent(100, 15),
                        "Ebenen",
                        50f,
                        Jaylib.LIGHTGRAY
                ),
                // Erstellt einen Button, der die Szene schließt
                new ButtonElement(
                        Vector2.byScreenPercent(94, 5),
                        Vector2.byScreenPercent(10, 7),
                        "Schließen",
                        40f,
                        STANDARD_BUTTON
                ) {
                    @Override
                    public void run() {
                        // Setzt die aktive Szene auf die TitelSzene
                        SceneManager.instance.setActiveScene(TitelSzene.class);
                    }
                },
                // Erstellt einen EbenenAuswaehler, der die verfügbaren Ebenen anzeigt
                new EbenenAuswaehler(
                        Vector2.byScreenPercent(50, 50),
                        Vector2.byScreenPercent(40, 60),
                        new EbenenAuswahltTab[]{
                                // TODO: Implement image assets!
                                new EbenenAuswahltTab("Tron", Jaylib.BLUE, basisVorderbild, false),
                                /*
                                new EbenenAuswahltTab("The Depths", Jaylib.RED, basisVorderbild, false),
                                new EbenenAuswahltTab("The Generator", Jaylib.GREEN, basisVorderbild, true),
                                new EbenenAuswahltTab("The Mainframe", Jaylib.YELLOW, basisVorderbild, true),

                                 */
                        }
                ) {
                    @Override
                    public void run() {
                        // Setzt die aktive Szene auf die KarteEinsSzene und startet das Spiel
                        SceneManager.instance.setActiveScene(KarteEinsSzene.class);
                        SpielManager.getInstance().start();
                    }
                }
        }, true);

        addElement(ebenenAuswahlPaket);
    }

    @Override
    public void update() {
        // Aktualisiert alle Elemente der Szene
        for (Element element : getIterableElements()) {
            element.update();
        }
    }

    @Override
    public void render() {
        // Rendert alle Elemente der Szene
        for (Element element : elements) {
            element.render();
        }
    }
}
