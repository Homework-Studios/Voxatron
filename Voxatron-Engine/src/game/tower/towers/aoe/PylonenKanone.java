package game.tower.towers.aoe;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
import engine.math.Vector3;
import game.GameManager;
import game.enemy.Gegner;
import game.tower.EnergieVerbraucher;

public class PylonenKanone extends EnergieVerbraucher {
    private final Raylib.Model modell;
    private float drehung = 0;
    private int pause;
    private int schuss;

    public PylonenKanone() {
        super(Type.PYLON);
        ModelAsset asset = new AssetManager<ModelAsset>().getAsset("Game/Towers/PylonModel");
        modell = asset.getNewModel();
    }

    @Override
    public void spielSchlag() {
        if (hatEnergie(25)) {
            pause++;
            if (pause >= 60) {
                pause = 0;
                for (Gegner gegner : GameManager.instance.getGegnerInReichweiteEinerPosition(position, reichweite)) {
                    gegner.schaden(5);
                }
                verbraucheEnergie(25);
                schuss = 5;
            }
        }
    }

    @Override
    public void update() {
        drehung += 0.01f;
        if (drehung > 360) drehung = 0;
        modell.transform(Raylib.MatrixRotateY(drehung));
        if (schuss > 0) {
            schuss--;
        }
    }

    @Override
    public void render() {
        Vector3 pos = new Vector3(position).add(0, (float) Math.sin(drehung), 0);
        Raylib.DrawModel(modell, pos.toRaylibVector3(), 3, typ.getFarbe());
        zeichneEnergie();
        drawRange();
        if (schuss > 0) {
            Vector3 kopie = new Vector3(pos).add(0, 5, 0);
            Raylib.DrawCircle3D(kopie.toRaylibVector3(), reichweite, new Vector3(1, 0, 0).toRaylibVector3(), 90, Jaylib.WHITE);
        }
    }
}
