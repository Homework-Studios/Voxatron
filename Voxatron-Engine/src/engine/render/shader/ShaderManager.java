package engine.render.shader;

import com.raylib.Raylib;
import engine.render.Renderer;

public class ShaderManager {

    public static ShaderManager instance;

    public Raylib.Shader lightShader;

    public ShaderManager() {
        instance = this;
    }

    // Mate Innit? Hehehehehe
    public void init() {
        lightShader = Raylib.LoadShader("C:\\Users\\j.windmann\\IdeaProjects\\Voxatron\\Voxatron-Engine\\src\\engine\\shader\\lighting.vert", "C:\\Users\\j.windmann\\IdeaProjects\\Voxatron\\Voxatron-Engine\\src\\engine\\shader\\lighting.frag");
    }

    public void update() {
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "viewPos"), Renderer.camera.position, Raylib.SHADER_ATTRIB_VEC3);
    }
}