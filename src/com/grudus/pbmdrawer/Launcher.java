package com.grudus.pbmdrawer;


import com.grudus.pbmdrawer.components.MainPanel;
import com.grudus.pbmdrawer.components.Window;
import com.grudus.pbmdrawer.properties.PbmDrawerProperties;

import javax.swing.*;
import java.io.IOException;

public class Launcher {

    public static final String PROPERTIES_DEFAULT_PATH = "res/properties/application.properties";
    public static final String PROPERTIES_ERROR_MESSAGE = "Cannot find " + PROPERTIES_DEFAULT_PATH + " file.";

    public static void main(String[] args) {
        PbmDrawerProperties properties = null;
        try {
            properties = new PbmDrawerProperties(PROPERTIES_DEFAULT_PATH);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, PROPERTIES_ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        MainPanel mainPanel = new MainPanel(properties);
        Window window = new Window(properties.getApplicationName(), properties.getWindowSize());

        window.add(mainPanel);

        window.show();

    }
}
