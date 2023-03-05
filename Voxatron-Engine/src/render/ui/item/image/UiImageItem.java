package render.ui.item.image;

import com.raylib.Raylib;
import math.Vector2;
import render.task.ui.UiTextureRenderTask;
import render.ui.box.BoxFilter;
import render.ui.item.UIItem;
import util.BoxLayoutUtil;

import static com.raylib.Jaylib.*;

public class UiImageItem extends UIItem {

    public Raylib.Texture texture;
    public Vector2 position;
    public BoxFilter filter;

    public UiImageItem(Raylib.Texture texture, Vector2 position, BoxFilter filter) {
        this.texture = texture;
        this.position = position;
        this.filter = filter;

        addTask(new UiTextureRenderTask(position, texture));
    }

    @Override
    public void update() {
        // Make the texture centered on the position
        int textureWidth = texture.width();
        int textureHeight = texture.height();

        Vector2 posOnScreen = BoxLayoutUtil.applyFilter(screen.position, screen.size, filter);
        Vector2 movedPosition = position.add(posOnScreen);

        ((UiTextureRenderTask)tasks.get(0)).position = movedPosition.subtract(new Vector2(textureWidth * 0.5f, textureHeight * 0.5f));
    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {
        this.position = position.add(this.position);
    }
}
