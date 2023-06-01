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

        // Randomly add 10 lights
        for (int i = 0; i < 10; i++) {
            addLightSource(
                    new Vector3((float) (Math.random() * 100), (float) (Math.random() * 100), (float) (Math.random() * 100)),
                    new Vector3((float) (Math.random() * 100), (float) (Math.random() * 100), (float) (Math.random() * 100)),
                    new Vector3((float) (Math.random() * 100), (float) (Math.random() * 100), (float) (Math.random() * 100)),
                    (float) Math.random() * 100
            );
        }
    }

    public int maxLights = 10;
    public int currentLight = 0;

    public void addLightSource(Vector3 position, Vector3 direction, Vector3 color, float intensity) {
        // if over max lights, return
        if (currentLight + 1 > maxLights) {
            return;
        }

        currentLight++;

        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + currentLight  + "].position"), position.toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC3);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + currentLight + "].direction"), direction.toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC3);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + currentLight + "].color"), color.toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC4);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + currentLight + "].intensity"), new FloatPointer(intensity), Raylib.SHADER_ATTRIB_FLOAT);

        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lightCount"), new FloatPointer(1f), Raylib.SHADER_ATTRIB_FLOAT);
    }

    public void updateLightSource(int index, Vector3 position, Vector3 direction, Vector3 color, float intensity) {
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + index + "].position"), position.toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC3);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + index + "].direction"), direction.toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC3);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + index + "].color"), color.toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC4);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[" + index + "].intensity"), new FloatPointer(intensity), Raylib.SHADER_ATTRIB_FLOAT);
    }

    public void update() {
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "viewPos"), Renderer.camera.position, Raylib.SHADER_ATTRIB_VEC3);
    }
}