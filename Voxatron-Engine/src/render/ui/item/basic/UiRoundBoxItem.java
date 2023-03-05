package render.ui.item.basic;

import com.raylib.Raylib;
import math.Vector2;
import render.task.ui.UiRoundBoxRenderTask;
import render.ui.UIScreen;
import render.ui.box.BoxFilter;
import render.ui.item.UIItem;
import util.BoxLayoutUtil;

import static com.raylib.Jaylib.GREEN;
import static com.raylib.Jaylib.RED;
import static com.raylib.Raylib.DrawCircle;
import static com.raylib.Raylib.DrawLine;

public class UiRoundBoxItem extends UIItem {

    public Vector2 sPosition;
    public Vector2 ePosition;
    public float v;
    public int i;
    public Raylib.Color color;
    public BoxFilter filter;

    public UiRoundBoxItem(Vector2 sPosition, Vector2 ePosition, float v, int i, Raylib.Color color, BoxFilter filter) {
        this.sPosition = sPosition;
        this.ePosition = ePosition;
        this.v = v;
        this.i = i;
        this.color = color;
        this.filter = filter;

        addTask(new UiRoundBoxRenderTask(sPosition, ePosition, v, i, color));
    }

    @Override
    public void update() {

        Vector2 posOnScreen = BoxLayoutUtil.applyFilter(screen.position, screen.size, filter);
        Vector2 middleOfRect = sPosition.add(ePosition).divide(new Vector2(2, 2));

        ((UiRoundBoxRenderTask)tasks.get(0)).position = sPosition.add(posOnScreen).subtract(middleOfRect);
        ((UiRoundBoxRenderTask)tasks.get(0)).size = ePosition;
        ((UiRoundBoxRenderTask)tasks.get(0)).v = v;
        ((UiRoundBoxRenderTask)tasks.get(0)).i = i;
        ((UiRoundBoxRenderTask)tasks.get(0)).color = color;
    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {
        // add the position to the start position
        sPosition = sPosition.add(position);
        // add the position to the end position
        ePosition = ePosition.add(position);
    }
}
