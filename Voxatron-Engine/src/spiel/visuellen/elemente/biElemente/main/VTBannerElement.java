package spiel.visuellen.elemente.biElemente.main;

import com.raylib.Raylib;
import engine.assets.basic.ImageAsset;
import engine.math.Vector2;
import engine.render.scene.elements.ImageElement;

public class VTBannerElement extends ImageElement {

    private final Vector2 originalePosition;

    public VTBannerElement(ImageAsset asset, Vector2 position, Vector2 groesse) {
        super(asset, position, groesse);
        this.originalePosition = new Vector2(position.x, position.y);
    }

    @Override
    public void update() {

        this.position.y = ((float) Math.sin(Raylib.GetTime() * 1.3f) * 30f) + originalePosition.y;

        super.update();
    }
}
