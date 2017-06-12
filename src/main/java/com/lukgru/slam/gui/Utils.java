package com.lukgru.slam.gui;

import com.lukgru.slam.robot.Position;

/**
 * Created by Łukasz on 2017-06-12.
 */
class Utils {

    public static Position fromPixels(double x, double y) {
        int worldX = (int) Math.round(x / 7);
        int worldY = (int) Math.round(y / 7);
        return new Position(worldX,worldY);
    }
}
