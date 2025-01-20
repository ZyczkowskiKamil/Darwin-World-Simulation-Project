package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static java.util.Collections.min;

public class SimulationPresenter {
    private GlobeMap worldMap;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label infoLabel;
    @FXML
    private Label moveInfoLabel;
    @FXML
    private Button simulationStartStopButton;
    @FXML
    private Button saveStatsButton;

    Simulation simulation;

    public void setWorldMap(GlobeMap worldMap) {
        this.worldMap = worldMap;
        clearGrid();
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    private void clearGrid() {
        if(!mapGrid.getChildren().isEmpty())
            mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst()); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void renderAxisLabels(Boundary boundary) {
        renderHorizontalAxis(boundary);
        renderVerticalAxis(boundary);
        placeCenterIndicators(boundary);
    }

    private void renderHorizontalAxis(Boundary boundary) {
        int minX = boundary.BOTTOM_LEFT().getX();
        int maxX = boundary.TOP_RIGHT().getX();
        int maxY = boundary.TOP_RIGHT().getY();

        for (int x = minX; x <= maxX; x++) {
            Label label = createLabel(String.valueOf(""));
            Label label2 = createLabel(String.valueOf(""));
            mapGrid.add(label, x - minX + 1, 0);
            mapGrid.add(label2, x - minX + 1, maxY+1);
        }
    }

    private void renderVerticalAxis(Boundary boundary) {
        int minY = boundary.BOTTOM_LEFT().getY();
        int maxY = boundary.TOP_RIGHT().getY();
        int maxX = boundary.TOP_RIGHT().getX();

        for (int y = minY; y <= maxY; y++) {
            Label label = createLabel(String.valueOf(""));
            Label label2 = createLabel(String.valueOf(""));
            mapGrid.add(label2, 0, maxY - y + 1);
            mapGrid.add(label, maxX+1, maxY - y + 1);
        }
    }

    private void populateGrid(Boundary boundary) {
        int minX = boundary.BOTTOM_LEFT().getX();
        int maxX = boundary.TOP_RIGHT().getX();
        int minY = boundary.BOTTOM_LEFT().getY();
        int maxY = boundary.TOP_RIGHT().getY();

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Vector2d position = new Vector2d(x, y);
                int columnIndex = x - minX + 1;
                int rowIndex = maxY - y + 1;
                addGridCell(position, columnIndex, rowIndex);
            }
        }
    }



    private void addGridCell(Vector2d position, int col, int row) {
        Pane pane = new Pane();
        WorldElement element = worldMap.objectAt(position);

        if (worldMap.isWaterAt(position)) {
            pane.setStyle("-fx-background-color: BLUE;");
//            label.setTextFill(Color.BLUE);
//            label.autosize();
        }
        else if (element != null) {
            if (element.toString().equals("A")) {
                Animal animal = (Animal) element;
                int energyLevel = animal.energyLevelColour();
                if (energyLevel == 3) { // reproduction ready
                    pane.setStyle("-fx-background-color: BROWN;");
                }
                else if(energyLevel == 0){
                    pane.setStyle("-fx-background-color: RED;");
                }
                else if(energyLevel == 1){
                    pane.setStyle("-fx-background-color: ORANGE;");
                }
                else {
                    pane.setStyle("-fx-background-color: YELLOW;");
                }
            }
            else {
                pane.setStyle("-fx-background-color: DARKGREEN;");
            }
        }
        else {
            pane.setStyle("-fx-background-color: LIGHTGREEN;");
        }
        mapGrid.add(pane, col, row);
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        Boundary boundary = worldMap.getBoundary();
        int scale = Math.max(boundary.TOP_RIGHT().getX(), boundary.TOP_RIGHT().getY());
        label.setMinWidth(Math.ceil((double) 500 / scale));
        label.setMinHeight(Math.ceil((double) 500 / scale));
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private void placeCenterIndicators(Boundary boundary) {
        int maxY = boundary.TOP_RIGHT().getY();
        int maxX = boundary.TOP_RIGHT().getX();
        Label centerLabel = createLabel("");
        Label centerLabel2 = createLabel("");
        Label centerLabel3 = createLabel("");
        Label centerLabel4 = createLabel("");


        mapGrid.add(centerLabel, 0, 0);
        mapGrid.add(centerLabel2, maxX+2, 0);
        mapGrid.add(centerLabel3, 0, maxY+2);
        mapGrid.add(centerLabel4, maxX+2, maxY+2);

    }

    @FXML
    public void drawMap() {
        Boundary boundary = worldMap.getBoundary();
        renderAxisLabels(boundary);
        populateGrid(boundary);
        infoLabel.setText("");
    }

    public void mapChanged(String message) {
        Platform.runLater(() -> {
            clearGrid();
            drawMap();
            moveInfoLabel.setText(message);
        });
    }

    @FXML
    private void onSimulationStartStopButtonClicked() {
        this.simulation.startStopSimulation();
    }

    @FXML
    private void onSaveStatsButtonClicked() {
        List<DailyStatistics> dailyStatistics = worldMap.getDailyStatistics();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Statistics");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(mapGrid.getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.append("Day,Animals,Grass,Free Spaces,Average Energy,Average Dead Animal Age, Average Kids Amount\n");
                for (DailyStatistics stats : dailyStatistics) {
                    writer.append(String.valueOf(stats.getDay())).append(",")
                            .append(String.valueOf(stats.getNumberOfAnimals())).append(",")
                            .append(String.valueOf(stats.getNumberOfGrasses())).append(",")
                            .append(String.valueOf(stats.getNumberOfFreeSpaces())).append(",")
                            .append(String.valueOf(stats.getAverageEnergy())).append(",")
                            .append(String.valueOf(stats.getAverageDeadAnimalAge())).append(",")
                            .append(String.valueOf(stats.getAverageKidsAmount())).append("\n");
                }
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}