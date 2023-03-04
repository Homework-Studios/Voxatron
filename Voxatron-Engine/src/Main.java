import input.Input;
import level.LevelManager;
import render.Renderer;
import window.Window;
public class Main {

    public static void main(String[] args) {

        new Input();

        Window window = new Window();
        window.init();

        Renderer renderer = new Renderer();
        renderer.begin();
    }
}