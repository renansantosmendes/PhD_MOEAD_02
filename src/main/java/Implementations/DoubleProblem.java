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
public interface DoubleProblem extends Problem<DoubleSolution> {

    Double getLowerBound(int index);

    Double getUpperBound(int index);
}
