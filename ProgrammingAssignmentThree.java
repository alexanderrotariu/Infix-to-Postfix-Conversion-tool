import java.util.Scanner;
import java.util.Stack;
import java.lang.Character;
import java.util.LinkedList;

public class ProgrammingAssignmentThree
{
    
    //Making a enum to assign precedence values to each operator. The magnitude of the number implies it value of precedence according to PEMDAS.
    public enum OperatorPrecedence
    {
        PLUS(1), 
        MINUS(1), 
        MULTIPLY(2), 
        DIVIDE(2), 
        CARROT(3),
        OPENPAR(4), 
        CLOSEPAR(4)
        ;
        int value;
        
        OperatorPrecedence(int value)
        {
            this.value = value;
        }
    }
        //+ = 11
        //- = 11
        //* = 12
        // / = 12
        // ( = 16
        // ) = 16
        
    
    public static void main(String [] args)
    {
        //To create a program that will implement a stack object to convert algebraic statements
        //from either infix notation to postfix notation or vice-versa.  
        // The program will also implement a link list data structure to track all the conversions done.

        
        //Print out a menu
        Scanner userInput = new Scanner(System.in);
        int input = 0;
        
        //Linked list to keep track of all the equations.
        //infixToPostList keeps track of infix to postfix conversions.
        //postToInfixList keeps track of postfix to infix conversions.
        LinkedList<String> infixToPostList = new LinkedList<>();
        LinkedList<String> postToInfixList = new LinkedList<>();
        
        //The while loop runs the program. I
        while(input !=4)
        {
            printMenu();
            
            input = userInput.nextInt();
            
            switch(input)
            {
                case 1:
                    String outputToPost = infixToPost();
                    infixToPostList.add(outputToPost);
                    System.out.println("Your new equation is: " + outputToPost);
                    break;
                    
                case 2: 
                    String outputToInfix = postToInfix();
                    postToInfixList.add(outputToInfix);
                    System.out.println("Your new equation is: "+ outputToInfix);
                    break;
                    
                case 3:
                    printAllEquations(infixToPostList, postToInfixList);
                    break;
                    
                case 4:
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("Please enter a number on the menu!");
            }
            
        }
    }
    
    //printMenu prints out the user menu using multiple print lines
    public static void printMenu()
    {
        System.out.println("Please select what type of conversion you would like to do: ");
        System.out.println("1) Infix to postfix");
        System.out.println("2) Postfix to infix");
        System.out.println("3) Print equations");
        System.out.println("4) Exit");
    }
    
