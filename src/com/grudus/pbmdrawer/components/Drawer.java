package com.grudus.pbmdrawer.components;

import com.grudus.pbmdrawer.PbmDrawerProperties;
import com.grudus.pbmdrawer.io.PbmImageWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


public class Drawer extends JPanel implements MouseListener, MouseMotionListener, ComponentListener {

    private final MainPanel mainPanel;

    private int columns, rows;
    private double tileWidth, tileHeight;
    private double columnWidth, rowHeight;
    private boolean isResized;

    private boolean fastSaving;
    private boolean gridIsEnabled;

    private boolean[][] paintedPoints;
    private Tile repaintedTile;

    private final Color LINE_COLOR = PbmDrawerProperties.DEFAULT_GRID_COLOR;
    private final Color TILE_COLOR = PbmDrawerProperties.DEFAULT_TILE_COLOR;
    private final Color BACKGROUND_COLOR = PbmDrawerProperties.DEFAULT_DRAWER_BACKGROUND_COLOR;


    public Drawer(MainPanel mainPanel, int columns, int rows) {
        this.mainPanel = mainPanel;
        this.columns = columns;
        this.rows = rows;

        setBackground(BACKGROUND_COLOR);
        paintedPoints = new boolean[rows][columns];

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                super.componentResized(componentEvent);
                isResized = true;
            }
        });

        addMouseListener(this);
        addMouseMotionListener(this);
        addComponentListener(this);

        gridIsEnabled = true;
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("paint " + getWidth());


        if (repaintedTile != null && !isResized) {
            g.setColor(TILE_COLOR);
            g.fillRect(repaintedTile.getRoundedX(), repaintedTile.getRoundedY(), repaintedTile.getRundedWidth(), repaintedTile.getRoundedHeight());
        }

        if (isResized) {
            handleResized(g);
            isResized = false;
        }
    }

    private void handleResized(Graphics g) {
        drawTiles(g);
        if (gridIsEnabled)
            drawLines(g);
    }

    private void drawTiles(Graphics g) {
        g.setColor(TILE_COLOR);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (paintedPoints[r][c]) {
                    g.fillRect((int) (c * tileWidth+0.5), (int) (r * tileHeight+0.5), (int)(tileWidth+0.5), (int)(tileHeight+0.5));
                }
            }
        }
    }

    private void drawLines(Graphics g) {
        g.setColor(LINE_COLOR);
        for (int i = 0; i < columns; i++) {
            g.drawLine((int) (columnWidth + i * columnWidth), 0, (int) (columnWidth + i * columnWidth), getHeight());
        }

        for (int i = 0; i < rows; i++) {
            g.drawLine(0, (int) (rowHeight + i * rowHeight), getWidth(), (int) (rowHeight + i * rowHeight));
        }
    }


    private void drawRect(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        if (x < 0 || y < 0)
            return;

        int tileX = (int) (x / tileWidth);
        int tileY = (int) (y / tileHeight);

        drawRect(tileX, tileY);

    }

    private void drawRect(int tileX, int tileY) {

        if (!paintedPoints[tileY][tileX]) {
            paintedPoints[tileY][tileX] = true;
            repaintedTile = new Tile(tileX * tileWidth, tileY * tileHeight, tileWidth, tileHeight, true);
            repaint(repaintedTile.toRectangle());
        }

    }

    public void clearAll() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                paintedPoints[i][j] = false;
        }
        isResized = true;
        repaint();
    }


    public boolean[][] getPaintedPoints() {
        return paintedPoints;
    }

    public void changeGridEnabled() {
        gridIsEnabled = !gridIsEnabled;
        isResized = true;
        repaint();

    }

    public void changeGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.paintedPoints = new boolean[rows][columns];
        repaintedTile = null;
        isResized = true;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        drawRect(mouseEvent);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        drawRect(mouseEvent);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (fastSaving) {
            new PbmImageWriter(mainPanel).fastSaving(new File(mainPanel.properties().getFastSavingDirectory()));
            clearAll();
        }
    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {
        columnWidth = tileWidth = (double) getWidth() / columns;
        rowHeight = tileHeight = (double) getHeight() / rows;
        repaint();
    }


//    trash #########################################

    @Override
    public void componentMoved(ComponentEvent componentEvent) {
    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {
    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }


    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    public void setFastSaving(boolean fastSaving) {
        this.fastSaving = fastSaving;
    }
}
