package ma.enset;

import java.util.Arrays;
import java.util.Comparator;

public class GenetiqueAlgorithm {
    private Individual[] population=new Individual[GAUtils.POPULATION_SIZE];
    private Individual individual1,individual2;

    public void initPopulation(){
        for (int i = 0; i < GAUtils.POPULATION_SIZE; i++) {
            population[i]=new Individual();
            population[i].calculateFitness();
        }
    }

    public void selection(){
        individual1=population[0];
        individual2=population[1];
    }
    
    public void showPopulation(){
        for (Individual individual : population) {
            System.out.println(Arrays.toString(individual.getChromosome())+" = "+individual.getFitness());
        }
    }
    public void sortPopulation(){
        Arrays.sort(population, Comparator.reverseOrder());
    }

}
