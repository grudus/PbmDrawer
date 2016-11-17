package com.grudus.pbmdrawer.components;


import java.awt.*;

public class Tile {
    public int x;
    public int y;
    public int width;
    public int height;

    private boolean enabled;

    private final boolean painting;

    public Tile(int x, int y, int width, int height, boolean painting) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.painting = painting;
        this.enabled = true;
    }

    public int getSmallerX() {
        return x + 1;
    }

    public int getSmallerY() {
        return y + 1;
    }

    public int getSmallerWidth() {
        return width - 1;
    }

    public int getSmallerHeight() {
        return height - 1;
    }

    public Rectangle toRectangle() {
        return new Rectangle(getSmallerX(), getSmallerY(), getSmallerWidth(), getSmallerHeight());
    }

    public boolean isPainting() {
        return painting;
    }

    public void drawTile(Graphics g) {
        if (enabled)
            g.fillRect(getSmallerX(), getSmallerY(), getSmallerWidth(), getSmallerHeight());
    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int tileWidth, int tileHeight) {
        this.width = tileWidth;
        this.height = tileHeight;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
