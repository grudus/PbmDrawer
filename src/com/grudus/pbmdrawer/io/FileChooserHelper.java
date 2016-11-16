package com.grudus.pbmdrawer.io;

import java.awt.*;
import java.io.File;

public class FileChooserHelper {

    public enum Option {
        SAVE(FileDialog.SAVE),
        LOAD(FileDialog.LOAD);

        private final int i;

        Option(int i) {
            this.i = i;
        }

    }

    public static File selectFile(String directory, Option option) {
        FileDialog fd = new FileDialog(new Frame(), "Choose a file", option.i);
        if (directory != null)
            fd.setDirectory(directory);
        fd.setVisible(true);

        String file = fd.getFile();

        return file == null ? null : new File(fd.getDirectory(), file);
    }

}
