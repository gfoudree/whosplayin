package com.Tree;

public class Main {

    public static void main(String[] args) {
        int dimensions = 3;
        int numberOfNodes = 40000;
        KDTree kdt = new KDTree(numberOfNodes, dimensions);
        double x1[] = {1,1,1};
        double x2[] = {2,2,2};

        kdt.add(x1,new User("Rick"));
        kdt.add(x2,new User("Jack"));


        double compare[] = {0,0,0};

        double d1 = getDistanceBetweenTwoPoints(x1,compare,3);
        double d2 = getDistanceBetweenTwoPoints(x2,compare,3);


        System.out.println(d1 + "\n" + d2);

        KdNode node = kdt.findNearest(compare);

        System.out.println(node.user.name + ":  " + node.toString());
    }

    public static double getDistanceBetweenTwoPoints(double[] x1, double[] x2, int dim) {
        double S = 0;
        for (int k = 0; k < dim; k++)
            S += (x1[k] - x2[k]) * (x1[k] - x2[k]);
        return S;
    }
}
