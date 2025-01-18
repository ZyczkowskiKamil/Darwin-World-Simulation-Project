package agh.ics.oop;

import agh.ics.oop.model.GlobeMap;
import agh.ics.oop.presenter.SimulationPresenter;
//import agh.ics.oop.presenter.SimulationStartPresenter;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class SimulationStartApp extends Application {
    public Button startSimulationButton;

    static agh.ics.oop.model.Parameters parameters;

    static {
        try {
            parameters = new agh.ics.oop.model.Parameters();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    GlobeMap worldMap = new GlobeMap(parameters);

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getClassLoader().getResource("simulationStart.fxml"));
        BorderPane viewRoot = loader1.load();

        configureStage(primaryStage, viewRoot);
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
