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
public class GenericSolutionAttribute <S extends Solution<?>, V> implements SolutionAttribute<S, V>{
    private Object identifier;

  /**
   * Constructor
   */
  public GenericSolutionAttribute() {
    identifier = this.getClass() ;
  }

  /**
   * Constructor
   * @param id Attribute identifier
   */
  public GenericSolutionAttribute(Object id) {
    this.identifier = id ;
  }

  @SuppressWarnings("unchecked")
  @Override
  public V getAttribute(S solution) {
    return (V)solution.getAttribute(getAttributeIdentifier());
  }

  @Override
  public void setAttribute(S solution, V value) {
     solution.setAttribute(getAttributeIdentifier(), value);
  }

  @Override
  public Object getAttributeIdentifier() {
    return identifier;
  }
}
