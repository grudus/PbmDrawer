package com.grudus.pbmdrawer.helpers;


import java.awt.*;

public class ColorHelper {

    public static String stringColor(Color color) {
        return "0x" + Integer.toHexString(color.getRGB() & 0xffffff);
    }

}
