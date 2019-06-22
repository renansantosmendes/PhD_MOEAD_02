/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmetal.qualityIndicator.Hypervolume;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;

/**
 *
 * @author renansantos
 */
public class SolutionsOutput {

    List<DoubleSolution> population = new ArrayList<>();
    Problem problem;
    String fileName = "";
    int currentEvaluation;
    int currentExecution;

    public SolutionsOutput(Problem problemPar, List<DoubleSolution> populationPar) {
        population.clear();
        population.addAll(populationPar);
        problem = problemPar;
    }

    public SolutionsOutput(Problem problemPar, List<DoubleSolution> populationPar, String fileName) {
        population.clear();
        population.addAll(populationPar);
        problem = problemPar;
        fileName = fileName + "_";
    }

    public SolutionsOutput(Problem problemPar, List<DoubleSolution> populationPar, String fileName,
            int currentEvaluation, int currentExecution) {
        population.clear();
        population.addAll(populationPar);
        problem = problemPar;
        this.fileName = fileName + "_";
        this.currentEvaluation = currentEvaluation;
        this.currentExecution = currentExecution;
    }

    public void saveRandomSolutions() {
        String folderName = "ClusterAnalysisRandomSolutions";
        fileName = fileName + problem.getName() + "-" + problem.getNumberOfObjectives();

        boolean success = (new File(folderName)).mkdirs();
        if (!success) {
            System.out.println("Folder already exists!");
        }
        try {
            PrintStream printStreamSolutions = new PrintStream(folderName + "/" + fileName + "-random_solutions.csv");

            for (DoubleSolution solution : population) {
                printStreamSolutions.print(solution + "\n");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SolutionsOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveSolutions() {
        String folderName = "Results";
        fileName = fileName + problem.getName() + "_" + problem.getNumberOfObjectives();

        boolean success = (new File(folderName)).mkdirs();
        if (!success) {
            System.out.println("Folder already exists!");
        }
        try {
            PrintStream printStreamSolutions = new PrintStream(folderName + "/" + fileName + "-solutions.csv");

            for (DoubleSolution solution : population) {
                printStreamSolutions.print(solution + "\n");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SolutionsOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveSolutionsDuringAlgorithmExecution() {
        String folderName = "Results";
        fileName = problem.getName() + "_" + problem.getNumberOfObjectives() + "-" +
                this.currentExecution + "-" + this.currentEvaluation;

        boolean success = (new File(folderName)).mkdirs();
        try {
            PrintStream printStreamSolutions = new PrintStream(folderName + "/" + fileName + "-solutions.csv");

            for (DoubleSolution solution : population) {
                Hypervolume qualityIndicator = new Hypervolume();
                printStreamSolutions.print(solution + "\n");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SolutionsOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
