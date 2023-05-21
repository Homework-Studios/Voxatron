package engine.render.camera;

import com.raylib.Jaylib;
import com.raylib.Raylib;

public class Camera {

    public static final int PERSPECTIVE = 0;

    public Raylib.Vector3 position;
    public Raylib.Vector3 target;
    public Raylib.Vector3 up;
    public float fovy;
    public int projection;

    private final Raylib.Camera3D camera;

    public Camera(Raylib.Vector3 position, Raylib.Vector3 target, Raylib.Vector3 up, float fovy, int projection) {
        this.position = position;
        this.target = target;
        this.up = up;
        this.fovy = fovy;
        this.projection = projection;

        camera = new Raylib.Camera3D();
        camera._position(position);
        camera.target(target);
        camera.up(up);
        camera.fovy(fovy);
        camera.projection(projection);
    }

    public void input() {
        Raylib.Vector3 forward = Jaylib.Vector3Subtract(camera.target(), camera._position());

        // move by one unit
        forward = Jaylib.Vector3Normalize(forward);

        if (Raylib.IsKeyDown(Raylib.KEY_W)) {
            move(forward);
        }

        if (Raylib.IsKeyDown(Raylib.KEY_S)) {
            move(Jaylib.Vector3Negate(forward));
        }

        // The cross product of two vectors results in a vector perpendicular to both.
        // This is linear algebra and is cool. I love linear algebra.
        // Kreuzprodukt oder auch Vektorprodukt

        if (Raylib.IsKeyDown(Raylib.KEY_A)) {
            Raylib.Vector3 right = Jaylib.Vector3CrossProduct(camera.up(), forward);
            right = Jaylib.Vector3Normalize(right);
            move(right);
        }

        if (Raylib.IsKeyDown(Raylib.KEY_D)) {
            Raylib.Vector3 right = Jaylib.Vector3CrossProduct(camera.up(), forward);
            right = Jaylib.Vector3Normalize(right);
            move(Jaylib.Vector3Negate(right));
        }

        if (Raylib.IsKeyDown(Raylib.KEY_SPACE)) {
            move(camera.up());
        }

        if (Raylib.IsKeyDown(Raylib.KEY_LEFT_SHIFT)) {
            move(Jaylib.Vector3Negate(camera.up()));
        }
    }

    public void update() {
        input();

        // limit the height of the camera to be at least 10 units above the ground and max 100 units
        if (camera._position().y() < 10) {
            camera._position().y(10);
            this.position.y(10);
        } else if (camera._position().y() > 300) {
            camera._position().y(300);
            this.position.y(300);
        }

        // the distance to 0,0,0 is 100 units max
        if (Jaylib.Vector3Length(camera._position()) > 400) {
            camera._position(Jaylib.Vector3Normalize(camera._position()));
            camera._position(Jaylib.Vector3Scale(camera._position(), 400));
            this.position = camera._position();
        }

        // the distance to 0,0,0 is 10 units min
        if (Jaylib.Vector3Length(camera._position()) < 50) {
            camera._position(Jaylib.Vector3Normalize(camera._position()));
            camera._position(Jaylib.Vector3Scale(camera._position(), 50));
            this.position = camera._position();
        }

        Raylib.Matrix rotation = Raylib.MatrixRotate(camera.up(), 0);
        Raylib.Vector3 view = Jaylib.Vector3Subtract(camera._position(), camera.target());
        view = Jaylib.Vector3Transform(view, rotation);
        camera._position(Jaylib.Vector3Add(camera.target(), view));
    }

    public Camera move(Raylib.Vector3 add) {
        this.position = Jaylib.Vector3Add(this.position, add);
        camera._position(this.position);
        return this;
    }

    public Raylib.Camera3D getCamera() {
        return camera;
    }
}
