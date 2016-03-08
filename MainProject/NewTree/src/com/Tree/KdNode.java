package com.Tree;

/**
 * Created by twohyjr on 3/7/16.
 */
public class KdNode {
    //The axis can be x,y,z...etc
    int axis;
    int dim;

    //This is the point that this node stores
    double[] point;

    User user;
    boolean checked;
    boolean orientation;

    KdNode Parent;
    KdNode Left;
    KdNode Right;

    public KdNode(double[] x0, int axis0, int dim, User user) {
        this.dim = dim;
        point = new double[dim];
        axis = axis0;
        for (int k = 0; k < dim; k++){
            point[k] = x0[k];
        }
        Left = Right = Parent = null;
        checked = false;
        this.user = user;
    }

    public KdNode FindParent(double[] x0){
        KdNode parent = null;
        KdNode next = this;
        int split;
        while (next != null)
        {
            split = next.axis;
            parent = next;
            if (x0[split] > next.point[split])
                next = next.Right;
            else
                next = next.Left;
        }
        return parent;
    }

    public KdNode Insert(double[] newPoint, User user) {
        KdNode parent = FindParent(newPoint);

        if (pointsAreEqual(newPoint, parent.point, dim) == true){
            return null;
        }
        KdNode newNode = new KdNode(newPoint, parent.axis + 1 < dim ? parent.axis + 1: 0, dim,user);
        newNode.Parent = parent;
        if (newPoint[parent.axis] > parent.point[parent.axis]){
            parent.Right = newNode;
            newNode.orientation = true;
        }else{
            parent.Left = newNode;
            newNode.orientation = false;
        }
        return newNode;
    }

    boolean pointsAreEqual(double[] points1, double[] points2, int dim) {
        for (int i = 0; i < dim; i++) {
            if (points1[i] != points2[i])
                return false;
        }
        return true;
    }

    double getDistanceBetweenTwoPoints(double[] x1, double[] x2) {
        double S = 0;
        for (int k = 0; k < dim; k++)
            S += (x1[k] - x2[k]) * (x1[k] - x2[k]);
        return S;
    }


    @Override
    public String toString(){
        String returnString = "(";
        for(int i = 0; i < point.length; i++){
            returnString += point[i];
            if(i != point.length - 1){
                returnString += ",";
            }
        }
        returnString += ")";
        return returnString;
    }
}
