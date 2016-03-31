package com.Tree;

/**
 * Created by twohyjr on 3/7/16.
 */
public class KDTree {


    //TREE INFORMATION
    private KdNode HEAD; //The top of the tree
    private double minDimension; //The smallest dimension for bounds to be in
    private KdNode nearestNeighbor;  //Used by nearest neighbor to keep track of the current nearest
    private int dimensions;  //How many attributes can be in a point
    private int treeSize;  // The current size of the tree

    //NEAREST NEIGHBOR
    private KdNode CheckedNodes[]; //The list of nodes that have been checked.  This is too eliminate double checks.
    private int checkedNodes;  //If this number = the size of the tree than their are no more branches to check.

    //BOUNDS FOR NEAREST NEIGHBOR & INSERT CHECKS
    double xMin[], xMax[];
    boolean maxBoundary[], minBoundary[];
    int boundarySize;

    public KDTree(int i, int dimensions) {

        this.dimensions = dimensions;
        HEAD = null;
        treeSize = 0;
        CheckedNodes = new KdNode[i];
        maxBoundary = new boolean[dimensions];
        minBoundary = new boolean[dimensions];
        xMin = new double[dimensions];
        xMax = new double[dimensions];
    }

    public boolean add(double[] x, User user) {
        if ( treeSize >= 2000000 - 1){
            return false; // can't add more points
        }

        if (HEAD == null) {
            HEAD = new KdNode(x, 0, dimensions, user);
        } else {
            KdNode pNode;
            if ((pNode = HEAD.Insert(x,user)) != null) {
                pNode.user = user;
            }
        }

        return true;
    }

    //THE MOST IMPORTANT METHOD. USED FOR POINT COMPARISON TO PULL BACK THE CLOSEST DATA IN O(logn) time
    public KdNode findNearest(double[] x){
        if (HEAD == null)
            return null;

        checkedNodes = 0;
        KdNode parent = HEAD.FindParent(x);
        nearestNeighbor = parent;
        minDimension = HEAD.getDistanceBetweenTwoPoints(x, parent.point);

        if (parent.pointsAreEqual(x, parent.point, dimensions) == true)
            return nearestNeighbor;

        //Called to make sure that the parent node doesn't have another nearest neighbor
        searchParent(parent, x);

        //Resets the nodes that have been checked.
        uncheck();

        return nearestNeighbor;
    }

    //Used to check all of current nodes sub nodes
    private void checkSubtree(KdNode node, double[] point) {
        if ((node == null) || node.checked)
            return;

        CheckedNodes[checkedNodes++] = node;
        node.checked = true;
        setBoundingBox(node, point);

        int dim = node.axis;
        double d = node.point[dim] - point[dim];

        if (d * d > minDimension){
            if (node.point[dim] > point[dim]){
                checkSubtree(node.Left, point);
            }else{
                checkSubtree(node.Right, point);
            }

        } else{
            checkSubtree(node.Left, point);
            checkSubtree(node.Right, point);
        }
    }

    //Used for setting the bounding box around the neighbors of a point
    private void setBoundingBox(KdNode node, double[] point) {
        if (node == null){
            return;
        }
        int currentDimension = 0;
        double dx;
        for (int i = 0; i < dimensions; i++) {
            dx = node.point[i] - point[i];
            if (dx > 0){
                dx *= dx;
                if (!maxBoundary[i]){
                    if (dx > xMax[i]){
                        xMax[i] = dx;
                    }
                    if (xMax[i] > minDimension){
                        maxBoundary[i] = true;
                        boundarySize++;
                    }
                }
            } else {
                dx *= dx;
                if (!minBoundary[i]){
                    if (dx > xMin[i]){
                        xMin[i] = dx;
                    }
                    if (xMin[i] > minDimension){
                        minBoundary[i] = true;
                        boundarySize++;
                    }
                }
            }
            currentDimension += dx;
            if (currentDimension > minDimension){
                return;
            }
        }
        if (currentDimension < minDimension) {
            minDimension = currentDimension;
            nearestNeighbor = node;
        }
    }

    //USED FOR SEARCHING THROUGH THE PARENT OF A NODE
    private KdNode searchParent(KdNode parent, double[] point){
        resetParentBoundries();

        KdNode searchRoot = parent;
        while (parent != null && (boundarySize != dimensions * dimensions)){
            checkSubtree(parent, point);
            searchRoot = parent;
            parent = parent.Parent;
        }

        return searchRoot;
    }

    //Resets all of the arrays to have no max or min boundry for searching
    private void resetParentBoundries(){
        for (int i = 0; i < dimensions; i++) {
            xMin[i] = xMax[i] = 0;
            maxBoundary[i] = minBoundary[i] = false;
        }
        boundarySize = 0;
    }

    //UNCHECKS ALL NODES
    private void uncheck() {
        for (int n = 0; n < checkedNodes; n++) {
            CheckedNodes[n].checked = false;
        }
    }

    //This will find a node given a point in the tree.
    public KdNode find(double [] point){
        //TODO

        return null;
    }

    //This will remove the node passed in.
    public boolean removeNode(KdNode nodeToRemove){
        //TODO
        return false;
    }

    //This will take a list of points and find the node that is closest
    public KdNode getNearestNeighbors(double[] pointToCompare, int numberOfPointsToReturn){
        //TODO
        return null;
    }

}
