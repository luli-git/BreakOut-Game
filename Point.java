package edu.macalester.comp124.breakout;

import java.awt.*;
import java.util.Objects;

/**
 * An immutable 2D point and associated helper methods.
 */
public final class Point {
    public static final int RADIUS = 10;
    public static final Color color = Color.CYAN;


    public static final Point
            MIN = new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY),
            MAX = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);


    private final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * @return the angle (in radians) formed between the x-axis and the vector from the origin (0,0) to this point.
     */
    public double angle() {
        return Math.atan2(y, x);
    }

    /**
     * Returns the distance between this point and p.
     * @param p
     * @return distance
     */
    public double distance(Point p) {
        return Math.hypot(p.x - x, p.y - y);
    }

    /**
     * Returns a new point made by adding this point's x and y coordinates to point p's x and y coordinates.
     * @param p
     * @return
     */
    public Point add(Point p) {
        return new Point(x + p.x, y + p.y);
    }

    /**
     * Returns a new point made by subtracting point p's x and y coordinates from this point's x and y coordinates.
     * This method can be used to shift/recent a point with point p being shifted to the origin (0,0)

     * @param p
     * @return
     */
    public Point subtract(Point p) {
        return new Point(x - p.x, y - p.y);
    }

    /**
     * scales a point up (or down) by multiplying the x and y coordinates by the s value
     *
     * @param s
     * @return
     */
    public Point scale(double s) {
        return scale(s, s);
    }

    /**
     * scales a point up (or down) by multiplying the x and y coordinates by their respective s values
     *
     * @param sx scaling factor for the x variable
     * @param sy scaling factor for the y variable
     * @return
     */

    public Point scale(double sx, double sy) {
        return new Point(x * sx, y * sy);
    }

    /**
     * Returns a point where the x value is the minimum x value between p0 and p1 and the y value is the minimum y value between the two points.
     * @param p0
     * @param p1
     * @return
     */
    public static Point min(Point p0, Point p1) {
        return new Point(
                Math.min(p0.x, p1.x),
                Math.min(p0.y, p1.y));
    }

    /**
     * Returns a point where the x value is the maximum x value between p0 and p1 and the y value is the maximum y value between the two points.
     * @param p0
     * @param p1
     * @return
     */
    public static Point max(Point p0, Point p1) {
        return new Point(
                Math.max(p0.x, p1.x),
                Math.max(p0.y, p1.y));
    }

    /**
     * Returns the point rotated around the origin (0,0) by angle
     * @param angle in radians
     * @return
     */
    public Point rotate(double angle) {
        return new Point(
                getX() * Math.cos(angle) - getY() * Math.sin(angle),
                getX() * Math.sin(angle) + getY() * Math.cos(angle));
    }

    /**
     * Returns the point rotated around the provided center point by angle
     * @param angle in radians
     * @param center
     * @return
     */
    public Point rotate(double angle, Point center) {
        return subtract(center)
                .rotate(angle)
                .add(center);
    }

    /**
     * Creates a point on the line between input point p0 and input point p1.
     * The alpha parameter is used to describe where the returned point should be along
     * the line between point p0 and point p1, with small (close to 0) values of alpha
     * meaning the returned point should be close to point p0 and large (close to 1)
     * values of alpha meaning the returned point should be close to p1.
     *
     * (Mathematically this is called linearly interpolating between p0 and p1, hence
     * the method name). See https://en.wikipedia.org/wiki/Linear_interpolation for
     * more information and a nice visualization of the basic idea.
     *
     * @param p0
     * @param p1
     * @param alpha a number between 0 and 1 indicating where the returned point should
     *              lie along the line between point p0 and point p1.
     * @return
     */
    public static Point interpolate(Point p0, Point p1, double alpha) {
        return p0.add(p1.subtract(p0).scale(alpha));
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Point that = (Point) o;
        return Double.compare(this.x, that.x) == 0
                && Double.compare(this.y, that.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
