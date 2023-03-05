package debug;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.raylib.Jaylib.BLUE;
import static com.raylib.Raylib.DrawText;

public class DebugDraw {

    public static final int MAX_STRINGS = 100;
    public static final int MAX_DURATION = 800;

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

    public long timePassed = 0;
    public long lastTime = System.currentTimeMillis();

    public void render() {
        if(debugStrings.size() == 0){
            timePassed = 0;
            return;
        }

        // every MAX_DURATION milliseconds, remove the oldest string
        if (timePassed > MAX_DURATION && debugStrings.size() > 0) {
            debugStrings.remove(0);
            lastTime = System.currentTimeMillis();
            timePassed = 0;
        }

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
                DrawText(string, 10, offset * 20 + 10, 20, BLUE);
                offset++;
            }
        }

        timePassed = System.currentTimeMillis() - lastTime;
    }
}
