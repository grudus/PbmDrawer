package com.grudus.pbmdrawer;


import com.grudus.pbmdrawer.components.MainPanel;
import com.grudus.pbmdrawer.components.Window;

public class Launcher {
    public static void main(String[] args) {
        MainPanel mainPanel = new MainPanel();

        Window window = new Window("Pbm Drawer", mainPanel.properties().getWindowSize());

        window.add(mainPanel);

        window.show();

    }
}
