package math;

import com.raylib.Raylib;

public class Hover {

    public static boolean isMouseOver(Vector2 areaPosition, Vector2 areaSize) {
        areaPosition = areaPosition.subtract(areaSize.divide(new Vector2(2, 2)));
        return Raylib.GetMouseX() > areaPosition.x && Raylib.GetMouseX() < areaPosition.x + areaSize.x && Raylib.GetMouseY() > areaPosition.y && Raylib.GetMouseY() < areaPosition.y + areaSize.y;
    }
}
