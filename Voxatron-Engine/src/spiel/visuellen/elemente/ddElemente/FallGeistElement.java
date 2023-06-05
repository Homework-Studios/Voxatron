package spiel.visuellen.elemente.ddElemente;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.math.Vector3;
import engine.render.scene.Element;
import engine.render.shader.ShaderManager;

public abstract class FallGeistElement extends Element implements Runnable {

    private final Raylib.Model kugel;
    public Vector3 position;

    public FallGeistElement() {
        position = new Vector3();
        kugel = Raylib.LoadModelFromMesh(Raylib.GenMeshSphere(1, 32, 32));
        kugel.materials().shader(ShaderManager.instance.lightShader);
    }

    @Override
    public void update() {
        run();
    }

    @Override
    public void render() {
        // Draw a sphere at the position
        Raylib.DrawModel(kugel, position.toRaylibVector3(), 2, Jaylib.GREEN);
    }
}
