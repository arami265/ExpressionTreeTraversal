package io.github.arami265;

import org.apache.commons.lang3.StringUtils;

import java.util.Deque;
import java.util.LinkedList;

public class NodeHelper {
    //FOR CREATING ANOTHER BRANCH IN THE TREE
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

    //CALCULATES THE VALUE OF THE EXPRESSION RECURSIVELY VIA THE TREE
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

    //WILL RETURN ROOT NODE OF TREE IF SUCCESSFUL;
    //ELSE WILL RETURN NULL
    public Node buildTree(String expression)
    {
        Deque<Character> operatorStack = new LinkedList<>();
        Deque<Node> nodeStack = new LinkedList<>();

        //TOKENIZATION
        //SIMPLIFIED FOR NOW
        String[] tokens = StringUtils.split(expression);

        //ITERATE THROUGH TOKENS
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
                    //PRECEDENCE NEEDS TO BE CHECKED IF A + OR - IS FOUND
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

                    //BRANCHES ARE CREATED WHILE LOOKING FOR PARENTHESIS
                    else
                    {
                        createBranch(nodeStack, operatorStack.pop());
                    }
                }
                //IF NO MATCHING PARENTHESIS IS FOUND
                throw new IllegalStateException("Unbalanced parentheses");
            }

            //IF THE TOKEN IS NUMERIC
            //TODO: ADD BETTER CHECK
            else
            {
                Node valNode = new Node(Double.parseDouble(tokens[i]));
                nodeStack.push(valNode);
            }
        }

        //AFTER READING TOKENS, REMAINING OPERATORS
        //ON THE STACK ARE PROCESSED
        while(!operatorStack.isEmpty())
            createBranch(nodeStack, operatorStack.pop());

        //CHECK FOR POSSIBLE ERRORS
        //STACK SHOULD CONSIST OF A SINGLE NODE
        if(nodeStack.size() == 1)
        {
            Node root = nodeStack.pop();

            return root;
        }
        else
            return null;

    }
}
