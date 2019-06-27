/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementations;

import java.io.Serializable;

/**
 *
 * @author renansantos
 */
public interface Problem<S> extends Serializable  {
    /* Getters */
  int getNumberOfVariables() ;
  int getNumberOfObjectives() ;
  int getNumberOfAggregatedObjectives();
  int getNumberOfConstraints() ;
  String getName() ;

  /* Methods */
  void evaluate(S solution) ;
  S createSolution() ;
}
