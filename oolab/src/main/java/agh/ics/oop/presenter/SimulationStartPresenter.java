package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.SimulationStartApp;
import agh.ics.oop.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class SimulationStartPresenter {
    public Button startSimulationButton; // implements MapChangeListener {
    private GlobeMap worldMap;

    static agh.ics.oop.model.Parameters parameters;

    static {
        try {
            parameters = new agh.ics.oop.model.Parameters();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private Label infoLabel;
    @FXML
    private TextField movesListTextField;

    @FXML
    private Button startButton;

    @FXML
    public void onSimulationStartClicked() {
        try {
            // Load simulation.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
            BorderPane simulationRoot = loader.load();

            // Get presenter and set up the simulation
            SimulationPresenter simulationPresenter = loader.getController();
            GlobeMap worldMap = new GlobeMap(parameters);
            simulationPresenter.setWorldMap(worldMap);

            // Create and start simulation
            Simulation simulation = new Simulation(simulationPresenter, worldMap);

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
}