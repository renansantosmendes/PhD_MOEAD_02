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
public interface Point {

    public int getNumberOfDimensions();

    public double[] getValues();

    public double getDimensionValue(int index);

    public void setDimensionValue(int index, double value);
}
