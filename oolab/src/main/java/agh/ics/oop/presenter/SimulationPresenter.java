package agh.ics.oop.presenter;

import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import static java.util.Collections.min;

public class SimulationPresenter {
    private GlobeMap worldMap;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label infoLabel;
    @FXML
    private Label moveInfoLabel;

    public void setWorldMap(GlobeMap worldMap) {
        this.worldMap = worldMap;
        clearGrid();
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
        placeCenterIndicator();
    }

    private void renderHorizontalAxis(Boundary boundary) {
        int minX = boundary.BOTTOM_LEFT().getX();
        int maxX = boundary.TOP_RIGHT().getX();

        for (int x = minX; x <= maxX; x++) {
            Label label = createLabel(String.valueOf(""));
            mapGrid.add(label, x - minX + 1, 0);
        }
    }

    private void renderVerticalAxis(Boundary boundary) {
        int minY = boundary.BOTTOM_LEFT().getY();
        int maxY = boundary.TOP_RIGHT().getY();

        for (int y = minY; y <= maxY; y++) {
            Label label = createLabel(String.valueOf(""));
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
                if(energyLevel == 0){
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


//    private void addGridCell(Vector2d position, int col, int row) {
//        Label label;
//        WorldElement element = worldMap.objectAt(position);
//
//        if (worldMap.isWaterAt(position)) {
//            label = createLabel("■");
//            label.setTextFill(Color.BLUE);
//            label.autosize();
//        }
//        else if (element != null) {
//            if (element.toString().equals("A")) {
//                label = createLabel("■");
//
//                Animal animal = (Animal) element;
//                int energyLevel = animal.energyLevelColour();
//                if(energyLevel == 0){
//                    label.setTextFill(Color.YELLOW);
//                }
//                else if(energyLevel == 1){
//                    label.setTextFill(Color.ORANGE);
//                }
//                else {
//                    label.setTextFill(Color.RED);
//                }
//                label.autosize();
//            }
//            else {
//                label = createLabel("■");
//                label.setTextFill(Color.GREEN);
//                label.autosize();
//            }
//        }
//        else {
//            label = createLabel(" ");
//        }
//        mapGrid.add(label, col, row);
//    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        Boundary boundary = worldMap.getBoundary();
        int scale = Math.max(boundary.TOP_RIGHT().getX(), boundary.TOP_RIGHT().getY());
        label.setMinWidth(Math.ceil((double) 500 / scale));
        label.setMinHeight(Math.ceil((double) 500 / scale));
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private void placeCenterIndicator() {
        Label centerLabel = createLabel("");
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
}