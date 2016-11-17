package com.grudus.pbmdrawer.components;

import com.grudus.pbmdrawer.images.PbmImage;
import com.grudus.pbmdrawer.properties.PbmDrawerProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class MainPanel extends JPanel {

    private final BorderLayout MAIN_LAYOUT;
    private final Drawer drawer;

    private int drawerColumns;
    private int drawerRows;

    private final PbmDrawerProperties properties;
    private BottomSettings settings;

    private boolean bottomSettingsResizable;

    private JPanel drawerWrapper;

    public MainPanel(PbmDrawerProperties properties) {
        this.properties = properties;
        setBackground(properties.getMainBackgroundColor());

        bottomSettingsResizable = properties.isBottomSettingsResizable();

        drawerColumns = properties.getGridColumns();
        drawerRows = properties.getGridRows();

        MAIN_LAYOUT = new BorderLayout();
        setLayout(MAIN_LAYOUT);

        settings = new BottomSettings(this);
        add(settings, BorderLayout.PAGE_END);

        drawer = new Drawer(this, drawerColumns, drawerRows);

        drawerWrapper = new JPanel();
        drawerWrapper.setLayout(new BorderLayout());
        drawerWrapper.setBackground(properties.getDrawerWrapperBackground());
        drawerWrapper.add(drawer, BorderLayout.CENTER);
        add(drawerWrapper, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                super.componentResized(componentEvent);
                resizeDrawer();
                if (bottomSettingsResizable)
                    settings.newSize(getHeight());
            }
        });
    }

    private void resizeDrawer() {
        int initDrawerWidth = getWidth();
        int initDrawertHeight = getHeight() - settings.getHeight();

        int tileWidth = initDrawerWidth / drawerColumns;
        int tileHeight = initDrawertHeight / drawerRows;

        int horizontalGap_2 = (initDrawerWidth - tileWidth * drawerColumns) / 2;
        int verticalGap_2 =( initDrawertHeight - tileHeight * drawerRows) / 2;

        drawer.setSize(new Dimension(tileWidth * drawerColumns, tileHeight * drawerRows));

        drawerWrapper.setBorder(new EmptyBorder(verticalGap_2, horizontalGap_2, verticalGap_2, horizontalGap_2));
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

    public PbmImage getImage() {
        return drawer.getImage();
    }

    public void changeGridEnable() {
        drawer.changeGridEnabled();
    }

    public void changeGrid(int rows, int columns) {
        this.drawerRows = rows;
        this.drawerColumns = columns;
        drawer.changeGrid(rows, columns);
        resizeDrawer();
        properties.setGridRows(rows);
        properties.setGridColumns(columns);
    }

    public void setFastSaving(boolean fastSaving) {
        drawer.setFastSaving(fastSaving);
    }

    public PbmDrawerProperties properties() {
        return properties;
    }

    public void addDrawerImage(boolean[][] image) {
        changeGrid(image.length, image[0].length);
        drawer.addImage(image);
    }

    public void changeCursorSize(int i, int j) {
        drawer.changeCursorSize(1, 1);
    }
}
