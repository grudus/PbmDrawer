package com.grudus.pbmdrawer.io;


import com.grudus.pbmdrawer.components.MainPanel;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PbmImageWriter {

    private final MainPanel panel;

    public PbmImageWriter(MainPanel panel) {
        this.panel = panel;
    }

    public void chooseFileAndSaveImage() {
        FileDialog fd = new FileDialog(new Frame(), panel.properties().getFileDialogTitle(), FileDialog.SAVE);

        fd.setDirectory(panel.properties().getSaveDirectory());
        fd.setVisible(true);
        String filename = fd.getFile();


        if (filename != null) {
            saveImage(new File(fd.getDirectory(), filename));
            panel.properties().setSaveDirectory(fd.getDirectory());
        }
    }

    public void saveImage(File file) {

        if (!file.getAbsolutePath().endsWith(panel.properties().getImageFormat()))
            file = new File(file.getAbsolutePath() + panel.properties().getImageFormat());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(generateStringToWrite());
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    private String generateStringToWrite() {
        StringBuilder builder = new StringBuilder();
        int rows = panel.getDrawerRows();
        int cols = panel.getDrawerColumns();

        builder.append("P1")
                .append("\n")
                .append("#Created by grudus https://github.com/grudus\n")
                .append(cols)
                .append(" ")
                .append(rows)
                .append("\n");

        boolean[][] data = panel.getPaintedPoints();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                builder.append(data[r][c] ? "1" : "0").append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public void fastSaving(File directory) {
        if (!directory.isDirectory())
            return;
        int index = directory.listFiles().length + 1;
        saveImage(new File(directory.getAbsolutePath(), panel.properties().getFastSavingFileName() + index));
    }
}
