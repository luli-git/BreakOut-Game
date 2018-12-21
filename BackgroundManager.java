package edu.macalester.comp124.breakout;

import comp124graphics.GraphicsGroup;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A wall class containing the bricks, the frame. It detects if the ball hits bricks or the walls. Also, it detects if
 * there's any bricks left.
 */
public class BackgroundManager extends GraphicsGroup {
    private static final int BRICK_WIDTH = 80;
    private static final int BRICK_HEIGHT = 20;
    private static final int GAP_BETWEEN_BRICKS = 15;
    private Color colors[];
    private List<Brick> bricks;


    /**
     * draws 10 lines of bricks.
     */
    protected void drawBricks() {
        Color first = new Color(0x8151FF);
        Color second = new Color(0x8D66FF);
        Color third = new Color(0xA888FF);
        Color fourth = new Color(0xA892FF);
        Color fifth = new Color(0xCDBDFF);

        colors = new Color[5];
        colors[0] = first;
        colors[1] = second;
        colors[2] = third;
        colors[3] = fourth;
        colors[4] = fifth;

        int currentX = 18;
        int currentY = 18;
        bricks = new ArrayList<>();


        for (int i = 0; i < 10; i++) {


            while (currentX + BRICK_WIDTH + GAP_BETWEEN_BRICKS < BreakoutGame.getCanvasWidth()) {
                Brick newBrick = new Brick(currentX, currentY, BRICK_WIDTH, BRICK_HEIGHT);
                currentX += BRICK_WIDTH + GAP_BETWEEN_BRICKS;
                newBrick.setFillColor(colors[i / 2]);
                add(newBrick);
                bricks.add(newBrick);
            }
            currentY += BRICK_HEIGHT + 10;
            currentX = 18;
        }
    }
    protected boolean bricksStillExist(){

            return bricks.size() > 0 ;
        }


    /**
     * Check if a ball collides with a brick and which corner of the ball is hitting the brick,
     * then change direction accordingly.
     * @param ball
     */

    protected void ifIntersectWithBrick(Ball ball){

        int diameter = ball.getBallRadius() * 2;
        double leftX = ball.getUpperLeftX();
        double rightX = ball.getUpperLeftX() + diameter;
        double upperY = ball.getUpperLeftY();
        double lowerY = ball.getUpperLeftY() + diameter;

        if (getElementAt(leftX,upperY) instanceof Brick) {
            bricks.remove(getElementAt(leftX,upperY));
            remove(getElementAt(leftX,upperY));
            ball.negativeDY();
        }

        if (getElementAt(rightX,upperY) instanceof Brick ){

            bricks.remove(getElementAt(rightX,upperY));
            remove(getElementAt(rightX,upperY));


            ball.negativeDY();
        }

        if (getElementAt(leftX,lowerY) instanceof Brick ){

            bricks.remove(getElementAt(leftX,lowerY));
            remove(getElementAt(leftX,lowerY ));
            ball.negativeDY();

        }

        if (getElementAt(rightX , lowerY)instanceof Brick){
            bricks.remove(getElementAt(rightX,lowerY));
            remove(getElementAt(rightX , lowerY));
            ball.negativeDY();

        }
    }
    /**
     * Test if the ball hit the frame of the wall. If hit the vertical wall, time dx with (-1);
     * if hit the upper horizontal wall, time dy with (-1). If it hits the bottom wall, remove the ball.
     */

    protected void testIfBallHitWall(Ball ball){
        if (ball.getUpperLeftX() + ball.getBallRadius() * 2 >= BreakoutGame.getCanvasWidth() || ball.getUpperLeftX() <= 0){
            ball.negativeDX();
        }

        if (ball.getUpperLeftY() <= 0){
            ball.negativeDY();
        }
    }
}
