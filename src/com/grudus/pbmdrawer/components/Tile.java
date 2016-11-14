package com.grudus.pbmdrawer.components;


import java.awt.*;

public class Tile {
    public final double x;
    public final double y;
    public final double width;
    public final double height;

    public Tile(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getRoundedX() {
        return (int) (0.5 + x);
    }

    public int getRoundedY() {
        return (int) (0.5 + y);
    }

    public int getRundedWidth() {
        return (int) (0.5 + width);
    }

    public int getRoundedHeight() {
        return (int) (0.5 + height);
    }

    public Rectangle toRectangle() {
        return new Rectangle(getRoundedX(), getRoundedY(), getRundedWidth(), getRoundedHeight());
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
