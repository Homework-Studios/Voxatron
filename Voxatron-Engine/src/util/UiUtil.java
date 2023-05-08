package util;

import com.raylib.Raylib;

public class UiUtil {

    public static float getWidthPercent(float percent) {
        return Raylib.GetScreenWidth() * (percent / 100);
    }

    public static float getHeightPercent(float percent) {
        return Raylib.GetScreenHeight() * (percent / 100);
    }
}
