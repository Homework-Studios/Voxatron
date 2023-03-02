package window;


import com.raylib.Raylib;

import static com.raylib.Raylib.*;

public class Window {

    public boolean devMode = true;

    public Window() {
    }

    public void init() {

        // Init window
        InitWindow(GetScreenWidth(), GetScreenHeight(), "Voxatron");
        SetTargetFPS(60);
        //Todo: Fix image not loading correctly
        SetWindowIcon(LoadImage("icon.png"));

        while (true) {
            if (WindowShouldClose()) {
                break;
            }

            BeginDrawing();
            ClearBackground(ColorFromHSV(0, 0, 0));
            DrawText("Hello World!", 190, 200, 20, ColorFromHSV(255, 255, 255));
            EndDrawing();
        }
    }

}
