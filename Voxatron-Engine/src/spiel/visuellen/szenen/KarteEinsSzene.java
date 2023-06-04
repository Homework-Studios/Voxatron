package spiel.visuellen.szenen;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.debug.DebugDraw;
import engine.math.Vector2;
import engine.math.Vector3;
import engine.render.Renderer;
import engine.render.scene.Element;
import engine.render.scene.ElementBatch;
import engine.render.scene.InGameScene;
import engine.render.scene.LightElement;
import spiel.WegManager;
import spiel.visuellen.elemente.biElemente.inGame.TurmAuswaehler;
import spiel.visuellen.elemente.ddElemente.BodenElement;
import spiel.visuellen.elemente.ddElemente.FallGeistElement;
import spiel.visuellen.elemente.ddElemente.SpielManagerElement;

import java.util.ArrayList;
import java.util.List;

public class KarteEinsSzene extends InGameScene {
    private final Vector3 pos = new Vector3(0, 0, 0);
    ElementBatch elementPaket;

    List<Vector3> fallPosition = new ArrayList<>();

    @Override
    public void init() {
        leereFarbe = new Jaylib.Color(146, 218, 225, 255);
        addElement3d(new FallGeistElement() {
            @Override
            public void run() {
                position = getFallPosition();
            }
        });
        addElement3d(new BodenElement(1));
        addElement(new SpielManagerElement());
        addElement3d(new LightElement(new Vector3(0, 10, 0), new Vector3(1, 1, 1), 5));
        WegManager.instance.generiereWeg(new Vector3(-59.104187f, 1.0f, 119.24182f), new Vector3(-91.08752f, 1.0000038f, -124.302444f), new Vector3[]{new Vector3(-56.042877f, 1.0f, 97.935196f), new Vector3(-49.55903f, 1.0f, 86.314285f), new Vector3(-39.249886f, 0.99998474f, 74.51933f), new Vector3(-11.454193f, 1.0f, 60.504158f), new Vector3(6.3443375f, 1.0f, 54.808945f), new Vector3(25.616684f, 1.0f, 47.605225f), new Vector3(42.326576f, 1.0f, 40.601532f), new Vector3(58.394463f, 1.0f, 28.115334f), new Vector3(75.64068f, 0.9999924f, 7.6816254f), new Vector3(79.29971f, 1.0f, 0.957016f), new Vector3(81.03331f, 1.0000076f, -12.71302f), new Vector3(75.32882f, 1.0000076f, -23.555527f), new Vector3(67.98616f, 0.9999962f, -30.91661f), new Vector3(53.591072f, 1.0f, -42.390305f), new Vector3(37.851006f, 1.0f, -51.62916f), new Vector3(29.738297f, 1.0000038f, -54.545876f), new Vector3(13.710178f, 0.9999962f, -54.213215f), new Vector3(3.065895f, 1.0f, -46.44816f), new Vector3(-8.562836f, 0.9999962f, -34.942547f), new Vector3(-19.924202f, 1.0f, -20.124527f), new Vector3(-32.822746f, 1.0f, -1.930069f), new Vector3(-40.539238f, 1.0f, 4.742708f), new Vector3(-55.273308f, 1.0f, 5.603359f), new Vector3(-67.85485f, 1.0f, -1.4805336f), new Vector3(-78.80371f, 1.0f, -14.840893f), new Vector3(-83.59611f, 1.0f, -21.735294f), new Vector3(-88.498604f, 1.0f, -36.753517f), new Vector3(-91.86037f, 1.0f, -58.470184f), new Vector3(-93.04567f, 0.9999924f, -70.41305f), new Vector3(-94.1987f, 1.0f, -113.85949f)});
        elementPaket = new ElementBatch(new Element[]{
                new TurmAuswaehler(Vector2.byScreenPercent(50, 105), Vector2.byScreenPercent(85, 20)) {
                    @Override
                    public void run() {
                        System.out.println("TowerSelector");
                    }
                }
        }, true);

        addElement(elementPaket);
    }

    public Vector3 getFallPosition() {
        Raylib.Ray ray = Jaylib.GetMouseRay(Jaylib.GetMousePosition(), Renderer.camera.getCamera());

        Raylib.Rectangle boden = new Raylib.Rectangle().x(-125).y(-125).width(250).height(250);
        Raylib.BoundingBox box = new Jaylib.BoundingBox().min(new Raylib.Vector3().x(boden.x()).y(0).z(boden.y())).max(new Raylib.Vector3().x(boden.x() + boden.width()).y(0).z(boden.y() + boden.height()));

        Raylib.RayCollision kollision = Jaylib.GetRayCollisionBox(ray, box);

        if (kollision.hit()) {
            // add y+1 to position
            kollision.point(new Raylib.Vector3().x(kollision.point().x()).y(kollision.point().y() + 1).z(kollision.point().z()));

            return new Vector3(kollision.point());
        }

        return new Vector3();
    }

    @Override
    public void update() {
        if (Raylib.IsKeyPressed(Raylib.KEY_P)) {
            fallPosition.add(getFallPosition());
            System.out.println(fallPosition);

            DebugDraw.instance.print(fallPosition.toString());
        }
        for (Element element : getIterableElements3d()) {
            if (element == null) continue;
            element.update();
        }

        for (Element element : getIterableElements()) {
            if (element == null) continue;
            element.update();
        }
    }

    @Override
    public void render() {
        // Enabled the renderer to render 3d elements, without this, the 3d elements will not be rendered.
        // --------------------------------------------
        Renderer.instance.toggleMode3d();
        for (int i = 0; i < fallPosition.size(); i++) {
            if (i + 1 < fallPosition.size()) {
                Vector3 pos1 = fallPosition.get(i);
                Vector3 pos2 = fallPosition.get(i + 1);
                Raylib.DrawLine3D(pos1.toRaylibVector3(), pos2.toRaylibVector3(), Jaylib.WHITE);
            }
        }

        List<Element> elements3dCopy = new ArrayList<>(elements3d);
        for (Element element : elements3dCopy) {
            element.render();
        }

        Renderer.instance.toggleMode3d();
        // --------------------------------------------

        for (Element element : elements) {
            element.render();
        }
    }
}
