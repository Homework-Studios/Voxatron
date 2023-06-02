package game.visuals.elements.uiElements.inGame;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.math.LerpUtil;
import engine.math.Vector2;
import engine.util.UiUtils;
import game.GameManager;
import game.tower.Turm;

import static com.raylib.Raylib.IsMouseButtonPressed;
import static com.raylib.Raylib.IsMouseButtonReleased;

public class TurmPanel {

    private final Jaylib.Rectangle bildschirm;
    public Turm.Type turm;
    public Raylib.Rectangle rechteck = new Raylib.Rectangle();
    public int index = 0;
    public float scroll = 0;
    public Vector2 position;
    public Vector2 groesse;
    private boolean verstecken = false;
    private boolean ziehen = false;

    public TurmPanel(Turm.Type typ) {
        this.turm = typ;
        this.bildschirm = new Jaylib.Rectangle(0, 0, Jaylib.GetScreenWidth(), Jaylib.GetScreenHeight());
    }

    public void render() {
        if (verstecken && !ziehen) return;

        int textGroesse = (int) UiUtils.getWidthPercent(2);
        Raylib.DrawRectangleRoundedLines(rechteck, 0.1f, 10, 5, turm.getFarbe());
        int kosten = turm.getKosten();
        if (turm == Turm.Type.ENERGIE_FABRIK) {
            kosten = (int) (kosten * GameManager.getInstance().energieFabriken.size() * GameManager.getInstance().energieFabriken.size() * Math.PI * 0.1f);
        }
        Raylib.DrawText(turm.getName() + "\n" + kosten, (int) rechteck.x(), (int) rechteck.y(), textGroesse / 2, Jaylib.WHITE);

    }

    public void update() {
        // Set the width of the tower panel
        float breite = groesse.x / 10;

        // Calculate the final scroll position of the tower panel
        float finalerScroll = -scroll + UiUtils.getWidthPercent(30);

        // Calculate the final X and Y position of the tower element
        float finalerX = position.x - groesse.x / 2 + index * (breite + UiUtils.getWidthPercent(1)) + finalerScroll;
        float finalerY = position.y - groesse.y / 2;

        // Move elements down at the sides
        finalerY += finalerX < UiUtils.getWidthPercent(30) ? (-finalerX + UiUtils.getWidthPercent(30)) * (-finalerX + UiUtils.getWidthPercent(30)) * 0.002 : 0;
        finalerY += finalerX > UiUtils.getWidthPercent(70) ? (UiUtils.getWidthPercent(70) - finalerX) * (UiUtils.getWidthPercent(70) - finalerX) * 0.002 : 0;

        // Set the rectangle for the tower element
        rechteck = new Raylib.Rectangle().x(finalerX - UiUtils.getWidthPercent(5)).y(finalerY).width(breite).height(groesse.y);

        // Cull the tower element if it is outside the screen
        verstecken = !Jaylib.CheckCollisionBoxes(
                new Jaylib.BoundingBox(
                        new Jaylib.Vector3(),
                        new Jaylib.Vector3().x(bildschirm.width()).y(bildschirm.height())
                ),
                new Jaylib.BoundingBox(
                        new Jaylib.Vector3().x(rechteck.x()).y(rechteck.y()),
                        new Jaylib.Vector3().x(rechteck.width()).y(rechteck.height())
                )
        );

        // check if the mouse is hovering over the tower element
        boolean schweben = Raylib.CheckCollisionPointRec(Raylib.GetMousePosition(), rechteck);

        // if dragging and left mouse button is pressed set dragging to false
        if (ziehen) {
            if (IsMouseButtonReleased(Raylib.MOUSE_BUTTON_LEFT)) {
                ziehen = false;
                GameManager.getInstance().placeTower(turm);
            }
        }

        // if not dragging and left mouse button is pressed and the mouse is hovering over the tower element set dragging to true
        if (!ziehen) {
            if (IsMouseButtonPressed(Raylib.MOUSE_BUTTON_LEFT) && schweben) {
                ziehen = true;
            }
        }

        // if dragging set the position to the mouse position
        if (ziehen) {
            // move the rectangle to the mouse position
            float zielX = Raylib.GetMousePosition().x() - rechteck.width() / 2;
            float zielY = Raylib.GetMousePosition().y() - rechteck.height() / 2;

            // move the rectangle to the mouse position 10%
            rechteck.x(LerpUtil.lerp(rechteck.x(), zielX, 0.3f));
            rechteck.y(LerpUtil.lerp(rechteck.y(), zielY, 0.3f));
        }
    }
}
