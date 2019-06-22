/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.util.ArrayList;
import java.util.List;
import jmetal.qualityIndicator.Hypervolume;
import org.uma.jmetal.algorithm.multiobjective.moead.util.MOEADUtils;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;

/**
 *
 * @author renansantos
 */
@SuppressWarnings("serial")
public class MOEAD extends AbstractMOEAD<DoubleSolution> {

    protected DifferentialEvolutionCrossover differentialEvolutionCrossover;
    protected int evaluationToSave;
    protected int maxExecutions;

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

                if (evaluations % evaluationToSave == 0) {
                    Hypervolume qualityIndicator = new Hypervolume();
                    double value = qualityIndicator.calculateHypervolume(getResultArray(), populationSize,
                            problem.getNumberOfObjectives());
                    System.out.println(evaluations);
                    System.out.println(value);
                    new SolutionsOutput(problem, this.getResult(), problem.getName(), execution, evaluations).
                            saveSolutionsDuringAlgorithmExecution();
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
