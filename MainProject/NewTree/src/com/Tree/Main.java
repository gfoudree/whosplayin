package com.Tree;

public class Main {

    public static void main(String[] args) {
        int dimensions = 3;
        int numberOfNodes = 40000;
        KDTree kdt = new KDTree(numberOfNodes, dimensions);
        double x1[] = {2.1,4.3,4};
        double x2[] = {3.3,1.5,800};
        double x3[] = {4.7,11.1,4};
        double x4[] = {5.0,12.3,4};
        double x5[] = {5.1,1.2,4};

        kdt.add(x1,new User("x1"));
        kdt.add(x2,new User("x2"));
        kdt.add(x3,new User("x3"));
        kdt.add(x4,new User("x4"));
        kdt.add(x5,new User("x5"));

        double compare[] = {0,0,0};

        double d1 = getDistanceBetweenTwoPoints(x1,compare,3);
        double d2 = getDistanceBetweenTwoPoints(x2,compare,3);
        double d3 = getDistanceBetweenTwoPoints(x3,compare,3);
        double d4 = getDistanceBetweenTwoPoints(x4,compare,3);
        double d5 = getDistanceBetweenTwoPoints(x5,compare,3);

        System.out.println(d1 + "\n" + d2 + "\n" + d3 + "\n" + d4 + "\n" + d5);

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
