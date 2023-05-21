package game.visuals.elements.uiElements.main;

import com.raylib.Raylib;
import engine.assets.basic.ImageAsset;
import engine.math.Vector2;
import game.visuals.elements.uiElements.ImageElement;

public class VTBannerElement extends ImageElement {

    private final Vector2 originalPosition;

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
