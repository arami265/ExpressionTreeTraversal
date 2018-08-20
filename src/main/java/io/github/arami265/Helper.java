package io.github.arami265;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class Helper {
    public String[] tokenize(String expression)
    {
        List<String> result = new LinkedList<>();

        expression = StringUtils.deleteWhitespace(expression);
        String temp = "";
        char c;

        for(int i = 0; i < expression.length(); i++)
        {
            c = expression.charAt(i);
            if(Character.isDigit(c) || c == '.')
            {
                //Checks if the token is at the BEGINNING of a numerical value
                if(i == 0)
                {
                    temp = "";
                }
                else if (!Character.isDigit(expression.charAt(i - 1)) && expression.charAt(i-1) != '.')
                {
                    temp = "";
                }

                //We then either start a new temp string w/ the first digit,
                //or just add the digit to the string

                temp += Character.toString(c);

                //Checks if the token is at the END of a numerical value
                //(These two ifs may both be true, if the digit is a single integer)
                //
                //In this case, we've already added at least one digit token to the string,
                //and we need to add the full value to our list to return.
                if(i == (expression.length() - 1))
                {
                    StringUtils.trim(temp);
                    ((LinkedList<String>) result).addLast(temp);
                }
                else if (!Character.isDigit(expression.charAt(i + 1)) && expression.charAt(i+1) != '.')
                {
                    StringUtils.trim(temp);
                    ((LinkedList<String>) result).addLast(temp);
                }
            }
            else
            {
                ((LinkedList<String>) result).addLast(Character.toString(c));
            }
        }


        /*for(int i = 0; i < result.size(); i++)
        {
            System.out.println("Token " + i + ": " + result.get(i));
        }*/

        String[] resultArray = result.toArray(new String[result.size()]);
        return resultArray;
    }
}
