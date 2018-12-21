package edu.macalester.comp124.breakout;


import java.awt.*;
import java.util.List;

/**
 * used to store path(a list of points) and name
 *
 * Created by ling 4.18
 */
public class Template {
    private String name;
    private List<Point> template;

    /**
     * a constructor to initialize template
     * @param name
     * @param template
     */
    public Template(String name, List<Point> template){
        this.name=name;
        this.template=template;
    }

    public String getName(){
        return name;
    }

    public List<Point> getTemplate(){return template;}

    @Override
    public String toString(){
        return "name of the template:"+name;
    }
}
