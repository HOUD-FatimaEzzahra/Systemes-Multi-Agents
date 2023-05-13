package ma.enset;

public class Individual implements Comparable {
    int [] chromosome = new int[GAUtils.CHROMOSOME_SIZE];
    int fitness ;

    public Individual(int[] chromosome) {
        this.chromosome = chromosome;
        calculateFitness();
    }
    public Individual(){}

    public void calculateFitness() {
        for (int gene: chromosome) {
            fitness+= gene;
        }
    }

    public int getFitness() {
        return fitness;
    }

    public int[] getChromosome() {
        return chromosome;
    }

    public int compareTo(Object o) {
        Individual individual = (Individual) o;
        if (this.fitness > individual.fitness) return 1;
        else if (this.fitness < individual.fitness) return -1;
        else return 0;
    }

    public void setChromosome(int[] newChromosome1) {
        this.chromosome = newChromosome1;
    }
}
