package spiel.turm.tuerme.einzelziel;

import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
import engine.math.Vector3;
import spiel.SpielManager;
import spiel.gegner.Gegner;
import spiel.turm.EnergieVerbraucher;

public class LaserKanone extends EnergieVerbraucher {
    private final Raylib.Model basis;
    private final Raylib.Model kanone;
    int verbrauch = 1;
    int gefeuert = 0;
    Gegner letztesZiel = null;
    private double schweben;

    public LaserKanone() {
        super(Type.LASER);
        ModelAsset modelAsset = new AssetManager<ModelAsset>().getAsset("Game/Towers/Laser");
        basis = modelAsset.getModel("base");
        kanone = modelAsset.getNewModel("laser");
    }

    @Override
    public void update() {
        // Funktion, die die Bewegung der Kanone aktualisiert
        schweben += 0.01f;
        schweben %= Math.PI * 2;
    }

    @Override
    public void render() {
        // Funktion, die das Modell der Kanone und der Basis rendert
        if (basis != null && kanone != null) {
            Vector3 pos = new Vector3(position).add(0, (float) Math.sin(schweben) / 2, 0);
            Raylib.DrawModel(basis, position.toRaylibVector3(), 2, typ.getFarbe());
            Raylib.DrawModel(kanone, pos.toRaylibVector3(), 2, typ.getFarbe());

            // Funktion, die den Laserstrahl rendert
            if (ziel != null)
                Raylib.DrawCylinderEx(pos.add(0, 10, 0).toRaylibVector3(), ziel.position.toRaylibVector3(), 0.1f * verbrauch, 0.1f * verbrauch, 12, typ.getFarbe());
        }

        drawRange();
        zeichneEnergie();
    }

    @Override
    public void spielSchlag() {
        // Funktion, die den Schuss der Kanone ausführt
        if (ziel == null || !ziel.istAmLeben) {
            ziel = SpielManager.instance.getWeitestenGegnerInReichweite(position, reichweite);
            if (ziel == null) {
                verbrauch = 1;
                gefeuert = 0;
            }
        }

        if (ziel != null)
            // Funktion, die die Kanone auf das Ziel ausrichtet
            kanone.transform(Raylib.MatrixRotateY((float) Math.atan2(ziel.position.x - position.x, ziel.position.z - position.z)));

        if (hatEnergie(verbrauch)) {

            if (ziel != null) {
                verbraucheEnergie(verbrauch * verbrauch);
                feuern();
                ziel.schaden(verbrauch * verbrauch);
                if (gefeuert >= 20) {
                    verbrauch++;
                    gefeuert = 0;
                }
                gefeuert++;
            }
        } else {
            ziel = null;
        }
    }
}
