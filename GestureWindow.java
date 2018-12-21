package edu.macalester.comp124.breakout;


import comp124graphics.CanvasWindow;
import comp124graphics.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * The window and user interface for drawing gestures and automatically recognizing them
 * Created by bjackson on 10/29/2016.
 */
public class GestureWindow extends CanvasWindow implements ActionListener, KeyListener,MouseListener,MouseMotionListener{

    private Recognizer recognizer;
    private IOManager ioManager;
    private JButton addTemplateButton;
    private JTextField templateNameField;
    private JLabel matchLabel;
    private List<Point> path;
    private double startX,startY;
    private boolean dragged=false;

    public GestureWindow(){
        super("Gesture Recognizer", 600, 600);
        recognizer = new Recognizer();
        path = new ArrayList<>();
        ioManager = new IOManager();
        setupUI();
    }

    /**
     * Create the user interface
     */
    private void setupUI(){
        setLayout(new BorderLayout());
        matchLabel = new JLabel("Match: ");
        matchLabel.setFont(new Font("SanSerif", Font.PLAIN, 24));
        add(matchLabel, BorderLayout.NORTH);
        JPanel panel = new JPanel();
        templateNameField = new JTextField(10);
        panel.add(templateNameField);
        addTemplateButton = new JButton("Add Template");
        addTemplateButton.addActionListener(this);
        panel.add(addTemplateButton);
        add(panel, BorderLayout.SOUTH);
        templateNameField.addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        //tell java to start receiving mouse events
        revalidate();
    }

    /**
     * Handle what happens when the add template button is pressed. This method adds the points stored in path as a template
     * with the name from the templateNameField textbox. If no text has been entered then the template is named with "no name gesture"
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addTemplateButton){
            String name = templateNameField.getText();
            if (name.isEmpty()){
                name = "no name gesture";
            }
            recognizer.addTemplate(name, path); // Add the points stored in the path as a template
        }
    }


    //Add mouse listeners to allow the user to draw and add the points to the path variable.
    public void mouseExited(MouseEvent e){}

    /**
     * if dragged, give corresponding feedback
     * @param e
     */
    public void mouseReleased(MouseEvent e){
        if (dragged){
            String[] score=recognizer.giveFeedback(path);
            System.out.println(path.size()+"haha");
            if (score.length==1){
                matchLabel.setText(score[0]);
            }
            else {
                matchLabel.setText("Match: "+score[0]+" with confidence of "+score[1]);
            }
        }
        dragged=false;
    }
    public void mouseMoved(MouseEvent e){}

    /**
     * if drag the mouse, starts to paint.
     * @param e
     */
    public void mouseDragged(MouseEvent e){
        dragged=true;
        paint(startX,startY,e.getX(),e.getY());
        startX=e.getX();
        startY=e.getY();
    }
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}

    /**
     * If pressed, initialize a brush.
     * @param e
     */
    public void mousePressed(MouseEvent e){
        removeAll();
        matchLabel.setText("match:");
        path.clear();
        startX=e.getX();
        startY=e.getY();
        path.add(new Point(startX,startY));
    }

    /**
     * Key listener used to save and load gestures for debugging and to write tests.
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_L && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0){
            String name = templateNameField.getText();
            if (name.isEmpty()){
                name = "gesture";
            }
            List<Point> points = ioManager.loadGesture(name+".xml");
            if (points != null){
                recognizer.addTemplate(name, points);
                System.out.println("Loaded "+name);
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_S && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0){
            String name = templateNameField.getText();
            if (name.isEmpty()){
                name = "gesture";
            }
            ioManager.saveGesture(path, name, name+".xml");
            System.out.println("Saved "+name);
        }
    }


    /**
     * paint the input picture on the canvas
     * @param startX the coordinate of the first point which is recorded when pressed
     * @param startY the coordinate of the first point which is recorded when pressed
     * @param x the latest x
     * @param y the latest y
     */
    private void paint(double startX,double startY,double x,double y){
        Line line=new Line(startX,startY,x,y);
        Point point=new Point(x,y);
        path.add(point);
        line.setStrokeWidth(3);
        add(line);
    }


    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args){
        GestureWindow window = new GestureWindow();
    }
}

