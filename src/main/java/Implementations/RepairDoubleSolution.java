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
public interface RepairDoubleSolution extends Serializable {
    public double repairSolutionVariableValue(double value, double lowerBound, double upperBound);
}
