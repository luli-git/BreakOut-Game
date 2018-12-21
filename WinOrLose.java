package edu.macalester.comp124.breakout;

/**
 * A class containing the logic of the game. If the ball is below the paddle, which mean the user fails to catch the ball,
 * they lose one round.
 *
 */
public class WinOrLose {
    public boolean winOrLose(Ball ball, Paddle paddle){

        if ((ball.getUpperLeftY() + ball.getBallRadius() * 2 ) < (paddle.getY() + paddle.getHeight())){

            return true;
        }
        else {

            return false;

        }
    }
}
