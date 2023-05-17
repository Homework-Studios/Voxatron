package render.scene.scenes.uiElements.levelButtons;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.tower.Tower;
import math.Vector2;
import util.UiUtil;

import static com.raylib.Raylib.DrawText;

public class TowerPanel {

    public Tower tower;

    public Raylib.Rectangle rectangle = new Raylib.Rectangle();
    public int index = 0;
    public float scroll = 0;
    public Vector2 position;
    public Vector2 size;

    private Jaylib.Rectangle screen;
    private boolean cull = false;

    public TowerPanel(Tower tower) {
        this.tower = tower;
        this.screen = new Jaylib.Rectangle(0, 0, Jaylib.GetScreenWidth(), Jaylib.GetScreenHeight());
    }

    public void render() {

        if(cull) return;

        int textSize = (int) UiUtil.getWidthPercent(3);
        Raylib.DrawRectangleRoundedLines(rectangle, 0.1f, 10, 5, tower.getColor());
        DrawText(tower.getName(), (int) rectangle.x(), (int) rectangle.y(), textSize / 2, Jaylib.WHITE);
    }

    public void update() {
        float width = size.x / 10;
        float finalScroll = -scroll + UiUtil.getWidthPercent(30);
        float finalX = position.x - size.x / 2 + index * (width + UiUtil.getWidthPercent(1)) + finalScroll;
        float finalY = position.y - size.y / 2;

        //move elements down at sides
        finalY += finalX < UiUtil.getWidthPercent(30) ? (-finalX + UiUtil.getWidthPercent(30)) * (-finalX + UiUtil.getWidthPercent(30)) * 0.002 : 0;
        finalY += finalX > UiUtil.getWidthPercent(70) ? (UiUtil.getWidthPercent(70) - finalX) * (UiUtil.getWidthPercent(70) - finalX) * 0.002 : 0;
        rectangle = new Raylib.Rectangle().x(finalX - UiUtil.getWidthPercent(5)).y(finalY).width(width).height(size.y);

        cull = true;
        if(Jaylib.CheckCollisionBoxes(
                new Jaylib.BoundingBox(
                    new Jaylib.Vector3(),
                    new Jaylib.Vector3().x(screen.width()).y(screen.height())
                ),
                new Jaylib.BoundingBox(
                    new Jaylib.Vector3().x(rectangle.x()).y(rectangle.y()),
                    new Jaylib.Vector3().x(rectangle.width()).y(rectangle.height())
                )
        )){
            cull = false;
        }
    }
}
