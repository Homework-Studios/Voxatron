package game.visuals.elements.tdElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.math.Vector3;
import engine.render.scene.Element;
import engine.render.shader.ShaderManager;

public abstract class DropGhostElement extends Element implements Runnable {

    public Vector3 position;

    private final Raylib.Model sphere;

    public DropGhostElement() {
        position = new Vector3();
        sphere = Raylib.LoadModelFromMesh(Raylib.GenMeshSphere(1, 32, 32));
        sphere.materials().shader(ShaderManager.instance.lightShader);
    }

    @Override
    public void update() {
        run();
    }

    @Override
    public void render() {
        // Draw a sphere at the position
        Raylib.DrawModel(sphere, position.toRaylibVector3(), 2, Jaylib.GREEN);

        // Draw a 3d line from 000 to the position
        Raylib.DrawLine3D(new Raylib.Vector3().y(1), position.toRaylibVector3(), Jaylib.GREEN);
    }
}
