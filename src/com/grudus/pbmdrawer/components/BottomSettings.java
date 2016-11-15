package com.grudus.pbmdrawer.components;

import com.grudus.pbmdrawer.components.dialogs.ResizeDialog;
import com.grudus.pbmdrawer.components.reusable.IconWrapper;
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
    private static final String[] BUTTON_IMAGES = {"clear_all", "save", "grid", "resize", "fast_save"};
    private static final IconWrapper[] BUTTONS = new IconWrapper[BUTTON_IMAGES.length];

    private Color normalBackgroudnd;
    private Color clickedBackground;

    private int height;
    private boolean fastSaving;

    public BottomSettings(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        normalBackgroudnd  = mainPanel.properties().getMainBackgroundColor();
        clickedBackground = mainPanel.properties().getClickedButtonColor();

        iconsPath = mainPanel.properties().getIconPath();
        iconsFormat = mainPanel.properties().getIconFormat();

        height = (int) (mainPanel.properties().getWindowSize().getHeight() / 16);

        setLayout(new GridBagLayout());

        setBackground(normalBackgroudnd);

        addButtons();
        addListeners();
    }

    private void addButtons() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;

        for (int i = 0; i < BUTTONS.length; i++) {
            BUTTONS[i] = new IconWrapper(new JLabel());
            BUTTONS[i].setBackground(normalBackgroudnd);
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
                if (! (mouseEvent.getSource() instanceof IconWrapper)) return;
                IconWrapper button = (IconWrapper) mouseEvent.getSource();

                button.setBackground(clickedBackground);

                if (button == BUTTONS[0]) {
                    mainPanel.clearAll();
                }
                else if (button == BUTTONS[1]) {
                    new PbmImageWriter(mainPanel).chooseFileAndSaveImage();
                    button.setBackground(normalBackgroudnd);
                }
                else if (button == BUTTONS[2]) {
                    mainPanel.changeGridEnable();
                }
                else if (button == BUTTONS[3]) {
                    new ResizeDialog(mainPanel.properties(), (mainPanel::changeGrid)).setVisible(true);
                }
                else if (button == BUTTONS[4]) {
                    enableFastSaving(!fastSaving);
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                super.mouseReleased(mouseEvent);

                IconWrapper button = (IconWrapper) mouseEvent.getSource();

                button.setBackground(normalBackgroudnd);
            }
        };

        Arrays.asList(BUTTONS).forEach(butt -> butt.addMouseListener(listener));

    }

    private void enableFastSaving(boolean b) {
        fastSaving = b;

        if (fastSaving) {
            BUTTONS[4].setBackground(clickedBackground);

            FileDialog fd = new FileDialog(new Frame(), "Choose a file", FileDialog.SAVE);
            fd.setDirectory(mainPanel.properties().getFastSavingDirectory());
            fd.setVisible(true);
            String filename = fd.getFile();

            if (filename != null)
                mainPanel.properties().setFastSavingDirectory(fd.getDirectory());
            else {
                BUTTONS[4].setBackground(normalBackgroudnd);
                fastSaving = false;
            }
        }

        else {
            BUTTONS[4].setBackground(normalBackgroudnd);
        }

        mainPanel.setFastSaving(fastSaving);
    }


}
