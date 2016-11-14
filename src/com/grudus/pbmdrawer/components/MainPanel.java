package com.grudus.pbmdrawer.components;

import com.grudus.pbmdrawer.PbmDrawerProperties;

import javax.swing.*;
import java.awt.*;


public class MainPanel extends JPanel {

    private final BorderLayout MAIN_LAYOUT;
    private final Drawer drawer;

    private int drawerColumns;
    private int drawerRows;

    private final PbmDrawerProperties properties;

    public MainPanel() {
        properties = new PbmDrawerProperties();
        setBackground(PbmDrawerProperties.DEFAULT_MAIN_BACKGROUND_COLOR);


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

    public void changeGrid(int rows, int columns) {
        this.drawerRows = rows;
        this.drawerColumns = columns;
        drawer.changeGrid(rows, columns);
    }

    public void setFastSaving(boolean fastSaving) {
        drawer.setFastSaving(fastSaving);
    }

    public PbmDrawerProperties properties() {
        return properties;
    }
}
