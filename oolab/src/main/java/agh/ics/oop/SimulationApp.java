package agh.ics.oop;

import agh.ics.oop.model.GlobeMap;
import agh.ics.oop.model.Parameters;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class SimulationApp extends Application {

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

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();

        configureStage(primaryStage, viewRoot);

        presenter.setWorldMap(worldMap);

        Simulation simulation = new Simulation(presenter, worldMap);
        simulation.runAsync();

        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
