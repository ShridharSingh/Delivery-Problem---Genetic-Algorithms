package assignment2.genetic_algorithms;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class Chromosome implements Comparable<Chromosome> {


    public ArrayList<Integer> genes; // either 1 or 0, 1 - in vehicle 0 - not in vehicle

    public int fitness;
    public int weight;
    //boolean isFitChanged;
    int fitness_temp;

    private Chromosome chromosome1, chromosome2;


    public Chromosome(ArrayList<Integer> genes)
    {
        this.genes = new ArrayList<>(genes);

    }

    public Chromosome()
    {
        this.genes = new ArrayList<>();

    }

    public ArrayList<Integer> getGenes()
    {
        return genes;
    }

    public int getFitness()
    {
        return fitness;
    }


    public int compareFitness(Chromosome c1, Chromosome c2){
        chromosome1 = c1;
        chromosome2 = c2;
        if (chromosome2.fitness > chromosome1.fitness) {

            return chromosome2.fitness;
        }
        else{
            return chromosome1.fitness;
        }
    }


    public Chromosome crossoverChromosome(Chromosome chromosome2)
    {
        Chromosome child = new Chromosome();
        //this.chromosome1 = chromosome1;
        this.chromosome2 = chromosome2;
        int crossoverPoint = ThreadLocalRandom.current().nextInt(genes.size());
        //child.genes = chromosome1.genes;

        ThreadLocalRandom.current().nextInt(1);
        for(int i = 0; i < genes.size(); i++) {

            //child.genes.add(chromosome1.genes.get(i));

            if(i > crossoverPoint) {
                child.genes.add(genes.get(i));
            }
            else {
                child.genes.add(chromosome2.genes.get(i));
            }
        }
        return child;
    }


    public void mutateChromosome(double mutationRate)
    {
        for(int i = 0; i < genes.size(); i++)
        {
            if(ThreadLocalRandom.current().nextInt(1) < mutationRate) //ensures it will only happen at that rate
                genes.set(i, ThreadLocalRandom.current().nextInt(0,2));
        }
    }




    @Override
    public int compareTo(Chromosome chromo) {

        if(this.fitness < chromo.fitness) {

            return 1;
        }
        else if(this.fitness == chromo.fitness) {

            return 0;
        }
        else {

            return -1;
        }
    }
}
