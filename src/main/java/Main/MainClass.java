/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import AggregatedProblems.AggDTLZ5_5;
import Algorithms.AbstractMOEAD.FunctionType;
import Algorithms.MOEAD;
import Algorithms.SolutionsOutput;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import Implementations.PolynomialMutation;
import Implementations.BinaryTournamentSelection;
import Implementations.Problem;
import AggregatedProblems.AggDTLZ5_5;
import Implementations.CrossoverOperator;
import Implementations.DifferentialEvolutionCrossover;
import Implementations.AbstractDoubleProblem;
import org.uma.jmetal.problem.multiobjective.wfg.*;
import org.uma.jmetal.problem.multiobjective.zdt.*;
import Implementations.DoubleSolution;
import Implementations.SelectionOperator;
import Problems.DTLZ5;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.point.impl.ArrayPoint;


/**
 *
 * @author renansantos 
*/
public class MainClass {    

    public static void main(String[] args) throws JMetalException, FileNotFoundException {
        
        //initializing problem and algorithm variables
        int numberOfObjectives = 5;//5,10,15
        int numberOfVariables = 10;
        int populationSize = 100;
        int resultPopulationSize = 100;
        int maxEvaluations = 200000;
        double neighborhoodSelectionProbability = 0.1;
        int maximumNumberOfReplacedSolutions = 10;
        int neighborSize = 10;
        int evaluationToSave = 5000;
        int maxExecutions = 30;
        int reducedNumberOfObjectives = 2;
        //initializing benchmark problem
//        Problem problem = new ZDT1(numberOfVariables); 
        Problem problem = new DTLZ5(numberOfVariables, numberOfObjectives);
//        Problem problem = new AggDTLZ5_5(numberOfVariables, numberOfObjectives, reducedNumberOfObjectives);
//        Problem problem = new WFG2(2*(numberOfObjectives - 1),20, numberOfObjectives); 
        
        
        //initializing algorithm operators
        CrossoverOperator crossover = new DifferentialEvolutionCrossover();
        PolynomialMutation mutation = new PolynomialMutation(0.02, 20);
        SelectionOperator selection = new BinaryTournamentSelection();
        
        MOEAD algorithm = new MOEAD(
                problem,
                populationSize,
                resultPopulationSize,
                maxEvaluations,
                maxExecutions,
                evaluationToSave,
                mutation,
                crossover,
                FunctionType.PBI,
                "home/renansantos/NetBeansProjects/MOEAD/MOEAD_Weights",
                neighborhoodSelectionProbability,
                maximumNumberOfReplacedSolutions,
                neighborSize);

       
        algorithm.runExperiment();
        System.out.println("MOEAD Finished!");
        System.out.println(algorithm.getResult());
        
        
        new SolutionsOutput(problem, algorithm.getResult(), problem.getName()).saveSolutions();
        
        
//        List<DoubleSolution> population = new ArrayList<>();
//        int numberOfSolutions = 10000;
//        population.addAll(algorithm.initializePopulation(problem, numberOfSolutions));
//        population.forEach(u -> System.out.println(u));
        
    }
}
