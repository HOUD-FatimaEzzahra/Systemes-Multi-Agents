package ma.enset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenetiqueAlgorithm {
    private Individual[] population=new Individual[GAUtils.POPULATION_SIZE];
    public void initPopulation(){
        for (int i = 0; i < GAUtils.POPULATION_SIZE; i++) {
            population[i]=new Individual();
        }
    }
    public void showPopulation(){
        for (Individual individual : population) {
            System.out.println(Arrays.toString(individual.getChromosome()));
        }
    }
}
