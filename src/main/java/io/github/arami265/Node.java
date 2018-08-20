package io.github.arami265;

public class Node
{
    boolean isOperator;
    char op;
    double value;

    Node left;
    Node right;

    //Constructors
    //
    //In case of operand
    public Node(double val)
    {
        isOperator = false;
        value = val;

        left = null;
        right = null;

    }

    //In case of operator and leaves
    public Node(char opChar, Node l, Node r)
    {
        isOperator = true;
        op = opChar;

        left = l;
        right = r;
    }

    //Get functions
    public boolean isOperator()
    { return isOperator; }

    public char getOperator()
    { return op; }

    public double getValue()
    { return value; }

    public Node getLeft()
    { return left; }

    public Node getRight()
    { return right; }
}