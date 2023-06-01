package game.tower.towers.singletarget;

import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
import engine.math.Vector3;
import game.GameManager;
import game.enemy.Enemy;
import game.tower.EnergyConsumer;

/**
 * concept:
 * <p>
 * - a tower that shoots phaser beams
 * <p>
 * - single target tower
 * <p>
 * - deals damage over time
 * <p>
 * - damage is increased the longer the beam is on the target
 * <p>
 * - make as gimmicky as possible
 */
public class LaserCanon extends EnergyConsumer {
    private final Raylib.Model base;
    private final Raylib.Model canon;
    int consume = 1;
    int fired = 0;
    Enemy lastTarget = null;
    private double hover;

    //TODO: make tank damager
    public LaserCanon() {
        super(Type.LASER_CANON);
        ModelAsset modelAsset = new AssetManager<ModelAsset>().getAsset("Game/Towers/Laser");
        base = modelAsset.getModel("base");
        canon = modelAsset.getNewModel("laser");
    }

    @Override
    public void update() {
        hover += 0.01f;
        hover %= Math.PI * 2;
    }

    @Override
    public void render() {
        if (base != null && canon != null) {
            Vector3 pos = new Vector3(position).add(0, (float) Math.sin(hover) / 2, 0);
            Raylib.DrawModel(base, position.toRaylibVector3(), 2, type.getColor());
            Raylib.DrawModel(canon, pos.toRaylibVector3(), 2, type.getColor());

            if (target != null)
                Raylib.DrawCylinderEx(pos.add(0, 10, 0).toRaylibVector3(), target.position.toRaylibVector3(), 0.1f * consume, 0.1f * consume, 12, type.getColor());
        }

        drawRange();
        drawEnergy();
    }

    @Override
    public void gameTick() {
        if (target == null || !target.isAlive) {
            target = GameManager.instance.getFurthestEnemyInRange(position, range);
            if (target == null) {
                consume = 1;
                fired = 0;
            }
        }

        if (target != null)
            //make canon look at target
            canon.transform(Raylib.MatrixRotateY((float) Math.atan2(target.position.x - position.x, target.position.z - position.z)));

        if (hasEnergy(consume)) {

            if (target != null) {
                consumeEnergy(consume * consume);
                fire();
                target.damage(consume * consume);
                if (fired >= 20) {
                    consume++;
                    fired = 0;
                }
                fired++;
            }
        } else {
            target = null;
        }
    }
}
