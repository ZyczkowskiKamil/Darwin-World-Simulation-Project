package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GenesTest {

    static Parameters parameters;
    static {
        try {
            parameters = new Parameters();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    Genes genes = new Genes();
    ArrayList<Integer> genesList;

    @Test
    void getGenesListLength() {
        assertEquals(parameters.GENES_LENGTH, genes.getGenesList().size());

        genes = new Genes(new Genes().getGenesList());

        assertEquals(parameters.GENES_LENGTH, genes.getGenesList().size());
    }

    @Test
    void getGenesListElements() {
        genesList = genes.getGenesList();
        for (int gene : genesList) {
            assertTrue(gene >= 0 && gene < 8);
        }
    }

    @Test
    void combineDuringReproducing() {
        Genes genes1 = new Genes();

        assertEquals(parameters.GENES_LENGTH, genes.combineDuringReproducing(100, 100, genes1).getGenesList().size());
    }
}