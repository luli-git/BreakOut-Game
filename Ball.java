package edu.macalester.comp124.breakout;

import comp124graphics.Ellipse;
import java.awt.*;
import java.util.Random;

/**
 * Represents a ball that moves in a straight trajectory, when released, will go to either up or down.
 * It will bounce off/ deflect if hit a brick or a paddle .
 */

public class Ball extends Ellipse {
    private static final int BALL_RADIUS = 5;
    private static double dX = 0.8;
    private static double dY = 1.6;

    private double upperLeftX, upperLeftY, width, height;

    public Ball(double upperLeftX, double upperLeftY, int width, int height) {
        super(upperLeftX, upperLeftY, BALL_RADIUS * 2, BALL_RADIUS * 2);
        this.upperLeftX = upperLeftX;
        this.upperLeftY = upperLeftY;
        this.width = BALL_RADIUS * 2;
        this.height = BALL_RADIUS * 2;
        super.setFilled(true);
        super.setFillColor(new Color(0x010101));

    }

    /**
     * Release the ball, half the times it goes up at an angle, half the times it goes down at an angle.
     */

    protected void releaseBall(){
        if (percentChance(50)){
            return;
        }
        else {
            negativeDY();
        }
    }

    private boolean percentChance(double chance) {
        Random random = new Random();
        return random.nextDouble() * 100 < chance;
    }


    /**
     * update the position of the ball in a linear motion movement.
     */

    public void updateBallPosition() {
        move(dX,dY);
        upperLeftX += dX;
        upperLeftY += dY;
    }



    /**
     * Two methods that change the signs of dx or dy.
     */
    protected void negativeDY(){
        dY = - dY;
    }
    protected void negativeDX(){
        dX = - dX;
    }
    protected void negativeAbsDY(){
        dY = - Math.abs(dY);
    }

    /**
     * Getter methods.
     * @return
     */
    public static int getBallRadius() {
        return BALL_RADIUS;
    }

    public static double getdX() {
        return dX;
    }

    public static double getdY() {
        return dY;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public double getUpperLeftX() {
        return upperLeftX;
    }

    public double getUpperLeftY() {
        return upperLeftY;
    }
}