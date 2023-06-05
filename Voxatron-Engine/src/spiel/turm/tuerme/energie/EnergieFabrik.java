package spiel.turm.tuerme.energie;

import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
import engine.render.shader.ShaderManager;
import spiel.turm.EnergieVerbraucher;
import spiel.turm.Fabrik;

public class EnergieFabrik extends Fabrik {

    private final Raylib.Model modell;
    private EnergieVerbraucher[] verbraucher = new EnergieVerbraucher[0];

    public EnergieFabrik() {
        super(Type.ENERGIE_FABRIK);
        ModelAsset asset = new AssetManager<ModelAsset>().getAsset("Game/Towers/EnergyFactory");
        modell = asset.getModel();
        modell.materials().shader(ShaderManager.instance.lightShader);
        reichweite = 100;
    }

    // berechnet die Energieproduktion und fügt sie dem Turm hinzu
    @Override
    public void spielSchlag() {
        if (momentanenSchlag >= gibEnergieProSchlag) {
            addEnergie(provideEnergy(5));
            momentanenSchlag = 0;
        }

        momentanenSchlag++;
    }

    // aktualisiert die Liste der Energieverbraucher in Reichweite
    @Override
    public void update() {
        verbraucher = spielManager.getNaechstenEnergieVerbraucher(position, reichweite);
    }

    // zeichnet den Turm und die Reichweite, sowie eine Linie zu allen Energieverbrauchern in Reichweite
    @Override
    public void render() {
        drawRange();

        if (modell != null)
            Raylib.DrawModel(modell, position.toRaylibVector3(), 3, typ.getFarbe());
        
        for (EnergieVerbraucher verbraucher : verbraucher) {
            Raylib.DrawLine3D(position.toRaylibVector3(), verbraucher.position.toRaylibVector3(), typ.getFarbe());
        }

    }
}
