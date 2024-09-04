package com.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;
import java.util.Stack;

@Service
public class MathSolverService {
    private List<String> readStringFromFile(@NotNull String pathToFile){
        List<String> resuList = new ArrayList<String>();
        try {
            File fileObject = new File(pathToFile);
            Scanner fileScannerObj = new Scanner(fileObject);
            while(fileScannerObj.hasNextLine()){
                String fileLIne = fileScannerObj.nextLine();
                resuList.add(fileLIne);
            }
            
        } catch (FileNotFoundException | NullPointerException e) {
            // TODO: Handle the exceptions
        }
        return resuList;

    }

       public void writeSolutionToFile( @NotNull Integer solution, @NotNull String pathToFile) throws IOException{
        try{
            File fileObject = new File(pathToFile);
            FileWriter fileWriter = new FileWriter(fileObject);
            fileWriter.write(solution);
        } catch(IOException e){
            File fileObject = new File("solutions.txt");
            FileWriter fileWriter = new FileWriter(fileObject);
            fileWriter.write(solution);
        }

    }

     // Method to check if a string is an operator
    private static boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("^");
    }

    // Method to check if a string is a function
    private static boolean isFunction(String s) {
        return s.equals("sin") || s.equals("cos");
    }

    // Method to return precedence of operators
    private static int precedence(String s) {
        switch (s) {
            case "+": case "-":
                return 1;
            case "*": case "/":
                return 2;
            case "^":
                return 3;
            case "sin": case "cos":
                return 4;
        }
        return -1;
    }

    // Method to convert infix expression to postfix expression
    private static String infixToPostfix(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<String> stack = new Stack<>();
        int i = 0;

        while (i < expression.length()) {
            char c = expression.charAt(i);

            // If the character is a digit, add it to output
            if (Character.isDigit(c)) {
                result.append(c);
                i++;
            }
            // If the character is a letter, it may be a function like sin or cos
            else if (Character.isLetter(c)) {
                StringBuilder func = new StringBuilder();
                while (i < expression.length() && Character.isLetter(expression.charAt(i))) {
                    func.append(expression.charAt(i++));
                }
                stack.push(func.toString());
            }
            // If the character is '(', push it to the stack
            else if (c == '(') {
                stack.push(String.valueOf(c));
                i++;
            }
            // If the character is ')', pop and output from the stack until an '(' is encountered
            else if (c == ')') {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    result.append(stack.pop());
                }
                stack.pop(); // remove '(' from stack
                // If a function is on top of the stack, pop it to output
                if (!stack.isEmpty() && isFunction(stack.peek())) {
                    result.append(stack.pop());
                }
                i++;
            }
            // If an operator is encountered
            else if (isOperator(String.valueOf(c))) {
                while (!stack.isEmpty() && precedence(String.valueOf(c)) <= precedence(stack.peek())) {
                    result.append(stack.pop());
                }
                stack.push(String.valueOf(c));
                i++;
            }
        }

        // Pop all the operators from the stack
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }

    // Method to evaluate a postfix expression
    private static double evaluatePostfix(String expression) {
        Stack<Double> stack = new Stack<>();
        int i = 0;

        while (i < expression.length()) {
            char c = expression.charAt(i);

            // If the character is a digit, push it to the stack
            if (Character.isDigit(c)) {
                stack.push((double) (c - '0'));
                i++;
            }
            // If the character is an operator, pop two elements from stack, apply the operator, and push the result back
            else if (isOperator(String.valueOf(c))) {
                double b = stack.pop();
                double a = stack.pop();
                switch (c) {
                    case '+':
                        stack.push(a + b);
                        break;
                    case '-':
                        stack.push(a - b);
                        break;
                    case '*':
                        stack.push(a * b);
                        break;
                    case '/':
                        stack.push(a / b);
                        break;
                    case '^':
                        stack.push(Math.pow(a, b));
                        break;
                }
                i++;
            }
            // If the character is a letter, it may be a function like sin or cos
            else if (Character.isLetter(c)) {
                StringBuilder func = new StringBuilder();
                while (i < expression.length() && Character.isLetter(expression.charAt(i))) {
                    func.append(expression.charAt(i++));
                }
                double a = stack.pop();
                switch (func.toString()) {
                    case "sin":
                        stack.push(Math.sin(Math.toRadians(a)));
                        break;
                    case "cos":
                        stack.push(Math.cos(Math.toRadians(a)));
                        break;
                }
            }
        }

        // The final result will be in the stack
        return stack.pop();
    }

    // Method to solve the expression
    public static double solveExpression(String expression) {
        String postfixExpression = infixToPostfix(expression);
        return evaluatePostfix(postfixExpression);
    }
}
