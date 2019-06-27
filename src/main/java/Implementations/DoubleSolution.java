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
public interface DoubleSolution extends Solution<Double> {

    public Double getLowerBound(int index);

    public Double getUpperBound(int index);

}
