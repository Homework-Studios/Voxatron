package render.scene.scenes.tdElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.GameManager;
import render.scene.Element;

public class PathDrawElement extends Element {

    public float currentLerp = 0;

    @Override
    public void update() {

    }

    @Override
    public void render() {
        GameManager.instance.pathManager.debugDraw();

        // use time to lerp from 0 to 1
        currentLerp += 0.01f;
        currentLerp %= 1;

        Raylib.Vector3 pos = GameManager.instance.pathManager.getLerp(currentLerp);

        Raylib.DrawSphere(Raylib.Vector3Add(pos, new Raylib.Vector3().y(2)), 2, Jaylib.YELLOW);
    }
}
