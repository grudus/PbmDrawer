package com.grudus.pbmdrawer;


import com.grudus.pbmdrawer.helpers.ColorHelper;

import java.awt.*;
import java.io.*;
import java.util.Properties;

public class PbmDrawerProperties {
    
    private final Properties properties;

    private Color gridColor;
    private Color tileColor;
    private Color drawerBackgroundColor;
    private Color mainBackgroundColor;

    private Color hintColor;
    private Color textColor;
    private Color errorBorderColor;
    private Color clickedButtonColor;
    private Color drawerWrapperBackground;

    private String iconFormat;
    private String iconPath;
    private String imageFormat;

    private String fastSavingDirectory;
    private String saveDirectory;

    private int gridRows;
    private int gridColumns;
    private int buttonIconSize;
    private int resizeDialogWidth;
    private int resizeDialogHeight;
    private Dimension windowSize;

    private String fileDialogTitle;
    private String fastSavingFileName;
    private String applicationName;

    private String fastSavingInfo;
    private boolean bottomSettingsResizable;


    private final File propertiesFile;

    public PbmDrawerProperties(String propertiesPath) throws IOException {
        properties = new Properties();

        propertiesFile = new File(propertiesPath);

        if (!propertiesFile.exists()) {
            throw new IOException("file " + propertiesFile + " doesn't exist!");
        }

        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(propertiesFile))) {
            properties.load(stream);
        } catch (IOException e) {
            throw new IOException("Cannot find load data from file " + propertiesFile);
        }

        loadProperties();
    }

    private void loadProperties() {
        gridColor = Color.decode(properties.getProperty("grid_color"));
        tileColor = Color.decode(properties.getProperty("tile_color"));
        drawerBackgroundColor = Color.decode(properties.getProperty("drawer_background_color"));
        mainBackgroundColor = Color.decode(properties.getProperty("main_background_color"));
        hintColor = Color.decode(properties.getProperty("hint_color"));
        textColor = Color.decode(properties.getProperty("text_color"));
        errorBorderColor = Color.decode(properties.getProperty("error_border_color"));
        clickedButtonColor = Color.decode(properties.getProperty("clicked_button_color"));

        iconFormat = properties.getProperty("icon_format");
        iconPath = properties.getProperty("icons_path");
        imageFormat = properties.getProperty("image_format");

        fastSavingDirectory = properties.getProperty("fast_saving_directory");
        saveDirectory = properties.getProperty("save_directory");

        gridRows = Integer.parseInt(properties.getProperty("grid_rows"));
        gridColumns = Integer.parseInt(properties.getProperty("grid_columns"));
        buttonIconSize = Integer.parseInt(properties.getProperty("button_icon_size"));
        resizeDialogWidth = Integer.parseInt(properties.getProperty("resize_dialog_width"));
        resizeDialogHeight = Integer.parseInt(properties.getProperty("resize_dialog_height"));
        windowSize = new Dimension(Integer.parseInt(properties.getProperty("window_width")),
                Integer.parseInt(properties.getProperty("window_height")));

        bottomSettingsResizable = properties.getProperty("bottom_settings_resizable").equals("true");

        fileDialogTitle = properties.getProperty("file_dialog_title");
        fastSavingFileName = properties.getProperty("fast_saving_filename");
        drawerWrapperBackground = Color.decode(properties.getProperty("drawer_wrapper_background"));
        applicationName = properties.getProperty("application_name");
        fastSavingInfo = properties.getProperty("fast_saving_info");

    }

    private void saveProperty(String key, String value) {
        properties.setProperty(key, value);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(propertiesFile))) {
            properties.store(writer, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
        saveProperty("icons_path", iconPath);
    }

    public String getIconFormat() {
        return iconFormat;
    }

    public void setIconFormat(String iconFormat) {
        this.iconFormat = iconFormat;
        saveProperty("icon_format", iconFormat);
    }

    public String getFastSavingDirectory() {
        return fastSavingDirectory;
    }

    public void setFastSavingDirectory(String fastSavingDirectory) {
        this.fastSavingDirectory = fastSavingDirectory;
        saveProperty("fast_saving_directory", fastSavingDirectory);
    }


    public Color getGridColor() {

        return gridColor;
    }

    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
        saveProperty("grid_color", ColorHelper.stringColor(gridColor));
    }

    public Color getTileColor() {
        return tileColor;
    }

    public void setTileColor(Color tileColor) {
        this.tileColor = tileColor;
        saveProperty("tile_color", ColorHelper.stringColor(tileColor));
    }

    public Color getDrawerBackgroundColor() {
        return drawerBackgroundColor;
    }

    public void setDrawerBackgroundColor(Color drawerBackgroundColor) {
        this.drawerBackgroundColor = drawerBackgroundColor;
        saveProperty("drawer_background_color", ColorHelper.stringColor(drawerBackgroundColor));
    }

    public Color getMainBackgroundColor() {
        return mainBackgroundColor;
    }

    public void setMainBackgroundColor(Color mainBackgroundColor) {
        this.mainBackgroundColor = mainBackgroundColor;
        saveProperty("main_background_color", ColorHelper.stringColor(mainBackgroundColor));
    }

    public Color getHintColor() {
        return hintColor;
    }

    public void setHintColor(Color hintColor) {
        this.hintColor = hintColor;
        saveProperty("hint_color", ColorHelper.stringColor(hintColor));
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        saveProperty("text_color", ColorHelper.stringColor(textColor));
    }

    public Color getErrorBorderColor() {
        return errorBorderColor;
    }

    public void setErrorBorderColor(Color errorBorderColor) {
        this.errorBorderColor = errorBorderColor;
        saveProperty("error_border_color", ColorHelper.stringColor(errorBorderColor));
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
        saveProperty("image_format", imageFormat);
    }

    public int getGridRows() {
        return gridRows;
    }

    public void setGridRows(int gridRows) {
        this.gridRows = gridRows;
        saveProperty("grid_rows", gridRows + "");
    }

    public int getGridColumns() {
        return gridColumns;
    }

    public void setGridColumns(int gridColumns) {
        this.gridColumns = gridColumns;
        saveProperty("grid_columns", gridColumns + "");
    }

    public String getSaveDirectory() {
        return saveDirectory;
    }

    public void setSaveDirectory(String saveDirectory) {
        this.saveDirectory = saveDirectory;
        saveProperty("save_directory", saveDirectory);
    }

    public int getButtonIconSize() {
        return buttonIconSize;
    }

    public void setButtonIconSize(int buttonIconSize) {
        this.buttonIconSize = buttonIconSize;
        saveProperty("button_icon_size", buttonIconSize + "");
    }

    public int getResizeDialogWidth() {
        return resizeDialogWidth;
    }

    public void setResizeDialogWidth(int resizeDialogWidth) {
        this.resizeDialogWidth = resizeDialogWidth;
        saveProperty("resize_dialog_width", resizeDialogWidth + "");
    }

    public int getResizeDialogHeight() {
        return resizeDialogHeight;
    }

    public void setResizeDialogHeight(int resizeDialogHeight) {
        this.resizeDialogHeight = resizeDialogHeight;
        saveProperty("resize_dialog_height", resizeDialogHeight + "");
    }

    public String getFileDialogTitle() {
        return fileDialogTitle;
    }

    public void setFileDialogTitle(String fileDialogTitle) {
        this.fileDialogTitle = fileDialogTitle;
        saveProperty("file_dialog_title", fileDialogTitle);
    }

    public String getFastSavingFileName() {
        return fastSavingFileName;
    }

    public void setFastSavingFileName(String fastSavingFileName) {
        this.fastSavingFileName = fastSavingFileName;
        saveProperty("fast_saving_filename", fastSavingFileName);
    }

    public Dimension getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(Dimension windowSize) {
        this.windowSize = windowSize;
        saveProperty("window_width", windowSize.getWidth() + "");
        saveProperty("window_height", windowSize.getHeight() + "");
    }

    public Color getClickedButtonColor() {
        return clickedButtonColor;
    }

    public void setClickedButtonColor(Color clickedButtonColor) {
        this.clickedButtonColor = clickedButtonColor;
        saveProperty("clicked_button_color", ColorHelper.stringColor(clickedButtonColor));
    }

    public Color getDrawerWrapperBackground() {
        return drawerWrapperBackground;
    }

    public void setDrawerWrapperBackground(Color drawerWrapperBackground) {
        this.drawerWrapperBackground = drawerWrapperBackground;
        saveProperty("drawer_wrapper_background", ColorHelper.stringColor(drawerWrapperBackground));
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        saveProperty("application_name", applicationName);
    }

    public String getFastSavingInfo() {
        return fastSavingInfo;
    }

    public void setFastSavingInfo(String fastSavingInfo) {
        this.fastSavingInfo = fastSavingInfo;
        saveProperty("fast_saving_info", fastSavingInfo);
    }

    public boolean isBottomSettingsResizable() {
        return bottomSettingsResizable;
    }

    public void setBottomSettingsResizable(boolean bottomSettingsResizable) {
        this.bottomSettingsResizable = bottomSettingsResizable;
        saveProperty("bottom_settings_resizable", bottomSettingsResizable ? "true" : "false");
    }
}
