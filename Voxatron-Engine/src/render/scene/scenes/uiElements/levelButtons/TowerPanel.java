package render.scene.scenes.uiElements.levelButtons;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import math.Vector2;
import util.UiUtil;

import static com.raylib.Raylib.DrawText;

public class TowerPanel {

    //TODO: replace with tower class
    public String tower;
    public Raylib.Color color;

    public Raylib.Rectangle rectangle = new Raylib.Rectangle();
    public int index = 0;
    public float scroll = 0;
    public Vector2 position;
    public Vector2 size;


    public TowerPanel(String tower, Raylib.Color color) {
        this.tower = tower;
        this.color = color;
    }

    public void render() {
        int textSize = (int) UiUtil.getWidthPercent(3);
        Raylib.DrawRectangleRounded(rectangle, 0.1f, 10, color);
        DrawText(tower, (int) rectangle.x(), (int) rectangle.y(), textSize / 2, Jaylib.WHITE);
    }

    public void update() {
        float width = size.x / 10;
        float finalScroll = -scroll + UiUtil.getWidthPercent(30);
        float finalX = position.x - size.x / 2 + index * (width + UiUtil.getWidthPercent(1)) + finalScroll;
        float finalY = position.y - size.y / 2;

        //move elements down at sides
        finalY += finalX < UiUtil.getWidthPercent(30) ? (-finalX + UiUtil.getWidthPercent(30)) * (-finalX + UiUtil.getWidthPercent(30)) * 0.002 : 0;
        finalY += finalX > UiUtil.getWidthPercent(70) ? (UiUtil.getWidthPercent(70) - finalX) * (UiUtil.getWidthPercent(70) - finalX) * 0.002 : 0;
        rectangle = new Raylib.Rectangle().x(finalX).y(finalY).width(width).height(size.y);
    }
}
