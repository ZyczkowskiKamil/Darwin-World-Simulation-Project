package agh.ics.oop;

import agh.ics.oop.model.GlobeMap;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;

public class SimulationStartApp extends Application {
    @FXML
    public Button startSimulationButton;
    @FXML
    public Button getParametersFromFileButton;
    @FXML
    public Button saveParametersToFileButton;

    static agh.ics.oop.model.Parameters parameters;

    static {
        try {
            parameters = new agh.ics.oop.model.Parameters();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private TextField mapRefreshTimeMsField;
    @FXML
    private TextField mapHeightField;
    @FXML
    private TextField mapWidthField;
    @FXML
    private TextField grassStartAmountField;
    @FXML
    private TextField energyAddedAfterEatingGrassField;
    @FXML
    private TextField numberOfGrassGrowingDailyField;
    @FXML
    private TextField startAnimalNumberField;
    @FXML
    private TextField startAnimalEnergyField;
    @FXML
    private TextField breedingReadyAnimalEnergyField;
    @FXML
    private TextField energyLostInReproductionField;
    @FXML
    private TextField minMutationNumberField;
    @FXML
    private TextField maxMutationNumberField;
    @FXML
    private TextField genesLengthField;
    @FXML
    private TextField agingAnimalsField;
    @FXML
    private TextField energyNeededForMovementField;
    @FXML
    private TextField waterAreasNumberField;
    @FXML
    private TextField waterAreasMinSizeField;
    @FXML
    private TextField waterAreasMaxSizeField;
    @FXML
    private TextField waterChangeDaysField;
    @FXML
    private TextField parametersFileName;
    @FXML
    private TextField userFileButtonMessage;

    @FXML
    public void initialize() {
        mapRefreshTimeMsField.setText(String.valueOf(parameters.MAP_REFRESH_TIME_MS));
        mapHeightField.setText(String.valueOf(parameters.MAP_HEIGHT));
        mapWidthField.setText(String.valueOf(parameters.MAP_WIDTH));
        grassStartAmountField.setText(String.valueOf(parameters.GRASS_START_AMOUNT));
        energyAddedAfterEatingGrassField.setText(String.valueOf(parameters.ENERGY_ADDED_AFTER_EATING_GRASS));
        numberOfGrassGrowingDailyField.setText(String.valueOf(parameters.NUMBER_OF_GRASS_GROWING_DAILY));
        startAnimalNumberField.setText(String.valueOf(parameters.START_ANIMAL_NUMBER));
        startAnimalEnergyField.setText(String.valueOf(parameters.START_ANIMAL_ENERGY));
        breedingReadyAnimalEnergyField.setText(String.valueOf(parameters.BREEDING_READY_ANIMAL_ENERGY));
        energyLostInReproductionField.setText(String.valueOf(parameters.ENERGY_LOST_IN_REPRODUCTION));
        minMutationNumberField.setText(String.valueOf(parameters.MIN_MUTATION_NUMBER));
        maxMutationNumberField.setText(String.valueOf(parameters.MAX_MUTATION_NUMBER));
        genesLengthField.setText(String.valueOf(parameters.GENES_LENGTH));
        agingAnimalsField.setText(String.valueOf(parameters.AGING_ANIMALS));
        energyNeededForMovementField.setText(String.valueOf(parameters.ENERGY_NEEDED_FOR_MOVEMENT));
        waterAreasNumberField.setText(String.valueOf(parameters.WATER_AREAS_NUMBER));
        waterAreasMinSizeField.setText(String.valueOf(parameters.WATER_AREAS_MIN_SIZE));
        waterAreasMaxSizeField.setText(String.valueOf(parameters.WATER_AREAS_MAX_SIZE));
        waterChangeDaysField.setText(String.valueOf(parameters.WATER_CHANGE_DAYS));
        parametersFileName.setText("parameters.txt");
    }

    public void setParameters() {
        parameters.MAP_REFRESH_TIME_MS = Integer.parseInt(mapRefreshTimeMsField.getText());
        System.out.println("MAP_REFRESH_TIME_MS: " + parameters.MAP_REFRESH_TIME_MS); //
        parameters.MAP_HEIGHT = Integer.parseInt(mapHeightField.getText());
        parameters.MAP_WIDTH = Integer.parseInt(mapWidthField.getText());
        parameters.GRASS_START_AMOUNT = Integer.parseInt(grassStartAmountField.getText());
        parameters.ENERGY_ADDED_AFTER_EATING_GRASS = Integer.parseInt(energyAddedAfterEatingGrassField.getText());
        parameters.NUMBER_OF_GRASS_GROWING_DAILY = Integer.parseInt(numberOfGrassGrowingDailyField.getText());
        parameters.START_ANIMAL_NUMBER = Integer.parseInt(startAnimalNumberField.getText());
        parameters.START_ANIMAL_ENERGY = Integer.parseInt(startAnimalEnergyField.getText());
        parameters.BREEDING_READY_ANIMAL_ENERGY = Integer.parseInt(breedingReadyAnimalEnergyField.getText());
        parameters.ENERGY_LOST_IN_REPRODUCTION = Integer.parseInt(energyLostInReproductionField.getText());
        parameters.MIN_MUTATION_NUMBER = Integer.parseInt(minMutationNumberField.getText());
        parameters.MAX_MUTATION_NUMBER = Integer.parseInt(maxMutationNumberField.getText());
        parameters.GENES_LENGTH = Integer.parseInt(genesLengthField.getText());
        parameters.AGING_ANIMALS = agingAnimalsField.getText().equals("true");
        parameters.ENERGY_NEEDED_FOR_MOVEMENT = Integer.parseInt(energyNeededForMovementField.getText());
        parameters.WATER_AREAS_NUMBER = Integer.parseInt(waterAreasNumberField.getText());
        parameters.WATER_AREAS_MIN_SIZE = Integer.parseInt(waterAreasMinSizeField.getText());
        parameters.WATER_AREAS_MAX_SIZE = Integer.parseInt(waterAreasMaxSizeField.getText());
        parameters.WATER_CHANGE_DAYS = Integer.parseInt(waterChangeDaysField.getText());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getClassLoader().getResource("simulationStart.fxml"));
        BorderPane viewRoot = loader1.load();

        configureStage(primaryStage, viewRoot);

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Application is closing...");
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    @FXML
    public void onSimulationStartClicked() {
        try {
            setParameters();
            parameters.saveToFile("parameters.txt");

            Thread.sleep(1000);

            // Load simulation.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
            BorderPane simulationRoot = loader.load();

            // Get presenter and set up the simulation
            SimulationPresenter simulationPresenter = loader.getController();
            GlobeMap worldMap = new GlobeMap(parameters);
            simulationPresenter.setWorldMap(worldMap);

            // Create and start simulation
            Simulation simulation = new Simulation(simulationPresenter, worldMap, parameters);
            simulationPresenter.setSimulation(simulation);

            // Create new scene and show it
            Stage stage = (Stage) startSimulationButton.getScene().getWindow();
            Scene simulationScene = new Scene(simulationRoot);
            stage.setScene(simulationScene);

            // Start simulation after scene transition
            simulation.runAsync();

            simulationPresenter.drawMap(); // Initial map draw

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onParametersGetButtonClicked() throws InterruptedException, IOException {
        String filename = parametersFileName.getText();

        parameters.getFromFile(filename);

        Thread.sleep(1000);

        initialize();
    }

    public void onParametersSaveButtonClicked() {
        String filename = parametersFileName.getText();
        setParameters();
        parameters.saveToFile(filename);
        userFileButtonMessage.setText("Saved to file successfully");
    }
}
