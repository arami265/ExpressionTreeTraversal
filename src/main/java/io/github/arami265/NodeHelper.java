package io.github.arami265;

import org.apache.commons.lang3.StringUtils;

import java.util.Deque;
import java.util.LinkedList;

public class NodeHelper {
    //FOR CREATING ANOTHER BRANCH IN THE TREE
    public void createBranch(Deque<Character> opStack, Deque<Node> nodeStack)
    {
        Node right = nodeStack.pop();
        Node left = nodeStack.pop();

        Node opNode = new Node(opStack.pop(), left, right);

        nodeStack.push(opNode);
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
        Deque<Character> opStack = new LinkedList<>();
        Deque<Node> nodeStack = new LinkedList<>();

        //TOKENIZATION
        //SIMPLIFIED FOR NOW
        String[] tokens = StringUtils.split(expression);

        for(int i = 0; i < tokens.length; i++)
        {
            tokens[i].trim();
        }

        //ITERATE THROUGH TOKENS
        for(int i  = 0; i < tokens.length; i++)
        {
            if(tokens[i].equals("("))
                opStack.push(tokens[i].charAt(0));

            //PRECEDENCE NEEDS TO BE CHECKED IF A + OR - IS FOUND
            else if(tokens[i].equals("+") || tokens[i].equals("-"))
            {
                if(!opStack.isEmpty())
                {
                    if(opStack.peek() == '*' || opStack.peek() == '/')
                    {
                        createBranch(opStack, nodeStack);

                        opStack.push(tokens[i].charAt(0));
                    }
                    else
                        opStack.push(tokens[i].charAt(0));
                }
            }

            else if (tokens[i].equals("*") || tokens[i].equals("/"))
                opStack.push(tokens[i].charAt(0));

            else if(tokens[i].equals(")"))
            {
                //FLAG TO CHECK IF MATCHING OPENING PARENTHESIS IS FOUND
                boolean parFlag = false;

                while(parFlag == false && !opStack.isEmpty())
                {
                    if(opStack.peek() == '(')
                    {
                        parFlag = true;
                        opStack.pop();
                    }

                    //BRANCHES ARE CREATED WHILE LOOKING FOR PARENTHESIS
                    else if (!opStack.isEmpty() && nodeStack.size() >= 2)
                    {
                        createBranch(opStack, nodeStack);
                    }

                    //IF NO MATCHING PARENTHESIS IS FOUND
                    else
                        return null;
                }
            }

            //IF THE TOKEN IS NUMERIC
            else
            {
                Node valNode = new Node(Double.parseDouble(tokens[i]));
                nodeStack.push(valNode);
            }
        }

        //AFTER READING TOKENS, REMAINING OPERATORS
        //ON THE STACK ARE PROCESSED
        while(!opStack.isEmpty())
        {
            //IF THERE ARE HANGING PARENTHESES,
            //THE EXPRESSION IS NOT VALID
            if(opStack.peek() == '(' || opStack.peek() == ')')
                return null;

            else
                createBranch(opStack, nodeStack);
        }

        //CHECK FOR POSSIBLE ERRORS
        if(opStack.isEmpty() && nodeStack.size() == 1)
        {
            Node root = nodeStack.pop();

            return root;
        }
        else
            return null;

    }
}
