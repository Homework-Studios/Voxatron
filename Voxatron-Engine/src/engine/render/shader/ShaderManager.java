package engine.render.shader;

import com.raylib.Raylib;
import engine.math.Vector3;
import engine.render.Renderer;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Pointer;

public class ShaderManager {

    public static ShaderManager instance;

    public Raylib.Shader lightShader;

    public ShaderManager() {
        instance = this;
    }

    // Mate Innit? Hehehehehe
    public void init() {
        String path = System.getProperty("user.dir") + "\\Voxatron-Engine\\src\\engine\\shader\\";
        lightShader = Raylib.LoadShader(path + "lighting.vert", path + "lighting.frag");
    }

    public void LoadDepthTexture(Raylib.Texture depthTexture) {
        Raylib.SetShaderValueTexture(lightShader, Raylib.GetShaderLocation(lightShader, "depthTexture"), depthTexture);
    }

    public int maxLights = 10;
    public int currentLight = 0;

    public int addLightSource(Vector3 position, Vector3 direction, Vector3 color, float intensity) {
        // if over max lights, return
        if (currentLight + 1 > maxLights) {
            return -1;
        }

        currentLight++;

        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + currentLight  + "].position"), position.toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC3);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + currentLight + "].direction"), direction.toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC3);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + currentLight + "].color"), color.toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC4);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + currentLight + "].intensity"), new FloatPointer(intensity), Raylib.SHADER_ATTRIB_FLOAT);

        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lightCount"), new FloatPointer((float)currentLight), Raylib.SHADER_ATTRIB_FLOAT);

        return currentLight;
    }

    public void updateLightSource(int index, Vector3 position, Vector3 direction, Vector3 color, float intensity) {
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + index + "].position"), position.toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC3);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + index + "].direction"), direction.toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC3);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + index + "].color"), color.toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC4);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + index + "].intensity"), new FloatPointer(intensity), Raylib.SHADER_ATTRIB_FLOAT);
    }

    public void update() {
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "viewPos"), Renderer.camera.position, Raylib.SHADER_ATTRIB_VEC3);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "ambient"), new Vector3(0.06f, 0.19f, 0.33f).toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC3);
    }
}