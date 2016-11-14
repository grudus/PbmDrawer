package com.grudus.pbmdrawer.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Drawer extends JPanel implements MouseListener, MouseMotionListener {

    private final JPanel mainPanel;

    private int columns, rows;
    private double tileWidth, tileHeight;
    private boolean isResized;

    private boolean gridIsEnabled;

    private boolean[][] paintedPoints;
    private Tile repaintedTile;

    private final Color LINE_COLOR = new Color(0x61, 0x61, 0x61, 0x61);
    private final Color TILE_COLOR = new Color(0x21, 0x21, 0x21);
    private final Color BACKGROUND_COLOR = new Color(0xFA, 0xFA, 0xFA);


    public Drawer(JPanel mainPanel, int columns, int rows) {
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

        gridIsEnabled = true;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        tileWidth = (double) getWidth() / columns;
        tileHeight = (double) getHeight() / rows;


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
            g.drawLine((int) (tileWidth + i * tileWidth), 0, (int) (tileWidth + i * tileWidth), getHeight());
        }

        for (int i = 0; i < rows; i++) {
            g.drawLine(0, (int) (tileHeight + i * tileHeight), getWidth(), (int) (tileHeight + i * tileHeight));
        }
    }


    private void drawRect(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        if (x < 0 || y < 0)
            return;

        drawRect(x, y);
    }

    private void drawRect(int x, int y) {
        int tileX = (int) (x / tileWidth);
        int tileY = (int) (y / tileHeight);
        if (!paintedPoints[tileY][tileX]) {
            paintedPoints[tileY][tileX] = true;
            repaintedTile = new Tile(tileX * tileWidth, tileY * tileHeight, tileWidth, tileHeight);
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

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        drawRect(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        drawRect(mouseEvent);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    public boolean[][] getPaintedPoints() {
        return paintedPoints;
    }

    public void changeGridEnabled() {
        if (gridIsEnabled)
            gridIsEnabled = false;
        else
            gridIsEnabled = true;
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
}
