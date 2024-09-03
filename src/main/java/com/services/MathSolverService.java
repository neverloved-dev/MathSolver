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

    public void solveMathProblem(String mathProblem){

    }

    // get the solution file and download it
}
