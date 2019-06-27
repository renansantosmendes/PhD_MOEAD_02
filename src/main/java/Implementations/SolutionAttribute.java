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
public interface SolutionAttribute <S, V> extends Serializable  {
    public void setAttribute(S solution, V value) ;
  public V getAttribute(S solution) ;
  public Object getAttributeIdentifier() ;
}
