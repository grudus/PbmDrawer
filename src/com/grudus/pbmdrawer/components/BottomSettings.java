package com.grudus.pbmdrawer.components;

import com.grudus.pbmdrawer.components.dialogs.ResizeDialog;
import com.grudus.pbmdrawer.components.reusable.IconWrapper;
import com.grudus.pbmdrawer.io.FileChooser;
import com.grudus.pbmdrawer.io.PbmImageReader;
import com.grudus.pbmdrawer.io.PbmImageWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class BottomSettings extends JPanel {

    private final MainPanel mainPanel;

    private String iconsPath;
    private String iconsFormat;
    private static final String[] BUTTON_IMAGES =
            {"clear_all", "grid", "resize", "cursor_size", "zoom",  "save", "load", "fast_save", "movie"};
    private static final IconWrapper[] BUTTONS = new IconWrapper[BUTTON_IMAGES.length];

    private Color normalBackground;
    private Color clickedBackground;

    private int height;
    private boolean fastSaving;

    public BottomSettings(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        normalBackground = mainPanel.properties().getMainBackgroundColor();
        clickedBackground = mainPanel.properties().getClickedButtonColor();

        iconsPath = mainPanel.properties().getIconPath();
        iconsFormat = mainPanel.properties().getIconFormat();

        height = (int) (mainPanel.properties().getWindowSize().getHeight() / 16);

        setLayout(new GridBagLayout());

        setBackground(normalBackground);

        addButtons();
        addListeners();
    }

    public void newSize(int parentHeight) {
        if (parentHeight <  330)
            this.height = parentHeight / 8;
        else
            this.height = parentHeight / 16;

        resizeImages();
    }

    private void resizeImages() {

        try {
            addIcons();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addButtons() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;

        for (int i = 0; i < BUTTONS.length; i++) {
            BUTTONS[i] = new IconWrapper(new JLabel(), BUTTON_IMAGES[i]);
            BUTTONS[i].setToolTipText(BUTTON_IMAGES[i]);
            BUTTONS[i].setBackground(normalBackground);
            gbc.gridx = i;
            add(BUTTONS[i], gbc);
        }

        try {
            addIcons();
        } catch (IOException e) {
            addLabels();
        }

    }

    private void addLabels() {
        for (int i = 0; i < BUTTON_IMAGES.length; i++) {
            BUTTONS[i].label.setText(BUTTON_IMAGES[i]);
        }
    }

    private void addIcons() throws IOException {
        for (int i = 0; i < BUTTON_IMAGES.length; i++) {
            Image img = ImageIO.read(new File(iconsPath + BUTTON_IMAGES[i] + iconsFormat))
                    .getScaledInstance(height, height, Image.SCALE_FAST);
            BUTTONS[i].label.setIcon(new ImageIcon(img));
        }
    }

    private void addListeners() {

        MouseListener listener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);
                if (!(mouseEvent.getSource() instanceof IconWrapper)) return;
                IconWrapper button = (IconWrapper) mouseEvent.getSource();

                button.setBackground(clickedBackground);

                String desc = button.description;

                switch (desc) {
                    case "clear_all":
                        mainPanel.clearAll();
                        break;
                    case "save": {
                        File file = FileChooser.selectFile(mainPanel.properties().getSaveDirectory(), FileChooser.Option.SAVE);
                        button.setBackground(normalBackground);

                        if (file != null) {
                            new PbmImageWriter(mainPanel).saveImage(file);
                            mainPanel.properties().setSaveDirectory(file.getParent());
                        }
                        break;
                    }
                    case "grid":
                        mainPanel.changeGridEnable();
                        break;
                    case "cursor_size":
                        mainPanel.changeCursorSize(1, 1);
                        break;
                    case "zoom":
                        mainPanel.findImageRange();
                        break;
                    case "resize":
                        new ResizeDialog(mainPanel.properties(), (mainPanel::changeGrid)).setVisible(true);
                        break;
                    case "fast_save":
                        enableFastSaving(button, !fastSaving);
                        break;
                    case "load": {
                        File file = FileChooser.selectFile(mainPanel.properties().getSaveDirectory(), FileChooser.Option.LOAD);
                        button.setBackground(normalBackground);
                        if (file == null) return;

                        try {
                            boolean[][] image = new PbmImageReader().loadImage(file);
                            mainPanel.addDrawerImage(image);
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    }
                    case "movie": {
                        File file = FileChooser.selectFile(mainPanel.properties().getSaveDirectory(), FileChooser.Option.LOAD);
                        button.setBackground(normalBackground);
                        if (file == null) return;

                        try {
                            mainPanel.startFilm(file);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                super.mouseReleased(mouseEvent);

                IconWrapper button = (IconWrapper) mouseEvent.getSource();

                button.setBackground(normalBackground);
            }
        };

        Arrays.asList(BUTTONS).forEach(butt -> butt.addMouseListener(listener));

    }


    private void enableFastSaving(IconWrapper wrapper, boolean b) {
        fastSaving = b;

        if (fastSaving) {
            JOptionPane.showMessageDialog(null, mainPanel.properties().getFastSavingInfo());
            wrapper.setBackground(clickedBackground);

            File file = FileChooser.selectFile(mainPanel.properties().getFastSavingDirectory(), FileChooser.Option.SAVE);

            if (file != null)
                mainPanel.properties().setFastSavingDirectory(file.getParent());
            else {
                wrapper.setBackground(normalBackground);
                fastSaving = false;
            }
        } else {
            wrapper.setBackground(normalBackground);
        }

        mainPanel.setFastSaving(fastSaving);
    }


}
