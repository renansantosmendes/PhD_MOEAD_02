/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementations;

import java.util.Comparator;

/**
 *
 * @author renansantos
 */
public class BinaryTournamentSelection <S extends Solution<?>> extends TournamentSelection<S> {
    public BinaryTournamentSelection() {
    super(new DominanceComparator<S>(), 2) ;
  }

  /** Constructor */
  public BinaryTournamentSelection(Comparator<S> comparator) {
    super(comparator,2);
  }
}
