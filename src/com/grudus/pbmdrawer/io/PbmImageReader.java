package com.grudus.pbmdrawer.io;


import com.grudus.pbmdrawer.exceptions.PbmImageException;

import java.io.*;
import java.util.Arrays;

public class PbmImageReader {


    private int width;
    private int height;
    private int imageLineCounter;
    private boolean[][] image;

    public PbmImageReader() {
        width = height = -1;
        imageLineCounter = -2;
    }

    public boolean[][] loadImage(File source) throws IOException {
        if (!source.exists())
            throw new FileNotFoundException("Cannot find file " + source);

        if (!source.getName().endsWith(".pbm"))
            throw new PbmImageException();


        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            String firstLine = reader.readLine();
            if (firstLine == null || !firstLine.trim().equalsIgnoreCase("P1"))
                throw new PbmImageException("Not valid pbm format!");

            String line;

            while ((line = reader.readLine()) != null && imageLineCounter < height) {

                if (isComment(line) || isEmpty(line))
                    continue;

                String[] numbers = line.split("\\s+");

                if (!onlyDigits(numbers))
                    throw new PbmImageException("Not a valid pbm file");

                if (!sizeIsSet() && imageHasStart(numbers))
                    throw new PbmImageException("Cannot find dimensions");

                if (width == -1) {
                    width = Integer.parseInt(numbers[0]);
                    if (numbers.length == 2) {
                        height = Integer.parseInt(numbers[1]);
                        initImage();
                    }
                    continue;
                }

                if (height == -1) {
                    height = Integer.parseInt(numbers[0]);
                    initImage();
                    continue;
                }

                if (hasIncorrectWidth(numbers))
                    throw new PbmImageException("The image has incorrect width!");

                image[imageLineCounter++] = createImageRow(numbers);
            }
        }
        return image;
    }

    private boolean[] createImageRow(String[] numbers) {
        int size = numbers.length;
        boolean[] row = new boolean[size];

        for (int i = 0; i < size; i++) {
            row[i] = numbers[i].equals("1");
        }
        return row;
    }

    private boolean hasIncorrectWidth(String[] numbers) {
        return numbers.length != width;
    }

    private void initImage() {
        imageLineCounter = 0;
        image = new boolean[height][width];
    }

    private boolean imageHasStart(String[] numbers) {
        return numbers.length > 2;
    }

    private boolean sizeIsSet() {
        return width > 0 && height > 0;
    }

    private boolean onlyDigits(String[] numbers) {
        return Arrays.stream(numbers).allMatch(it -> it.matches("\\d+"));
    }

    private boolean isComment(String line) {
        return line.startsWith("#");
    }

    private boolean isEmpty(String line) {
        return line.trim().isEmpty();
    }


}
