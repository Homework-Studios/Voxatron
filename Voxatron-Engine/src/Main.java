import input.Input;
import level.LevelManager;
import render.Renderer;
import window.Window;
import com.raylib.java.Raylib;
public class Main {

    public static void main(String[] args) {
        Raylib raylib = new Raylib();

        new Input(raylib);

        Window window = new Window(raylib);
        window.init();

        Renderer renderer = new Renderer(raylib);
        renderer.begin();

        new LevelManager();
    }
}