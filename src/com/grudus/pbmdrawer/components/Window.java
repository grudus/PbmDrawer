package com.grudus.pbmdrawer.components;

import javax.swing.*;
import java.awt.*;

public class Window {

    public static final int MIN_WIDTH = 200;
    public static final int MIN_HEIGHT = 100;

    public static final  int DEFAULT_WIDTH = 800;
    public static final  int DEFAULT_HEIGHT = 600;

    private final JFrame frame;

    public Window(String title) {
        frame = new JFrame(title);
        init();
    }

    private void init() {
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //todo change later
        frame.setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

    }


    public void show() {
        frame.setVisible(true);
    }

    public void add(Component component) {
        frame.add(component);
    }


}
