package render.ui.item.basic;

import com.raylib.Raylib;
import math.Vector2;
import render.task.ui.UIRoundBoxRenderTask;
import render.ui.box.BoxFilter;
import render.ui.item.UIItem;
import util.BoxLayoutUtil;

public class UIRoundBoxItem extends UIItem {

    public Vector2 sPosition;
    public Vector2 ePosition;
    public float v;
    public int i;
    public Raylib.Color color;
    public BoxFilter filter;

    public UIRoundBoxItem(Vector2 sPosition, Vector2 ePosition, float v, int i, Raylib.Color color, BoxFilter filter) {
        this.sPosition = sPosition;
        this.ePosition = ePosition;
        this.v = v;
        this.i = i;
        this.color = color;
        this.filter = filter;

        addTask(new UIRoundBoxRenderTask(sPosition, ePosition, v, i, color));
    }

    @Override
    public void update() {

        Vector2 posOnScreen = BoxLayoutUtil.applyFilter(screen.position, screen.size, filter);
        Vector2 middleOfRect = sPosition.add(ePosition).divide(new Vector2(2, 2));

        ((UIRoundBoxRenderTask) tasks.get(0)).position = sPosition.add(posOnScreen).subtract(middleOfRect);
        ((UIRoundBoxRenderTask) tasks.get(0)).size = ePosition;
        ((UIRoundBoxRenderTask) tasks.get(0)).v = v;
        ((UIRoundBoxRenderTask) tasks.get(0)).i = i;
        ((UIRoundBoxRenderTask) tasks.get(0)).color = color;
    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {
        // add the position to the start position
        sPosition = sPosition.add(position);
        // add the position to the end position
        ePosition = ePosition.add(position);
    }
}
