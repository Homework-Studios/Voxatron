import input.Input;
import level.LevelManager;
import render.Renderer;
import testing.AppdataFolder;
import window.Window;
public class Main {

    public static void main(String[] args) {

        new Input();

        // for testing -- no function
        // new AppdataFolder();

        Window window = new Window();
        window.init();

        Renderer renderer = new Renderer();
        renderer.begin();
    }
}