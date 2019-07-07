/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementations;

import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author renansantos
 */
public class DefaultDoubleSolution extends AbstractGenericSolution<Double, DoubleProblem>
    implements DoubleSolution {
    static int counter = 0;
  /** Constructor
     * @param problem */
  public DefaultDoubleSolution(DoubleProblem problem) {
    super(problem) ;

    initializeDoubleVariables();
    initializeObjectiveValues();
  }

  /** Copy constructor */
  public DefaultDoubleSolution(DefaultDoubleSolution solution) {
    super(solution.problem) ;

    for (int i = 0; i < problem.getNumberOfVariables(); i++) {
      setVariableValue(i, solution.getVariableValue(i));
    }

    for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
      setObjective(i, solution.getObjective(i)) ;
    }

    attributes = new HashMap<Object, Object>(solution.attributes) ;
  }

  @Override
  public Double getUpperBound(int index) {
    return problem.getUpperBound(index);
  }

  @Override
  public Double getLowerBound(int index) {
    return problem.getLowerBound(index) ;
  }

  @Override
  public DefaultDoubleSolution copy() {
    return new DefaultDoubleSolution(this);
  }

  @Override
  public String getVariableValueString(int index) {
    return getVariableValue(index).toString() ;
  }
  
  private void initializeDoubleVariables() {
    for (int i = 0 ; i < problem.getNumberOfVariables(); i++) {
      Double value = randomGenerator.nextDouble(getLowerBound(i), getUpperBound(i)) ;
      value = new Random(100000*i*i + counter*counter*counter).nextDouble();
      setVariableValue(i, value) ;
      
      counter++;
    }
  }
}
