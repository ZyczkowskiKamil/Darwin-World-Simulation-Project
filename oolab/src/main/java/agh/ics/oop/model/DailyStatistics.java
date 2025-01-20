package agh.ics.oop.model;

public class DailyStatistics {
    private final int day;
    private final int numberOfAnimals;
    private final int numberOfGrasses;
    private final int numberOfFreeSpaces;
    private final double averageEnergy;
    private final double averageDeadAnimalAge;
    private final double averageKidsAmount;

    public DailyStatistics(int day, int numberOfAnimals, int numberOfGrasses, int numberOfFreeSpaces, double averageEnergy, double averageDeadAnimalAge, double averageKidsAmount) {
        this.day = day;
        this.numberOfAnimals = numberOfAnimals;
        this.numberOfGrasses = numberOfGrasses;
        this.numberOfFreeSpaces = numberOfFreeSpaces;
        this.averageEnergy = averageEnergy;
        this.averageDeadAnimalAge = averageDeadAnimalAge;
        this.averageKidsAmount = averageKidsAmount;
    }

    public int getDay() {
        return day;
    }
    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public int getNumberOfGrasses() {
        return numberOfGrasses;
    }

    public int getNumberOfFreeSpaces() {
        return numberOfFreeSpaces;
    }

    public double getAverageEnergy() {
        return averageEnergy;
    }

    public double getAverageDeadAnimalAge() {
        return averageDeadAnimalAge;
    }

    public double getAverageKidsAmount() {
        return averageKidsAmount;
    }
}