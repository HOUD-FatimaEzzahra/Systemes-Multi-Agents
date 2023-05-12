package ma.enset;

public class Individual implements Comparable {
    int [] chromosome = new int[GAUtils.CHROMOSOME_SIZE];
    int fitness ;

    public Individual() {
        for (int i = 0; i < GAUtils.CHROMOSOME_SIZE; i++) {
            if(Math.random() >= 0.5) chromosome[i] = 1;
            else chromosome[i] = 0;
        }
    }

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
}
