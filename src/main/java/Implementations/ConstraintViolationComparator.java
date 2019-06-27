/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementations;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author renansantos
 */
public interface ConstraintViolationComparator <S> extends Comparator<S>, Serializable {
    public int compare(S solution1, S solution2);
}
