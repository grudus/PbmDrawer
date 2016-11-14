package com.grudus.pbmdrawer;


import com.grudus.pbmdrawer.components.MainPanel;
import com.grudus.pbmdrawer.components.Window;

public class Launcher {
    public static void main(String[] args) {
        Window window = new Window("Pbm Drawer");

        MainPanel mainPanel = new MainPanel();
        window.add(mainPanel);

        window.show();

    }
}