    //infixToPost takes a user inputed infix equation and converts to equation into postfix notation.
    public static String infixToPost()
    {
        //Initializing the output as a String. Output will be updated as the algorithm continues.
        String output = "";
        
        //Declaring and instantiating a scanner object.
        Scanner charInput = new Scanner(System.in);
        
        //Declaring and instantiating a stack of characters.
        Stack<Character> operator = new Stack<>();
        
        //Prompting the user of an a equation. The user will manually input each character from the equation. As they input their characters, the algorithm will calculate.
        System.out.println("Please enter your equation, one character at a time. When finished, type ! to end.");
        
        //Initializing the input String to be updated.
        String input = new String("~");
        
        
        //The loop runs while the user doesnt input a !. Once a ! is inputed, then the while loop terminates.
        while(input.charAt(0) != '!')
        {
            input = charInput.next();

            //if the input is a letter or a digit
            if((input.charAt(0) >= 97 && input.charAt(0) <=122) || (input.charAt(0) >= 48 && input.charAt(0) <= 57))
            {
                output = output + input;
            }
                   // + - / * ^ ( ) 
            if((input.charAt(0) == 42 || input.charAt(0) == 43 || input.charAt(0) == 47 || input.charAt(0) == 45 || input.charAt(0) == 94 || input.charAt(0) == 40 || input.charAt(0) == 41) && (operator.isEmpty() == true || operator.peek() == '('))
            {
                operator.push(input.charAt(0));
            }
            //if its an operator and, and then the stack isnt empty
            else if(input.charAt(0) == 42 || input.charAt(0) == 43 || input.charAt(0) == 47 || input.charAt(0) == 45 || input.charAt(0) == 94 || input.charAt(0) == 40 || input.charAt(0) == 41 && operator.isEmpty() == false)
            {
                if(input.charAt(0) == '(')
                {
                    operator.push(input.charAt(0));
                }
                else if(input.charAt(0) == ')')
                {
                    for(int i = operator.size()-1; i >= 0; i--)
                    {
                        if(operator.peek() != '(')
                        {
                            output = output + operator.peek();
                            operator.pop();
                        }
                        else
                        {
                            operator.pop();
                        }
                    }
                }
                //If the incoming precedence is higher than the top of the stack
                else if(getOrderPrecedence(input.charAt(0)) > getOrderPrecedence(operator.peek()))
                {
                    operator.push(input.charAt(0));
                }
                //If the incoming precedence is equal to the top of the stack
                else if(getOrderPrecedence(input.charAt(0)) == getOrderPrecedence(operator.peek()))
                {
                    output = output + operator.peek();
                    operator.pop();
                    operator.push(input.charAt(0));
                }
                //if the incoming precedence is less than the precedence to the top of the stack.
                else if(getOrderPrecedence(input.charAt(0)) < getOrderPrecedence(operator.peek()))
                {
                    output = output + operator.peek();
                    operator.pop();
                    if(input.charAt(0) == '(')
                    {
                        operator.push(input.charAt(0));
                    }
                    else if(input.charAt(0) == ')')
                    {
                        for(int i = operator.size()-1; i >= 0; i--)
                        {
                            if(operator.peek() != '(')
                            {
                                output = output + operator.peek();
                                operator.pop();
                            }
                            else
                            {
                                operator.pop();
                            }
                        }
                    }
                    else if(operator.isEmpty() == false && getOrderPrecedence(input.charAt(0)) > getOrderPrecedence(operator.peek()))
                    {
                        operator.push(input.charAt(0));
                    }
                    else if(operator.isEmpty() == false && getOrderPrecedence(input.charAt(0)) == getOrderPrecedence(operator.peek()))
                    {
                        output = output + operator.peek();
                        operator.pop();
                        operator.push(input.charAt(0));
                    }
                    else
                    {
                        operator.push(input.charAt(0));
                    }
                }
                else
                {
                    System.out.println("Please enter a operand or and operator!");
                }
            }
        }
        
        for(int i = operator.size()-1; i >= 0; i--)
        {
            if(operator.peek() != '(' || operator.peek() != ')')
                output = output + operator.get(i);
            
        }
         
        return output;
    }
   
    
    public static String postToInfix()
    {
        String output = "";
        
        Scanner charInput = new Scanner(System.in);
        Stack<String> operand = new Stack<>();
        System.out.println("Please enter your equation, one character at a time. When finished, type ! to end.");
        
        String input = new String("~");
        
        while(input.charAt(0) != '!')
        {
            input = charInput.next();
            
            //IF YOU SCAN AN OPERAND, PUSH IT TO THE STACK
            if((input.charAt(0) >= 97 && input.charAt(0) <=122) || (input.charAt(0) >= 48 && input.charAt(0) <= 57))
            {
                operand.push(input);
            }
            //IF YOU SCAN AN OPERATOR, POP THE TOP TWO OPERANDS and then push the output
            if((input.charAt(0) == 42 || input.charAt(0) == 43 || input.charAt(0) == 47 || input.charAt(0) == 45 || input.charAt(0) == 94 || input.charAt(0) == 40 || input.charAt(0) == 41))
            {
                String operand1 = operand.pop();
                String operand2 = operand.pop();
                operand.push("("+operand2+input+operand1+")");
            }
            
        }
        
        output = output + operand.pop();
        
        return output;
    }
    
    public static int getOrderPrecedence(char sym1)
    {
        //+ = 11
        //- = 11
        //* = 12
        // / = 12
        // ( = 16
        // ) = 16
        
        if(sym1 == '+')
        {
            return OperatorPrecedence.PLUS.value;
        }
        else if(sym1 == '-')
        {
            return OperatorPrecedence.MINUS.value;
        }
        else if(sym1 == '*')
        {
            return OperatorPrecedence.MULTIPLY.value;
        }
        else if(sym1 == '/')
        {
            return OperatorPrecedence.DIVIDE.value;
        }
        else if(sym1 == '(')
        {
            return OperatorPrecedence.OPENPAR.value;
        }
        else if(sym1 == ')')
        {
            return OperatorPrecedence.CLOSEPAR.value;
        }
        else if(sym1 == '^')
        {
            return OperatorPrecedence.CARROT.value;
        }
        else
        {
            return 0;
        }
    }
    
    public static void printAllEquations(LinkedList infixToPost, LinkedList postToInfix)
    {
        System.out.println("----------------------------------------------------");
        System.out.println("All the infix notation to postfix notation conversions you have done are: ");
        
        for(int i = 0 ; i < infixToPost.size(); i++)
        {
            System.out.println(infixToPost.get(i));
        }
        
        System.out.println("----------------------------------------------------");
        System.out.println("All the postfix notation to infix notation conversions you have done are: ");
        
        for(int i = 0 ; i < postToInfix.size(); i++)
        {
            System.out.println(postToInfix.get(i));
        }

        System.out.println("----------------------------------------------------");

    }
    
}