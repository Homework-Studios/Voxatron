package engine.util;

import com.raylib.Raylib;
import engine.math.Vector2;

import static com.raylib.Raylib.DrawRectangleRoundedLines;

public class UiUtils {

    public static float getWidthPercent(float percent) {
        return Raylib.GetScreenWidth() * (percent / 100);
    }

    public static float getHeightPercent(float percent) {
        return Raylib.GetScreenHeight() * (percent / 100);
    }

    public static void scaleButtonRectangle(Vector2 size, Vector2 position, float scaleFactor, Raylib.Color color, Raylib.Rectangle buttonRectangle) {
        float width = size.x * scaleFactor;
        float height = size.y * scaleFactor;
        float widthDiff = size.x - width;
        float heightDiff = size.y - height;
        if (widthDiff > UiUtils.getWidthPercent(1)) {
            width = size.x - UiUtils.getWidthPercent(1);
        }
        if (heightDiff > UiUtils.getHeightPercent(1)) {
            height = size.y - UiUtils.getHeightPercent(1);
        }
        float x = position.x - width / 2;
        float y = position.y - height / 2;
        buttonRectangle = new Raylib.Rectangle().x(x).y(y).width(width).height(height);
        DrawRectangleRoundedLines(buttonRectangle, 0.3f, 5, 5, color);
    }
}
