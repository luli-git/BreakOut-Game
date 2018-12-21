package edu.macalester.comp124.breakout;


import comp124graphics.CanvasWindow;
import comp124graphics.GraphicsText;
import comp124graphics.Rectangle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Main program for the breakout game.
 *
 */
public class BreakoutGame extends CanvasWindow implements MouseMotionListener {

    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 700;
    private static final int NUM_OF_LIVES = 3;

    private PaddleController paddleController;
    private Ball newBall ;
    private BackgroundManager bricks;
    private Paddle paddle;

    WinOrLose winOrLose = new WinOrLose();


    private BreakoutGame() {
        super("Breakout!", CANVAS_WIDTH, CANVAS_HEIGHT);

    }

    private void run(){
        resetGame();

    }

    /**
     * Resets the game.
     */
    private void resetGame(){
        removeAll();
        bricks = new BackgroundManager();
        bricks.drawBricks();
        add(bricks);

//        drawFrame();

        paddleController = new PaddleController();
        paddle = paddleController.drawPaddle();
        add(paddle);
        add(paddleController);


        addMouseMotionListener(this);

        drawBall();


    }

    /**
     * Draws the ball that starts to move after a pause.
     */
    private void drawBall(){
        newBall = new Ball(CANVAS_WIDTH * 0.5, CANVAS_HEIGHT * 0.5,newBall.getBallRadius() * 2,newBall.getBallRadius() * 2);
        add(newBall);
        pause(6000);
        activateBall();
        testLives();
    }
    int cnt = 0;

    /**
     * Activates the ball to move and while it's moving, test if it hits the wall or the bricks,
     * and how many bricks are still left.
     */
    private void activateBall(){
        newBall.releaseBall();
        while (winOrLose.winOrLose(newBall, paddle)){

            newBall.updateBallPosition();
            bricks.testIfBallHitWall(newBall);

            pause(5);

            paddleController.testIfHitPaddle(newBall);

            bricks.ifIntersectWithBrick(newBall);
            if ( !bricks.bricksStillExist()){
                pause(3000);
                drawWinBox();
                pause(5000);
            }

        }
    }
    /**
     * Tests how many lives a user still has.
     */
    private void testLives(){
        int livesLeft = countLivesLeft();

        if (livesLeft >= 0){
            drawLivesLeftNotificationBox(livesLeft);
            drawBall();
//            resetGame();

        }
        else {
            drawLoseBox();
            pause(7000);
        }

    }
    /**
     * Draws a box that pumps out every time the ball hits the lower wall,
     * notifying the user how many lives they still have.
     * @param numLeft
     */
    private void drawLivesLeftNotificationBox(int numLeft){
        GraphicsText win = new GraphicsText("Number of lives you have: " + numLeft + ".", CANVAS_WIDTH/2 - 80 , CANVAS_HEIGHT/2 - 20);
        Rectangle winBox = new Rectangle(CANVAS_WIDTH/2 - 200,CANVAS_HEIGHT/2 - 100, 400,200);

        winBox.setFillColor(new Color(0xA8FF4A));
        winBox.setFilled(true);
        add(winBox);
        add(win);
        pause(5000);
        remove(win);
        remove(winBox);
        remove(newBall);


    }

    /**
     * Counts how many shot a user still has after their ball drops below the paddle.
     * @return
     */
    private int countLivesLeft(){

        if (!winOrLose.winOrLose(newBall, paddle)){
            cnt++;
        }
        int numLeft = NUM_OF_LIVES - cnt;
        return numLeft;
    }

    /**
     * Draws the box that pumps out if a user wins the game.
     */
    private void drawWinBox(){
        GraphicsText win = new GraphicsText("You Win!", CANVAS_WIDTH/2 - 80 , CANVAS_HEIGHT/2 - 20);
        Rectangle winBox = new Rectangle(CANVAS_WIDTH/2 - 200,CANVAS_HEIGHT/2 - 100, 400,200);

        winBox.setFillColor(new Color(0x8BDFFF));
        winBox.setFilled(true);
        add(winBox);
        add(win);
    }

    /**
     * Draws the box that pumps out if a user loses the game.
     */
    private void drawLoseBox(){
        GraphicsText lose = new GraphicsText("You Lose!", CANVAS_WIDTH/2 - 80 , CANVAS_HEIGHT/2 - 20);
        Rectangle loseBox = new Rectangle(CANVAS_WIDTH/2 - 200,CANVAS_HEIGHT/2 - 100, 400,200);

        loseBox.setFillColor(new Color(0xFFAD10));
        loseBox.setFilled(true);
        add(loseBox);
        add(lose);

    }

//    private void drawFrame(){
//        Rectangle frame = new Rectangle(5,5,790,745);
//        frame.setStrokeWidth(3);
//        add(frame);
//    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }
    @Override
    public void mouseMoved(MouseEvent e) {

        if (e.getX() <= CANVAS_WIDTH - paddle.getWidth() && e.getY() <= CANVAS_HEIGHT - paddle.getHeight()){
            paddle.setPosition(e.getX(), CANVAS_HEIGHT * 0.8 );
            paddleController.testIfHitPaddle(newBall);
        }
    }
    public static int getCanvasWidth() {
        return CANVAS_WIDTH;
    }

    public static int getCanvasHeight() {
        return CANVAS_HEIGHT;
    }
    public Ball getNewBall() {
        return newBall;
    }

    public BackgroundManager getBricks() {
        return bricks;
    }

    public Paddle getPaddle() {
        return paddle;
    }


    public static void main (String[] args){
        BreakoutGame prog = new BreakoutGame();
        prog.run();
    }
}
