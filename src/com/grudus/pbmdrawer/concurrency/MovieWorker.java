package com.grudus.pbmdrawer.concurrency;

import com.grudus.pbmdrawer.exceptions.FileTypeExcpetion;
import com.grudus.pbmdrawer.images.PbmImage;
import com.grudus.pbmdrawer.io.PbmImageReader;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;


public class MovieWorker extends SwingWorker<Boolean, PbmImage> {

    private final File directory;
    private final Consumer<PbmImage> function;
    private final long time;

    private static final Pattern FILE_PATTERN = Pattern.compile("(\\d+)(\\.pbm)?");

    public MovieWorker(File directory, long time, Consumer<PbmImage> function) throws FileTypeExcpetion {
        checkDirectory(directory);
        this.directory = directory;
        this.function = function;
        this.time = time;
    }

    private void checkDirectory(File directory) throws FileTypeExcpetion {
        if (!directory.isDirectory()) throw new FileTypeExcpetion(directory + "is not a directory");
        if (!Arrays.stream(directory.listFiles()).allMatch(file -> FILE_PATTERN.matcher(file.getName()).matches()))
            throw new FileTypeExcpetion("Filename must be a number");
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        File[] images = directory.listFiles();

        Arrays.sort(images, Comparator.comparingInt(value -> {
            final String name = value.getName();
            if (name.endsWith(".pbm"))
                return Integer.parseInt(name.substring(0, name.length() - 4));
            else return Integer.parseInt(name);
        }));

        for (File image : images) {
            PbmImage pbmImage = new PbmImage(new PbmImageReader().loadImage(image));
            publish(pbmImage);
            Thread.sleep(time);
        }
        return true;
    }

    @Override
    protected void process(List<PbmImage> chunks) {
        chunks.forEach(function);
    }
}