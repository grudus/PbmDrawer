package com.grudus.pbmdrawer.components;

import javax.swing.*;
import java.awt.*;


public class Drawer extends JPanel {

    private int columns, rows;

    private int tileWidth, tileHeight;


    public Drawer(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;

        System.err.println(getWidth());

        setBackground(Color.WHITE);
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        tileWidth = getWidth() / columns;
        tileHeight = getHeight() / rows;

        for (int i = 0; i <= columns; i++) {
            g.drawLine(i*tileWidth, 0, i*tileWidth, getHeight());
        }

        for (int i = 0; i <= rows; i++) {
            g.drawLine(0, i * tileHeight, getWidth(), i * tileHeight);
        }

    }
}
