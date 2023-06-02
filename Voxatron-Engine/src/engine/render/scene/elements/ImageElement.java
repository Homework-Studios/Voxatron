package engine.render.scene.elements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.assets.basic.ImageAsset;
import engine.math.Vector2;
import engine.render.scene.Element;

public class ImageElement extends Element {

    public Raylib.Image image;
    public Vector2 position;
    public Vector2 size;
    private Raylib.Texture texture;
    private Vector2 sizeOld;

    public ImageElement(ImageAsset asset, Vector2 position, Vector2 size) {
        this.image = asset.getImage();
        this.position = position;
        this.size = size;
        this.sizeOld = size;

        Raylib.ImageResize(image, (int) size.x, (int) size.y);
        texture = Raylib.LoadTextureFromImage(image);
    }

    @Override
    public void update() {
        // if the size has changed, resize the image
        if (!size.equals(sizeOld)) {
            Raylib.ImageResize(image, (int) size.x, (int) size.y);
            Raylib.UnloadTexture(texture);
            texture = Raylib.LoadTextureFromImage(image);
            sizeOld = size;
        }
    }

    @Override
    public void render() {
        // the origin of the image is in the center of the image
        Raylib.DrawTexture(texture, (int) (position.x - size.x / 2), (int) (position.y - size.y / 2), Jaylib.WHITE);
    }
}
