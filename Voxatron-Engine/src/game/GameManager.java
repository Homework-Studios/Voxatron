package game;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.math.Vector3;
import engine.render.scene.Element;
import engine.render.scene.InGameScene;
import engine.render.scene.SceneManager;
import game.enemy.Enemy;
import game.enemy.enemies.*;
import game.tower.EnergyConsumer;
import game.tower.Tower;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public abstract class GameManager extends Element {

    public static GameManager instance;
    public static float gameSpeed = 1;
    public final List<Tower> energyFactories = new ArrayList<>();
    public final List<Enemy> enemies = new ArrayList<>();
    private final List<Enemy> enemyList = new ArrayList<>();
    private final List<Enemy> spawnableEnemies = new ArrayList<>();
    public PathManager pathManager;
    public int enemyKilled = 0;
    public int spawnWeight;
    private int round = 0;
    private boolean roundEnded = true;
    private boolean gameShouldEnd = false;
    private int energy = 500000;
    private int lives = 100;
    private List<Tower> towers = new ArrayList<>();
    private int remainingWeight;

    public GameManager() {
        instance = this;
        Tower.gameManager = this;
        pathManager = new PathManager();

        enemyList.addAll(List.of(new Enemy[]{new RedCube(), new BlueCube(), new GreenCube(), new YellowCube(), new PinkCube(), new Sphere()}));
        spawnableEnemies.add(new RedCube());
    }

    public static GameManager getInstance() {
        return instance;
    }

    public void placeTower(Tower.Type type) {
        InGameScene scene;
        if (!(SceneManager.instance.getActiveScene() instanceof InGameScene)) return;

        scene = ((InGameScene) SceneManager.instance.getActiveScene());
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

    private boolean canDrop(Vector3 location) {
        return location.x != 0 && location.y != 0 && location.z != 0;
    }

    /**
     * Difficulty Scaling Function
     * <p>
     * <30:  0,83x
     * <p>
     * >30:  (20-x)^2*0,05+20
     */
    public int getDifficulty() {
        return (round < 30 ? (int) (round * 0.83f) : (int) ((20 - round) * (20 - round) * 0.05f + 20)) + 1;
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

    public int getEnergy() {
        return energy;
    }

    public int getLives() {
        return lives;
    }

    public void removeLives(int lives) {
        this.lives -= lives;
    }

    public void nextRound() {
        round++;
        roundEnded = false;
        spawnableEnemies.clear();
        remainingWeight = getDifficulty() + 3;
        spawnWeight = 0;
        for (Enemy enemy : enemyList) {
            if (enemy.weight <= getDifficulty()) {
                spawnableEnemies.add(enemy);
            }
        }
//        Collections.reverse(spawnableEnemies);
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
        }, 0, (long) (50 / gameSpeed));
    }

    @Override
    public void update() {
        uiUpdate();
        if (Raylib.IsKeyPressed(Jaylib.KEY_T)) {
            nextRound();
        }
    }

    public void enemyLogicUpdate() {
        spawnWeight += Math.sqrt(round * 3);
        if (spawnWeight <= 50) return;
        Enemy enemyToSpawn = null;
        for (Enemy enemy : spawnableEnemies) {
            if (enemy.weight <= remainingWeight) {
                if (enemyToSpawn == null || enemy.weight > enemyToSpawn.weight) {
                    enemyToSpawn = enemy;
                }
            }
        }
        if (enemyToSpawn != null) {
            addEnemy(getNewEnemy(enemyToSpawn));
            spawnWeight -= enemyToSpawn.weight * 20;
            remainingWeight -= enemyToSpawn.weight;
        }
        if (remainingWeight == 0 && enemies.size() == 0) nextRound();
    }

    public Enemy getNewEnemy(Enemy enemy) {
        if (enemy.getClass().equals(RedCube.class)) {
            return new RedCube();
        } else if (enemy.getClass().equals(BlueCube.class)) {
            return new BlueCube();
        } else if (enemy.getClass().equals(GreenCube.class)) {
            return new GreenCube();
        } else if (enemy.getClass().equals(YellowCube.class)) {
            return new YellowCube();
        } else if (enemy.getClass().equals(PinkCube.class)) {
            return new PinkCube();
        } else if (enemy.getClass().equals(Sphere.class)) {
            return new Sphere();
        }
        return null;
    }

    public void addEnemy(Enemy enemy) {
        enemy.position = pathManager.currentPath.getTravel(0);
        parentScene.addElement3d(enemy);
        enemies.add(enemy);
    }

    public Enemy[] getEnemiesInRangeFromPosition(Vector3 position, float range) {
        List<Enemy> enemiesInRange = new ArrayList<>();
        for (Enemy enemy : getEnemies()) {
            float distance = position.distance(enemy.position);
            if (distance < range) {
                enemiesInRange.add(enemy);
            }
        }
        return enemiesInRange.toArray(new Enemy[0]);
    }

    public Enemy getClosestEnemy(Vector3 position, float range) {
        Enemy closest = null;
        float closestDistance = Float.MAX_VALUE;
        for (Enemy enemy : getEnemies()) {
            float distance = position.distance(enemy.position);
            if (distance < closestDistance) {
                closest = enemy;
                closestDistance = distance;
            }
        }

        if (closestDistance > range) return null;

        return closest;
    }

    public Enemy getFurthestEnemyInRange(Vector3 position, float range) {
        Enemy furtherst = null;
        float furthestOnPath = 0;
        for (Enemy enemy : getEnemies()) {
            float distance = enemy.positionOnPath;
            if (distance > furthestOnPath && enemy.position.distance(position) <= range) {
                furtherst = enemy;
                furthestOnPath = distance;
            }
        }
        return furtherst;
    }

    public void killEnemy(Enemy enemy) {
        enemies.remove(enemy);
        parentScene.removeElement3d(enemy);
        enemyKilled++;
    }

    public EnergyConsumer[] getClosestEnergyConsumers(Vector3 position, float range) {
        List<EnergyConsumer> closest = new ArrayList<>();

        // loop over all the energy consumers
        for (Tower tower : towers) {
            if (tower instanceof EnergyConsumer) {

                float distance = position.distance(tower.position);

                if (distance < range) {
                    closest.add((EnergyConsumer) tower);
                }
            }
        }

        return closest.toArray(new EnergyConsumer[0]);
    }

    /**
     * runs every 50ms / gameSpeed (20 times per second * gameSpeed)
     */
    public void gameTick() {
        if (gameShouldEnd) System.out.println("Game Over");
        enemyLogicUpdate();
        if (roundEnded) {
            nextRound();
            System.out.println("Round " + round + " started");
        }
        // hands over the Game Ticks
        towers.forEach(Tower::gameTick);
        energyFactories.forEach(Tower::gameTick);
        enemies.forEach(Enemy::gameTick);
    }

    public abstract void uiUpdate();

    public List<Enemy> getEnemies() {
        return new ArrayList<>(enemies);
    }

}
