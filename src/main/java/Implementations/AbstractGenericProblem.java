/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementations;

/**
 *
 * @author renansantos
 */
@SuppressWarnings("serial")
public abstract class AbstractGenericProblem<S> implements Problem<S>  {

    private int numberOfVariables = 0;
    private int numberOfObjectives = 0;
    private int numberOfConstraints = 0;
    private int numberOfAggregatedObjectives = 0;
    private String name = null;

    /* Getters */
    @Override
    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    @Override
    public int getNumberOfObjectives() {
        return numberOfObjectives;
    }

    @Override
    public int getNumberOfConstraints() {
        return numberOfConstraints;
    }

    @Override
    public String getName() {
        return name;
    }

    /* Setters */
    protected void setNumberOfVariables(int numberOfVariables) {
        this.numberOfVariables = numberOfVariables;
    }

    protected void setNumberOfObjectives(int numberOfObjectives) {
        this.numberOfObjectives = numberOfObjectives;
    }
    
    protected void setNumberOfAggregatedObjectives(int numberOfObjectives) {
        this.numberOfAggregatedObjectives = numberOfObjectives;
    }

    protected void setNumberOfConstraints(int numberOfConstraints) {
        this.numberOfConstraints = numberOfConstraints;
    }

    protected void setName(String name) {
        this.name = name;
    }
}
