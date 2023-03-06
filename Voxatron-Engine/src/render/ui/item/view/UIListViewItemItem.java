package render.ui.item.view;

import math.Vector2;
import render.ui.item.UIItem;

public abstract class UIListViewItemItem extends UIItem {
    public Vector2 position;
    public Vector2 size;

    public Vector2 effectivePosition;

    public UIListViewItemItem(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {
        this.position = position.add(this.position);
    }
}
