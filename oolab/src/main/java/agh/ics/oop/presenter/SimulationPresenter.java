package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

public class SimulationPresenter {
    public Button startSimulationButton; // implements MapChangeListener {
    private GlobeMap worldMap;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label infoLabel;
    @FXML
    private TextField movesListTextField;
    @FXML
    private Label moveInfoLabel;
    @FXML
    private Button startButton;

    public void setWorldMap(GlobeMap worldMap) {
        this.worldMap = worldMap;
        clearGrid();
        drawMap(); // Draw initial map state
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst()); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void renderAxisLabels(Boundary boundary) {
        renderHorizontalAxis(boundary);
        renderVerticalAxis(boundary);
        placeCenterIndicator();
    }

    private void renderHorizontalAxis(Boundary boundary) {
        int minX = boundary.BOTTOM_LEFT().getX();
        int maxX = boundary.TOP_RIGHT().getX();

        for (int x = minX; x <= maxX; x++) {
            Label label = createLabel(String.valueOf(x));
            mapGrid.add(label, x - minX + 1, 0);
        }
    }

    private void renderVerticalAxis(Boundary boundary) {
        int minY = boundary.BOTTOM_LEFT().getY();
        int maxY = boundary.TOP_RIGHT().getY();

        for (int y = minY; y <= maxY; y++) {
            Label label = createLabel(String.valueOf(y));
            mapGrid.add(label, 0, maxY - y + 1);
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
        Label label;
        WorldElement element = worldMap.objectAt(position);

        if (worldMap.isWaterAt(position)) {
            label = createLabel("■");
            label.setTextFill(Color.BLUE);
        }
        else if (element != null) {
            label = createLabel(element.toString());
        }
        else {
            label = createLabel(" ");
        }
        mapGrid.add(label, col, row);
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setMinWidth(50);
        label.setMinHeight(50);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private void placeCenterIndicator() {
        Label centerLabel = createLabel("x/y");
        mapGrid.add(centerLabel, 0, 0);
    }

    @FXML
    public void drawMap() {
        Boundary boundary = worldMap.getBoundary();
        renderAxisLabels(boundary);
        populateGrid(boundary);
        infoLabel.setText("");
    }

//    @Override
    public void mapChanged(String message) {
        Platform.runLater(() -> {
            clearGrid();
            drawMap();
            moveInfoLabel.setText(message);
        });
    }

    @FXML
    public void onSimulationStartClicked() {
        try {
            Simulation simulation = new Simulation(this, worldMap);
            simulation.runAsync();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}