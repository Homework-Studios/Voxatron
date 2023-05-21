package game.enemy;

import com.raylib.Raylib;
import game.GameManager;
import math.Vector3;
import render.scene.Element;

public abstract class Enemy extends Element {

    public int health = 10;

    public float walkSpeed = 1;
    public float positionOnPath = 0;

    public Vector3 position = new Vector3();
    public boolean isAlive = true;

    //TODO: make more modular
    public void stepOnPath() {
        positionOnPath += (walkSpeed * 0.0001f);

        // modulo 1 to keep it in the range of 0 to 1
        // TODO: Remove this
        positionOnPath %= 1;

        position = GameManager.instance.pathManager.getLerp(positionOnPath);
    }

    public void damage(int damage) {
        health -= damage;
        if (health <= 0) {
            isAlive = false;
            GameManager.instance.addEnergy(getEnergyGainOnKill());
            GameManager.instance.killEnemy(this);
        }
    }

    public abstract int getEnergyGainOnKill();
}
