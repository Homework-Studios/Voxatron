package game.tower.towers.singletarget;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
import engine.math.Vector3;
import game.GameManager;
import game.tower.EnergieVerbraucher;

public class WuerfelKanone extends EnergieVerbraucher {

    private final Raylib.Model basis;
    private final Raylib.Model kanone;

    public WuerfelKanone() {
        super(Type.WUERFEL_KANONE);
        ModelAsset asset = new AssetManager<ModelAsset>().getAsset("Game/Towers/CubeCanon");
        basis = asset.getModel("cubecanonbase");
        kanone = asset.getNewModel("cubecanoncanon");
    }

    @Override
    public void update() {
        ziel = GameManager.instance.getWeitestenGegnerInReichweite(position, reichweite);
        if (ziel != null && ziel.position.distance(position) < reichweite)
            //make canon look at target
            kanone.transform(Raylib.MatrixRotateY((float) Math.atan2(ziel.position.x - position.x, ziel.position.z - position.z)));
        versucheZuFeuern();
        if (hatEnergie(5) && kannFeuern()) {
            if (ziel != null) {
                verbraucheEnergie(5);
                feuern();
                ziel.schaden(5);
            }
        } else {
            ziel = null;
        }
    }

    @Override
    public void render() {

        drawRange();

        Raylib.DrawModel(basis, position.toRaylibVector3(), 2, typ.getFarbe());
        Raylib.DrawModel(kanone, position.toRaylibVector3(), 2, typ.getFarbe());


        zeichneEnergie();

        // draw a line to the target if not null
        if (ziel != null) {
            Vector3 kopie = new Vector3(position).add(0, 4, 0);
            Raylib.DrawLine3D(kopie.toRaylibVector3(), ziel.position.toRaylibVector3(), Jaylib.WHITE);
        }
    }

    @Override
    public void spielSchlag() {

    }
}
