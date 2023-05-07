package engine.scripting;

import ch.obermuhlner.scriptengine.java.JavaScriptEngine;
import engine.DevelopmentConstants;
import engine.assets.Asset;
import util.FileUtils;
import util.TreeUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ScriptingManager {
    public static HashMap<String, Script> scriptHashMap = new HashMap<>();
    public static JTree tree;

    public static ScriptEngine engine;
    public static ScriptingManager instance;
    public static JavaScriptEngine javaScriptEngine;


    File scriptsDir = new File(Asset.ASSET_DIR.replace("Assets", "Scripts"));
    HashMap<String, DefaultMutableTreeNode> path_nodes;

    public ScriptingManager() throws ScriptException, IOException {
        instance = this;
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("java");
        javaScriptEngine = (JavaScriptEngine) engine;

        List<File> scripts = FileUtils.getSubFiles(scriptsDir);
        for (File file : scripts) {
            Script script = new Script(file);
            scriptHashMap.put(file.getAbsolutePath(), script);
        }
    }


    public void updateScripts() {
        path_nodes = new HashMap<>();
        path_nodes.put("", (DefaultMutableTreeNode) tree.getModel().getRoot());
        File scriptDir = new File(Asset.ASSET_DIR.replace("Assets", "Scripts"));
        File newScriptDir = new File(DevelopmentConstants.NEW_SCRIPT_DIR);
        if (DevelopmentConstants.SCRIPT_UPDATE) {
            for (File file : Objects.requireNonNull(scriptDir.listFiles())) {
                FileUtils.deleteFileOrDirectory(file);
            }
            FileUtils.copyFiles(newScriptDir, scriptDir);
        }


        List<File> scripts = FileUtils.getAllFiles(scriptDir);
        for (File script : scripts) {
            if (!script.isDirectory() && !script.getName().endsWith(".vescript")) break;
            String path = script.getAbsolutePath().replace(scriptDir.getAbsolutePath(), "");
            createNodeLevelUp(path);
            System.out.println(path);
        }

        tree.expandPath(new TreePath(path_nodes.get("").getPath()));
    }

    public void createNodeLevelUp(String path) {
        TreeUtils.genTreeNodesLevelUp(path, path_nodes, tree);
    }


}



