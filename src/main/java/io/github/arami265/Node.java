package io.github.arami265;

public class Node
{
    boolean isOperator;
    char op;
    double value;

    Node left;
    Node right;

    //CONSTRUCTORS
    //
    //IN CASE OF OPERAND
    public Node(double val)
    {
        isOperator = false;
        value = val;

        left = null;
        right = null;

    }

    //IN CASE OF OPERATOR AND LEAVES
    public Node(char opChar, Node l, Node r)
    {
        isOperator = true;
        op = opChar;

        left = l;
        right = r;
    }

    //GET FUNCTIONS
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