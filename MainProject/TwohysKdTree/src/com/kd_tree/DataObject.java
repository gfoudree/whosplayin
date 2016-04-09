package com.kd_tree;

/**
 * Created by twohyjr on 3/3/16.
 * This class is used for creating a generic object with a location
 */
public class DataObject {

    private double[] point;

    public DataObject(double[] points){
        point = points.clone();
    }

    //-------Getters and Setters--------
    public double[] getPoint() {
        return point;
    }

    public double getPointAtIndex(int index){
        return point[index];
    }

}
