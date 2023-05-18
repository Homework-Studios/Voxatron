package game;

import com.raylib.Raylib;
import game.tower.Tower;
import render.scene.Element;
import render.scene.InGameScene;
import render.scene.SceneManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public abstract class GameManager extends Element {

    public static GameManager instance;
    private int round = 0;
    private boolean roundEnded = true;
    private boolean gameShouldEnd = false;
    private int energy = 500;
    private int lives = 100;
    private List<Tower> towers = new ArrayList<>();

    public PathManager pathManager = new PathManager();

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
        if (canDrop(scene.getDropPosition()) && buy(type.getCost())) {
            Tower tower = type.createTower();
            assert tower != null;
            tower.setLocation(scene.getDropPosition());
            scene.addElement3d(tower);
            addTower(tower);
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

    public void addMoney(int money) {
        this.energy += money;
    }

    public void removeMoney(int money) {
        this.energy -= money;
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
        energy = 500;

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

    /**
     * runs every 50ms (20 times per second)
     */
    public void gameTick() {
        if (gameShouldEnd) System.out.println("Game Over");
        if (roundEnded) {
            nextRound();
            System.out.println("Round " + round + " started");
        }
        //TODO: remove this and make a normal way to get money
        energy++;
    }

    public abstract void uiUpdate();
}
