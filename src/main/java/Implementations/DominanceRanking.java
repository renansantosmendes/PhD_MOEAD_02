/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.uma.jmetal.util.JMetalException;

/**
 *
 * @author renansantos
 */
public class DominanceRanking<S extends Solution<?>>
        extends GenericSolutionAttribute<S, Integer> implements Ranking<S> {

    private Comparator<S> dominanceComparator;
    private static final Comparator<Solution<?>> CONSTRAINT_VIOLATION_COMPARATOR
            = new OverallConstraintViolationComparator<Solution<?>>();

    private List<ArrayList<S>> rankedSubPopulations;

    /**
     * Constructor
     */
    public DominanceRanking(Comparator<S> comparator) {
        this.dominanceComparator = comparator;
        rankedSubPopulations = new ArrayList<>();
    }

    /**
     * Constructor
     */
    public DominanceRanking() {
        this(new DominanceComparator<>());
    }

    public DominanceRanking(Object id) {
        super(id);
        rankedSubPopulations = new ArrayList<>();
    }

    @Override
    public Ranking<S> computeRanking(List<S> solutionSet) {
        List<S> population = solutionSet;

        // dominateMe[i] contains the number of solutions dominating i
        int[] dominateMe = new int[population.size()];

        // iDominate[k] contains the list of solutions dominated by k
        List<List<Integer>> iDominate = new ArrayList<>(population.size());

        // front[i] contains the list of individuals belonging to the front i
        ArrayList<List<Integer>> front = new ArrayList<>(population.size() + 1);

        // Initialize the fronts
        for (int i = 0; i < population.size() + 1; i++) {
            front.add(new LinkedList<Integer>());
        }

        // Fast non dominated sorting algorithm
        // Contribution of Guillaume Jacquenot
        for (int p = 0; p < population.size(); p++) {
            // Initialize the list of individuals that i dominate and the number
            // of individuals that dominate me
            iDominate.add(new LinkedList<Integer>());
            dominateMe[p] = 0;
        }

        int flagDominate;
        for (int p = 0; p < (population.size() - 1); p++) {
            // For all q individuals , calculate if p dominates q or vice versa
            for (int q = p + 1; q < population.size(); q++) {
                flagDominate
                        = CONSTRAINT_VIOLATION_COMPARATOR.compare(solutionSet.get(p), solutionSet.get(q));
                if (flagDominate == 0) {
                    flagDominate = dominanceComparator.compare(solutionSet.get(p), solutionSet.get(q));
                }
                if (flagDominate == -1) {
                    iDominate.get(p).add(q);
                    dominateMe[q]++;
                } else if (flagDominate == 1) {
                    iDominate.get(q).add(p);
                    dominateMe[p]++;
                }
            }
        }

        for (int i = 0; i < population.size(); i++) {
            if (dominateMe[i] == 0) {
                front.get(0).add(i);
                solutionSet.get(i).setAttribute(getAttributeIdentifier(), 0);
            }
        }

        //Obtain the rest of fronts
        int i = 0;
        Iterator<Integer> it1, it2; // Iterators
        while (front.get(i).size() != 0) {
            i++;
            it1 = front.get(i - 1).iterator();
            while (it1.hasNext()) {
                it2 = iDominate.get(it1.next()).iterator();
                while (it2.hasNext()) {
                    int index = it2.next();
                    dominateMe[index]--;
                    if (dominateMe[index] == 0) {
                        front.get(i).add(index);
                        //RankingAndCrowdingAttr.getAttributes(solutionSet.get(index)).setRank(i);
                        solutionSet.get(index).setAttribute(getAttributeIdentifier(), i);
                    }
                }
            }
        }

        rankedSubPopulations = new ArrayList<>();
        //0,1,2,....,i-1 are fronts, then i fronts
        for (int j = 0; j < i; j++) {
            rankedSubPopulations.add(j, new ArrayList<S>(front.get(j).size()));
            it1 = front.get(j).iterator();
            while (it1.hasNext()) {
                rankedSubPopulations.get(j).add(solutionSet.get(it1.next()));
            }
        }

        return this;
    }

    @Override
    public List<S> getSubfront(int rank) {
        if (rank >= rankedSubPopulations.size()) {
            throw new JMetalException("Invalid rank: " + rank + ". Max rank = " + (rankedSubPopulations.size() - 1));
        }
        return rankedSubPopulations.get(rank);
    }

    @Override
    public int getNumberOfSubfronts() {
        return rankedSubPopulations.size();
    }
}
