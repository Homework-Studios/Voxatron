package game.enemy;

import com.raylib.Raylib;
import game.GameManager;
import render.scene.Element;

public abstract class Enemy extends Element {

    public float walkSpeed = 1;

    public float positionOnPath = 0;

    public Raylib.Vector3 position = new Raylib.Vector3();

    public void stepOnPath() {
        positionOnPath += (walkSpeed * 0.0001f);

        // modulo 1 to keep it in the range of 0 to 1
        // TODO: Remove this
        positionOnPath %= 1;

        position = GameManager.instance.pathManager.getLerp(positionOnPath);
    }
}
