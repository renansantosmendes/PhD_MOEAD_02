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
public interface Ranking<S> extends SolutionAttribute<S, Integer> {

    public Ranking<S> computeRanking(List<S> solutionList);

    public List<S> getSubfront(int rank);

    public int getNumberOfSubfronts();
}
