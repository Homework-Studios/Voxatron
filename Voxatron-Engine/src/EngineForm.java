import com.raylib.Raylib;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.awt.event.WindowEvent;

public class EngineForm extends JFrame {

    public JPanel Game;
    private JPanel MainPanel;
    private JTree ObjectsTree;
    private JTabbedPane tabbedPane1;
    private JPanel Inspector;
    private JPanel CurrentlyEmpty;
    private JPanel AssetPanel;
    private JPanel BottomSpacingPane;
    private JPanel LeftSpacingPane;
    private JPanel TopSpacing;


    public EngineForm() {
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
        AssetPanel.setPreferredSize(new Dimension(1, 150));

        //set colors because it does not work with editor,
        Color background = new Color(43, 45, 48);
        Color highlightArea = new Color(101, 101, 101);
        Color foreground = new Color(255, 255, 255);
        Color importantHighlight = new Color(37, 255, 151, 255);

        MainPanel.setBackground(background);
        Game.setBackground(highlightArea);
        tabbedPane1.setBackground(background);
        ObjectsTree.setBackground(background);
        AssetPanel.setBackground(background);

        ObjectsTree.setForeground(foreground);
        tabbedPane1.setForeground(foreground);
        AssetPanel.setForeground(foreground);

        //Trees
        TreeCellRenderer renderer = ObjectsTree.getCellRenderer();
        ((DefaultTreeCellRenderer) renderer).setBackgroundNonSelectionColor(background);
        ((DefaultTreeCellRenderer) renderer).setBackgroundSelectionColor(highlightArea);
        ((DefaultTreeCellRenderer) renderer).setTextNonSelectionColor(foreground);
        ((DefaultTreeCellRenderer) renderer).setTextSelectionColor(foreground);

        ObjectsTree.setEditable(true);

        //Assets

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
                Insets tabAreaInsets = getTabAreaInsets(tabPlacement);

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
        AssetPanel.setBorder(new LineBorder(highlightArea, 1, true));
        ObjectsTree.setBorder(new LineBorder(highlightArea, 1, true));
        //endregion

        //Asset Rendering

    }

    private void createUIComponents() {

    }
}
