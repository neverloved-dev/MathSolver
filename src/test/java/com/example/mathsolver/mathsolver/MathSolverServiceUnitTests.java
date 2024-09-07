package com.example.mathsolver.mathsolver;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.services.MathSolverService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class MathSolverServiceUnitTests {

    private   MathSolverService mathSolverService;
    private  String testFilePath = "file.txt";
    private String resultFilePath = "result.txt";

    private  void createTestFile() throws Exception {
        FileWriter writer = new FileWriter(testFilePath);
        writer.write("This is line 1\n");
        writer.write("This is line 2\n");
        writer.write("This is line 3\n");
        writer.close();
    }
    private void createResultFile() throws Exception{
        File file = new File(resultFilePath);
        file.createNewFile();
    }
    @Before
    public  void setup() throws Exception{
        mathSolverService = new MathSolverService();
        createTestFile();
        createResultFile();
    }


    @After
    public  void cleanUp() throws Exception{
        File testFile = new File(testFilePath);
        if(testFile.exists()){
            testFile.delete();
        }
    }
    @Test
    public void loadsFile() throws IOException {
        //Arrange

        // Act
        List<String> resultList = mathSolverService.readStringFromFile(testFilePath);
        // Assert
        assertNotNull(resultList);
        assertEquals(3,resultList.size());
        assertTrue(resultList.contains("This is line 1"));
        assertTrue(resultList.contains("This is line 2"));
        assertTrue(resultList.contains("This is line 3"));
    }

    @Test
    public void savesResultToFile() throws IOException {
        // Arrange
        List<Integer> results = new ArrayList<Integer>();
        results.add(1);
        results.add(2);
        results.add(3);
        results.add(4);
        for (int i = 0; i < results.size(); i++) {
            //Act
            mathSolverService.writeSolutionToFile(i,resultFilePath);
        }
        //Assert
        List<String> resultList = mathSolverService.readStringFromFile(resultFilePath);
        assertNotNull(resultList);
        assertEquals(4,resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(results.get(i), Optional.of(Integer.parseInt(resultList.get(i))));
        }
    }


    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateExpressionWithUnknownOperation() {
        // Test case with an unknown operation in the expression
        mathSolverService.solveExpression("unknown(4)");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateExpressionWithUnsupportedOperator() {
        // Test case with an unsupported operator
        mathSolverService.solveExpression("2 $ 3");
    }
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
