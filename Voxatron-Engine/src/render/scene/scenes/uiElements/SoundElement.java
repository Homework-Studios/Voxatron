package render.scene.scenes.uiElements;

import com.raylib.Raylib;
import engine.assets.basic.SoundAsset;
import render.scene.Element;

public class SoundElement extends Element {

    public SoundElement(SoundAsset sound) {
        Raylib.PlaySound(sound.getSound());
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }
}
