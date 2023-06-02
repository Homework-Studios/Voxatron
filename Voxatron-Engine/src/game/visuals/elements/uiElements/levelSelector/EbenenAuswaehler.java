package game.visuals.elements.uiElements.levelSelector;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.math.LerpUtil;
import engine.math.Vector2;
import engine.render.scene.Element;

public abstract class EbenenAuswaehler extends Element implements Runnable {

    private final Raylib.Rectangle[] tabRechtecke;
    private final int luecke = 30;
    private final Raylib.Texture mysterienVorderbild;
    public Vector2 position;
    public Vector2 groesse;
    public EbenenAuswahltTab[] tabs;
    public int schwebenderTab = -1;
    private int scrolling = 0;
    private int zielScrolling = 0;
    private int tabBreite;
    private int tabHoehe;

    public EbenenAuswaehler(Vector2 position, Vector2 groesse, EbenenAuswahltTab[] tabs) {
        this.position = position;
        this.groesse = groesse;
        this.tabs = tabs;

        tabRechtecke = new Raylib.Rectangle[tabs.length];
        generiereRechtecke();

        mysterienVorderbild = Raylib.LoadTextureFromImage(Raylib.GenImageChecked(1000, 1000, 5, 5, Jaylib.WHITE, Jaylib.GRAY));
    }

    public void generiereRechtecke() {
        for (int i = 0; i < tabs.length; i++) {
            tabRechtecke[i] = new Jaylib.Rectangle((int) position.x - (int) groesse.x / 2 + (tabBreite + luecke) * i - scrolling, (int) position.y - (int) groesse.y / 2, tabBreite, tabHoehe);
        }
    }

    @Override
    public void update() {
        tabBreite = (int) groesse.x - luecke;
        tabHoehe = (int) groesse.y;

        // Check if the mouse is hovering over any of the tabs
        for (int i = 0; i < tabRechtecke.length; i++) {
            if (Raylib.CheckCollisionPointRec(Raylib.GetMousePosition(), tabRechtecke[i])) {
                schwebenderTab = i;
                break;
            } else {
                schwebenderTab = -1;
            }
        }

        // if left mouse button is pressed, run the action of the hovered tab
        if (Raylib.IsMouseButtonPressed(Raylib.MOUSE_BUTTON_LEFT)) {
            if (schwebenderTab != -1) {
                // check if the tab is locked
                if (!tabs[schwebenderTab].verschlossen) run();
            }
        }

        // Check if the mouse is scrolling
        int scrollSpeed = 60;
        if (Raylib.GetMouseWheelMove() > 0) {
            zielScrolling += scrollSpeed;
        }

        if (Raylib.GetMouseWheelMove() < 0) {
            zielScrolling -= scrollSpeed;
        }

        // clamp scrolling
        if (zielScrolling < 0) {
            zielScrolling = 0;
        }
        // clamp scrolling at the end
        if (zielScrolling > (tabBreite + luecke) * tabs.length - (int) groesse.x) {
            zielScrolling = (tabBreite + luecke) * tabs.length - (int) groesse.x;
        }

        // smooth scrolling
        scrolling = (int) LerpUtil.lerp(scrolling, zielScrolling, 0.1f);

        // update the Rectangles
        generiereRechtecke();
    }

    @Override
    public void render() {
        // Draw a square with each tab's color next to each other
        // Draw the name of each tab in the center of each square
        // each tab should be centered in the y axis and evenly spaced in the x axis

        // fit to tab size
        int y = (int) position.y - (int) groesse.y / 2;

        for (int i = 0; i < tabs.length; i++) {
            boolean istTabBlockiert = tabs[i].verschlossen;
            int x = (int) position.x - (int) groesse.x / 2 + (tabBreite + luecke) * i - scrolling;

            Raylib.DrawRectangle(x, y, tabBreite, tabHoehe, istTabBlockiert ? tabs[i].farbe : Jaylib.GRAY);

            Raylib.BeginScissorMode(x, y, tabBreite, tabHoehe);

            Raylib.Texture vorderbild = tabs[i].vorderbild;
            Raylib.DrawTexture(istTabBlockiert ? mysterienVorderbild : vorderbild, x + tabBreite / 2 - vorderbild.width() / 2, y + tabHoehe / 2 - vorderbild.height() / 2 - tabs[i].vorderbildY, istTabBlockiert ? Jaylib.DARKGRAY : Jaylib.GRAY);
            Raylib.DrawText(istTabBlockiert ? "???" : tabs[i].name, x + (tabBreite / 2) - (Raylib.MeasureText(istTabBlockiert ? "???" : tabs[i].name, 40) / 2), y + (tabHoehe / 2) - 20, 40, Jaylib.WHITE);

            Raylib.EndScissorMode();


            // Draw a white line under the tab if the mouse is hovering over it
            if (i == schwebenderTab) {
                int linienBreite = 5;
                Raylib.DrawLineEx(new Jaylib.Vector2(x, y + tabHoehe - ((float) linienBreite / 2)), new Jaylib.Vector2(x + tabBreite, y + tabHoehe - ((float) linienBreite / 2)), 5, istTabBlockiert ? Jaylib.RED : Jaylib.WHITE);

                // lerp the thumbnailY up until it reaches 20
                tabs[i].vorderbildY = (int) LerpUtil.lerp(tabs[i].vorderbildY, 50, 0.1f);
            } else {
                // lerp the thumbnailY down until it reaches 0
                tabs[i].vorderbildY = (int) LerpUtil.lerp(tabs[i].vorderbildY, 0, 0.1f);
            }
        }

        // scroll bar slider/display

        int minScrolling = 0;
        int maxScrolling = (tabBreite + luecke) * tabs.length - (int) groesse.x;
        float normalisiertesScrolling = scrolling / (float) maxScrolling;

        int scrollzeileY = (int) position.y + (int) groesse.y / 2 + 20;

        int scrollzeileBreite = (int) groesse.x - 20;

        // draw the darkgray background
        Raylib.DrawRectangle((int) position.x - (int) groesse.x / 2, scrollzeileY, scrollzeileBreite, 20, Jaylib.DARKGRAY);

        // draw the white slider
        Raylib.DrawRectangle((int) position.x - (int) groesse.x / 2 + (int) (normalisiertesScrolling * scrollzeileBreite), scrollzeileY, 20, 20, Jaylib.WHITE);
    }
}
