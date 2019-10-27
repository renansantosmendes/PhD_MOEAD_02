/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AggregatedProblems;

import Implementations.AbstractDoubleProblem;
import Implementations.DoubleSolution;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.util.JMetalException;

/**
 *
 * @author renansantos
 */
public class AggDTLZ4_5 extends AbstractDoubleProblem {

    private Integer reducedNumberOfObjectives;
    private Integer originalNumberOfObjectives;

    /**
     * Creates a default DTLZ4 problem (12 variables and 3 objectives)
     */
    public AggDTLZ4_5() {
        this(12, 3, 3);
    }

    /**
     * Creates a DTLZ4 problem instance
     *
     * @param numberOfVariables Number of variables
     * @param numberOfObjectives Number of objective functions
     */
    public AggDTLZ4_5(Integer numberOfVariables, Integer originalNumberOfObjectives, Integer reducedNumberOfObjectives) throws JMetalException {
        setNumberOfVariables(numberOfVariables);
        setNumberOfObjectives(originalNumberOfObjectives);
        setNumberOfAggregatedObjectives(reducedNumberOfObjectives);
        this.reducedNumberOfObjectives = reducedNumberOfObjectives;
        setOriginalNumberOfObjectives(originalNumberOfObjectives);
        setName("AggDTLZ4");

        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());

        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(0.0);
            upperLimit.add(1.0);
        }

        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);
    }

    /**
     * Evaluate() method
     */
    public void evaluate(DoubleSolution solution) {
        int numberOfVariables = getNumberOfVariables();
        int numberOfObjectives = getOriginalNumberOfObjectives();
        double alpha = 100.0;

        double[] f = new double[numberOfObjectives];
        double[] x = new double[numberOfVariables];

        int k = getNumberOfVariables() - getOriginalNumberOfObjectives() + 1;

        for (int i = 0; i < numberOfVariables; i++) {
            x[i] = solution.getVariableValue(i);
        }

        double g = 0.0;
        for (int i = numberOfVariables - k; i < numberOfVariables; i++) {
            g += (x[i] - 0.5) * (x[i] - 0.5);
        }

        for (int i = 0; i < numberOfObjectives; i++) {
            f[i] = 1.0 + g;
        }

        for (int i = 0; i < numberOfObjectives; i++) {
            for (int j = 0; j < numberOfObjectives - (i + 1); j++) {
                f[i] *= java.lang.Math.cos(java.lang.Math.pow(x[j], alpha) * (java.lang.Math.PI / 2.0));
            }
            if (i != 0) {
                int aux = numberOfObjectives - (i + 1);
                f[i] *= java.lang.Math.sin(java.lang.Math.pow(x[aux], alpha) * (java.lang.Math.PI / 2.0));
            }
        }

//        for (int i = 0; i < numberOfObjectives; i++) {
//            solution.setObjective(i, f[i]);
//        }
        //calculando a agregaçao
        Double F = 0.0;
        for (int i = 1; i < numberOfObjectives; i++) {
            F += f[i];
        }

        double[] reducedObjectives = new double[reducedNumberOfObjectives];
        reducedObjectives[0] = f[0];
        reducedObjectives[1] = F;

        List<Double> objectivesList = new ArrayList<>();

        for (int i = 0; i < numberOfObjectives; i++) {
            objectivesList.add(f[i]);

        }
        solution.setAttribute("RealObjectives", objectivesList);

        solution.setObjectives(reducedObjectives);
        this.setNumberOfObjectives(reducedNumberOfObjectives);
    }
}
