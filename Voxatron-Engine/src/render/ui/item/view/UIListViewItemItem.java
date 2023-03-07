package render.ui.item.view;

import math.Vector2;
import render.ui.item.UIItem;

public abstract class UIListViewItemItem extends UIItem implements UIListViewItemInterface {

    public Vector2 position;
    public Vector2 relativePosition;
    public Vector2 size;

    public UIListViewItemItem(Vector2 relativePosition, Vector2 size) {
        this.relativePosition = relativePosition;
        this.size = size;
    }

    @Override
    public void update(Vector2 position) {
        this.position = position.add(relativePosition);
    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {

    }

    @Override
    public void update() {

    }
}
