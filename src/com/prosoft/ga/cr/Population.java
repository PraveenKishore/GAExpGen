package com.prosoft.ga.cr;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Praveen on 11-Aug-16.
 */
public class Population {
    private float elitism;
    private float mutationRate;
    private int populationSize;
    private float crossoverRate;

    private Chromosome population[];
    private Random random = new Random(System.currentTimeMillis());

    public Population(int populationSize, float crossoverRate, float mutationRate, float elitism) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitism = elitism;

        population = new Chromosome[populationSize];
        for(int i = 0; i < populationSize; i++) {
            population[i] = Chromosome.generateRandom();
        }
    }

    public void evolve() {
        Chromosome[] buffer = new Chromosome[population.length];
        int index = Math.round(population.length * elitism);
        System.arraycopy(population, 0, buffer, 0, index);

        for(int i = index; i < population.length; i++) {
            if(random.nextFloat() < crossoverRate) { // Determine if crossover should occur,
                Chromosome[] parent = selectParents();
                Chromosome[] child = parent[0].mates(parent[1]);

                // Determine if mutation is required for first child
                if(random.nextFloat() <= mutationRate) {
                    buffer[i] = child[0].mutate();
                } else {
                    buffer[i] = child[0];
                }

                // Check if buffer is full,
                if(i >= buffer.length-1) {
                    break;
                }

                // Determine if mutation is required for second child
                if(random.nextFloat() <= mutationRate) {
                    buffer[i+1] = child[1].mutate();
                } else {
                    buffer[i+1] = child[1];
                }

                i++; // Done because 2 child chromosomes are generated, but the counter i is increment only one in for()
            } else {
                // No crossover
                // Determine if mutation is required
                if(random.nextFloat() <= mutationRate) {
                    buffer[i] = population[i].mutate();
                } else {
                    buffer[i] = population[i];
                }
            }
        }

        try {
            Arrays.sort(buffer);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        population = buffer;
    }

    public Chromosome[] selectParents() {
        Chromosome[] parents = new Chromosome[2];

        // Randomly select two parents via tournament selection.
        for (int i = 0; i < 2; i++) {
            parents[i] = population[random.nextInt(population.length)];
            for (int j = 0; j < 3; j++) {
                int idx = random.nextInt(population.length);
                if (population[idx].compareTo(parents[i]) < 0) {
                    parents[i] = population[idx];
                }
            }
        }
        return parents;
    }

    public Chromosome getBestChromosome() {
        return population[0];
    }

    public Chromosome[] getPopulation() {
        Chromosome buff[] = new Chromosome[population.length];
        System.arraycopy(population, 0, buff, 0, population.length);
        Arrays.sort(buff);
        return buff;
    }
}
