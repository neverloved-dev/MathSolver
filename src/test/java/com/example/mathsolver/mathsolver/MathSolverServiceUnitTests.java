package com.example.mathsolver.mathsolver;

import com.services.MathSolverService;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

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
        mathSolverService.solveMathProblem("unknown(4)");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateExpressionWithUnsupportedOperator() {
        // Test case with an unsupported operator
        mathSolverService.solveMathProblem("2 $ 3");
    }
}
