package util;

import com.raylib.Jaylib;
import com.raylib.Raylib;

public class ColorPalette {
    public final Raylib.Color foreground;
    public final Raylib.Color background;
    public final Raylib.Color accent;
    public final Raylib.Color hover;
    public final Raylib.Color on;
    public final Raylib.Color off;
    public final Raylib.Color text;

    public ColorPalette(Raylib.Color foreground, Raylib.Color background, Raylib.Color accent, Raylib.Color hover, Raylib.Color on, Raylib.Color off, Raylib.Color text) {
        this.foreground = foreground;
        this.background = background;
        this.accent = accent;
        this.hover = hover;
        this.on = on;
        this.off = off;
        this.text = text;
    }


    public enum ColorPalettes {
        STANDARD_BUTTON(new ColorPalette(Jaylib.LIGHTGRAY, Jaylib.BLANK, Jaylib.LIME, Jaylib.WHITE, Jaylib.GREEN, Jaylib.RED, Jaylib.WHITE)),

        ;
        ColorPalette colorPalette;

        ColorPalettes(ColorPalette colorPalette) {
            this.colorPalette = colorPalette;
        }

        public ColorPalette getColorPalette() {
            return colorPalette;
        }
    }
}
