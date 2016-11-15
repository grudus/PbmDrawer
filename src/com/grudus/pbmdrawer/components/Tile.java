package com.grudus.pbmdrawer.components;


import java.awt.*;

public class Tile {
    public final int x;
    public final int y;
    public final int width;
    public final int height;

    private final boolean painting;

    public Tile(int x, int y, int width, int height, boolean painting) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.painting = painting;
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

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
