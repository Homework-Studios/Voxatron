package engine.util;

import com.raylib.Jaylib;
import com.raylib.Raylib;

public class ColorUtils {

    // Smoothly interpolates between two colors
    // Resultant Color = RGB(A*(1-P)+X*P,B*(1-P)+Y*P,C*(1-P)+Z*P)
    public static Raylib.Color colorFade(Raylib.Color aa, Raylib.Color bb, float t) {
        float r = aa.r() * (1 - t) + bb.r() * t;
        float g = aa.g() * (1 - t) + bb.g() * t;
        float b = aa.b() * (1 - t) + bb.b() * t;
        return new Jaylib.Color((byte) (r / 255), (byte) (g / 255), (byte) (b / 255), (aa.a()));
    }
}
