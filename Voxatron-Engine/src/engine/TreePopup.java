package engine;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class TreePopup extends MouseAdapter {
    private void myPopupEvent(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        JTree tree = (JTree) e.getSource();
        TreePath path = tree.getPathForLocation(x, y);
        if (path == null)
            return;

        DefaultMutableTreeNode rightClickedNode = (DefaultMutableTreeNode) path
                .getLastPathComponent();

        TreePath[] selectionPaths = tree.getSelectionPaths();

        boolean isSelected = false;
        if (selectionPaths != null) {
            for (TreePath selectionPath : selectionPaths) {
                if (selectionPath.equals(path)) {
                    isSelected = true;
                    break;
                }
            }
        }
        if (!isSelected) {
            tree.setSelectionPath(path);
        }
        JPopupMenu popup = new JPopupMenu();

        final JMenuItem deleteMenuItem = new JMenuItem("Delete");
        deleteMenuItem.addActionListener(ev -> {
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            model.removeNodeFromParent(rightClickedNode);
        });
        popup.add(deleteMenuItem);
        popup.show(tree, x, y);
    }

    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger())
            myPopupEvent(e);
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger())
            myPopupEvent(e);
    }
}
