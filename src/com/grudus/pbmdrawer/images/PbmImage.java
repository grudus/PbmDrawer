package com.grudus.pbmdrawer.images;


import com.grudus.pbmdrawer.helpers.MathHelper;

import java.awt.*;

public class PbmImage {
    private boolean[][] image;

    public PbmImage(boolean[][] image) {

        this.image = image;
    }

    public PbmImage(int rows, int columns) {
        this(new boolean[rows][columns]);

    }

    public PbmImage() {
        this(new boolean[1][1]);

    }


    public boolean[][] getImage() {
        return image;
    }

    public int getWidth() {
        return image[0].length;
    }

    public int getHeight() {
        return image.length;
    }

    public void fill(int row, int column, boolean fill) {
        image[row][column] = fill;
    }

    public void setImage(boolean[][] image) {
        this.image = image;
    }

    public void resizeImage(int newRows, int newColumns) {
        boolean[][] newImage = new boolean[newRows][newColumns];
        int rows = getHeight();
        int columns = getWidth();

        int tempHeight = MathHelper.lcm(newRows, rows);
        int tempWidth = MathHelper.lcm(newColumns, columns);

        boolean[][] temporary = new boolean[tempHeight][tempWidth];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if(image[row][col])
                    initTemporary(temporary, row, col, tempWidth / columns, tempHeight / rows);
            }
        }

        for (int row = 0; row < newRows; row++) {
            for (int col = 0; col < newColumns; col++) {
                if (isPixel(temporary, row, col, tempWidth / newColumns, tempHeight / newRows))
                    newImage[row][col] = true;
            }
        }

        this.image = newImage;
    }


    private void initTemporary(boolean[][] temporary, int row, int col, int scaleX, int scaleY) {
        for (int r = 0; r < scaleY; r++) {
            for (int c = 0; c < scaleX; c++) {
                temporary[row*scaleY+r][scaleX*col+c] = true;
            }
        }
    }

    private boolean isPixel(boolean[][] temporary, int row, int col, int scaleX, int scaleY) {
        int counter = 0;
        for (int r = 0; r < scaleY; r++) {
            for (int c = 0; c < scaleX; c++) {
                if (temporary[row*scaleY+r][scaleX*col+c])
                    counter++;
            }
        }

        int pole = scaleY * scaleX+1;

        double ratio = ((double)counter ) / pole;

        return ratio >= 0.5;
    }

    public Insets findImageRange() {
        int left = getWidth(), bottom = 0, right = 0, top =  -1;

        for (int r = 0; r < getHeight(); r++) {
            for (int c = 0; c < getWidth(); c++) {
                if (image[r][c]) {
                    if (top == -1)
                        top = r;
                    if (c < left)
                        left = c;
                    if (r > bottom)
                        bottom = r;
                    if (c > right)
                        right = c;
                }
            }
        }

        return new Insets(top, left, bottom, right);
    }
}
