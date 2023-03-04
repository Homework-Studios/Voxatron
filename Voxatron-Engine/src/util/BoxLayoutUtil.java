package util;

import math.Vector2;
import render.ui.box.BoxFiler;

public class BoxLayoutUtil {

    public static Vector2 applyFilter(Vector2 a, Vector2 b, BoxFiler filer){
        switch (filer){
            case TOP_LEFT:
                return new Vector2(a.x, a.y);
            case TOP_CENTER:
                return new Vector2(a.x + (b.x / 2), a.y);
            case TOP_RIGHT:
                return new Vector2(a.x + b.x, a.y);
            case CENTER_LEFT:
                return new Vector2(a.x, a.y + (b.y / 2));
            case CENTER:
                return new Vector2(a.x + (b.x / 2), a.y + (b.y / 2));
            case CENTER_RIGHT:
                return new Vector2(a.x + b.x, a.y + (b.y / 2));
            case BOTTOM_LEFT:
                return new Vector2(a.x, a.y + b.y);
            case BOTTOM_CENTER:
                return new Vector2(a.x + (b.x / 2), a.y + b.y);
            case BOTTOM_RIGHT:
                return new Vector2(a.x + b.x, a.y + b.y);
        }
        return new Vector2();
    }

}
