package render.scene.scenes.uiElements;

import com.raylib.Raylib;
import engine.assets.basic.ImageAsset;
import math.Vector2;

public class VTBannerElement extends ImageElement {
    public VTBannerElement(ImageAsset asset, Vector2 position, Vector2 size) {
        super(asset, position, size);
    }

    @Override
    public void update() {
        // slowly move up the banner and down again to bring more life to the title screen
        // value is n to -n and is dependend on a sin and the Raylib.GetTime() function

        this.position.y += (float) Math.sin(Raylib.GetTime() * 1.3f) * 0.7f;

        super.update();
    }
}
