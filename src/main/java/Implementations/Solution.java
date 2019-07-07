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
public interface Solution<T> extends Serializable {

    void setObjectives(double[] value);
    
    void setObjective(int index, double value);

    double getObjective(int index);

    T getVariableValue(int index);

    void setVariableValue(int index, T value);

    String getVariableValueString(int index);

    int getNumberOfVariables();

    int getNumberOfObjectives();

    Solution<T> copy();

    void setAttribute(Object id, Object value);

    Object getAttribute(Object id);
}
