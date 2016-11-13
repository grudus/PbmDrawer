package com.grudus.pbmdrawer;

import javax.swing.*;
import java.awt.*;

public class Window {

    private final int HEIGHT;
    private final int WIDTH;

    private final JFrame frame;

    public Window(String title, int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;

        frame = new JFrame(title);
        init();
    }

    private void init() {
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }


    public void show() {
        frame.setVisible(true);
    }


}
