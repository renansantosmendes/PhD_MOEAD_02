/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementations;

import org.uma.jmetal.util.JMetalException;

/**
 *
 * @author renansantos
 */
public class RepairDoubleSolutionAtBounds implements RepairDoubleSolution{
    public double repairSolutionVariableValue(double value, double lowerBound, double upperBound) {
    if (lowerBound > upperBound) {
      throw new JMetalException("The lower bound (" + lowerBound + ") is greater than the "
          + "upper bound (" + upperBound+")") ;
    }

    double result = value ;
    if (value < lowerBound) {
      result = lowerBound ;
    }
    if (value > upperBound) {
      result = upperBound ;
    }

    return result ;
  }
}
