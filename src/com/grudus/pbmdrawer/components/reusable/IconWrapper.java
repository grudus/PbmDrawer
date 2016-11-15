package com.grudus.pbmdrawer.components.reusable;


import javax.swing.*;
import java.awt.*;

public class IconWrapper extends JPanel {

    public final JLabel label;

    public IconWrapper(JLabel label) {
        this.label = label;

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        add(label, gbc);
    }
}
