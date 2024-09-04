package com.example.mathsolver.mathsolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.services.MathSolverService;

@SpringBootTest
public class MathSolverServiceUnitTests {

      @Test
    public void testSimpleAddition() {
        String expression = "2+3";
        double result = MathSolverService.solveExpression(expression);
        assertEquals(5.0, result, 0.0001);
    }

    @Test
    public void testComplexExpression() {
        String expression = "3+(2*2)-4/(1+1)";
        double result = MathSolverService.solveExpression(expression);
        assertEquals(6.0, result, 0.0001);
    }

    @Test
    public void testExpressionWithPower() {
        String expression = "2^3+1";
        double result = MathSolverService.solveExpression(expression);
        assertEquals(9.0, result, 0.0001);
    }

    @Test
    public void testExpressionWithSin() {
        String expression = "sin(30)";
        double result = MathSolverService.solveExpression(expression);
        assertEquals(0.5, result, 0.0001);
    }

    @Test
    public void testExpressionWithCos() {
        String expression = "cos(60)";
        double result = MathSolverService.solveExpression(expression);
        assertEquals(0.5, result, 0.0001);
    }

    @Test
    public void testExpressionWithSinAndCos() {
        String expression = "sin(30) + cos(60)";
        double result = MathSolverService.solveExpression(expression);
        assertEquals(1.0, result, 0.0001);
    }

    @Test
    public void testExpressionWithMultipleOperatorsAndFunctions() {
        String expression = "3+sin(30)*2^3-cos(60)";
        double result = MathSolverService.solveExpression(expression);
        assertEquals(7.0, result, 0.0001);
    }

    @Test
    public void testExpressionWithBracketsAndPower() {
        String expression = "(2+3)^2";
        double result = MathSolverService.solveExpression(expression);
        assertEquals(25.0, result, 0.0001);
    }

    @Test
    public void testExpressionWithNestedFunctions() {
        String expression = "sin(30 + cos(60))";
        double result = MathSolverService.solveExpression(expression);
        assertEquals(Math.sin(Math.toRadians(30.5)), result, 0.0001);
    }
}
