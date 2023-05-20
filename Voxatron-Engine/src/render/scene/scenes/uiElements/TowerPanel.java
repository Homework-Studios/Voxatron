package render.scene.scenes.uiElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.GameManager;
import game.tower.Tower;
import math.LerpUtil;
import math.Vector2;
import util.UiUtil;

import static com.raylib.Raylib.IsMouseButtonPressed;

public class TowerPanel {

    private final Jaylib.Rectangle screen;
    public Tower.Type tower;
    public Raylib.Rectangle rectangle = new Raylib.Rectangle();
    public int index = 0;
    public float scroll = 0;
    public Vector2 position;
    public Vector2 size;
    private boolean cull = false;
    private boolean hover = false;
    private boolean dragging = false;

    public TowerPanel(Tower.Type type) {
        this.tower = type;
        this.screen = new Jaylib.Rectangle(0, 0, Jaylib.GetScreenWidth(), Jaylib.GetScreenHeight());
    }

    public void render() {
        if (cull && !dragging) return;

        int textSize = (int) UiUtil.getWidthPercent(3);
        Raylib.DrawRectangleRoundedLines(rectangle, 0.1f, 10, 5, tower.getColor());
        Raylib.DrawText(tower.getName() + System.lineSeparator() + tower.getCost(), (int) rectangle.x(), (int) rectangle.y(), textSize / 2, Jaylib.WHITE);

    }

    public void update() {
        // Set the width of the tower panel
        float width = size.x / 10;

        // Calculate the final scroll position of the tower panel
        float finalScroll = -scroll + UiUtil.getWidthPercent(30);

        // Calculate the final X and Y position of the tower element
        float finalX = position.x - size.x / 2 + index * (width + UiUtil.getWidthPercent(1)) + finalScroll;
        float finalY = position.y - size.y / 2;

        // Move elements down at the sides
        finalY += finalX < UiUtil.getWidthPercent(30) ? (-finalX + UiUtil.getWidthPercent(30)) * (-finalX + UiUtil.getWidthPercent(30)) * 0.002 : 0;
        finalY += finalX > UiUtil.getWidthPercent(70) ? (UiUtil.getWidthPercent(70) - finalX) * (UiUtil.getWidthPercent(70) - finalX) * 0.002 : 0;

        // Set the rectangle for the tower element
        rectangle = new Raylib.Rectangle().x(finalX - UiUtil.getWidthPercent(5)).y(finalY).width(width).height(size.y);

        // Cull the tower element if it is outside the screen
        cull = !Jaylib.CheckCollisionBoxes(
                new Jaylib.BoundingBox(
                        new Jaylib.Vector3(),
                        new Jaylib.Vector3().x(screen.width()).y(screen.height())
                ),
                new Jaylib.BoundingBox(
                        new Jaylib.Vector3().x(rectangle.x()).y(rectangle.y()),
                        new Jaylib.Vector3().x(rectangle.width()).y(rectangle.height())
                )
        );

        // check if the mouse is hovering over the tower element
        hover = Raylib.CheckCollisionPointRec(Raylib.GetMousePosition(), rectangle);

        // if dragging and left mouse button is pressed set dragging to false
        if (dragging) {
            if (IsMouseButtonPressed(Raylib.MOUSE_BUTTON_LEFT)) {
                dragging = false;
                GameManager.getInstance().placeTower(tower);
            }
        }

        // if not dragging and left mouse button is pressed and the mouse is hovering over the tower element set dragging to true
        if (!dragging) {
            if (IsMouseButtonPressed(Raylib.MOUSE_BUTTON_LEFT) && hover) {
                dragging = true;
            }
        }

        // if dragging set the position to the mouse position
        if (dragging) {
            // move the rectangle to the mouse position
            float targetX = Raylib.GetMousePosition().x() - rectangle.width() / 2;
            float targetY = Raylib.GetMousePosition().y() - rectangle.height() / 2;

            // move the rectangle to the mouse position 10%
            rectangle.x(LerpUtil.lerp(rectangle.x(), targetX, 0.3f));
            rectangle.y(LerpUtil.lerp(rectangle.y(), targetY, 0.3f));
        }
    }
}
