package game.visuals.scenes;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.math.Vector2;
import engine.render.scene.Element;
import engine.render.scene.ElementBatch;
import engine.render.scene.Scene;
import engine.render.scene.SceneManager;
import engine.render.scene.elements.ButtonElement;
import engine.render.scene.elements.TextElement;
import game.GameManager;
import game.visuals.elements.uiElements.levelSelector.EbenenAuswaehler;
import game.visuals.elements.uiElements.levelSelector.EbenenAuswahltTab;

import static engine.util.ColorPalette.ColorPalettes.STANDARD_BUTTON;

public class EbenenAuswaehlerSzene extends Scene {

    public ElementBatch ebenenAuswahlPaket;

    public EbenenAuswaehlerSzene() {
    }

    @Override
    public void init() {
        Raylib.Texture basisVorderbild = Raylib.LoadTextureFromImage(Jaylib.GenImageChecked(1000, 1000, 10, 10, Jaylib.PINK, Jaylib.BLACK));

        ebenenAuswahlPaket = new ElementBatch(new Element[]{
                new TextElement(
                        Vector2.byScreenPercent(0, 0),
                        Vector2.byScreenPercent(100, 15),
                        "Ebenen",
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
                        SceneManager.instance.setActiveScene(KarteEinsSzene.class);
                        GameManager.getInstance().start();
                    }
                }
        }, true);

        addElement(ebenenAuswahlPaket);
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
