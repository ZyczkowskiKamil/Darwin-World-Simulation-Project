package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Random;

public class Genes {
    private final ArrayList<Integer> genesList;

    private final static int GENES_LENGTH = 8; // jako parametr

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

        //mutacja

        return new Genes(newGenes);
    }
}
