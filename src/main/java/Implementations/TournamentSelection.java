/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementations;

import java.util.Comparator;
import java.util.List;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.SolutionListUtils;

/**
 *
 * @author renansantos
 */
public class TournamentSelection <S extends Solution<?>> implements SelectionOperator<List<S>,S> {
    private Comparator<S> comparator;

  private final int numberOfTournaments;

  /** Constructor */
  public TournamentSelection(int numberOfTournaments) {
    this(new DominanceComparator<S>(), numberOfTournaments) ;
  }

  /** Constructor */
  public TournamentSelection(Comparator<S> comparator, int numberOfTournaments) {
    this.numberOfTournaments = numberOfTournaments;
    this.comparator = comparator ;
  }

  @Override
  /** Execute() method */
  public S execute(List<S> solutionList) {
    if (null == solutionList) {
      throw new JMetalException("The solution list is null") ;
    } else if (solutionList.isEmpty()) {
      throw new JMetalException("The solution list is empty") ;
    }

    S result;
    if (solutionList.size() == 1) {
      result = solutionList.get(0);
    } else {
      result = SolutionListUtils.selectNRandomDifferentSolutions(1, solutionList).get(0);
      int count = 1; // at least 2 solutions are compared
      do {
        S candidate = SolutionListUtils.selectNRandomDifferentSolutions(1, solutionList).get(0);
        result = SolutionUtils.getBestSolution(result, candidate, comparator) ;
      } while (++count < this.numberOfTournaments);
    }

    return result;
  }
}
