package agh.ics.oop.model;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;

public class Parameters {
    //prowizoryczna wersja, dopóki nie ma interfejsu graficznego

    public int MAP_REFRESH_TIME_MS;
    public int MAP_HEIGHT;
    public int MAP_WIDTH;
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
    public int WATER_AREAS_NUMBER;
    public int WATER_AREAS_MIN_SIZE;
    public int WATER_AREAS_MAX_SIZE;
    public int WATER_CHANGE_DAYS;

    public Parameters() throws FileNotFoundException {

        try {
            URL parametersURL = getClass().getClassLoader().getResource("parameters/parameters.txt");
            Scanner scanner = new Scanner(new File(parametersURL.getFile()));

            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.MAP_REFRESH_TIME_MS = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.MAP_HEIGHT = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.MAP_WIDTH = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.GRASS_START_AMOUNT = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.ENERGY_ADDED_AFTER_EATING_GRASS = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.NUMBER_OF_GRASS_GROWING_DAILY = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.START_ANIMAL_NUMBER = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.START_ANIMAL_ENERGY = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.BREEDING_READY_ANIMAL_ENERGY = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.ENERGY_LOST_IN_REPRODUCTION = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.MIN_MUTATION_NUMBER = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.MAX_MUTATION_NUMBER = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.GENES_LENGTH = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.AGING_ANIMALS = (tmp.equals("true"));
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.ENERGY_NEEDED_FOR_MOVEMENT = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.WATER_AREAS_NUMBER = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.WATER_AREAS_MIN_SIZE = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.WATER_AREAS_MAX_SIZE = Integer.parseInt(tmp);
            }
            if (scanner.hasNextLine()) {
                String tmp = scanner.nextLine().split("=")[1];
                this.WATER_CHANGE_DAYS = Integer.parseInt(tmp);
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(String filename) {
        try {
            File file = Paths.get("src/main/resources/parameters/" + filename).toFile();

            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println("MAP_REFRESH_TIME_MS=" + MAP_REFRESH_TIME_MS);
                writer.println("MAP_HEIGHT=" + MAP_HEIGHT);
                writer.println("MAP_WIDTH=" + MAP_WIDTH);
                writer.println("GRASS_START_AMOUNT=" + GRASS_START_AMOUNT);
                writer.println("ENERGY_ADDED_AFTER_EATING_GRASS=" + ENERGY_ADDED_AFTER_EATING_GRASS);
                writer.println("NUMBER_OF_GRASS_GROWING_DAILY=" + NUMBER_OF_GRASS_GROWING_DAILY);
                writer.println("START_ANIMAL_NUMBER=" + START_ANIMAL_NUMBER);
                writer.println("START_ANIMAL_ENERGY=" + START_ANIMAL_ENERGY);
                writer.println("BREEDING_READY_ANIMAL_ENERGY=" + BREEDING_READY_ANIMAL_ENERGY);
                writer.println("ENERGY_LOST_IN_REPRODUCTION=" + ENERGY_LOST_IN_REPRODUCTION);
                writer.println("MIN_MUTATION_NUMBER=" + MIN_MUTATION_NUMBER);
                writer.println("MAX_MUTATION_NUMBER=" + MAX_MUTATION_NUMBER);
                writer.println("GENES_LENGTH=" + GENES_LENGTH);
                writer.println("AGING_ANIMALS=" + AGING_ANIMALS);
                writer.println("ENERGY_NEEDED_FOR_MOVEMENT=" + ENERGY_NEEDED_FOR_MOVEMENT);
                writer.println("WATER_AREAS_NUMBER=" + WATER_AREAS_NUMBER);
                writer.println("WATER_AREAS_MIN_SIZE=" + WATER_AREAS_MIN_SIZE);
                writer.println("WATER_AREAS_MAX_SIZE=" + WATER_AREAS_MAX_SIZE);
                writer.println("WATER_CHANGE_DAYS=" + WATER_CHANGE_DAYS);
            }

            System.out.println("Parameters saved successfully to " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
