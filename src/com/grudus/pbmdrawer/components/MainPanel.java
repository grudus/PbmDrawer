package com.grudus.pbmdrawer.components;

import com.grudus.pbmdrawer.PbmDrawerProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class MainPanel extends JPanel {

    private final BorderLayout MAIN_LAYOUT;
    private final Drawer drawer;

    private int drawerColumns;
    private int drawerRows;

    private final PbmDrawerProperties properties;

    public MainPanel() {
        properties = new PbmDrawerProperties();
        setBackground(properties.getMainBackgroundColor());


        drawerColumns = properties.getGridColumns();
        drawerRows = properties.getGridRows();

        MAIN_LAYOUT = new BorderLayout();
        setLayout(MAIN_LAYOUT);

        add(new BottomSettings(this), BorderLayout.PAGE_END);

        drawer = new Drawer(this, drawerColumns, drawerRows);

        add(drawer, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                super.componentResized(componentEvent);

                int initDrawerWidth = drawer.getWidth();
                int initDrawertHeight = drawer.getHeight();

                int tileWidth = initDrawerWidth / drawerColumns;
                int tileHeight = initDrawertHeight / drawerRows;

                int horizontalGap_2 = (initDrawerWidth - tileWidth * drawerColumns) / 2;
                int verticalGap_2 =( initDrawertHeight - tileHeight * drawerRows) / 2;

                drawer.setSize(new Dimension(tileWidth * drawerColumns, tileHeight * drawerRows));

            }
        });
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
        properties.setGridRows(rows);
        properties.setGridColumns(columns);
    }

    public void setFastSaving(boolean fastSaving) {
        drawer.setFastSaving(fastSaving);
    }

    public PbmDrawerProperties properties() {
        return properties;
    }
}
