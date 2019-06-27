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
public abstract class ReferencePoint extends PointSolution{
    public ReferencePoint(int numberOfObjectives) {
    super(numberOfObjectives);
  }

  public abstract void update(Solution<?> solution) ;
}
