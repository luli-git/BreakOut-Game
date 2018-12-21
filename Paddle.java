package edu.macalester.comp124.breakout;

import comp124graphics.Rectangle;

import java.awt.*;

/**
 * Represents a paddle.
 */

public class Paddle extends Rectangle {

        public Paddle (double x, double y, double width, double height) {
            super(x, y, width, height);
            super.setFilled(true);
            super.setFillColor(new Color(0x44AFFF));
        }
}
