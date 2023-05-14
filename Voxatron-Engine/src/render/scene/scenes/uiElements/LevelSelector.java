package render.scene.scenes.uiElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import math.LerpUtil;
import math.Vector2;
import render.scene.Element;
import render.scene.scenes.uiElements.levelSelector.LevelSelectorTab;

public abstract class LevelSelector extends Element implements Runnable {

    public Vector2 position;
    public Vector2 size;
    public LevelSelectorTab[] tabs;

    private int scrolling = 0;
    private int targetScrolling = 0;
    private int scrollSpeed = 60;

    private Raylib.Rectangle[] tabRectangles;
    public int hoveringTab = -1;
    private int gap = 30;
    private int tabWidth;
    private int tabHeight;

    private Raylib.Texture misteryThumbnail;
    private int thumbnailY = 0;

    public LevelSelector(Vector2 position, Vector2 size, LevelSelectorTab[] tabs) {
        this.position = position;
        this.size = size;
        this.tabs = tabs;

        tabRectangles = new Raylib.Rectangle[tabs.length];
        generateRectangles();

        misteryThumbnail = Raylib.LoadTextureFromImage(Raylib.GenImageChecked(1000, 1000, 5, 5, Jaylib.WHITE, Jaylib.GRAY));
    }

    public void generateRectangles(){
        for (int i = 0; i < tabs.length; i++) {
            tabRectangles[i] = new Jaylib.Rectangle((int) position.x - (int) size.x / 2 + (tabWidth + gap) * i - scrolling, (int) position.y - (int) size.y / 2, tabWidth, tabHeight);
        }
    }

    @Override
    public void update() {
        tabWidth = (int) size.x - gap;
        tabHeight = (int) size.y;

        // Check if the mouse is hovering over any of the tabs
        for (int i = 0; i < tabRectangles.length; i++) {
            if (Raylib.CheckCollisionPointRec(Raylib.GetMousePosition(), tabRectangles[i])) {
                hoveringTab = i;
                break;
            } else {
                hoveringTab = -1;
            }
        }

        // if left mouse button is pressed, run the action of the hovered tab
        if (Raylib.IsMouseButtonPressed(Raylib.MOUSE_BUTTON_LEFT)) {
            if (hoveringTab != -1) {
                // check if the tab is locked
                if (!tabs[hoveringTab].locked) run();
            }
        }

        // Check if the mouse is scrolling
        if (Raylib.GetMouseWheelMove() > 0) {
            targetScrolling += scrollSpeed;
        }

        if (Raylib.GetMouseWheelMove() < 0) {
            targetScrolling -= scrollSpeed;
        }

        // clamp scrolling
        if (targetScrolling < 0) {
            targetScrolling = 0;
        }
        // clamp scrolling at the end
        if (targetScrolling > (tabWidth + gap) * tabs.length - (int) size.x) {
            targetScrolling = (tabWidth + gap) * tabs.length - (int) size.x;
        }

        // smooth scrolling
        scrolling = (int) LerpUtil.lerp(scrolling, targetScrolling, 0.1f);

        // update the Rectangles
        generateRectangles();
    }

    @Override
    public void render() {
        // Draw a square with each tab's color next to each other
        // Draw the name of each tab in the center of each square
        // each tab should be centered in the y axis and evenly spaced in the x axis

        // fit to tab size
        int y = (int) position.y - (int) size.y / 2;

        for (int i = 0; i < tabs.length; i++) {
            boolean isTabLocked = tabs[i].locked;
            int x = (int) position.x - (int) size.x / 2 + (tabWidth + gap) * i - scrolling;

            Raylib.DrawRectangle(x, y, tabWidth, tabHeight, isTabLocked ? tabs[i].color : Jaylib.GRAY);

            Raylib.BeginScissorMode(x, y, tabWidth, tabHeight);

            Raylib.Texture thumbnail = tabs[i].thumbnail;
            Raylib.DrawTexture(isTabLocked ? misteryThumbnail : thumbnail, x + tabWidth / 2 - thumbnail.width() / 2, y + tabHeight / 2 - thumbnail.height() / 2 - tabs[i].thumbnailY, isTabLocked ? Jaylib.DARKGRAY : Jaylib.GRAY);
            Raylib.DrawText(isTabLocked ? "???" : tabs[i].name, x + (tabWidth / 2) - (Raylib.MeasureText(isTabLocked ? "???" : tabs[i].name, 40) / 2), y + (tabHeight / 2) - 20, 40, Jaylib.WHITE);

            Raylib.EndScissorMode();


            // Draw a white line under the tab if the mouse is hovering over it
            if (i == hoveringTab) {
                int lineThickness = 5;
                Raylib.DrawLineEx(new Jaylib.Vector2(x, y + tabHeight - ((float) lineThickness / 2)), new Jaylib.Vector2(x + tabWidth, y + tabHeight - ((float) lineThickness / 2)), 5, isTabLocked ? Jaylib.RED : Jaylib.WHITE);

                // lerp the thumbnailY up until it reaches 20
                tabs[i].thumbnailY = (int) LerpUtil.lerp(tabs[i].thumbnailY, 50, 0.1f);
            } else {
                // lerp the thumbnailY down until it reaches 0
                tabs[i].thumbnailY = (int) LerpUtil.lerp(tabs[i].thumbnailY, 0, 0.1f);
            }
        }

        // scroll bar slider/display

        int minScrolling = 0;
        int maxScrolling = (tabWidth + gap) * tabs.length - (int) size.x;
        float normalizedScrolling = scrolling / (float) maxScrolling;

        int scrollbarY = (int) position.y + (int) size.y / 2 + 20;

        int scrollbarWidth = (int) size.x - 20;

        // draw the darkgray background
        Raylib.DrawRectangle((int) position.x - (int) size.x / 2, scrollbarY, scrollbarWidth, 20, Jaylib.DARKGRAY);

        // draw the white slider
        Raylib.DrawRectangle((int) position.x - (int) size.x / 2 + (int) (normalizedScrolling * scrollbarWidth), scrollbarY, 20, 20, Jaylib.WHITE);
    }
}
