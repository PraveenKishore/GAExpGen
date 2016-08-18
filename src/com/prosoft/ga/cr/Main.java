package com.prosoft.ga.cr;

import com.prosoft.ga.cr.utils.MathInterpreter;

public class Main {

    public static void main(String[] args) {
        int populationSize = 109; // Size of population in each generation
        float elitismRate = 0.1f;   // The ratio of chromosomes that should be present in next generation. 0 <= elitismRate <= 1
        float mutationRate = 0.05f; // Mutation is likely this percentage of times. 0 <= mutationRate <= 1
        float crossoverRate = 0.8f; // The value used to determine whether crossover should occur. 0 <= crossoverRate <= 1

        int maxGenerations = 10000; // Stop after 10000 generations. LOL
        Population population = new Population(populationSize, crossoverRate, mutationRate, elitismRate);

        Chromosome best = population.getBestChromosome();
        int i = 0;
        while(best.getFitness() != 0 && i < maxGenerations) {
            i++;
            population.evolve();
            best = population.getBestChromosome();

            System.out.println("Generation: " + i + "\t" + best.getExpression() + "\t Fitness: " + best.getFitness() + " = " + MathInterpreter.solve(best.getExpression()));
        }

        System.out.println("Solution: " + best.getExpression() + " = " + MathInterpreter.solve(best.getExpression()));
    }

    public static void printPopulation(Chromosome[] chromosomes) {
        for(Chromosome c:chromosomes) {
            c.printChromosome();
        }
    }
}
