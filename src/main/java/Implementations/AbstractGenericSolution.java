/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 *
 * @author renansantos
 */
public class AbstractGenericSolution <T, P extends Problem<?>> implements Solution<T> {
    
  private double[] objectives;
  private double[] aggregatedObjectives;
  private List<T> variables;
  protected P problem ;
  protected Map<Object, Object> attributes ;
  protected final JMetalRandom randomGenerator ;

  /**
   * Constructor
   */
  protected AbstractGenericSolution(P problem) {
    this.problem = problem ;
    attributes = new HashMap<>() ;
    randomGenerator = JMetalRandom.getInstance() ;

    objectives = new double[problem.getNumberOfObjectives()] ;
    aggregatedObjectives = new double[problem.getNumberOfAggregatedObjectives()] ;
    variables = new ArrayList<>(problem.getNumberOfVariables()) ;
    for (int i = 0; i < problem.getNumberOfVariables(); i++) {
      variables.add(i, null) ;
    }
  }

  @Override
  public void setAttribute(Object id, Object value) {
    attributes.put(id, value) ;
  }

  @Override
  public Object getAttribute(Object id) {
    return attributes.get(id) ;
  }

  @Override
  public void setObjective(int index, double value) {
    objectives[index] = value ;
  }

  @Override
  public double getObjective(int index) {
    return objectives[index];
  }
  
  public void setAggregatedObjective(int index, double value) {
    aggregatedObjectives[index] = value ;
  }

  public double getAggregatedObjective(int index) {
    return aggregatedObjectives[index];
  }

  @Override
  public T getVariableValue(int index) {
    return variables.get(index);
  }

  @Override
  public void setVariableValue(int index, T value) {
    variables.set(index, value);
  }

  @Override
  public int getNumberOfVariables() {
    return variables.size();
  }

  @Override
  public int getNumberOfObjectives() {
    return objectives.length;
  }

  protected void initializeObjectiveValues() {
    for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
      objectives[i] = 0.0 ;
    }
  }

  @Override
  public String toString() {
    String result = "Variables: " ;
    for (T var : variables) {
      result += "" + var + " " ;
    }
    result += "Objectives: " ;
    for (Double obj : objectives) {
      result += "" + obj + " " ;
    }
    result += "\t" ;
    result += "AlgorithmAttributes: " + attributes + "\n" ;

    return result ;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    AbstractGenericSolution that = (AbstractGenericSolution) o;

    if (!attributes.equals(that.attributes))
      return false;
    if (!Arrays.equals(objectives, that.objectives))
      return false;
    if (!variables.equals(that.variables))
      return false;

    return true;
  }

  @Override public int hashCode() {
    int result = Arrays.hashCode(objectives);
    result = 31 * result + variables.hashCode();
    result = 31 * result + attributes.hashCode();
    return result;
  }

    @Override
    public String getVariableValueString(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Solution<T> copy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
