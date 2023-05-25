package game.visuals.elements.tdElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.render.scene.Element;
import engine.render.shader.ShaderManager;

import static com.raylib.Jaylib.DARKGRAY;
import static com.raylib.Jaylib.DrawPlane;

public class FloorElement extends Element {

    private final Raylib.Model model;

    public FloorElement() {
        model = Raylib.LoadModelFromMesh(Raylib.GenMeshPlane(300, 300, 10, 10));
        model.materials().shader(ShaderManager.instance.lightShader);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

        if(model == null) return;

        Raylib.DrawModel(model, new Jaylib.Vector3().y(-5), 1, DARKGRAY);
    }
}
