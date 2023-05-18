package render.scene;

import com.raylib.Raylib;

public abstract class InGameScene extends Scene {
    public abstract Raylib.Vector3 getDropPosition();

}
