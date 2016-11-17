package com.grudus.pbmdrawer.components;

import javax.swing.*;
import java.awt.*;

public class Window {


    private final JFrame frame;

    public Window(String title, Dimension size) {
        frame = new JFrame(title);
        frame.setSize(size);
        init();
    }

    private void init() {
        //todo change later
        frame.setMinimumSize(new Dimension(frame.getWidth() / 2, frame.getHeight() / 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
