package agh.ics.oop.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Genes {
    private final ArrayList<Integer> genesList;

    static Parameters parameters;
    static {
        try {
            parameters = new Parameters();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final static int GENES_LENGTH = parameters.GENES_LENGTH;
    private final static int MIN_MUTATION_NUMBER = parameters.MIN_MUTATION_NUMBER;
    private final static int MAX_MUTATION_NUMBER = parameters.MAX_MUTATION_NUMBER;

    public Genes() {

        Random rand = new Random();

        genesList = new ArrayList<>(GENES_LENGTH);
        for (int i = 0; i < GENES_LENGTH; i++) {
            genesList.add(rand.nextInt(8));
        }
    }

    public Genes(ArrayList<Integer> genes) {
        this.genesList = genes;
    }

    public ArrayList<Integer> getGenesList() {
        return genesList;
    }

    public Genes combineDuringReproducing(int energy1, int energy2, Genes genes2) {
        ArrayList<Integer> newGenes = new ArrayList<>();

        try {
            float percentage = (float) energy1 / (energy2 + energy1);
            int genes1ToPass = Math.round(GENES_LENGTH * percentage);
            int genes2ToPass = GENES_LENGTH - genes1ToPass;

            Random rand = new Random();
            int firstSide = rand.nextInt(2);
            if (firstSide == 0) {
                // pierwszy z lewej
                for (int i = 0; i < genes1ToPass; i++) {
                    newGenes.add(this.genesList.get(i));
                }
                for (int i = genes1ToPass; i < GENES_LENGTH; i++) {
                    newGenes.add(genes2.genesList.get(i));
                }
            }
            else {
                // pierwszy z prawej
                for (int i = 0; i < genes2ToPass; i++) {
                    newGenes.add(genes2.genesList.get(i));
                }
                for (int i = genes2ToPass; i < GENES_LENGTH; i++) {
                    newGenes.add(this.genesList.get(i));
                }
            }
        }
        catch (ArithmeticException e) {
            System.out.println("Division by zero");
        }

        Random rand = new Random();
        int genesToMutate = rand.nextInt(MAX_MUTATION_NUMBER - MIN_MUTATION_NUMBER) + MIN_MUTATION_NUMBER;

        List<Integer> indices = new ArrayList<>(List.of(0,1,2,3,4,5,6,7));

        Collections.shuffle(indices);

        for (int i = 0; i < genesToMutate; i++) {
            newGenes.set(indices.get(i), rand.nextInt(8));
        }

        return new Genes(newGenes);
    }
}
