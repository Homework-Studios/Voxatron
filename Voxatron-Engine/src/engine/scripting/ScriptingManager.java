package engine.scripting;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptingManager {
    public static void main(String[] args) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("java");

        Object result = engine.eval("public class Script {" +
                "   public String getMessage() {" +
                "       return \"Hello World\";" +
                "   } " +
                "}");
        System.out.println("Result: " + result);
    }
}
