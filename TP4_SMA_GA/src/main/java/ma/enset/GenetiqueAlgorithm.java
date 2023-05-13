package ma.enset;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class GenetiqueAlgorithm {
    private Individual[] population=new Individual[GAUtils.POPULATION_SIZE];
    private Individual individual1,individual2;

    public void initPopulation(){
        for (int i = 0; i < GAUtils.POPULATION_SIZE; i++) {
            population[i]=new Individual(population[i].getChromosome());
            population[i].calculateFitness();
        }
    }

    /*public void selection(){
        individual1=population[0];
        individual2=population[1];
    }*/
    public void crossover(){
        individual1=new Individual(population[0].getChromosome());
        individual2=new Individual(population[1].getChromosome());
        Random random=new Random();
        int crossoverPoint=random.nextInt(GAUtils.CHROMOSOME_SIZE-1);
        crossoverPoint++;
        for(int i=0;i<crossoverPoint;i++){
            individual1.getChromosome()[i]=population[1].getChromosome()[i];
            individual2.getChromosome()[i]=population[0].getChromosome()[i];
        }
        System.out.println("Crossover point: "+crossoverPoint);
        System.out.println("Individual 1: "+Arrays.toString(individual1.getChromosome()));
        System.out.println("Individual 2: "+Arrays.toString(individual2.getChromosome()));
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
