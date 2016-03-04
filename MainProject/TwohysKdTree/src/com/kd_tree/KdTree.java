package com.kd_tree;

/**
 * Created by twohyjr on 3/3/16.
 */
public class KdTree {

    private int dimension;
    public KdNode head;
    public int size;

    private boolean checkSimilar; //True will toggle checking similar on.

    public KdTree(int dimension, boolean checkSimilar){
        this.dimension = dimension;
        this.checkSimilar = checkSimilar;
        this.head = null;
    }

    /*
      The insert method adds a node with data into the tree.
     */
    public KdNode insert(DataObject obj, KdNode node, int cutDimension){

        if(this.head == null){
            node = new KdNode(obj.getPoint());
            node.dataObject = obj;
            this.head = node;
        }else if(node == null){
            node = new KdNode(obj.getPoint());
        }else if(checkSimilarPoints(obj.getPoint(),node.getPoint())){
            System.out.println("duplicate point exists");
        }else if(obj.getPointAtIndex(cutDimension) < node.getPointAtIndex(cutDimension)){
            node.left = (insert(obj,node.left,(cutDimension + 1) % dimension));
            node.dataObject = obj;
        }else{
            node.right = (insert(obj,node.right,(cutDimension + 1) % dimension));
            node.dataObject = obj;
        }
        size++;
        return node;
    }

    public KdNode findMin(KdNode node, int cutDimension){
        if(node == null){
            return null;
        }
        //If the dimension we are searching = the cut dimension only search the left subtree
        if(cutDimension == dimension){
            if(node.left == null){
                return node;
            }else{
                return findMin(node.left,(cutDimension + 1) % dimension);
            }
        }else{

            KdNode leftMin = findMin(node.left,(cutDimension+1) % dimension);
            KdNode rightMin = findMin(node.right,(cutDimension+1) % dimension);

            double[] zeroVector = {0,0};
            double distLeft = getPointDist(zeroVector,leftMin.getPoint());
            double distRight = getPointDist(zeroVector,rightMin.getPoint());

            if(distLeft < distRight){
                return leftMin;
            }else{
                return rightMin;
            }
        }
    }

    public double getPointDist(double[] p1, double[] p2) {
        double distance = 0;
        if(p1 == null || p2 == null){
            return 0;
        }
        //simple euclidean distance finder
        for (int i = 0; i < p1.length; i++) {
            double diff = (p1[i] - p2[i]);
            if (!Double.isNaN(diff)) {
                distance += diff * diff;
            }
        }
        return distance;
    }


    //This is used for seeing if a point already exists
    //Can be toggled on and off with the boolean "checkSimilar"
    public boolean checkSimilarPoints(double[] point1, double[] point2){
        if(point1.length != point2.length){
            System.out.println("ERROR: Vectors are of two different sizes!");
            return true;
        }
        if(checkSimilar) {
            for (int i = 0; i < point1.length; i++) {
                if (point1[i] != point2[i]) {
                    return false;
                }
            }
        }
        return  true;
    }

}
