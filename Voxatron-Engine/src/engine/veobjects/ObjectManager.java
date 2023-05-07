package engine.veobjects;

import engine.DevelopmentConstants;
import engine.assets.Asset;
import util.FileUtils;
import util.Text;
import util.TreeUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static engine.EngineForm.highlightArea;

public class ObjectManager {
    public static ObjectManager instance;

    public static JTree worldTree;
    public static JTree tree;
    public List<VoxatronEngineObject> voxatronEngineObjectList = new ArrayList<>();

    HashMap<String, DefaultMutableTreeNode> worldPath_Nodes = new HashMap<>();
    HashMap<String, DefaultMutableTreeNode> enginePath_Nodes = new HashMap<>();
    String objectDir = Asset.ASSET_DIR.replace("Assets", "Objects");
    File worldObjectDir = new File(objectDir + "/World");
    File engineObjectDir = new File(objectDir + "/Engine");


    public ObjectManager() {
        loadObjects();
    }

    public static void init() {
        instance = new ObjectManager();
    }

    public void saveObjects() {

    }

    public void updateObjects() {

    }

    public void loadObjects() {
        if (DevelopmentConstants.DEVELOPMENT_MODE) {
            worldPath_Nodes.put("", (DefaultMutableTreeNode) worldTree.getModel().getRoot());
            enginePath_Nodes.put("", (DefaultMutableTreeNode) tree.getModel().getRoot());

            FileUtils.getAllFiles(worldObjectDir).forEach(file -> {
                worldPath_Nodes.put(file.getPath().replace(worldObjectDir.getPath(), ""), new DefaultMutableTreeNode(file.getName()));
            });
            FileUtils.getAllFiles(engineObjectDir).forEach(file -> {
                enginePath_Nodes.put(file.getPath().replace(engineObjectDir.getPath(), ""), new DefaultMutableTreeNode(file.getName()));
            });

            updateTreeNodes();
        }
    }

    public void updateTreeNodes() {
        FileUtils.getAllFiles(worldObjectDir).forEach(file -> {
            TreeUtils.genTreeNodesLevelUp(file.getAbsolutePath().replace(worldObjectDir.getPath(), ""), worldPath_Nodes, tree);
        });
        FileUtils.getAllFiles(engineObjectDir).forEach(file -> {
            TreeUtils.genTreeNodesLevelUp(file.getAbsolutePath().replace(engineObjectDir.getPath(), ""), enginePath_Nodes, tree);
        });

        worldTree.expandPath(worldTree.getPathForRow(0));
        tree.expandPath(tree.getPathForRow(0));
        updateNamings();
    }

    public void updateNamings() {
        worldPath_Nodes.forEach((path, node) -> {
            updateNaming(path, worldPath_Nodes);
        });
        enginePath_Nodes.forEach((path, node) -> {
            updateNaming(path, enginePath_Nodes);
        });
    }

    public void updateNaming(String path, HashMap<String, DefaultMutableTreeNode> path_nodes) {
        DefaultMutableTreeNode node = path_nodes.get(path);
        if (!node.getUserObject().toString().endsWith(".veobject")) return;
        node.setUserObject(new Text().writeText(node.getUserObject().toString().replace(".veobject", "")).setItalic(true).setSize(10).setColor(highlightArea).writeText(" (Object)"));
    }
}
