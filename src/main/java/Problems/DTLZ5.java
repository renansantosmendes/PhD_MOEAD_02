/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Problems;

import Implementations.AbstractDoubleProblem;
import Implementations.DoubleSolution;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.util.JMetalException;

/**
 *
 * @author renansantos
 */
public class DTLZ5 extends AbstractDoubleProblem {

    private Integer originalNumberOfObjectives;
    /**
     * Creates a default DTLZ5 problem (12 variables and 3 objectives)
     */
    public DTLZ5() {
        this(12, 3);
    }

    /**
     * Creates a DTLZ5 problem instance
     *
     * @param numberOfVariables Number of variables
     * @param numberOfObjectives Number of objective functions
     */
    public DTLZ5(Integer numberOfVariables, Integer numberOfObjectives) throws JMetalException {
        setNumberOfVariables(numberOfVariables);
        setNumberOfObjectives(numberOfObjectives);
        setOriginalNumberOfObjectives(numberOfObjectives);
        
        setNumberOfAggregatedObjectives(numberOfObjectives);
        setName("DTLZ5");

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
        double[] theta = new double[numberOfObjectives - 1];
        double g = 0.0;

        double[] f = new double[numberOfObjectives];
        double[] x = new double[numberOfVariables];

        int k = getNumberOfVariables() - getOriginalNumberOfObjectives() + 1;

        for (int i = 0; i < numberOfVariables; i++) {
            x[i] = solution.getVariableValue(i);
        }

        for (int i = numberOfVariables - k; i < numberOfVariables; i++) {
            g += (x[i] - 0.5) * (x[i] - 0.5);
        }

        double t = java.lang.Math.PI / (4.0 * (1.0 + g));

        theta[0] = x[0] * java.lang.Math.PI / 2.0;
        for (int i = 1; i < (numberOfObjectives - 1); i++) {
            theta[i] = t * (1.0 + 2.0 * g * x[i]);
        }

        for (int i = 0; i < numberOfObjectives; i++) {
            f[i] = 1.0 + g;
        }

        for (int i = 0; i < numberOfObjectives; i++) {
            for (int j = 0; j < numberOfObjectives - (i + 1); j++) {
                f[i] *= java.lang.Math.cos(theta[j]);
            }
            if (i != 0) {
                int aux = numberOfObjectives - (i + 1);
                f[i] *= java.lang.Math.sin(theta[aux]);
            }
        }

        for (int i = 0; i < numberOfObjectives; i++) {
            solution.setObjective(i, f[i]);
        }
        //Renan added this part
        List<Double> objectivesList = new ArrayList<>();
        for (int i = 0; i < numberOfObjectives; i++) {
            objectivesList.add(f[i]);
        }
        solution.setAttribute("RealObjectives", objectivesList);
    }
}
