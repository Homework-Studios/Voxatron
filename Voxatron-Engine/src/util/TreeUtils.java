package util;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.HashMap;

public class TreeUtils {

    public static void genTreeNodesLevelUp(String path, HashMap<String, DefaultMutableTreeNode> pathNodes, JTree tree) {
        if (!path.startsWith("\\")) path = "\\" + path;
        String parentPath = path.substring(0, path.lastIndexOf("\\"));
        DefaultMutableTreeNode child = pathNodes.get(path);
        if (child == null) {
            child = new DefaultMutableTreeNode(path.substring(path.lastIndexOf("\\") + 1));
            pathNodes.put(path, child);
        }

        DefaultMutableTreeNode parent = pathNodes.get(parentPath);
        if (parent == null) {
            String parentName = parentPath.substring(parentPath.lastIndexOf("\\") + 1);
            parent = new DefaultMutableTreeNode(parentName);
            pathNodes.put(parentPath, parent);
        }

        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.insertNodeInto(child, parent, 0);
        tree.expandPath(tree.getSelectionPath());
    }
}
