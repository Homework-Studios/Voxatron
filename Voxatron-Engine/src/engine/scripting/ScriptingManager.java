package engine.scripting;

import ch.obermuhlner.scriptengine.java.JavaScriptEngine;
import engine.assets.Asset;
import util.FileUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ScriptingManager {
    public static HashMap<String, Script> scriptHashMap = new HashMap<>();

    public static ScriptEngine engine;
    public static ScriptingManager instance;
    public static JavaScriptEngine javaScriptEngine;


    File scriptDir = new File(Asset.ASSET_DIR.replace("Assets", "Scripts"));

    public ScriptingManager() throws ScriptException, IOException {
        instance = this;
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("java");
        javaScriptEngine = (JavaScriptEngine) engine;

        List<File> scripts = FileUtils.getSubFiles(scriptDir);
        for (File file : scripts) {
            Script script = new Script(file);
            scriptHashMap.put(file.getAbsolutePath(), script);
        }
    }
}



