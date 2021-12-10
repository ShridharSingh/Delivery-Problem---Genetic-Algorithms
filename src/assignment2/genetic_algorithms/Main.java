package assignment2.genetic_algorithms;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args)    {

        ArrayList<GeneticAlgorithm> inputPopulation = Population.loadPopulation("input.txt");

        for(int i = 0; i < inputPopulation.size(); i++){

            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println("Evolving Population "+(i + 1)+"...");
            inputPopulation.get(i).generateAlgorithm();
            System.out.println("\n--------------------------------------------------------------------------------");

        }

        System.out.println("End of File... Exiting Program");
        System.exit(0);

    }
}
