package game.visuals.elements.tdElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
import engine.render.scene.Element;

import static com.raylib.Jaylib.DARKGRAY;

public class BodenElement extends Element {

    private final Raylib.Model modell;

    public BodenElement(int i) {
        ModelAsset asset = new AssetManager<ModelAsset>().getAsset("Game/Maps");
        modell = asset.getNewModel(String.valueOf(i));
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

        if (modell == null) return;

        Raylib.DrawModel(modell, new Jaylib.Vector3(), 5, DARKGRAY);
    }
}
