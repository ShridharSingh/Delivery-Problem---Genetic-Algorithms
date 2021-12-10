package assignment2.genetic_algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {

    private ArrayList<String> populationData;
    private ArrayList<Population> members;
    private  ArrayList<Chromosome> POPULATION;
    private int CAPACITY;
    private int QUOTA;
    private int SIZE;
    public static int[] GOAL_STATE = {};
    public static final int TOURNAMENT_SELECTION_SIZE = 2;
    //public static final int NUM_ELITE_CHROMOSOMES = 1;
    public static final double MUTATION_RATE = 0.025;
    private static final int CUTOFF_RATE = 0;
    ArrayList<Chromosome> chromosomes;


    public GeneticAlgorithm(int capacity, int quota, int size)
    {
        this.populationData = new ArrayList<String>();
        this.members = new ArrayList<Population>();
        this.POPULATION = new ArrayList<Chromosome>();
        this.SIZE = size;
        this.CAPACITY = capacity;
        this.QUOTA = quota;

    }

    public void insert(String member)
    {

        populationData.add(member);
    }

    public void evolve() {

        ArrayList<Integer> genes = new ArrayList<Integer>();

        for (int i = 0; i < populationData.size(); i++){

            String[] data = populationData.get(i).split(" ");
            ArrayList<String> dataList = new ArrayList<String>();

            for (String s : data){
                dataList.add(s); //adding all of the items in the chromosome to the list
            }

            for (int j = 0; j < dataList.size(); j++){

                String currentLine = dataList.get(j);
                if (currentLine.equals("")) {

                    dataList.remove(j); //removing blank lines
                }
            }

            members.add(new Population(dataList.get(0), Integer.parseInt(dataList.get(1)),
                    Integer.parseInt(dataList.get(2)))); // adding elements into members
        }

        for (int i = 0; i < SIZE; i++) {

            for(int j = 0; j < populationData.size(); j++) {

                int randomInt = ThreadLocalRandom.current().nextInt(0,2);
                genes.add(randomInt);
            }

            Chromosome newMember = new Chromosome(genes);
            POPULATION.add(newMember);
            genes.clear();
        }

    }


    public void calculateFitness() { //fitness of a member of a population/totalSelectedMembers list

        int totalWeight = 0;
        int totalValue = 0;

        for (int i = 0; i < POPULATION.size(); i++) {

            Chromosome totalSelectedMembers = POPULATION.get(i);

            for (int j = 0; j < members.size(); j++) {

                if(totalSelectedMembers.genes.get(j) == 1) {

                    totalWeight += members.get(j).weight;
                    totalValue += members.get(j).value;
                }
            }
            if(totalWeight > CAPACITY) {

                totalSelectedMembers.fitness = 0; //member/chromosome from within the new totalSelectedMembers list
                                                  // who's weight exceeds the capacity of the are not deemed unfit
                                                  // from within the population
            }

            else {
                totalSelectedMembers.fitness = totalValue;
                totalSelectedMembers.weight = totalWeight;
            }
        }
        Collections.sort(POPULATION);

    }

    public void calculateFitness(Chromosome chromosome) { //fitness of a single chromosome/member


         Chromosome selectedMember = chromosome;

         int weight = 0;
         int value = 0;


         for(int i = 0; i < members.size(); i++) {
             if(selectedMember.genes.get(i) == 1) {

                 weight += members.get(i).weight;
                 value += members.get(i).value;
             }
         }
         if(weight > CAPACITY) {

             selectedMember.fitness = 0; //individual member who's weight exceeds the capacity of the are not considered
         }
         else {
             selectedMember.fitness = value;
             selectedMember.weight = weight;
         }

    }



    public ArrayList<Chromosome> tournamentSelection(int selectionSize){

        int participants = selectionSize;
        int start = 0;

        ArrayList<Chromosome> roundWinners = new ArrayList<Chromosome>();
        ArrayList<Chromosome> tournamentMembers = new ArrayList<Chromosome>();
        ArrayList<Chromosome> fittest = new ArrayList<Chromosome>();

        for (int i = start; i < POPULATION.size(); i++) {
            tournamentMembers.add(POPULATION.get(i));
        }

        Collections.sort(tournamentMembers);
        roundWinners.add(tournamentMembers.get(0)); // highest fitness at 0
        Collections.sort(roundWinners);


        if (roundWinners.size() == 1) {
            roundWinners.add(tournamentMembers.get(1)); //fitness is incremental and second participant will be the second most fit
        }
        else{
            fittest.add(roundWinners.get(1));
        }

        fittest.add(roundWinners.get(0));
        fittest.add(roundWinners.get(1));
        tournamentMembers.clear();

        return fittest;

    }



    public void breedPopulation() {

        //finds parents to reproduce and applies crossover and mutation on children.
        //adds children to population to be reproduced again

        SIZE = POPULATION.size();

        for (int i = 0; i < SIZE; i++) {

            ArrayList<Chromosome> parents = tournamentSelection(SIZE);
            Chromosome child = parents.get(0).crossoverChromosome(parents.get(1));


            child.mutateChromosome(MUTATION_RATE);
            calculateFitness(child);
            POPULATION.add(child);
        }

        //unfit members of the population are then removed from consideration for selection/reproduction process
        if(POPULATION.size() >= 3000) {

            Collections.sort(POPULATION);
            for(int i = POPULATION.size() - 1; i > POPULATION.size() / 2 ; i--) {
                POPULATION.remove(i);
            }
        }
    }



    public void generateAlgorithm(){

        evolve();
        calculateFitness();


        int generation = 1;
        int maxGenerations = 200;
        int currentRound = 1;
        int solutionFitness = 0;
        int solutionGeneration = 0;

        Chromosome mostFit;

        while(true) {

            calculateFitness();
            mostFit = POPULATION.get(0);
            breedPopulation();
            int currentFitness = mostFit.fitness;


            if(solutionFitness < currentFitness) {

                solutionFitness = currentFitness;
                solutionGeneration = generation;
            }

            generation++;
            currentRound++;

            if(currentRound == maxGenerations){
                if(mostFit.fitness< QUOTA){currentRound = 0;}

                if(generation == maxGenerations){
                    break;
                }
                else{
                    break;
                }
            }
        }


        SIZE = POPULATION.size();
        Collections.sort(POPULATION);

        System.out.println("\nTotal Generations: "+generation);

        System.out.println("Solution Generation: "+solutionGeneration+"\n");
        System.out.println("Quota:    "+ QUOTA +"\t\tValue:  "+ POPULATION.get(0).fitness);
        System.out.println("Capacity: "+ CAPACITY +"\t\tWeight: "+ POPULATION.get(0).weight);
        System.out.print("Solution set: { ");

        for(int i = 0; i < POPULATION.get(0).genes.size(); i++){
            if(POPULATION.get(0).genes.get(i) == 1){
                System.out.print(members.get(i).name + ' ');
            }

        }
        System.out.print("}");
        System.out.println();
        if (POPULATION.get(0).weight <= CAPACITY && POPULATION.get(0).fitness >= QUOTA){
            System.out.println("Optimal Solution achieved!!");
        } else if (POPULATION.get(0).weight == 0 && POPULATION.get(0).fitness == 0){
            System.out.println("No Solution Achieved");
        }
        else {
            System.out.println("Optimal Solution Not Achieved. Best Solution within constraints");
        }
    }
}
