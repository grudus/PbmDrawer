package com.grudus.pbmdrawer.components.dialogs;


import com.grudus.pbmdrawer.components.reusable.InputField;
import com.grudus.pbmdrawer.properties.PbmDrawerProperties;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class ResizeDialog extends JDialog {

    private InputField rows;
    private InputField columns;

    private JLabel okButton;
    private JLabel cancelButton;

    private int height;

    private OnOkListener listener;
    private PbmDrawerProperties properties;

    public ResizeDialog(PbmDrawerProperties properties, OnOkListener listener) {
        this.properties = properties;
        int initWidth = properties.getResizeDialogWidth();
        int initHeight = properties.getResizeDialogHeight();

        this.setSize(initWidth, initHeight);
        this.setFocusable(true);
        this.listener = listener;

        height = properties.getButtonIconSize();

        getRootPane ().setOpaque (false);
        getContentPane ().setBackground (properties.getMainBackgroundColor());

        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;


        rows = new InputField(properties, "Rows", InputField.InputType.NUMBERS);
        rows.setText(properties.getGridRows() + "");
        rows.setMaxLength(3);

        add(rows, gbc);

        gbc.gridy = 1;
        columns = new InputField(properties, "Columns", InputField.InputType.NUMBERS);
        columns.setMaxLength(3);
        columns.setText(properties.getGridColumns() + "");
        add(columns, gbc);


        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        okButton = new JLabel();
        gbc.gridy = 2;

        add(okButton, gbc);

        cancelButton = new JLabel();
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        add(cancelButton, gbc);

        addIcons();
        addListeners();
    }

    private void addIcons() {
        try {
            Image okImg = ImageIO.read(new File(properties.getIconPath() + "/buttons/done.png")).getScaledInstance(height, height, Image.SCALE_FAST);
            Image cancelImg = ImageIO.read(new File(properties.getIconPath() + "/buttons/close.png")).getScaledInstance(height, height, Image.SCALE_FAST);

            okButton.setIcon(new ImageIcon(okImg));
            cancelButton.setIcon(new ImageIcon(cancelImg));

        } catch (IOException ex) {
            System.err.println("Cannot read image " + ex.getMessage());
            okButton.setText("OK");
            cancelButton.setText("CANCEL");

        }
    }

    private void addListeners() {
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                setVisible(false);
                dispose();
            }
        });

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);

                if (rows.isEmpty()) {
                    rows.showError(true);
                    return;
                }
                if (columns.isEmpty()) {
                    columns.showError(true);
                    return;
                }

                int newRows = Integer.parseInt(rows.getText());
                int newColumns = Integer.parseInt(columns.getText());

                // TODO: 23.11.16 remove
                if (newRows != newColumns) {
                    JOptionPane.showMessageDialog(null, "Rows and columns must be the same");
                    return;
                }

                listener.ok(newRows, newColumns);

                setVisible(false);
                dispose();
            }
        });
    }

    public interface OnOkListener {
        void ok(int rows, int columns);
    }

}
