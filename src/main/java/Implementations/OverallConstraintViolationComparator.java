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
public class OverallConstraintViolationComparator<S extends Solution<?>>
    implements ConstraintViolationComparator<S>  {
    private OverallConstraintViolation<S> overallConstraintViolation ;

  /**
   * Constructor
   */
  public OverallConstraintViolationComparator() {
    overallConstraintViolation = new OverallConstraintViolation<S>() ;
  }

  /**
   * Compares two solutions. If the solutions has no constraints the method return 0
   *
   * @param solution1 Object representing the first <code>Solution</code>.
   * @param solution2 Object representing the second <code>Solution</code>.
   * @return -1, or 0, or 1 if o1 is less than, equal, or greater than o2,
   * respectively.
   */
  public int compare(S solution1, S solution2) {
    double violationDegreeSolution1 ;
    double violationDegreeSolution2;
    if (overallConstraintViolation.getAttribute(solution1) == null) {
      return 0 ;
    }
    violationDegreeSolution1 =  overallConstraintViolation.getAttribute(solution1);
    violationDegreeSolution2 = overallConstraintViolation.getAttribute(solution2);

    if ((violationDegreeSolution1 < 0) && (violationDegreeSolution2 < 0)) {
      if (violationDegreeSolution1 > violationDegreeSolution2) {
        return -1;
      } else if (violationDegreeSolution2 > violationDegreeSolution1) {
        return 1;
      } else {
        return 0;
      }
    } else if ((violationDegreeSolution1 == 0) && (violationDegreeSolution2 < 0)) {
      return -1;
    } else if ((violationDegreeSolution1 < 0) && (violationDegreeSolution2 == 0)) {
      return 1;
    } else {
      return 0;
    }
  }
}
