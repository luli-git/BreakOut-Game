package edu.macalester.comp124.breakout;

import comp124graphics.GraphicsGroup;
import comp124graphics.Rectangle;
import java.awt.*;

/**
 * Draws the paddle and test if the ball hits the paddle.
 */
public class PaddleController extends GraphicsGroup {

    private static final int PADDLE_WIDTH = 80;
    private static final int PADDLE_HEIGHT = 20;

    protected Paddle drawPaddle(){
        Paddle littlePaddle = new Paddle(BreakoutGame.getCanvasWidth() * 0.5, BreakoutGame.getCanvasHeight() * 0.8, PADDLE_WIDTH,PADDLE_HEIGHT);
        add(littlePaddle);
        return littlePaddle;
    }



    /**
     * Test if the ball hits the paddle. If hit, divert it to the opposite direction.
     * @param ball
     */
    protected void testIfHitPaddle(Ball ball ){

        int diameter = ball.getBallRadius() * 2;

        double leftX = ball.getUpperLeftX();
        double rightX = ball.getUpperLeftX() + diameter;
        double upperY = ball.getUpperLeftY();
        double lowerY = ball.getUpperLeftY() + diameter;


        if ( getElementAt(leftX,upperY) instanceof Paddle)
        {

            ball.negativeAbsDY();
        }
        if (getElementAt(rightX,upperY) instanceof Paddle){

            ball.negativeAbsDY();


        }
        if (getElementAt(leftX,lowerY) instanceof Paddle){

            ball.negativeAbsDY();

        }
        if (getElementAt(rightX , lowerY) instanceof Paddle){

            ball.negativeAbsDY();

        }
    }


}




