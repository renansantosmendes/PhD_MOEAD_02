/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AggregatedProblems;

import java.util.ArrayList;
import java.util.List;
import Implementations.AbstractDoubleProblem;
import Implementations.DoubleSolution;
import org.uma.jmetal.util.JMetalException;

/**
 *
 * @author renansantos
 */
public class AggDTLZ1_5 extends AbstractDoubleProblem {

    private Integer reducedNumberOfObjectives;
    private Integer originalNumberOfObjectives;

    /**
     * Creates a default DTLZ1 problem (7 variables and 3 objectives)
     */
    public AggDTLZ1_5() {
        this(7, 3, 3);
    }

    /**
     * Creates a DTLZ1 problem instance
     *
     * @param numberOfVariables Number of variables
     * @param numberOfObjectives Number of objective functions
     */
    public AggDTLZ1_5(Integer numberOfVariables, Integer originalNumberOfObjectives, Integer reducedNumberOfObjectives) throws JMetalException {
        setNumberOfVariables(numberOfVariables);
        setNumberOfObjectives(originalNumberOfObjectives);
        setNumberOfAggregatedObjectives(reducedNumberOfObjectives);
        setName("AggDTLZ1");

        this.reducedNumberOfObjectives = reducedNumberOfObjectives;
        setOriginalNumberOfObjectives(originalNumberOfObjectives);
        
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

        double[] f = new double[numberOfObjectives];
        double[] x = new double[numberOfVariables];

        int k = getNumberOfVariables() - getOriginalNumberOfObjectives() + 1;

        for (int i = 0; i < numberOfVariables; i++) {
            x[i] = solution.getVariableValue(i);
        }

        double g = 0.0;
        for (int i = numberOfVariables - k; i < numberOfVariables; i++) {
            g += (x[i] - 0.5) * (x[i] - 0.5) - Math.cos(20.0 * Math.PI * (x[i] - 0.5));
        }

        g = 100 * (k + g);
        for (int i = 0; i < numberOfObjectives; i++) {
            f[i] = (1.0 + g) * 0.5;
        }

        for (int i = 0; i < numberOfObjectives; i++) {
            for (int j = 0; j < numberOfObjectives - (i + 1); j++) {
                f[i] *= x[j];
            }
            if (i != 0) {
                int aux = numberOfObjectives - (i + 1);
                f[i] *= 1 - x[aux];
            }
        }

//        for (int i = 0; i < numberOfObjectives; i++) {
//            solution.setObjective(i, f[i]);
//        }
        
        //calculando a agregaçao
        Double F = 0.0;
        for (int i = 0; i < numberOfObjectives - 3; i++) {
            F += f[i];
        }

        double[] reducedObjectives = new double[reducedNumberOfObjectives];
        reducedObjectives[0] = F;
        reducedObjectives[1] = f[numberOfObjectives - 3];
        reducedObjectives[2] = f[numberOfObjectives - 2];
        reducedObjectives[3] = f[numberOfObjectives - 1];


        List<Double> objectivesList = new ArrayList<>();

        for (int i = 0; i < numberOfObjectives; i++) {
            objectivesList.add(f[i]);

        }
        solution.setAttribute("RealObjectives", objectivesList);

        solution.setObjectives(reducedObjectives);
        this.setNumberOfObjectives(reducedNumberOfObjectives);
    }
}
