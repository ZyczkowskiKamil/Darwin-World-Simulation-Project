package agh.ics.oop.model;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parameters {
    //prowizoryczna wersja, dopóki nie ma interfejsu graficznego

    public int MAP_HEIGHT;
    public int MAP_WIDTH;
    public boolean WATER_MAP_VARIANT = false;
    public int GRASS_START_AMOUNT;
    public int ENERGY_ADDED_AFTER_EATING_GRASS;
    public int NUMBER_OF_GRASS_GROWING_DAILY;
    public int START_ANIMAL_NUMBER;
    public int START_ANIMAL_ENERGY;
    public int BREEDING_READY_ANIMAL_ENERGY;
    public int ENERGY_LOST_IN_REPRODUCTION;
    public int MIN_MUTATION_NUMBER;
    public int MAX_MUTATION_NUMBER;
    public int GENES_LENGTH;
    public boolean AGING_ANIMALS = false;
    public int ENERGY_NEEDED_FOR_MOVEMENT;

    public Parameters() throws FileNotFoundException {

        try {
            String filePath = System.getProperty("user.dir") + "\\src\\main\\java\\agh\\ics\\oop\\model\\" + "parameters.txt";
            Scanner scanner = new Scanner(new File(filePath));

            if (scanner.hasNextLine()) this.MAP_HEIGHT = scanner.nextInt();
            if (scanner.hasNextLine()) this.MAP_WIDTH = scanner.nextInt();
            if (scanner.hasNextLine()) this.WATER_MAP_VARIANT = scanner.nextBoolean();
            if (scanner.hasNextLine()) this.GRASS_START_AMOUNT = scanner.nextInt();
            if (scanner.hasNextLine()) this.ENERGY_ADDED_AFTER_EATING_GRASS = scanner.nextInt();
            if (scanner.hasNextLine()) this.NUMBER_OF_GRASS_GROWING_DAILY = scanner.nextInt();
            if (scanner.hasNextLine()) this.START_ANIMAL_NUMBER = scanner.nextInt();
            if (scanner.hasNextLine()) this.START_ANIMAL_ENERGY = scanner.nextInt();
            if (scanner.hasNextLine()) this.BREEDING_READY_ANIMAL_ENERGY = scanner.nextInt();
            if (scanner.hasNextLine()) this.ENERGY_LOST_IN_REPRODUCTION = scanner.nextInt();
            if (scanner.hasNextLine()) this.MIN_MUTATION_NUMBER = scanner.nextInt();
            if (scanner.hasNextLine()) this.MAX_MUTATION_NUMBER = scanner.nextInt();
            if (scanner.hasNextLine()) this.GENES_LENGTH = scanner.nextInt();
            if (scanner.hasNextLine()) this.AGING_ANIMALS = scanner.nextBoolean();
            if (scanner.hasNextLine()) this.ENERGY_NEEDED_FOR_MOVEMENT = scanner.nextInt();
            scanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
