package com.kd_tree;

/**
 * Created by twohyjr on 3/3/16.
 * This class represents an object in a KdTree with k dimensions of a point
 */
public class KdNode {

    private double[] point;

    public KdNode left;
    public KdNode right;

    public DataObject dataObject = null;

    public KdNode(double[] point){
        this.point = point.clone();
        this.left = null;
        this.right = null;
    }

    //--------Getters and Setters---------
    public double[] getPoint() {
        return point;
    }

    public double getPointAtIndex(int index){
        return point[index];
    }


    //-----TO STRING OVERRIDE-------
    @Override
    public String toString(){
        String string = "(";
        if(this == null){
            string = "This is a null KdNode";
        }else {
            for (int i = 0; i < point.length; i++) {
                string += (int) point[i];
                if (i != point.length - 1) {
                    string += ",";
                }
            }
        }
        string += ")";
        return string;
    }

}
