package assignment2.genetic_algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Population {


    public String name;
    public static int capacity;
    public static int quota;
    public static int size;

    public int weight;
    public int value;


    private static int maxObjects = 20;


    public Population(String name, int weight, int value)
    {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    public ArrayList<Chromosome> chromosomes;

    public static ArrayList<GeneticAlgorithm> loadPopulation(String path)
    {
        ArrayList<GeneticAlgorithm> population = new ArrayList<GeneticAlgorithm>();
        File inputFile = new File("input.txt");
        Scanner scan;
        GeneticAlgorithm initialisePopulation = null;
        try {

            scan = new Scanner(inputFile);
            String current = scan.nextLine(); //reads current line

            while(scan.hasNextLine()){

                if(current.equals("\n")|| current.equals("")){
                    current = scan.nextLine();
                    continue;
                }
                else{
                    if(current.equals("***")){ //symbolise new population

                        scan.hasNextLine();

                        capacity = scan.nextInt();
                        quota = scan.nextInt();
                        size = scan.nextInt();


                        if (size > maxObjects){

                            System.out.println("Error population too large. Too many Objects");
                            System.out.println("Program terminating");
                            System.exit(1);
                        }

                        population.add(new GeneticAlgorithm(capacity, quota, size));

                        initialisePopulation = population.get(population.size() - 1); // gets the latest population
                        current = scan.nextLine();
                    }
                    else {

                        initialisePopulation.insert(current);
                        current = scan.nextLine();

                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return population;
    }

    public ArrayList<Chromosome> getChromosomes(){

        return chromosomes;
    }





}
