package engine.scripting;

/*
    This Engine Was Created For A School Project
    Voxatron
    Script Script
    created 28.04.2023 by timon
 */

import ch.obermuhlner.scriptengine.java.JavaCompiledScript;
import ch.obermuhlner.scriptengine.java.execution.MethodExecutionStrategy;
import util.StringUtils;

import javax.script.Bindings;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static engine.scripting.ScriptingManager.javaScriptEngine;

public class Script {
    public File scriptFile;
    public JavaCompiledScript script;
    public Bindings bindings = ScriptingManager.engine.createBindings();
    public String scriptText;

    public Script(File scriptFile) throws IOException, ScriptException {
        this.scriptFile = scriptFile;
        compile();
    }

    public void compile() throws IOException, ScriptException {
        BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
        String line = "";
        scriptText = "";
        while ((line = reader.readLine()) != null) {
            scriptText += line + "\n";
        }
        script = javaScriptEngine.compile(scriptText);

        String variableString = StringUtils.getStringBetween(scriptText, "//Inspector Variables", "//End Inspector Variables\n").replace("    ", "").replace("public ", "");
        System.out.println(variableString);

        System.out.println("Compiled Script: " + scriptFile.getName());
        execute("start");
    }

    public void setExecutionMethod(String methodName, Object... args) throws ScriptException {
        script.setExecutionStrategy(MethodExecutionStrategy.byMatchingArguments(
                script.getCompiledClass(),
                methodName,
                args));
    }

    public void execute(String method, Object... args) throws ScriptException {
        setExecutionMethod(method, args);
        script.eval();
    }
}
