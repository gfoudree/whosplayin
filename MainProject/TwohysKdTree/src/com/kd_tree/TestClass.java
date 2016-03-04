package com.kd_tree;

/**
 * Created by twohyjr on 3/3/16.
 */
public class TestClass {

    public void testNullKdNode(){
        KdTree tree = new KdTree(2,true);
        System.out.println(tree.head.toString());
    }

    public void runTestFindMin(){
        double[] point1 = {1,1};
        double[] point2 = {0,0};
        double[] point3 = {2,2};

        DataObject obj1 = new DataObject(point1);
        DataObject obj2 = new DataObject(point2);
        DataObject obj3 = new DataObject(point3);

        KdTree tree = new KdTree(2,true);
        tree.insert(obj1,tree.head,0);
        tree.insert(obj2,tree.head,0);
        tree.insert(obj3,tree.head,0);

        KdNode k = tree.findMin(tree.head,0);
        System.out.println(k.toString());
    }

    public void runTestPointDistance(){
        double[] point1 = {0,0};
        double[] point2 = {1,1};

        KdTree tree = new KdTree(2,true);

        double x = tree.getPointDist(point1,point2);
        System.out.println("EXP: 2  |  " + "ACT:" + x);
    }

    public void run2DTest(){
        double[] point1 = {30,40};
        double[] point2 = {5,25};
        double[] point3 = {70,70};
        double[] point4 = {10,12};
        double[] point5 = {50,30};
        double[] point6 = {35,45};

        DataObject obj1 = new DataObject(point1);
        DataObject obj2 = new DataObject(point2);
        DataObject obj3 = new DataObject(point3);
        DataObject obj4 = new DataObject(point4);
        DataObject obj5 = new DataObject(point5);
        DataObject obj6 = new DataObject(point6);

        KdTree tree = new KdTree(2,true);
        tree.insert(obj1,tree.head,0);
        tree.insert(obj2,tree.head,0);
        tree.insert(obj3,tree.head,0);
        tree.insert(obj4,tree.head,0);
        tree.insert(obj5,tree.head,0);
        tree.insert(obj6,tree.head,0);

        //EXP = expected
        //ACT = actual
        //H = head | L = Left | R = Right
        System.out.println("H    EXP:(30,40) " + "ACT:" + tree.head.toString());
        System.out.println("HL   EXP: (5,25) " + "ACT:" + tree.head.left.toString());
        System.out.println("HLL  EXP:(10,12) " + "ACT:" + tree.head.left.left.toString());
        System.out.println("HR   EXP:(70,70) " + "ACT:" + tree.head.right.toString());
        System.out.println("HRL  EXP:(50,30) " + "ACT:" + tree.head.right.left.toString());
        System.out.println("HRLL EXP:(35,45) " + "ACT:" + tree.head.right.left.left.toString());

    }

    public void run3DTest(){
        double[] point1 = {3,1,4};
        double[] point2 = {2,3,7};
        double[] point3 = {4,3,4};
        double[] point4 = {2,1,3};
        double[] point5 = {2,4,5};
        double[] point6 = {6,1,4};
        double[] point7 = {1,4,4};
        double[] point8 = {0,5,7};
        double[] point9 = {5,2,5};
        double[] point10 = {4,0,6};
        double[] point11 = {7,1,6};

        DataObject obj1 = new DataObject(point1);
        DataObject obj2 = new DataObject(point2);
        DataObject obj3 = new DataObject(point3);
        DataObject obj4 = new DataObject(point4);
        DataObject obj5 = new DataObject(point5);
        DataObject obj6 = new DataObject(point6);
        DataObject obj7 = new DataObject(point7);
        DataObject obj8 = new DataObject(point8);
        DataObject obj9 = new DataObject(point9);
        DataObject obj10= new DataObject(point10);
        DataObject obj11 = new DataObject(point11);

        KdTree tree = new KdTree(3,true);
        tree.insert(obj1,tree.head,0);
        tree.insert(obj2,tree.head,0);
        tree.insert(obj3,tree.head,0);
        tree.insert(obj4,tree.head,0);
        tree.insert(obj5,tree.head,0);
        tree.insert(obj6,tree.head,0);
        tree.insert(obj7,tree.head,0);
        tree.insert(obj8,tree.head,0);
        tree.insert(obj9,tree.head,0);
        tree.insert(obj10,tree.head,0);
        tree.insert(obj11,tree.head,0);

        //EXP = expected
        //ACT = actual
        //H = head | L = Left | R = Right
        System.out.println("H     EXP:(3,1,4) " + "ACT:" + tree.head.toString());
        System.out.println("HL    EXP:(2,3,7) " + "ACT:" + tree.head.left.toString());
        System.out.println("HLL   EXP:(2,1,3) " + "ACT:" + tree.head.left.left.toString());
        System.out.println("HLR   EXP:(2,4,5) " + "ACT:" + tree.head.left.right.toString());
        System.out.println("HLRL  EXP:(1.4.4) " + "ACT:" + tree.head.left.right.left.toString());
        System.out.println("HLRR  EXP:(0,5,7) " + "ACT:" + tree.head.left.right.right.toString());
        System.out.println("HR    EXP:(4,3,4) " + "ACT:" + tree.head.right.toString());
        System.out.println("HRL   EXP:(6,1,4) " + "ACT:" + tree.head.right.left.toString());
        System.out.println("HRLR  EXP:(5,2,5) " + "ACT:" + tree.head.right.left.right.toString());
        System.out.println("HRLRL EXP:(4,0,6) " + "ACT:" + tree.head.right.left.right.left.toString());
        System.out.println("HRLRR EXP:(7,1,6) " + "ACT:" + tree.head.right.left.right.right.toString());
    }

}
