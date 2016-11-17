package com.grudus.pbmdrawer.components.reusable;


import javax.swing.*;
import java.awt.*;

public class IconWrapper extends JPanel {

    public final JLabel label;
    public final String description;

    public IconWrapper(JLabel label, String description) {
        this(label, description, new Insets(10, 10, 10, 10));
    }

    public IconWrapper(JLabel label, String description, Insets padding) {
        this.label = label;
        this.description = description;

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = padding;

        add(label, gbc);
    }
}
