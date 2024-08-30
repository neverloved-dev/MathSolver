package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.services.MathSolverService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;

@Controller
public class MathController {

    @Autowired
    MathSolverService mathSolverService;

    @PostMapping("/api/solve")
    public File solveMathproblem(){



    }
    
}
