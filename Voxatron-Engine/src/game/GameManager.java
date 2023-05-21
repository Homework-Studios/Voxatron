package game;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.enemy.Enemy;
import game.enemy.enemies.BlueCube;
import game.enemy.enemies.RedCube;
import game.enemy.enemies.TestTank;
import game.tower.EnergyConsumer;
import game.tower.Tower;
import render.scene.Element;
import render.scene.InGameScene;
import render.scene.SceneManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public abstract class GameManager extends Element {

    public static GameManager instance;
    public final List<Tower> energyFactories = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    public PathManager pathManager = new PathManager();
    public boolean roundHasStarted = false;
    public int enemyToSpawn = 0;
    public int enemySpawned = 0;
    public int enemyKilled = 0;
    public int gameHeartbeat = 0;
    public int gameHeartbeatMax = 25;
    public int enemySpawnRate = 3;
    private int round = 1;
    private boolean roundEnded = true;
    private boolean gameShouldEnd = false;
    private int energy = 999999;
    private int lives = 100;
    private List<Tower> towers = new ArrayList<>();

    public GameManager() {
        instance = this;
        Tower.gameManager = this;
    }

    public static GameManager getInstance() {
        return instance;
    }

    public void placeTower(Tower.Type type) {
        if (!(SceneManager.instance.getActiveScene() instanceof InGameScene)) return;
        InGameScene scene = (InGameScene) SceneManager.instance.getActiveScene();
        boolean buy;
        if (!canDrop(scene.getDropPosition())) return;
        if (type == Tower.Type.ENERGY_FACTORY) {
            buy = buy((int) (energyFactories.size() * energyFactories.size() * Math.PI * 0.1f));
        } else {
            buy = buy(type.getCost());
        }
        if (buy) {
            Tower tower = type.createTower();
            assert tower != null;
            tower.setPosition(scene.getDropPosition());
            scene.addElement3d(tower);

            if (tower.type == Tower.Type.ENERGY_FACTORY) {
                addEnergyFactory(tower);
            } else {
                addTower(tower);
            }
        }
    }

    private boolean canDrop(Raylib.Vector3 location) {
        return location.x() != 0 && location.y() != 0 && location.z() != 0;
    }

    public void setGameShouldEnd(boolean gameShouldEnd) {
        this.gameShouldEnd = gameShouldEnd;
    }

    public void setRoundEnded(boolean roundEnded) {
        this.roundEnded = roundEnded;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public void setTowers(List<Tower> towers) {
        this.towers = towers;
    }

    public void addTower(Tower tower) {
        towers.add(tower);
    }

    public void addEnergyFactory(Tower tower) {
        energyFactories.add(tower);
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void nextRound() {
        round++;
        roundEnded = false;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
    }

    public void removeMoney(int energy) {
        this.energy -= energy;
    }

    public boolean buy(int cost) {
        if (energy >= cost) {
            removeMoney(cost);
            return true;
        }
        return false;
    }

    public boolean sell(int cost) {
        addEnergy((int) (cost * 0.75f));
        return true;
    }

    public void start() {
        round = 1;
        roundEnded = false;

        //run game tick 20 times per second
        Timer timer = new Timer();
        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                if (gameShouldEnd || Raylib.WindowShouldClose()) timer.cancel();
                gameTick();
            }
        }, 0, 50);

        pathManager = new PathManager();

        // Raylib.Vector3[] last arguement
        pathManager.genPath(new Raylib.Vector3().x(-150), new Raylib.Vector3().x(150), new Raylib.Vector3[]{
                new Raylib.Vector3().z(-150),
                new Raylib.Vector3().z(-50).x(-50),
                new Raylib.Vector3().z(50).x(50),
                new Raylib.Vector3().z(150),
        });
    }

    @Override
    public void update() {
        uiUpdate();
    }

    public void enemyLogicUpdate() {
        if (Raylib.IsKeyPressed(Jaylib.KEY_T)) {
            addEnemy(new TestTank());
        }
        if (!roundHasStarted) {
            enemySpawned = 0;
            enemyKilled = 0;
            enemyToSpawn = enemySpawnRate * round;
            roundHasStarted = true;
        }

        if (gameHeartbeat >= gameHeartbeatMax) {

            // randomly do nothing
            if (Math.random() > 0.5) {
                gameHeartbeat = 0;
                return;
            }

            gameHeartbeat = 0;
            if (enemySpawned < enemyToSpawn) {
                enemySpawned++;
                if (Math.random() > 0.5) addEnemy(new BlueCube());
                else
                    addEnemy(new RedCube());
            }
        }
        gameHeartbeat++;

        if (roundHasStarted && enemySpawned == enemyToSpawn && enemyKilled == enemyToSpawn) {
            roundHasStarted = false;
            nextRound();
        }
    }

    public void addEnemy(Enemy enemy) {
        enemy.position = pathManager.currentPath.getLerp(0);
        parentScene.addElement3d(enemy);
        enemies.add(enemy);
    }

    public Enemy getClosetEnemy(Raylib.Vector3 position, float range) {
        Enemy closest = null;
        float closestDistance = Float.MAX_VALUE;
        for (Enemy enemy : enemies) {
            float distance = Raylib.Vector3Distance(position, enemy.position);
            if (distance < closestDistance) {
                closest = enemy;
                closestDistance = distance;
            }
        }

        if (closestDistance > range) return null;

        return closest;
    }

    public void killEnemy(Enemy enemy) {
        enemies.remove(enemy);
        parentScene.removeElement3d(enemy);
        enemyKilled++;
    }

    public EnergyConsumer[] getClosestEnergyConsumers(Raylib.Vector3 position, float range) {
        List<EnergyConsumer> closest = new ArrayList<>();

        // loop over all the energy consumers
        for (Tower tower : towers) {
            if (tower instanceof EnergyConsumer) {
                float distance = Raylib.Vector3Distance(position, tower.position);
                if (distance < range) {
                    closest.add((EnergyConsumer) tower);
                }
            }
        }

        return closest.toArray(new EnergyConsumer[0]);
    }

    /**
     * runs every 50ms (20 times per second)
     */
    public void gameTick() {
        if (gameShouldEnd) System.out.println("Game Over");
        if (roundEnded) {
            nextRound();
            System.out.println("Round " + round + " started");
        }
        // hands over the Game Tick to towers
        towers.forEach(Tower::gameTick);
        energyFactories.forEach(Tower::gameTick);
    }

    public abstract void uiUpdate();
}
