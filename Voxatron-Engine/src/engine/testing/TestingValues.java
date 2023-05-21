package engine.testing;

import com.raylib.Raylib;

public class TestingValues {

    public static TestingValues instance;

    public int value = 0;

    public TestingValues() {
        instance = this;
    }

    public void update() {
        if (Raylib.IsKeyDown(Raylib.KEY_KP_ADD)) {
            value++;
        }

        if (Raylib.IsKeyDown(Raylib.KEY_KP_SUBTRACT)) {
            value--;
        }
    }

    public int getValue() {
        return value;
    }

}
