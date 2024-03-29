/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import Implementations.DifferentialEvolutionCrossover;
import Implementations.DoubleSolution;
import Implementations.Problem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import Implementations.CrossoverOperator;
import Implementations.MutationOperator;
import jmetal.qualityIndicator.QualityIndicator;
import org.uma.jmetal.util.point.impl.ArrayPoint;

/**
 *
 * @author renansantos
 */
public class MOEAD extends AbstractMOEAD<DoubleSolution> {

    protected DifferentialEvolutionCrossover differentialEvolutionCrossover;
    protected int evaluationToSave;
    protected int maxExecutions;
    protected PrintStream hypervolumeStream;
    protected SolutionsOutput solutionsOutput;
    protected ArrayPoint referencePoint;

    public MOEAD(Problem<DoubleSolution> problem,
            int populationSize,
            int resultPopulationSize,
            int maxEvaluations,
            int maxExecutions,
            int evaluationToSave,
            MutationOperator<DoubleSolution> mutation,
            CrossoverOperator<DoubleSolution> crossover,
            FunctionType functionType,
            String dataDirectory,
            double neighborhoodSelectionProbability,
            int maximumNumberOfReplacedSolutions,
            int neighborSize) {
        super(problem, populationSize, resultPopulationSize, maxEvaluations, crossover, mutation, functionType,
                dataDirectory, neighborhoodSelectionProbability, maximumNumberOfReplacedSolutions,
                neighborSize);

        differentialEvolutionCrossover = (DifferentialEvolutionCrossover) crossoverOperator;
        this.evaluationToSave = evaluationToSave;
        this.maxExecutions = maxExecutions;

        referencePoint = new ArrayPoint(problem.getNumberOfObjectives());
        for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
            referencePoint.setDimensionValue(i, 11.0);
        }

    }

    private void initializeHypervolumeStream(int currentExecution) {
        try {
            String folderName = "Results/" + this.problem.getName() + "-" + this.problem.getNumberOfObjectives() + "/";
            boolean success = (new File(folderName)).mkdirs();
            hypervolumeStream = new PrintStream(folderName + "hypervolume_execution_" + currentExecution + ".csv");

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getStackTrace());
        }
    }
    
    private void initializePopulationStream(int currentExecution) {
        try {
            String folderName = "Results/Populations" + this.problem.getName() + "-" + this.problem.getNumberOfObjectives() + "/";
            boolean success = (new File(folderName)).mkdirs();
            hypervolumeStream = new PrintStream(folderName + "hypervolume_execution_" + currentExecution + ".csv");

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getStackTrace());
        }
    }

    @Override
    public void run() {
        initializePopulation();
        initializeUniformWeight();
        initializeNeighborhood();
        initializeIdealPoint();

        evaluations = populationSize;
        do {
            int[] permutation = new int[populationSize];
            MOEADUtils.randomPermutation(permutation, populationSize);

            for (int i = 0; i < populationSize; i++) {
                int subProblemId = permutation[i];

                NeighborType neighborType = chooseNeighborType();
                List<DoubleSolution> parents = parentSelection(subProblemId, neighborType);

                differentialEvolutionCrossover.setCurrentSolution(population.get(subProblemId));
                List<DoubleSolution> children = differentialEvolutionCrossover.execute(parents);

                DoubleSolution child = children.get(0);
                mutationOperator.execute(child);
                problem.evaluate(child);

                evaluations++;

                updateIdealPoint(child);
                updateNeighborhood(child, subProblemId, neighborType);
            }

        } while (evaluations < maxEvaluations);

    }

    public void runExperiment() {
        int execution = 0;
        do {
            initializePopulation();
            initializeUniformWeight();
            initializeNeighborhood();
            initializeIdealPoint();
            initializeHypervolumeStream(execution);
            evaluations = populationSize;
            if (execution == 0) {
                population.forEach(u -> System.out.println(u));
            }
            System.out.println("Execution = " + execution);
            do {
                int[] permutation = new int[populationSize];
                MOEADUtils.randomPermutation(permutation, populationSize);

                 if (evaluations == 100) {
                    solutionsOutput = new SolutionsOutput(problem, population, execution, 0);
                    solutionsOutput.saveSolutionsDuringAlgorithmExecution();
                }
                
                
                for (int i = 0; i < populationSize; i++) {
                    int subProblemId = permutation[i];

                    NeighborType neighborType = chooseNeighborType();
                    List<DoubleSolution> parents = parentSelection(subProblemId, neighborType);

                    differentialEvolutionCrossover.setCurrentSolution(population.get(subProblemId));
                    List<DoubleSolution> children = differentialEvolutionCrossover.execute(parents);

                    DoubleSolution child = children.get(0);
                    mutationOperator.execute(child);
                    problem.evaluate(child);

                    evaluations++;

                    updateIdealPoint(child);
                    updateNeighborhood(child, subProblemId, neighborType);
                }

                if ((evaluations % evaluationToSave == 0) ||(evaluations == 200)) {
//                     
//                    WFGHypervolume qualityIndicator = new WFGHypervolume();
//                    double value = qualityIndicator.computeHypervolume(getResult(), referencePoint);
                    solutionsOutput = new SolutionsOutput(problem, population, execution, evaluations);
                    solutionsOutput.saveSolutionsDuringAlgorithmExecution();
                    
//                    Metrics.Hypervolume hv = new Metrics.Hypervolume();
//
//                    double value = hv.calculateHypervolume(getResultArray(), populationSize, problem.getNumberOfObjectives());

//                    double value2 = qualityIndicator.hypervolume(getResult(), getParetoArray(600), populationSize);
//                    System.out.println(value);
//                    hypervolumeStream.println(value);
                    
                }

            } while (evaluations < maxEvaluations);
            execution++;
        } while (execution < maxExecutions);
    }

    protected void initializePopulation() {
        population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            DoubleSolution newSolution = (DoubleSolution) problem.createSolution();

            problem.evaluate(newSolution);
            population.add(newSolution);
        }
    }

    public List<DoubleSolution> initializePopulation(Problem<DoubleSolution> problem, int populationSize) {
        List<DoubleSolution> population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            DoubleSolution newSolution = (DoubleSolution) problem.createSolution();

            problem.evaluate(newSolution);
            population.add(newSolution);
        }
        return population;
    }

    @Override
    public String getName() {
        return "MOEAD";
    }

    @Override
    public String getDescription() {
        return "Multi-Objective Evolutionary Algorithm based on Decomposition";
    }
}
