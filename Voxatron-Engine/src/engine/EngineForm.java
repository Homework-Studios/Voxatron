package engine;

import com.raylib.Raylib;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class EngineForm extends JFrame {

    public static DefaultMutableTreeNode root = new DefaultMutableTreeNode("Assets");
    public static DefaultTreeModel model;
    public static EngineForm instance;
    public JTree AssetTree;
    public JPanel Game;
    private JPanel MainPanel;
    private JTree ObjectsTree;
    private JTabbedPane tabbedPane1;
    private JPanel Inspector;
    private JPanel AssetPanel;
    private JPanel DebuggerPanel;
    private JPanel BottomSpacingPane;
    private JPanel LeftSpacingPane;
    private JPanel TopSpacing;
    private JTextPane Debugger;
    private JPanel AssetTreeButtonPanel;
    private JButton NewAssetButton;


    public EngineForm() {
        instance = this;
        setContentPane(MainPanel);
        Raylib.SetConfigFlags(Raylib.FLAG_WINDOW_TOPMOST);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setName("Voxatron Engine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Action Listeners
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                Raylib.MinimizeWindow();
                super.windowIconified(e);
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                Raylib.MaximizeWindow();
                super.windowDeiconified(e);
            }
        });

        //set sizing
        Game.setPreferredSize(new Dimension((int) (500 * 1.7), 500));
        tabbedPane1.setPreferredSize(new Dimension(200, 500));
        ObjectsTree.setPreferredSize(new Dimension(200, 500));
        DebuggerPanel.setPreferredSize(new Dimension(1, 150));

        //set colors because it does not work with editor,
        Color background = new Color(43, 45, 48);
        Color highlightArea = new Color(101, 101, 101);
        Color highlight = new Color(255, 255, 255);
        Color importantHighlight = new Color(37, 255, 151, 255);
        TreeCellRenderer treeCellRenderer = new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree,
                                                          Object value, boolean selected, boolean expanded,
                                                          boolean leaf, int row, boolean hasFocus) {
                String stringValue = tree.convertValueToText(value, selected,
                        expanded, leaf, row, hasFocus);
                setBackgroundSelectionColor(background);
                setBackgroundNonSelectionColor(background);
                setTextSelectionColor(highlight);
                setTextNonSelectionColor(highlight);
                setBorderSelectionColor(importantHighlight);

                super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

                /*
                if (tree.getModel().getRoot().equals(node)) {
                    //setIcon(root);
                } else if (node.getChildCount() > 0) {
                    //setIcon(parent);
                } else {
                    //setIcon(text);
                }

                 */
                /*
                switch (stringValue.split(".")[1]) {
                    case ".asset":
                        setIcon();
                        break;
                    case ".png":
                        setIcon();
                        break;
                     }
                 */


                System.out.println(stringValue);

                return this;
            }
        };

        MainPanel.setBackground(background);
        Game.setBackground(highlightArea);
        tabbedPane1.setBackground(background);
        ObjectsTree.setBackground(background);
        DebuggerPanel.setBackground(background);
        Debugger.setBackground(background);
        AssetTree.setBackground(background);
        AssetTreeButtonPanel.setBackground(background);
        NewAssetButton.setBackground(background);

        AssetTreeButtonPanel.setForeground(highlight);
        NewAssetButton.setForeground(highlight);
        ObjectsTree.setForeground(highlight);
        tabbedPane1.setForeground(highlight);
        DebuggerPanel.setForeground(highlight);
        Debugger.setForeground(highlight);
        AssetTree.setForeground(highlight);

        //Trees
        TreeCellRenderer renderer = ObjectsTree.getCellRenderer();
        ((DefaultTreeCellRenderer) renderer).setBackgroundNonSelectionColor(background);
        ((DefaultTreeCellRenderer) renderer).setBackgroundSelectionColor(highlightArea);
        ((DefaultTreeCellRenderer) renderer).setTextNonSelectionColor(highlight);
        ((DefaultTreeCellRenderer) renderer).setTextSelectionColor(highlight);

        renderer = AssetTree.getCellRenderer();
        ((DefaultTreeCellRenderer) renderer).setBackgroundNonSelectionColor(background);
        ((DefaultTreeCellRenderer) renderer).setBackgroundSelectionColor(highlightArea);
        ((DefaultTreeCellRenderer) renderer).setTextNonSelectionColor(highlight);
        ((DefaultTreeCellRenderer) renderer).setTextSelectionColor(highlight);

        ObjectsTree.setTransferHandler(new TreeTransferHandler());
        ObjectsTree.setCellRenderer(treeCellRenderer);
        ObjectsTree.setDragEnabled(true);
        ObjectsTree.setDropMode(DropMode.ON_OR_INSERT);
        ObjectsTree.setEditable(true);


        //Tabs
        for (Component component : tabbedPane1.getComponents()) {
            component.setBackground(background);
        }

        //region Render Changing
        //Part original Native Java code but changed to fit style
        tabbedPane1.setUI(new BasicTabbedPaneUI() {
            //can be changed if wanted makes the tabs split form each other
            final int tabSizeShift = 0;

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
                int width = tabPane.getWidth();
                int height = tabPane.getHeight();
                Insets insets = tabPane.getInsets();
                //Insets tabAreaInsets = getTabAreaInsets(tabPlacement);

                int x = insets.left;
                int y = insets.top;
                int w = width - insets.right - insets.left;
                int h = height - insets.top - insets.bottom;

                switch (tabPlacement) {
                    case LEFT:
                        x += calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
                        w -= (x - insets.left);
                        break;
                    case RIGHT:
                        w -= calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
                        break;
                    case BOTTOM:
                        h -= calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                        break;
                    case TOP:
                    default:
                        y += calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                        h -= (y - insets.top);
                }

                if (tabPane.getTabCount() > 0 && (tabPane.isOpaque())) {
                    // Fill region behind content area
                    Color color = UIManager.getColor("TabbedPane.contentAreaColor");
                    if (color != null) {
                        g.setColor(color);
                    } else if (selectedIndex == -1) {
                        g.setColor(tabPane.getBackground());
                    } else {
                        g.setColor(highlightArea);
                    }
                    g.fillRect(x, y, w, h);
                }

                paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
                paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
                paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
                paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);

            }

            @Override
            protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
                g.setColor(highlightArea);
                g.drawLine(x + 2, y, x + w - 2, y);
            }

            @Override
            protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
                g.setColor(highlightArea);
                g.drawLine(x + 2, y, x + 2, y + h - 2);
            }

            @Override
            protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
                g.setColor(highlightArea);
                g.drawLine(x + 2, y + h - 2, x + w - 2, y + h - 2);
            }

            @Override
            protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
                g.setColor(highlightArea);
                g.drawLine(x + w - 2, y, x + w - 2, y + h);
            }


            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                if (isSelected) {
                    g.setColor(importantHighlight);
                } else {
                    g.setColor(background);
                }
                int bottom = (int) (h * 0.1);
                g.fillRect(x + tabSizeShift, y + h - bottom, w, bottom);
            }

            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement,
                                          int tabIndex,
                                          int x, int y, int w, int h,
                                          boolean isSelected) {
                g.setColor(highlightArea);

                //Only top is changed to fit style
                switch (tabPlacement) {
                    case LEFT:
                        g.drawLine(x + 1, y + h - 2, x + 1, y + h - 2); // bottom-left highlight
                        g.drawLine(x, y + 2, x, y + h - 3); // left highlight
                        g.drawLine(x + 1, y + 1, x + 1, y + 1); // top-left highlight
                        g.drawLine(x + 2, y, x + w - 1, y); // top highlight
                        break;
                    case RIGHT:
                        g.drawLine(x, y, x + w - 3, y); // top highlight
                        break;
                    case BOTTOM:
                        g.drawLine(x, y, x, y + h - 3); // left highlight
                        g.drawLine(x + 1, y + h - 2, x + 1, y + h - 2); // bottom-left highlight
                        break;
                    case TOP:
                    default:
                        g.drawLine(x + tabSizeShift, y, x + tabSizeShift, y + h); // left highlight
                        g.drawLine(x + tabSizeShift, y, x + w - tabSizeShift, y); // top highlight
                        g.drawLine(x + w - tabSizeShift, y, x + w - tabSizeShift, y + h); // right highlight
                }
            }


        });
        DebuggerPanel.setBorder(new LineBorder(highlightArea, 1, true));
        ObjectsTree.setBorder(new LineBorder(highlightArea, 1, true));
        //endregion

        //Debugging

        StyledDocument document = Debugger.getStyledDocument();
        try {
            document.insertString(0, "Start of debugging" + System.lineSeparator(), null);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            while (true) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(baos, true);
                PrintStream old = System.out;
                System.setOut(printStream);
                try {
                    System.out.flush();
                    if (baos.toString().length() > 0) {
                        document.insertString(document.getLength(), baos + System.lineSeparator(), null);
                    }
                    System.setOut(old);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        });


        //Assets
        model = new DefaultTreeModel(root) {
            @Override
            public void reload() {
                //Asset.onReloadTreeModel();
                reload(root);
            }
        };

        AssetTree.setTransferHandler(new TreeTransferHandler());
        AssetTree.setDropMode(DropMode.ON_OR_INSERT);
        AssetTree.setEditable(true);
        AssetTree.setDragEnabled(true);
        AssetTree.setModel(model);
        AssetTree.setCellRenderer(treeCellRenderer);


    }

    private void createUIComponents() {

    }
}
