package proj4;

//Alec Marcum
//Comp 282 SP 2018
//Project 4 TestCases for Students
//Run proj4.Proj4AVLTreeDriver with your proj4.AVLTree class.
//Hand in driver and printout from driver with the hard copy
//of your project.

import proj4.AVLTree;

public class Proj4AVLTreeDriver
{

    public static void printTreeInfo(AVLTree bst)
    {

        System.out.println(     "size = " + bst.getSize()
                + "  height = " + bst.getHeight()
                + "  min value = " + bst.findMin()
                + "  max value = " + bst.findMax()
                + "  is height balanced? " + bst.isHeightBalanced() );

    }

    public static void testCase(String s, int[] values)
    {
        AVLTree<Integer>  bst = new AVLTree();
        System.out.println(s);
        for( int i = 0; i < values.length; i++)
            bst.insert(values[i]);
        printTreeInfo( bst);
        System.out.println("inOrder: " );
        bst.inOrderTraversal();
        System.out.println("preOrder: " );
        bst.preOrderTraversal();


    }

    public static void removeTestCase(String s, int[] values, int z)
    {
        AVLTree<Integer>  bst = new AVLTree();
        System.out.println(s);
        for( int i = 0; i < values.length; i++)
            bst.insert(values[i]);
        bst.remove(z);
        printTreeInfo( bst);
        System.out.println("inOrder: " );
        bst.inOrderTraversal();
        System.out.println("preOrder: " );
        bst.preOrderTraversal();



    }


    public static void main(String[] args)
    {

        //Testcase 1
        int[] values1 = {50,40,70,20,5};
        testCase( "\nTestCase 1" , values1);
        //TestCase 2
        int[] values2 = {30,10,50,40,45};
        testCase("\nTestCase 2" , values2);
        //TestCase 3
        int[] values3 = { 100,50,200,150,300,160};
        testCase("\nTestCase 3" , values3);

        //TestCase 4
        int[] values4 = { 50,60,20,30,10,70,80};
        testCase("\nTestCase 4" , values4);

        //TestCase 5
        int[] values5 = { 9,8,7,6,5,4,3,2,1};
        testCase("\nTestCase 5" , values5);

        //TestCase 6
        int [] values6 = {50, 20, 60, 10,30};
        int removeVal6 = 60;
        removeTestCase("\nTestCase 6", values6,removeVal6);

        //TestCase 7
        int [] values7 = {50, 20, 60, 10,30};
        int removeVal7 = 20;
        removeTestCase("\nTestCase 7", values7,removeVal7);

        //TestCase 8
        int [] values8 = {100,50, 300, 400,250,40,60,10,200,350,450,310,380};
        int removeVal8 = 100;
        removeTestCase("\nTestCase 8", values8,removeVal8);

        //TestCase 9   Uses strings
        System.out.println("\nTestcase 9");
        String[] svalues = {"apple", "banana", "grape","strawberries",  "lemon", "orange", "pear"};
        AVLTree<String> bst = new AVLTree<String>();
        for(int j = 0; j < svalues.length; j++)
            bst.insert(svalues[j]);
        printTreeInfo( bst);
        System.out.println("inOrder: " );
        bst.inOrderTraversal();
        System.out.println("preOrder: " );
        bst.preOrderTraversal();


    }


}
