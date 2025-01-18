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
            Stage simulationStage = new Stage();

            // Load the FXML for SimulationApp
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("simulation.fxml"));
            BorderPane viewRoot = loader.load();

            // Set up the scene and stage
            Scene simulationScene = new Scene(viewRoot);
            simulationStage.setScene(simulationScene);
            simulationStage.setTitle("Simulation App");

            // Get the presenter from the SimulationApp
            SimulationPresenter presenter = loader.getController();

            // Set the world map in the presenter
            GlobeMap worldMap = new GlobeMap(parameters);
            presenter.setWorldMap(worldMap); // Assuming parameters are accessible

            // Show the new stage (window)
            simulationStage.show();

            // Close the current stage (SimulationStartApp window)
            Stage currentStage = (Stage) startSimulationButton.getScene().getWindow();
            currentStage.close();

            Simulation simulation = new Simulation(presenter, worldMap);
            simulation.runAsync();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}