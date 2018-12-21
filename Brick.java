package edu.macalester.comp124.breakout;

import comp124graphics.Rectangle;

/**
 * Represents a brick that is a rectangle.
 */

public class Brick extends Rectangle {
    public Brick(double x, double y, double width, double height) {
        super(x, y, width, height);
        super.setFilled(true);

    }



}

