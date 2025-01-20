package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.MapVisualizer;
import javafx.application.Application;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;



public class World {
    public World(){
    }

    public static void main(String[] args) {
        Application.launch(SimulationStartApp.class, args);
    }

    static Parameters parameters;

    static {
        try {
            parameters = new Parameters();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void run() throws FileNotFoundException {         //do zmiany po UI
//        Application.launch(SimulationApp.class);
//        Simulation simulation = new Simulation(); ///////////
//        simulation.run();
    }
}
