package com.grudus.pbmdrawer.components;

import javax.swing.*;
import java.awt.*;


public class MainPanel extends JPanel {

    private final BorderLayout MAIN_LAYOUT;
    private final Drawer drawer;

    private int drawerColumns;
    private int drawerRows;

    public static final Color BACKGROUND_COLOR = new Color(0xB0, 0xBE, 0xC5);

    public MainPanel() {
        setBackground(BACKGROUND_COLOR);

        drawerColumns = drawerRows = 28;

        MAIN_LAYOUT = new BorderLayout();
        setLayout(MAIN_LAYOUT);

        add(new BottomSettings(this), BorderLayout.PAGE_END);

        drawer = new Drawer(this, drawerColumns, drawerRows);
        add(drawer, BorderLayout.CENTER);
    }

    public void clearAll() {
        drawer.clearAll();
    }

    public int getDrawerColumns() {
        return drawerColumns;
    }

    public int getDrawerRows() {
        return drawerRows;
    }

    public boolean[][] getPaintedPoints() {
        return drawer.getPaintedPoints();
    }

    public void changeGridEnable() {
        drawer.changeGridEnabled();
    }
}
