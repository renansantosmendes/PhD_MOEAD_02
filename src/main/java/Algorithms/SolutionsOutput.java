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
import Implementations.Problem;
import Implementations.DoubleSolution;

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
    String folderName;

    public SolutionsOutput() {

    }

    public SolutionsOutput(Problem problemPar, List<DoubleSolution> populationPar) {
        population.clear();
        population.addAll(populationPar);
        problem = problemPar;
        generateFolderName();
    }

    public SolutionsOutput(Problem problemPar, List<DoubleSolution> populationPar, String fileName) {
        population.clear();
        population.addAll(populationPar);
        problem = problemPar;
        this.fileName = fileName + "_";
        generateFolderName();
    }

    public SolutionsOutput(Problem problemPar, List<DoubleSolution> populationPar, int currentExecution, int currentEvaluation) {
        population.clear();
        population.addAll(populationPar);
        problem = problemPar;
        this.fileName = "";
        this.currentEvaluation = currentEvaluation;
        this.currentExecution = currentExecution;
        generateFolderName();
    }

    public SolutionsOutput(Problem problemPar, String fileName, int currentExecution, int currentEvaluation) {
        problem = problemPar;
        this.fileName = fileName + "_";
        this.currentEvaluation = currentEvaluation;
        this.currentExecution = currentExecution;
        generateFolderName();
    }

    private void generateFolderName() {
        folderName = problem.getName() + "-" + problem.getNumberOfObjectives();
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
        String folderName = "RandomSolutions/" + this.folderName;
        fileName = problem.getName() + "-" + problem.getNumberOfObjectives();

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
            System.out.println(ex);
        }
    }

    public void saveSolutionsDuringAlgorithmExecution() {

        String folderName = "Results/" + this.folderName;
        fileName = problem.getName() + "_" + problem.getNumberOfObjectives() + "-"
                + this.currentExecution + "-" + this.currentEvaluation;

        boolean success = (new File(folderName)).mkdirs();
        try {
            PrintStream printStreamSolutions = new PrintStream(folderName + "/" + fileName + "-solutions.csv");

            for (DoubleSolution solution : population) {
                printStreamSolutions.print(solution.getAttribute("RealObjectives") + "\n");
//                for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
//                    if (i != problem.getNumberOfObjectives() - 1) {
//                        printStreamSolutions.print(solution.getObjective(i) + ",");
//                    } else {
//                        printStreamSolutions.print(solution.getObjective(i) + "\n");
//                    }
//                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SolutionsOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveSolutionsDuringAlgorithmExecution(List<DoubleSolution> populationPar) {
        population.clear();
        population.addAll(populationPar);

        String folderName = "Results/" + this.folderName;
        fileName = problem.getName() + "_" + problem.getNumberOfObjectives() + "-"
                + this.currentExecution + "-" + this.currentEvaluation;

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

    public void saveMetricDuringAlgorithmExecution(PrintStream printStreamSolutions) {
        String folderName = "Results/" + this.folderName;
        fileName = problem.getName() + "_" + problem.getNumberOfObjectives() + "-"
                + this.currentExecution + "-" + this.currentEvaluation;

        boolean success = (new File(folderName)).mkdirs();
        try {
            printStreamSolutions = new PrintStream(folderName + "/" + fileName + "-solutions.csv");

            for (DoubleSolution solution : population) {
                Hypervolume qualityIndicator = new Hypervolume();
                printStreamSolutions.print(solution + "\n");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SolutionsOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
