package render.scene.scenes.tdElements;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

import com.raylib.Jaylib;
import render.scene.Element;

public class FloorElement extends Element {

    Texture map;
    Model heighMap;

    public FloorElement() {
        Image pmap = GenImageChecked(500, 500, 50, 50, BLACK, WHITE);
        this.map = LoadTextureFromImage(pmap);
        this.heighMap = LoadModelFromMesh(
                GenMeshHeightmap(
                        pmap,
                        new Jaylib.Vector3().x(250).y(5).z(250)
                )
        );
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        DrawModel(heighMap, new Vector3(), 2, WHITE);
    }
}
