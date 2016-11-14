package com.grudus.pbmdrawer.components;

import javax.swing.*;
import java.awt.*;


public class MainPanel extends JPanel {

    private final BorderLayout MAIN_LAYOUT;

    public MainPanel() {
        setBackground(Color.GRAY);

        MAIN_LAYOUT = new BorderLayout();
        setLayout(MAIN_LAYOUT);

        JPanel nil = new JPanel();
        nil.setPreferredSize(new Dimension(Window.DEFAULT_WIDTH/8, Window.DEFAULT_HEIGHT));

        add(nil, BorderLayout.LINE_START);

        add(new Drawer(28, 28), BorderLayout.CENTER);
    }
}
