package com.grudus.pbmdrawer.components;

import com.grudus.pbmdrawer.images.PbmImage;
import com.grudus.pbmdrawer.io.PbmImageWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
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
    private Tile cursorTile;

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

        cursorTile = new Tile(0, 0, tileWidth, tileHeight, true);

//        hideCursor();
    }

    public void hideCursor() {
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        this.setCursor(blankCursor);

    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawCursor(g);

        if (repaintedTile != null && !refresh) {
            g.setColor(repaintedTile.isPainting() ? tileColor : backgroundColor);
            repaintedTile.drawTile(g);
        }

        if (refresh) {
            handleRefresh(g);
            refresh = false;
        }

    }

    private void drawCursor(Graphics g) {
        if (!cursorTile.isEnabled()) {
            return;
        }
        g.setColor(Color.RED);
        cursorTile.drawTile(g);
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
                    g.fillRect(c * columnWidth+1, r * rowHeight+1, columnWidth-1, rowHeight-1);
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


    private void drawRect(boolean cleanRect) {
        int x = cursorTile.x;
        int y = cursorTile.y;

        if (x < 0 || y < 0 || x > getWidth()-1 || y > getHeight()-1)
            return;

        int scaleX = tileWidth / columnWidth;
        int scaleY = tileHeight / rowHeight;

        for (int c = 0; c < scaleX; c++) {
            for (int r = 0; r < scaleY; r++) {
                int tileX = x / columnWidth + c;
                int tileY = y / rowHeight + r;

                drawRect(tileX, tileY, cleanRect);
            }
        }
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
        columnWidth = getWidth() / columns;
        rowHeight =  getHeight() / rows;
        tileWidth = columnWidth * 2;
        tileHeight = rowHeight * 3;
        cursorTile.setSize(tileWidth, tileHeight);
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
        drawRect(mouseEvent.isMetaDown());

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (cursorTile.isEnabled()) {
            cursorTile.disable();
            refresh = true;
            repaint();
        }
        findCursorPosition(mouseEvent);
        drawRect(mouseEvent.isMetaDown());

        refresh = true;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        cursorTile.enable();
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
       findCursorPosition(mouseEvent);
        refresh = true;
        repaint();
    }

    private void findCursorPosition(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        int tileX = x / columnWidth;
        int tileY = y / rowHeight;

        int middleX = columnWidth * tileX;
        int middleY = rowHeight * tileY;
        cursorTile.setPosition(middleX, middleY);
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
