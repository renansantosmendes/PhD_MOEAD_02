/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementations;

import java.util.List;

/**
 *
 * @author renansantos
 */
public abstract class AbstractDoubleProblem extends AbstractGenericProblem<DoubleSolution>
        implements DoubleProblem {

    private List<Double> lowerLimit;
    private List<Double> upperLimit;

    /* Getters */
    @Override
    public Double getUpperBound(int index) {
        return upperLimit.get(index);
    }

    @Override
    public Double getLowerBound(int index) {
        return lowerLimit.get(index);
    }

    /* Setters */
    protected void setLowerLimit(List<Double> lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    protected void setUpperLimit(List<Double> upperLimit) {
        this.upperLimit = upperLimit;
    }

    @Override
    public DoubleSolution createSolution() {
        return new DefaultDoubleSolution(this);
    }
    
    @Override
    public int getNumberOfAggregatedObjectives() {
        return 0;
    }
}
