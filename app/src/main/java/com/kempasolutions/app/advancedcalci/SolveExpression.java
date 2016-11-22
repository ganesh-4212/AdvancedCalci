package com.kempasolutions.app.advancedcalci;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Created by Ganesh Poojary on 4/9/2016.
 */
class SolveExpression {
    static final char SEPERATOR = ':';
    public static String convertToPostfix(String infixExpression) throws InvalidExpressionException {
        char op;
        boolean isSecondOP = false;
        float op1, op2;
        StringBuffer postfix = new StringBuffer();
        Stack<Character> stack = new Stack<Character>();
        try {
            infixExpression += ")";
            stack.push('(');
            char infix[] = infixExpression.toCharArray();
            for (int i = 0; i < infixExpression.length(); i++) {
                if (infix[i] == '(') {
                    stack.push('(');
                } else if (infix[i] == ')') {
                    op = (char) stack.pop();
                    while (op != '(') {
                        postfix.append(op);
                        isSecondOP = false;
                        op = (char) stack.pop();
                    }

                } else if (Character.isDigit(infix[i])) {
                    StringBuffer num = new StringBuffer();
                    while (validDigit(infix[i])) {
                        num.append(infix[i++]);
                    }
                    i--;
                    if (isSecondOP) {
                        postfix.append(SEPERATOR + num.toString());
                    } else {
                        postfix.append(num.toString());
                        isSecondOP = true;
                    }
                } else {
                    op = (char) stack.pop();
                    while (precedence(op) >= precedence(infix[i])) {
                        postfix.append(op);
                        isSecondOP = false;
                        op = (char) stack.pop();
                    }
                    stack.push(op);
                    stack.push(infix[i]);
                }
            }
            if (!stack.isEmpty()) {
                throw new EmptyStackException();
            }
        } catch (EmptyStackException ex) {
            throw new InvalidExpressionException("Invalid expression");
        }

        return postfix.toString();
    }

    static float solveExpression(String infixExpression) throws InvalidExpressionException {
        char postfix[] = convertToPostfix(infixExpression).toCharArray();
        Stack<Float> stack = new Stack<Float>();
        Float op1,op2,result=0f,retValue=0f;
        try {
            for (int i = 0; i < postfix.length; i++) {
                if (Character.isDigit(postfix[i])) {
                    StringBuffer num = new StringBuffer();
                    while (validDigit(postfix[i])) {
                        num.append(postfix[i++]);
                    }
                    if (postfix[i] != SEPERATOR) i--;
                    stack.push(Float.parseFloat(num.toString()));

                    continue;
                }
                op2 = stack.pop();
                op1 = stack.pop();
                switch (postfix[i]) {
                    case '+':
                        result = op1 + op2;
                        break;
                    case '-':
                        result = op1 - op2;
                        break;
                    case '*':
                        result = op1 * op2;
                        break;
                    case '/':
                        result = op1 / op2;
                        break;
                }
                stack.push(result);
            }
            retValue=stack.pop();
        }
        catch (EmptyStackException ex){
                throw new InvalidExpressionException("Invalid expression");
        }
        return retValue;
    }

    static boolean validDigit(char digit) {
        char digits[] = {'.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (char dg : digits) {
            if (dg == digit) {
                return true;
            }
        }
        return false;
    }

    static int precedence(char op) {
        if (op == '*' || op == '/') {
            return (1);
        } else if (op == '+' || op == '-') {
            return 0;
        }
        return -1;
    }

    static class InvalidExpressionException extends Exception {

        public InvalidExpressionException(String message) {
            super(message);
        }
    }
}
