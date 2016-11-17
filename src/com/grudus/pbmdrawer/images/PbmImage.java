package com.grudus.pbmdrawer.images;


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
}
