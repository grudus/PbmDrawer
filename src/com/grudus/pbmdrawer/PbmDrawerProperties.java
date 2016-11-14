package com.grudus.pbmdrawer;


import java.awt.*;

public class PbmDrawerProperties {
    public static final Color DEFAULT_GRID_COLOR = new Color(0x61, 0x61, 0x61, 0x61);
    public static final  Color DEFAULT_TILE_COLOR = new Color(0x21, 0x21, 0x21);
    public static final Color DEFAULT_DRAWER_BACKGROUND_COLOR = new Color(0xFA, 0xFA, 0xFA);
    public static final Color DEFAULT_MAIN_BACKGROUND_COLOR = new Color(0xB0, 0xBE, 0xC5);
    public static final Color DEFAULT_HINT_COLOR = new Color(0x9E, 0x9E, 0x9E);
    public static final Color DEFAULT_TEXT_COLOR = new Color(0x21, 0x21, 0x21);
    public static final Color DEFAULT_ERROR_BORDER_COLOR = new Color(0xd5, 0x00, 0x00);


    public static final String DEFAULT_ICON_PATHS = "res/icons/";
    public static final String DEFAULT_ICON_FORMAT = ".png";
    public static final String SAVING_IMAGE_FORMAT = ".pbm";




    private String iconFormat = DEFAULT_ICON_FORMAT;
    private String iconPath = DEFAULT_ICON_PATHS;
    private String fastSavingDirectory;


    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconFormat() {
        return iconFormat;
    }

    public void setIconFormat(String iconFormat) {
        this.iconFormat = iconFormat;
    }

    public String getFastSavingDirectory() {
        return fastSavingDirectory;
    }

    public void setFastSavingDirectory(String fastSavingDirectory) {
        this.fastSavingDirectory = fastSavingDirectory;
    }
}
