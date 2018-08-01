package io.github.arami265;

/**
 * Hello world!
 * Author: Arnold Ramirez
 *
 * Originally written in C++ for CSCI 3333: Algorithms and Data Structures
 * for Dr. Chen at UTRGV
 *
 * Inspired by information found at
 * https://en.wikipedia.org/wiki/Shunting-yard_algorithm
 * http://cplusplus.kurttest.com/notes/stack.html#binary-expression-tree
 * https://www.youtube.com/watch?v=xoU69C4lKlM
 */

public class App
{
    public static void main( String[] args )
    {
        NodeHelper helper = new NodeHelper();
        //FOR NOW THE EXPRESSION IS HARDCODED FOR SIMPLE PARSING
        //BETTER INPUT WILL BE ADDED IN MAYBE ANOTHER HELPER/FUNCTION CLASS
        String expression = "( 10.24 + 5.4 * 2.5 ) / 6.7 + ( 12.5 * 20.67 + 10 ) * 25";

        //
        Node root = helper.buildTree(expression);

        //IF TREE BUILDING IS UNSUCCESSFUL
        //(MORE ERROR CHECKING TO BE ADDED IN NODEHELPER
        if(root == null)
        {
            System.out.println("Something's wrong with the expression...\n");
        }

        else
        {
            System.out.println(expression + "\nResult: " + helper.calc(root));
        }

    }
}
