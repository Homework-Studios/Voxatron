package render.scene.scenes.uiElements;

import com.raylib.Raylib;
import engine.assets.basic.ImageAsset;
import math.Vector2;

public class VTBannerElement extends ImageElement {

    private Vector2 originalPosition;

    public VTBannerElement(ImageAsset asset, Vector2 position, Vector2 size) {
        super(asset, position, size);
        this.originalPosition = new Vector2(position.x, position.y);
    }

    @Override
    public void update() {

        this.position.y = ((float) Math.sin(Raylib.GetTime() * 1.3f) * 30f) + originalPosition.y;

        super.update();
    }
}
