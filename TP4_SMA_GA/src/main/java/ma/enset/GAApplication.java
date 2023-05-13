package ma.enset;

import java.util.ArrayList;
import java.util.List;

public class GAApplication {

    public static void main(String[] args) {
        GenetiqueAlgorithm genetiqueAlgorithm = new GenetiqueAlgorithm();
        genetiqueAlgorithm.initPopulation();
        genetiqueAlgorithm.sortPopulation();
        genetiqueAlgorithm.showPopulation();
        genetiqueAlgorithm.crossover();
    }
}