package com.grudus.pbmdrawer.components;

import com.grudus.pbmdrawer.images.PbmImage;
import com.grudus.pbmdrawer.io.PbmImageWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


public class Drawer extends JPanel implements MouseListener, MouseMotionListener, ComponentListener {

    private final MainPanel mainPanel;

    private int columns, rows;
    private int tileWidth, tileHeight;
    private int columnWidth, rowHeight;
    private boolean refresh;

    private boolean fastSaving;
    private boolean gridIsEnabled;

    private PbmImage image;

    private Tile repaintedTile;

    private Color lineColor;
    private Color tileColor;
    private Color backgroundColor;


    public Drawer(MainPanel mainPanel, int columns, int rows) {
        this.mainPanel = mainPanel;
        this.columns = columns;
        this.rows = rows;
        
        lineColor = mainPanel.properties().getGridColor();
        tileColor = mainPanel.properties().getTileColor();
        backgroundColor = mainPanel.properties().getDrawerBackgroundColor();

        setBackground(backgroundColor);
        image = new PbmImage(rows, columns);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                super.componentResized(componentEvent);
                refresh = true;
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


        if (repaintedTile != null && !refresh) {
            g.setColor(repaintedTile.isPainting() ? tileColor : backgroundColor);
            g.fillRect(repaintedTile.x+1, repaintedTile.y+1, repaintedTile.width-1, repaintedTile.height-1);
        }

        if (refresh) {
            handleRefresh(g);
            refresh = false;
        }
    }

    private void handleRefresh(Graphics g) {
        drawTiles(g);
        if (gridIsEnabled)
            drawLines(g);
    }

    private void drawTiles(Graphics g) {
        g.setColor(tileColor);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (image.getImage()[r][c]) {
                    g.fillRect(c * tileWidth+1, r * tileHeight+1, tileWidth-1, tileHeight-1);
                }
            }
        }
    }

    private void drawLines(Graphics g) {
        g.setColor(lineColor);
        for (int i = 0; i < columns; i++) {
            g.drawLine(columnWidth + i * columnWidth, 0, columnWidth + i * columnWidth, getHeight());
        }

        for (int i = 0; i < rows; i++) {
            g.drawLine(0, rowHeight + i * rowHeight, getWidth(), rowHeight + i * rowHeight);
        }
    }


    private void drawRect(MouseEvent mouseEvent, boolean cleanRect) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        if (x < 0 || y < 0 || x > getWidth()-1 || y > getHeight()-1)
            return;

        int tileX = x / tileWidth;
        int tileY = y / tileHeight;

        drawRect(tileX, tileY, cleanRect);
    }

    private void drawRect(int tileX, int tileY, boolean cleanRect) {

        if (image.getImage()[tileY][tileX] == cleanRect) {
            image.fill(tileY, tileX, !cleanRect);
            repaintedTile = new Tile(tileX * tileWidth, tileY * tileHeight, tileWidth, tileHeight, !cleanRect);
            repaint(repaintedTile.toRectangle());
        }

    }

    public void clearAll() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                image.fill(i, j, false);
        }
        refresh = true;
        repaint();
    }

    private void resize() {
        columnWidth = tileWidth = getWidth() / columns;
        rowHeight = tileHeight =  getHeight() / rows;
        repaint();
    }

    public PbmImage getImage() {
        return image;
    }

    public void changeGridEnabled() {
        gridIsEnabled = !gridIsEnabled;
        refresh = true;
        repaint();

    }

    public void changeGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.image = new PbmImage(rows, columns);
        repaintedTile = null;
        refresh = true;
        resize();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        //when user press right button on mouse metaDown is true
        drawRect(mouseEvent, mouseEvent.isMetaDown());
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        drawRect(mouseEvent, mouseEvent.isMetaDown());
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
        resize();
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

    public void addImage(boolean[][] image) {
        this.image = new PbmImage(image);
        refresh = true;
        repaint();
    }
}
