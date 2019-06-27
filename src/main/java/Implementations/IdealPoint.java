/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementations;

import org.uma.jmetal.util.JMetalException;
import Implementations.ReferencePoint;

/**
 *
 * @author renansantos
 */
public class IdealPoint extends ReferencePoint{
    private static final double DEFAULT_INITIAL_VALUE = Double.POSITIVE_INFINITY ;

  public IdealPoint(int dimension) {
    super(dimension) ;

    for (int i = 0; i < dimension; i++) {
      this.setObjective(i, DEFAULT_INITIAL_VALUE);
    }
  }

  @Override
  public void update(Solution<?> solution) {
    if (solution == null) {
      throw new JMetalException("The solution is null") ;
    } else if (solution.getNumberOfObjectives() != this.getNumberOfObjectives()) {
      throw new JMetalException("The number of objectives of the solution ("
          + solution.getNumberOfObjectives()
          + ") "
          + "is different to the size of the reference point("
          + this.getNumberOfObjectives()
          + ")"
      );
    }

    for (int i = 0; i < this.getNumberOfObjectives(); i++) {
      if (this.getObjective(i) > solution.getObjective(i)) {
        this.setObjective(i, solution.getObjective(i));
      }
    }
  }
}
