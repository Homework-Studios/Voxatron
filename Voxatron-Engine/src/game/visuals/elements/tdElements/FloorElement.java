package game.visuals.elements.tdElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
import engine.render.scene.Element;

import static com.raylib.Jaylib.DARKGRAY;

public class FloorElement extends Element {

    private final Raylib.Model model;

    public FloorElement(int i) {
        ModelAsset asset = new AssetManager<ModelAsset>().getAsset("Game/Maps");
        model = asset.getNewModel(String.valueOf(i));
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

        if (model == null) return;

        Raylib.DrawModel(model, new Jaylib.Vector3(), 5, DARKGRAY);
    }
}
