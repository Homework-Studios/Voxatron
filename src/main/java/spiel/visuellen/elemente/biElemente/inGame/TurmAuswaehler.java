package spiel.visuellen.elemente.biElemente.inGame;

import com.raylib.Raylib;
import engine.math.LerpUtil;
import engine.math.Vector2;
import engine.render.scene.Element;
import engine.util.UiUtils;
import spiel.turm.Turm;

public abstract class TurmAuswaehler extends Element implements Runnable {

    private final Vector2 position;
    private final Vector2 groesse;
    private final TurmPanel[] tuerme;
    private int scroll = 0;
    private int zeilScrolling = 0;
    private float schwebenOffset;

    /**
     * WICHTIG: Mindestens drei Türme im Turm-Array (Scrolling-Probleme)
     */
    public TurmAuswaehler(Vector2 position, Vector2 groesse) {
        this.position = position;
        this.groesse = groesse;
        this.tuerme = Turm.Type.panels;
    }

    @Override
    public void update() {
        // Hover-Check
        Raylib.Rectangle kollisionen = new Raylib.Rectangle().x(position.x - groesse.x / 2).y(position.y - groesse.y / 2 - schwebenOffset).width(groesse.x).height(groesse.y + UiUtils.getHeightPercent(5));
        boolean schweben = Raylib.CheckCollisionPointRec(Raylib.GetMousePosition(), kollisionen);

        // Überprüfen, ob die Maus scrollt
        final int scrollGeschwindigkeit = 60;
        if (Raylib.GetMouseWheelMove() > 0 && schweben) {
            zeilScrolling += scrollGeschwindigkeit;
        }

        if (Raylib.GetMouseWheelMove() < 0 && schweben) {
            zeilScrolling -= scrollGeschwindigkeit;
        }

        // Ziel-Scrolling begrenzen
        if (zeilScrolling < 0) {
            zeilScrolling = 0;
        }

        float turmBreite = groesse.x / 10;
        float maxScroll = (tuerme.length - 3) * (turmBreite + UiUtils.getWidthPercent(1));
        if (zeilScrolling > maxScroll) {
            zeilScrolling = (int) maxScroll;
        }

        // Sanftes Scrolling
        scroll = (int) LerpUtil.lerp(scroll, zeilScrolling, 0.1f);

        // Beim Scrollen nach oben bewegen
        float zielSchwebenAbstand = schweben ? UiUtils.getHeightPercent(20) : 0;

        // Effektive Position aktualisieren
        schwebenOffset = LerpUtil.lerp(schwebenOffset, zielSchwebenAbstand, 0.1f);
        Vector2 effectivePosition = new Vector2(position.x, position.y - schwebenOffset);

        // Tabs aktualisieren
        for (int i = 0; i < tuerme.length; i++) {
            TurmPanel turm = tuerme[i];
            turm.index = i;
            turm.scroll = scroll;
            turm.position = effectivePosition;
            turm.groesse = groesse;
            turm.update();
        }
    }

    @Override
    public void render() {
        for (TurmPanel turm : tuerme) {
            turm.render();
        }
    }
}
