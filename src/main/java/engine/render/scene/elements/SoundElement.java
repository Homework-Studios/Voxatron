package engine.render.scene.elements;

import com.raylib.Raylib;
import engine.assets.basic.SoundAsset;
import engine.render.scene.Element;

public class SoundElement extends Element {

    public SoundAsset sound;
    public boolean infinite;

    public SoundElement(SoundAsset sound, boolean infinite) {
        this.sound = sound;
        this.infinite = infinite;


        Raylib.PlaySound(sound.getSound());
    }

    @Override
    public void update() {
        if (infinite) {
            if (!Raylib.IsSoundPlaying(sound.getSound())) {
                Raylib.PlaySound(sound.getSound());
            }
        }
    }

    @Override
    public void render() {

    }
}
