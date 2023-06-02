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

    public void verbraucheEnergie(int menge) {
        verfuegbareEnergie -= menge;
    }

    public int addEnergy(int menge) {
        // try to add as much energy as possible
        if (menge + verfuegbareEnergie <= maximaleEnergie) {
            verfuegbareEnergie += menge;
            return 0;
        }

        // add as much energy as possible
        int hinzugefuegteEnergie = maximaleEnergie - verfuegbareEnergie;
        verfuegbareEnergie = maximaleEnergie;

        // all leftover energy is returned
        return menge - hinzugefuegteEnergie;
    }

    public boolean hatEnergie(int amount) {
        return verfuegbareEnergie >= amount;
    }

    public int getVerfuegbareEnergie() {
        return verfuegbareEnergie;
    }

    public void zeichneEnergie() {
        Raylib.EndMode3D();
        Raylib.Vector2 bildschirmPosition = Raylib.GetWorldToScreen(position.toRaylibVector3(), Renderer.camera.getCamera());

        Raylib.DrawText(String.valueOf(verfuegbareEnergie), (int) bildschirmPosition.x(), (int) bildschirmPosition.y(), 20, Jaylib.WHITE);
        Raylib.BeginMode3D(Renderer.camera.getCamera());
    }
}
