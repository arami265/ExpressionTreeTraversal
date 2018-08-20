package io.github.arami265;

import java.util.Deque;
import java.util.LinkedList;

public class NodeHelper {
    //Will return root node of tree if successful;
    //Else will return null
    public Node buildTree(String expression)
    {
        Helper helper = new Helper();
        Deque<Character> operatorStack = new LinkedList<>();
        Deque<Node> nodeStack = new LinkedList<>();

        //Tokenization

        String[] tokens = helper.tokenize(expression);

        //Iterate through tokens
        main:
        for(int i  = 0; i < tokens.length; i++)
        {
            tokens[i].trim();

            if(tokens[i].equals("("))
                operatorStack.push(tokens[i].charAt(0));


            else if(tokens[i].equals("+") || tokens[i].equals("-") || tokens[i].equals("*") || tokens[i].equals("/"))
            {
                while(!operatorStack.isEmpty())
                {
                    //Precedence needs to be checked if a + or - is found
                    if((tokens[i].equals("+") || tokens[i].equals("-")) && (operatorStack.peek() == '*' || operatorStack.peek() == '/'))
                        createBranch(nodeStack, operatorStack.pop());
                    else
                        break;
                }
                operatorStack.push(tokens[i].charAt(0));
            }

            else if(tokens[i].equals(")"))
            {
                while(!operatorStack.isEmpty())
                {
                    if(operatorStack.peek() == '(')
                    {
                        operatorStack.pop();
                        continue main;
                    }

                    //Branches are created while looking for parenthesis
                    else
                    {
                        createBranch(nodeStack, operatorStack.pop());
                    }
                }
                //If no matching parenthesis is found
                throw new IllegalStateException("Unbalanced parentheses");
            }

            //If the token is numeric
            //TODO: Add better check
            else
            {
                Node valNode = new Node(Double.parseDouble(tokens[i]));
                nodeStack.push(valNode);
            }
        }

        //After reading tokens, remaining operators
        //on the stack are processed
        while(!operatorStack.isEmpty())
            createBranch(nodeStack, operatorStack.pop());

        //Check for possible errors
        //Stack should consist of a single node
        if(nodeStack.size() == 1)
        {
            Node root = nodeStack.pop();

            return root;
        }
        else
            return null;

    }

    //For creating another branch in the tree
    public void createBranch(Deque<Node> nodeStack, char operator)
    {
        Node right = nodeStack.pop();
        Node left = nodeStack.pop();

        Node opNode = new Node(operator, left, right);

        nodeStack.push(opNode);
    }

    //Preorder traversal
    //Nodes are printed before their children
    public void preorder(Node currentNode)
    {
        Node cNode = currentNode;

        if(cNode.isOperator())
            System.out.print(cNode.getOperator() + " ");
        else
            System.out.print(cNode.getValue() + " ");

        if(cNode.getLeft() != null)
            postorder(cNode.getLeft());
        if(cNode.getRight() != null)
            postorder(cNode.getRight());
    }

    //Postorder traversal
    //Children are printed before parent nodes,
    //resulting in a conversion to Reverse Polish Notation
    public void postorder(Node currentNode)
    {
        Node cNode = currentNode;

        if(cNode.getLeft() != null)
            postorder(cNode.getLeft());
        if(cNode.getRight() != null)
            postorder(cNode.getRight());

        if(cNode.isOperator())
            System.out.print(cNode.getOperator() + " ");
        else
            System.out.print(cNode.getValue() + " ");
    }

    //Calculates the value of the expression recursively via the tree
    public double calc(Node currentNode)
    {
        Node cNode = currentNode;
        double result = -9999;

        if(cNode.isOperator())
        {
            switch(cNode.getOperator())
            {
                case '+':
                    result = calc(cNode.getLeft()) + calc(cNode.getRight());
                    break;
                case '-':
                    result = calc(cNode.getLeft()) - calc(cNode.getRight());
                    break;
                case '*':
                    result = calc(cNode.getLeft()) * calc(cNode.getRight());
                    break;
                case '/':
                    result = calc(cNode.getLeft()) / calc(cNode.getRight());
                    break;
            }
        }

        else
            result = cNode.getValue();

        return result;
    }
}
