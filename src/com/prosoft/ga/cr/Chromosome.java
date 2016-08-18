package com.prosoft.ga.cr;

import com.prosoft.ga.cr.utils.MathInterpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Praveen on 09-Aug-16.
 */
public class Chromosome implements Comparable<Chromosome> {
    private String expression;
    private double fitness;

    private final static double TARGET = 100;
    private final static int MAX_GENE_LENGTH = 50;
    private final static int MIN_GENE_LENGTH = 3;
    private final static float OPERATOR_FREQUENCY = 0.6f;
    private final static Random random = new Random(System.currentTimeMillis());

    public Chromosome(String expression) {
        this.expression = correct(expression.replaceAll("\\?", " "));
        this.fitness = calculateFitness();
    }

    public double calculateFitness() {
        double fitness = 0f;
        try {
            fitness = Math.abs(MathInterpreter.solve(expression) - TARGET);
        } catch (RuntimeException e) {
            fitness = TARGET;
        }
        return fitness;
    }

    public String getExpression() {
        return new String(expression.replaceAll(" ", ""));
    }

    public double getFitness() {
        return fitness;
    }

    public Chromosome[] mates(Chromosome chromosome) {

        char[] parent1 = expression.toCharArray();
        char[] parent2 = chromosome.expression.toCharArray();

        char[] temp1, temp2;
        int len = 0;
        if(parent1.length > parent2.length) {
            len = parent1.length;
            temp1 = new char[len];
            temp2 = new char[len];

            System.arraycopy(parent1, 0, temp1, 0, parent1.length);
            System.arraycopy(parent2, 0, temp2, 0, parent2.length);
            for(int i = parent2.length; i < len; i++) {
                temp2[i] = '?';
            }
        } else {
            len = parent2.length;
            temp1 = new char[len];
            temp2 = new char[len];

            System.arraycopy(parent2, 0, temp2, 0, parent2.length);
            System.arraycopy(parent1, 0, temp1, 0, parent1.length);
            for(int i = parent1.length; i < len; i++) {
                temp1[i] = '?';
            }
        }
        if(len <= 0) {
            return new Chromosome[]{this, chromosome};
        }

        char[] child1 = new char[len];
        char[] child2 = new char[len];

        int crossoverPoint = random.nextInt(((len-1) - 0) + 1) + 0;
        System.arraycopy(temp1, 0, child1, 0, crossoverPoint);
        System.arraycopy(temp2, crossoverPoint, child1, crossoverPoint, (len - crossoverPoint));


        System.arraycopy(temp2, 0, child2, 0, crossoverPoint);
        System.arraycopy(temp1, crossoverPoint, child2, crossoverPoint, (len - crossoverPoint));

        /*System.out.println("1: " + String.valueOf(temp1));
        System.out.println("2: " + String.valueOf(temp2));

        System.out.println("3: " + String.valueOf(child1));
        System.out.println("4: " + String.valueOf(child2));*/

        return new Chromosome[] {new Chromosome(String.valueOf(child1)), new Chromosome(String.valueOf(child2))};
    }

    public Chromosome mutate() {
        char[] expr = expression.toCharArray();
        if(expr.length <= 0) {
            return new Chromosome("");
        }
        int i = random.nextInt(((expr.length-1) - 0) + 1) + 0;
        if (random.nextFloat() > OPERATOR_FREQUENCY) {
            expr[i] = (char)((random.nextInt((9 - 0) + 1) + 0) + 48);
        } else {
            int index = random.nextInt(((OPERATORS.length - 1) - 0) + 1)  + 0;
            expr[i]  = OPERATORS[index];
        }
        return new Chromosome(String.valueOf(expr));
    }

    public static Chromosome generateRandom() {
        int len = random.nextInt((MAX_GENE_LENGTH - MIN_GENE_LENGTH) + 1) + MIN_GENE_LENGTH;
        String exp = new String();
        for(int i = 0; i < len; i++) {
            if (random.nextFloat() > OPERATOR_FREQUENCY) {
                exp = exp + random.nextInt((9 - 0) + 1) + 0;
            } else {
                int index = random.nextInt(((OPERATORS.length - 1) - 0) + 1)  + 0;
                exp = exp + OPERATORS[index];
            }
        }
        return new Chromosome(exp);
    }

    public String correct(String expression) {
        if(expression == null || expression.isEmpty()) {
            return "";
        }
        char[] expch = expression.toCharArray();
        String newExpr = "";

        if(isOperator(expch[0])) {
            newExpr = "";
        } else {
            newExpr += expch[0];
        }
        for(int i = 1; i < expch.length; i++) {
            if(isOperator(expch[i]) && isOperator(expch[i-1])) {

            } else {
                newExpr += expch[i];
            }
        }

        if(newExpr.length() > 2 && isOperator(expch[expch.length-1])) {
            newExpr = newExpr.substring(0, newExpr.length() - 2);
        }
        return newExpr;
    }

    public void printChromosome() {
        System.out.println(fitness + "\t\t:" + expression);
    }

    public static boolean isOperator(char c) {
        for(int i = 0; i < OPERATORS.length; i++) {
            if(OPERATORS[i] == c) {
                return true;
            }
        }
        return false;
    }
    public static final char[] OPERATORS = {'+', '-', '*', '/'};

    @Override
    public int compareTo(Chromosome o) {
        if(fitness < o.fitness) {
            return -1;
        } else if(fitness > o.fitness) {
            return 1;
        }
        return 0;
    }
}
