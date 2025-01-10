package agh.ics.oop.model;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
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

}
