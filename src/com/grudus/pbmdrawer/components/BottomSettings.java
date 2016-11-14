package com.grudus.pbmdrawer.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class BottomSettings extends JPanel {

    private final MainPanel mainPanel;

    private JLabel clearAllButton;
    private JLabel saveButton;
    private JLabel gridButton;

    private int height = Window.DEFAULT_HEIGHT / 14;

    public BottomSettings(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        setPreferredSize(new Dimension(Window.DEFAULT_WIDTH, height));
        setLayout(new GridBagLayout());

        setBackground(MainPanel.BACKGROUND_COLOR);

        addButtons();
        addListeners();
    }

    private void addButtons() {
        GridBagConstraints gbc = new GridBagConstraints();

        gridButton = new JLabel();
        saveButton = new JLabel();
        clearAllButton = new JLabel();

        addIcons();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(clearAllButton, gbc);

        gbc.gridx = 1;
        add(gridButton, gbc);

        gbc.gridx = 2;
        add(saveButton, gbc);


    }

    private void addIcons() {
        try {
            Image clearImage = ImageIO.read(new File("res/icons/clear_all.png")).getScaledInstance(height, height, Image.SCALE_FAST);
            Image saveImage = ImageIO.read(new File("res/icons/save.png")).getScaledInstance(height, height, Image.SCALE_FAST);
            Image gridImage = ImageIO.read(new File("res/icons/grid.png")).getScaledInstance(height, height, Image.SCALE_FAST);

            clearAllButton.setIcon(new ImageIcon(clearImage));
            saveButton.setIcon(new ImageIcon(saveImage));
            gridButton.setIcon(new ImageIcon(gridImage));
        } catch (IOException ex) {
            System.err.println("Cannot read image " + ex.getMessage());
            clearAllButton.setText("Clear");
            saveButton.setText("Save");
            gridButton.setText("Grid");
        }
    }

    private void addListeners() {
        clearAllButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                mainPanel.clearAll();
            }
        });

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                saveFile();
            }
        });

        gridButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                mainPanel.changeGridEnable();
            }
        });
    }

    private void saveFile() {
        FileDialog fd = new FileDialog(new Frame(), "Choose a file", FileDialog.SAVE);

        fd.setVisible(true);
        String filename = fd.getFile();


        if (filename == null)
            System.err.println("You cancelled the choice");
        else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fd.getDirectory(), filename)))) {
                writer.write(generateStringToWrite());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateStringToWrite() {
        StringBuilder builder = new StringBuilder();
        int rows = mainPanel.getDrawerRows();
        int cols = mainPanel.getDrawerColumns();

        builder.append("P1")
                .append("\n")
                .append(cols)
                .append(" ")
                .append(rows)
                .append("\n");

        boolean[][] data = mainPanel.getPaintedPoints();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                builder.append(data[r][c] ? "1" : "0").append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
     }

}
