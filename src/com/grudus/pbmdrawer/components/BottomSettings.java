package com.grudus.pbmdrawer.components;

import com.grudus.pbmdrawer.PbmDrawerProperties;
import com.grudus.pbmdrawer.components.dialogs.ResizeDialog;
import com.grudus.pbmdrawer.io.PbmImageWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;


public class BottomSettings extends JPanel {

    private final MainPanel mainPanel;

    private String iconsPath;
    private String iconsFormat;
    private static final String[] BUTTON_IMAGES = {"clear_all", "save", "grid", "resize", "fast_save"};
    private static final JLabel[] BUTTONS = new JLabel[BUTTON_IMAGES.length];

    private int height = Window.DEFAULT_HEIGHT / 14;
    private boolean fastSaving;

    public BottomSettings(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        iconsPath = mainPanel.properties().getIconPath();
        iconsFormat = mainPanel.properties().getIconFormat();

        setPreferredSize(new Dimension(Window.DEFAULT_WIDTH, height));
        setLayout(new GridBagLayout());

        setBackground(PbmDrawerProperties.DEFAULT_MAIN_BACKGROUND_COLOR);

        addButtons();
        addListeners();
    }

    private void addButtons() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;

        for (int i = 0; i < BUTTONS.length; i++) {
            BUTTONS[i] = new JLabel();
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
            BUTTONS[i].setText(BUTTON_IMAGES[i]);
        }
    }

    private void addIcons() throws IOException {
        for (int i = 0; i < BUTTON_IMAGES.length; i++) {
            Image img = ImageIO.read(new File(iconsPath + BUTTON_IMAGES[i] + iconsFormat))
                    .getScaledInstance(height, height, Image.SCALE_FAST);
            BUTTONS[i].setIcon(new ImageIcon(img));
        }
    }

    private void addListeners() {
        //clear all
        BUTTONS[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                mainPanel.clearAll();
            }
        });

        //save
        BUTTONS[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                new PbmImageWriter(mainPanel).chooseFileAndSaveImage();
            }
        });

        //show/hide grid
        BUTTONS[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                mainPanel.changeGridEnable();
            }
        });

        //change number of columns / rows
        BUTTONS[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                new ResizeDialog((
                        mainPanel::changeGrid)).setVisible(true);
            }
        });

        // fast saving
        BUTTONS[4].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                enableFastSaving(!fastSaving);
            }
        });
    }

    private void enableFastSaving(boolean b) {
        fastSaving = b;

        if (fastSaving) {
            setBackground(Color.RED);

            FileDialog fd = new FileDialog(new Frame(), "Choose a file", FileDialog.SAVE);
            fd.setVisible(true);
            String filename = fd.getFile();

            if (filename != null)
                mainPanel.properties().setFastSavingDirectory(fd.getDirectory());
        }

        else {
            setBackground(PbmDrawerProperties.DEFAULT_MAIN_BACKGROUND_COLOR);
        }

        mainPanel.setFastSaving(fastSaving);
    }


}
