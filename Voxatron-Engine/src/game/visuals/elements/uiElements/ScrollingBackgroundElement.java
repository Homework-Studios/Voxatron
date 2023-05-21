package game.visuals.elements.uiElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.math.Vector2;
import engine.render.scene.Element;

public class ScrollingBackgroundElement extends Element {

    public Raylib.Image image;
    public Vector2 position;
    public Vector2 size;
    public int scrollSpeed;
    private Raylib.Texture texture;
    private Raylib.Texture texture2;

    public ScrollingBackgroundElement(Raylib.Image image, Vector2 position, Vector2 size, int scrollSpeed, boolean flip) {
        this.image = image;
        this.position = position;
        this.size = size;
        this.scrollSpeed = scrollSpeed;

        Raylib.ImageResize(image, (int) size.x, (int) size.y);
        texture = Raylib.LoadTextureFromImage(image);

        if (flip) {
            Raylib.ImageFlipVertical(image);
            // resize the image again because it got flipped
            Raylib.ImageResize(image, (int) size.x, (int) size.y);
            texture2 = Raylib.LoadTextureFromImage(image);
        } else {
            texture2 = texture;
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        double time = Raylib.GetTime();
        Raylib.DrawTexture(texture, (int) (position.x - size.x / 2), (int) (position.y - size.y / 2 + (time * scrollSpeed) % size.y), Jaylib.WHITE);
        Raylib.DrawTexture(texture2, (int) (position.x - size.x / 2), (int) (position.y - size.y / 2 + (time * scrollSpeed) % size.y - size.y), Jaylib.WHITE);

        // if reached the end of the image, flip the image 1 and 2
        if ((time * scrollSpeed) % size.y > size.y - 1) {
            Raylib.Texture old = texture;
            texture = texture2;
            texture2 = old;
        }
    }
}
