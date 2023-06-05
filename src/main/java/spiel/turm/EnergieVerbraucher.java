package spiel.turm;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.render.Renderer;

public abstract class EnergieVerbraucher extends Turm {

    public int verfuegbareEnergie = 0;
    public int maximaleEnergie = 100;

    public EnergieVerbraucher(Type type) {
        super(type);
    }

    // verbraucht die angegebene Menge an Energie
    public void verbraucheEnergie(int menge) {
        verfuegbareEnergie -= menge;
    }

    // fügt die angegebene Menge an Energie hinzu und gibt die überschüssige Energie zurück
    public int addEnergy(int menge) {
        // versuche so viel Energie wie möglich hinzuzufügen
        if (menge + verfuegbareEnergie <= maximaleEnergie) {
            verfuegbareEnergie += menge;
            return 0;
        }

        // füge so viel Energie wie möglich hinzu
        int hinzugefuegteEnergie = maximaleEnergie - verfuegbareEnergie;
        verfuegbareEnergie = maximaleEnergie;

        // die überschüssige Energie wird zurückgegeben
        return menge - hinzugefuegteEnergie;
    }

    // gibt zurück, ob genug Energie vorhanden ist
    public boolean hatEnergie(int amount) {
        return verfuegbareEnergie >= amount;
    }

    // gibt die verfügbare Energiemenge zurück
    public int getVerfuegbareEnergie() {
        return verfuegbareEnergie;
    }

    // zeichnet die verfügbare Energiemenge auf dem Bildschirm
    public void zeichneEnergie() {
        Raylib.EndMode3D();
        Raylib.Vector2 bildschirmPosition = Raylib.GetWorldToScreen(position.toRaylibVector3(), Renderer.camera.getCamera());

        Raylib.DrawText(String.valueOf(verfuegbareEnergie), (int) bildschirmPosition.x(), (int) bildschirmPosition.y(), 20, Jaylib.WHITE);
        Raylib.BeginMode3D(Renderer.camera.getCamera());
    }
}
