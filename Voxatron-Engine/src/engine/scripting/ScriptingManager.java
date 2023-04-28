package engine.scripting;

import ch.obermuhlner.scriptengine.java.JavaCompiledScript;
import ch.obermuhlner.scriptengine.java.JavaScriptEngine;
import ch.obermuhlner.scriptengine.java.execution.MethodExecutionStrategy;
import engine.assets.Asset;
import util.FileUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ScriptingManager {
    public static ScriptingManager instance;
    File scriptDir = new File(Asset.ASSET_DIR.replace("Assets", "Scripts"));

    public ScriptingManager() throws ScriptException, IOException {
        instance = this;

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("java");
        JavaScriptEngine javaScriptEngine = (JavaScriptEngine) engine;

        List<File> scripts = FileUtils.getSubFiles(scriptDir);
        if (scripts != null)
            for (File script : scripts) {
                BufferedReader reader = new BufferedReader(new FileReader(script));
                String line = "";
                String scriptText = "";
                while ((line = reader.readLine()) != null) {
                    scriptText += line + "\n";
                }
                JavaCompiledScript compiledScript = javaScriptEngine.compile(scriptText);
                System.out.println("Compiled Script: " + script.getName());
                executeScript(compiledScript, "start");
                executeScript(compiledScript, "startFixedUpdate");
            }
    }

    void setScriptExecutionMethod(JavaCompiledScript script, String methodName, Object... args) throws ScriptException {
        script.setExecutionStrategy(MethodExecutionStrategy.byMatchingArguments(
                script.getCompiledClass(),
                methodName,
                args));
    }

    void executeScript(JavaCompiledScript script, String method, Object... args) throws ScriptException {
        setScriptExecutionMethod(script, method, args);
        script.eval();
    }
}
