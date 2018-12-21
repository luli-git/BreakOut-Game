//package edu.macalester.comp124.breakout;
//
//import comp124graphics.CanvasWindow;
//import comp124graphics.Ellipse;
//import comp124graphics.GraphicsGroup;
//import comp124graphics.Line;
//
//import java.awt.*;
//import java.lang.reflect.Array;
//import java.util.*;
//import java.util.List;
//
///**
// * Recognizer to recognize 2D gestures. Uses the $1 gesture recognition algorithm.
// */
//public class Recognizer {
//    private final double SIZE=100;
//    private final int TEMPLATE_SIZE=64;
//    private List<Template> templates;
//    /**
//     * Constructs a recognizer object
//     */
//    public Recognizer(){
//        templates=new ArrayList<>();
//    }
//
//    /**
//     * Process the path passed in and give back the score as a string array
//     * @param path
//     * @return a string that contains the name of the template and the corresponding score
//     */
//    public String[] giveFeedback(List<Point> path){
//        List<Point> standardInput=processInput(path);
//        return score(standardInput);
//    }
//
//    /**
//     * process a path into a standard form
//     * @param path
//     * @return
//     */
//    private List<Point> processInput(List<Point> path){
//        List<Point> list=resizedList(path,TEMPLATE_SIZE);
//        double a=findTheta(findCenter(list),list);
//        list=roratedList(list,-a);
//        list=scaleTo(list);
//        list=translateTo(list);
//        return list;
//    }
//
//    /**
//     * Create a template to use for matching
//     * @param name of the template
//     * @param points in the template gesture's path
//     */
//    public void addTemplate(String name, List<Point> points){
//        List<Point> standardList=processInput(points);
//        Template t=new Template(name,standardList);
//        templates.add(t);
//        // process the points and add them as a template. Use Decomposition!
//    }
//
//    // Add recognize and other processing methods here
//
//    /**
//     * find the best template which has the smallest error
//     * @param list
//     * @return
//     */
//    private Template matchTemplate(List<Point> list){
//        Template matched;
//        double optiDis;
//        Iterator<Template> iter=templates.iterator();
//        if (!iter.hasNext()){matched=null;}
//        else {
//            matched=iter.next();
//            optiDis=distanceAtBestAngle(list,matched.getTemplate());
//            while (iter.hasNext()){
//                Template curT=iter.next();
//                if (optiDis>distanceAtBestAngle(list,curT.getTemplate())){
//                    matched=curT;
//                }
//            }
//        }
//        return matched;
//    }
//
//    /**
//     * return a score for the list. return "no stored" if no templates was stored yet.
//     * @param list
//     * @return
//     */
//    private String[] score(List<Point> list){
//        Template matched=matchTemplate(list);
//        String[] ans;
//        if (matched==null){
//            ans=new String[]{"No stored template. Add one bellow."};
//        }
//        else {
//            double distance=distanceAtBestAngle(list,matched.getTemplate())/TEMPLATE_SIZE;
//            distance=1-(distance/((0.5)*Math.sqrt(2*Math.pow(SIZE,2))));
//            String name=matched.getName();
//            ans=new String[]{name,String.valueOf(distance)};
//        }
//        return ans;
//    }
//
//
//    /**
//     * Uses a golden section search to calculate rotation that minimizes the distance between the gesture and the template points.
//     * @param points
//     * @param templatePoints
//     * @return best distance
//     */
//    private double distanceAtBestAngle(List<Point> points, List<Point> templatePoints){
//        double thetaA = -Math.toRadians(45);
//        double thetaB = Math.toRadians(45);
//        final double deltaTheta = Math.toRadians(2);
//        double phi = 0.5*(-1.0 + Math.sqrt(5.0));// golden ratio
//        double x1 = phi*thetaA + (1-phi)*thetaB;
//        double f1 = distanceAtAngle(points, templatePoints, x1);
//        double x2 = (1 - phi)*thetaA + phi*thetaB;
//        double f2 = distanceAtAngle(points, templatePoints, x2);
//        while(Math.abs(thetaB-thetaA) > deltaTheta){
//            if (f1 < f2){
//                thetaB = x2;
//                x2 = x1;
//                f2 = f1;
//                x1 = phi*thetaA + (1-phi)*thetaB;
//                f1 = distanceAtAngle(points, templatePoints, x1);
//            }
//            else{
//                thetaA = x1;
//                x1 = x2;
//                f1 = f2;
//                x2 = (1-phi)*thetaA + phi*thetaB;
//                f2 = distanceAtAngle(points, templatePoints, x2);
//            }
//        }
//        return Math.min(f1, f2);
//    }
//
//    /**
//     * return the distance at an angle given by 2 lists with a given theta
//     * @param points
//     * @param templatePoints
//     * @param theta
//     * @return
//     */
//    private double distanceAtAngle(List<Point> points, List<Point> templatePoints, double theta){
//        //Uncomment after rotate method is implemented
//        List<Point> rotatedPoints = roratedList(points, theta);
//        return pathDistance(rotatedPoints, templatePoints);
//    }
//
//    /**
//     * return the sum of distance between 2 lists of points
//     * @param a
//     * @param b
//     * @return
//     */
//    private double pathDistance(List<Point> a, List<Point> b){
//        double ans=0;
//        Iterator<Point> a1=a.iterator();
//        Iterator<Point> b1=b.iterator();
//        while (a1.hasNext()){
//            ans+=a1.next().distance(b1.next());
//        }
//
//        return ans;
//    }
//
//    /**
//     * scale a list to the standard size
//     * @param list
//     * @return
//     */
//    public List<Point> scaleTo(List<Point> list){
//        List<Point> ans=new ArrayList<>(list.size());
//        double[] ratioSet= boxBound(list);
//        double rH=ratioSet[1];
//        double rW=ratioSet[0];
//        Iterator<Point> iterator=list.iterator();
//        while (iterator.hasNext()){
//            Point cur=iterator.next();
//            ans.add(cur.scale(rW,rH));
//        }
//        return ans;
//    }
//
//    /**
//     * return the scale ratio of a list in order to scale the list into a standard size
//     * @param list
//     * @return
//     */
//    public double[] boxBound(List<Point> list){
//        Iterator<Point> iter=list.iterator();
//        Point min,max;
//        Point pre=iter.next();
//        min=pre;
//        max=pre;
//        while (iter.hasNext()){
//            Point cur=iter.next();
//            min=Point.min(min,cur);
//            max=Point.max(max,cur);
//        }
//        return new double[]{SIZE/(max.getX()-min.getX()),SIZE/(max.getY()-min.getY())};
//    }
//
//    /**
//     * pin the graph to the origin (0,0)
//     * @param list
//     * @return
//     */
//    public List<Point> translateTo(List<Point> list){
//        List<Point> ans=new ArrayList<>(list.size());
//        Point center=findCenter(list);
//        Iterator<Point> iter=list.iterator();
//        while (iter.hasNext()){
//            ans.add(iter.next().subtract(center));
//        }
//        return ans;
//    }
//
//
//
//    /**
//     * find the center of a list
//     * @param list
//     * @return
//     */
//    public Point findCenter(List<Point> list){
//        Iterator<Point> iter=list.iterator();
//        double x=0;
//        double y=0;
//        while (iter.hasNext()){
//            Point cur=iter.next();
//            x+=cur.getX();
//            y+=cur.getY();
//        }
//        return new Point(x/list.size(),y/list.size());
//    }
//
//    /**
//     * find the theta from a given list.
//     * @param center
//     * @param list
//     * @return
//     */
//    public double findTheta(Point center,List<Point> list){
//        return center.subtract(list.get(0)).angle();
//    }
//
//    /**
//     * return a list of rotated list
//     * @param list
//     * @return
//     */
//    public List<Point> roratedList(List<Point> list,double a){
//        List<Point> ans=new ArrayList<>(list.size());
//        Point center=findCenter(list);
//        Iterator<Point> iter=list.iterator();
//        while (iter.hasNext()){
//            ans.add(iter.next().rotate(a,center));
//        }
//        return ans;
//    }
//
//
//    /**
//     * calculate the length of the path
//     * @param list
//     * @return
//     */
//    public double pathLength(List<Point> list){
//        double ans=0;
//        Point pre,cur;
//        Iterator<Point> iter=list.iterator();
//        pre=iter.next();
//        while (iter.hasNext()){
//            cur=iter.next();
//            ans+=pre.distance(cur);
//            pre=cur;
//        }
//        return ans;
//    }
//
//    /**
//     * resize the inputlist with n points
//     * @param inputList
//     * @param n
//     * @return the resized list
//     */
//    public ArrayList<Point> resizedList(List<Point> inputList,int n){
//        double totalLength=pathLength(inputList);
//        double interval=totalLength/(n-1);
//        double accum=0;
//        int index=0;
//        ArrayList<Point> list= new ArrayList<>(n);//resampledPoint
//        list.add(inputList.get(0));
//        Point cur=inputList.get(index);
//        Point next=inputList.get(index+1);
//        while (list.size()<n-1){
//            double distanceBt2=cur.distance(next);
//            if ((accum+distanceBt2)>=interval){
//                Point newPoint= Point.interpolate(cur,next,
//                        (interval-accum)/distanceBt2);
//                list.add(newPoint);
//                inputList.add(index+1,newPoint);
//                accum=0;
//            }
//            else {accum+=inputList.get(index).distance(inputList.get(index+1)); }
//            index++;
//            cur=inputList.get(index);
//            next=inputList.get(index+1);
////            if (inputList.get(inputList.size()-1)==next){
////                list.add(next);
////                break;
////            }
//        }
//        list.add(inputList.get(inputList.size()-1));
//        return list;
//    }
//
////    public Result recognize(List<Point> inputPoints){
////        List<Point> processedInputPoints = processPoints(inputPoints);
////        double currentD = distanceAtBestAngle(processedInputPoints, templates.get(0).getTemplate());
////        Template result = templates.get(0);
////        double minD = currentD;
////        for (Template T: templates)
////        {
////            currentD = distanceAtBestAngle(inputPoints, T.getTemplate());
////            if (currentD < minD){
////                minD = currentD;
////
////                result = T;
////            }
////        }
////        double score = 1 - minD/(0.5* Math.hypot(SIZE,SIZE));
////        Result match = new Result(result.getName(), score);
////        return match;
////
////    }
//
//}