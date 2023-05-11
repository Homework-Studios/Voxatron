package render.scene.scenes.uiElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.assets.basic.ImageAsset;
import math.Vector2;
import render.scene.Element;

public class ImageElement extends Element {

    private final Raylib.Texture texture;
    public Raylib.Image image;
    public Vector2 position;
    public Vector2 size;

    public ImageElement(ImageAsset asset, Vector2 position, Vector2 size) {
        this.image = asset.getImage();
        this.position = position;
        this.size = size;

        Raylib.ImageResize(image, (int) size.x, (int) size.y);
        texture = Raylib.LoadTextureFromImage(image);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        // the origin of the image is in the center of the image
        Raylib.DrawTexture(texture, (int) (position.x - size.x / 2), (int) (position.y - size.y / 2), Jaylib.WHITE);
    }
}
