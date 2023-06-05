package engine.debug;

import com.raylib.Raylib;

import java.util.ArrayList;
import java.util.List;

import static com.raylib.Raylib.DrawText;

public class DebugDraw {

    public static final int MAX_STRINGS = 100;
    public static DebugDraw instance;

    public List<String> debugStrings = new ArrayList<>();

    public DebugDraw() {
        instance = this;
    }

    public void print(String string) {
        debugStrings.add(string);

        if (debugStrings.size() > MAX_STRINGS) {
            debugStrings.remove(0);
        }
    }

    public void render() {
        if (debugStrings.size() == 0)
            return;

        String[] stringsReversed = new String[debugStrings.size()];
        debugStrings.toArray(stringsReversed);

        for (int i = 0; i < stringsReversed.length / 2; i++) {
            String temp = stringsReversed[i];
            stringsReversed[i] = stringsReversed[stringsReversed.length - i - 1];
            stringsReversed[stringsReversed.length - i - 1] = temp;
        }

        // Display the strings in the top left corner like unreal engine using Jaylib
        int offset = 0;

        for (String string : stringsReversed) {
            if (offset < MAX_STRINGS) {
                DrawText(string, 10, offset * 20 + 10, 20, Raylib.ColorFromHSV(30, 255, 200));
                offset++;
            }
        }
    }
}
