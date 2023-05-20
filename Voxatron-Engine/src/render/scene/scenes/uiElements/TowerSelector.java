package render.scene.scenes.uiElements;

import com.raylib.Raylib;
import game.tower.Tower;
import math.LerpUtil;
import math.Vector2;
import render.scene.Element;
import util.UiUtil;

public abstract class TowerSelector extends Element implements Runnable {

    private final Vector2 position;
    private final Vector2 size;
    private final TowerPanel[] towers;
    private int scroll = 0;
    private int targetScrolling = 0;
    private float hoverOffset;
    private Raylib.Rectangle collisions;

    /**
     * IMPORTANT: minimum of three towers in towers array (scrolling issues)
     */
    public TowerSelector(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
        this.towers = Tower.Type.panels;
    }

    @Override
    public void update() {
        // hover check
        collisions = new Raylib.Rectangle().x(position.x - size.x / 2).y(position.y - size.y / 2 - hoverOffset).width(size.x).height(size.y + UiUtil.getHeightPercent(5));
        boolean hover = Raylib.CheckCollisionPointRec(Raylib.GetMousePosition(), collisions);


        // Check if the mouse is scrolling
        final int scrollSpeed = 60;
        if (Raylib.GetMouseWheelMove() > 0 && hover) {
            targetScrolling += scrollSpeed;
        }

        if (Raylib.GetMouseWheelMove() < 0 && hover) {
            targetScrolling -= scrollSpeed;
        }

        //clamp target scrolling
        if (targetScrolling < 0) {
            targetScrolling = 0;
        }

        float towerWidth = size.x / 10;
        float maxScroll = (towers.length - 3) * (towerWidth + UiUtil.getWidthPercent(1));
        if (targetScrolling > maxScroll) {
            targetScrolling = (int) maxScroll;
        }

        // smooth scrolling
        scroll = (int) LerpUtil.lerp(scroll, targetScrolling, 0.1f);

        //move up when scrolling
        float targetHoverOffset = hover ? UiUtil.getHeightPercent(20) : 0;

        // update effective position
        hoverOffset = LerpUtil.lerp(hoverOffset, targetHoverOffset, 0.1f);
        Vector2 effectivePosition = new Vector2(position.x, position.y - hoverOffset);

        // update tabs
        for (int i = 0; i < towers.length; i++) {
            TowerPanel tower = towers[i];
            tower.index = i;
            tower.scroll = scroll;
            tower.position = effectivePosition;
            tower.size = size;
            tower.update();
        }
    }

    @Override
    public void render() {
        for (TowerPanel tower : towers) {
            tower.render();
        }
    }
}
