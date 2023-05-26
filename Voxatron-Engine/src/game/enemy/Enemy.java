package game.enemy;

import engine.math.Vector3;
import engine.render.scene.Element;
import game.GameManager;

public abstract class Enemy extends Element {

    public int health = 10;
    public int maxHealth = 10;

    public float walkSpeed = 1;
    public float positionOnPath = 0;

    public Vector3 position = new Vector3();
    public boolean isAlive = true;

    public Enemy(int health, float walkSpeed) {
        maxHealth = health;
        this.health = health;
        this.walkSpeed = walkSpeed;

    }

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
            kill();
            GameManager.instance.addEnergy(maxHealth);
            GameManager.instance.killEnemy(this);
        }
    }

    public void gameTick() {

    }

    public abstract void kill();

    @Override
    public void update() {
        stepOnPath();
    }

    public void spawnEnemy(Enemy enemy) {
        GameManager.instance.addEnemy(enemy);
        enemy.positionOnPath = positionOnPath;
    }
}
