package spiel.turm.tuerme.aoe;

import com.raylib.Raylib;
import spiel.SpielManager;
import spiel.gegner.Gegner;
import spiel.turm.EnergieVerbraucher;

public class KugelKanone extends EnergieVerbraucher {

    public KugelKanone() {
        super(Type.SPHERE_CANON);
        reichweite = 100;
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 5, 5, 5, typ.getFarbe());
        if (ziel != null)
            Raylib.DrawSphereWires(ziel.position.toRaylibVector3(), 10, 10, 10, typ.getFarbe());
        zeichneEnergie();
        drawRange();
    }

    @Override
    public void spielSchlag() {
        versucheZuFeuern();
        if (hatEnergie(50) && kannFeuern()) {
            ziel = SpielManager.instance.getNaechstenGegen(position, reichweite);

            if (ziel != null) {
                verbraucheEnergie(50);
                feuern();
                final float reichweite = 10;
                for (Gegner gegner : SpielManager.instance.getGegnerInReichweiteEinerPosition(ziel.position, reichweite)) {
                    gegner.schaden(50);
                }
            }
        } else {
            ziel = null;
        }
    }
}