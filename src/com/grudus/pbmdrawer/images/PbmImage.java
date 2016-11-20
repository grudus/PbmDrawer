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

    public void resizeImage(int newRows, int newColumns) {
        boolean[][] newImage = new boolean[newRows][newColumns];
        int rows = getHeight();
        int columns = getWidth();

//        todo rename
        boolean[][] temporary = new boolean[rows * newRows][columns * newColumns];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if(image[row][col])
                    initTemporary(temporary, row, col, newRows);
            }
        }



        for (int row = 0; row < newRows; row++) {
            for (int col = 0; col < newColumns; col++) {
                if (isPixel(temporary, row, col, rows))
                    newImage[row][col] = true;
            }
        }

        image = newImage;
    }


    private void initTemporary(boolean[][] temporary, int row, int col, int scale) {
        for (int r = 0; r < scale; r++) {
            for (int c = 0; c < scale; c++) {
                temporary[row*scale+r][scale*col+c] = true;
            }
        }
    }

    private boolean isPixel(boolean[][] temporary, int row, int col, int scale) {
        int counter = 0;
        for (int r = 0; r < scale; r++) {
            for (int c = 0; c < scale; c++) {
                if (temporary[row*scale+r][scale*col+c])
                    counter++;
            }
        }

        int pole = scale * scale+1;

        double ratio = ((double)counter ) / pole;

        return ratio >= 0.5;
    }


}
