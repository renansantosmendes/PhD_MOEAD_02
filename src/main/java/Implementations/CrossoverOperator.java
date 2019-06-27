/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementations;

import java.util.List;

/**
 *
 * @author renansantos
 */
public interface CrossoverOperator<Source> extends Operator<List<Source>, List<Source>> {

    int getNumberOfRequiredParents();

    int getNumberOfGeneratedChildren();
}
