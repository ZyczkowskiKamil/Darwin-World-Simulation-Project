package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        run();
    }

    public static void run() {

        GlobeMap map = new GlobeMap(10,10, 10);
        MapVisualizer mapVisualizer = new MapVisualizer(map);

        Genes genes = new Genes(new ArrayList<>(List.of(0,1,2,3,4,5,6,7)));
        Animal animal1 = new Animal(new Vector2d(2,2), genes, 30);
        map.placeAnimal(animal1);

        Animal animal2 = new Animal(new Vector2d(3,3), genes, 30);
        map.placeAnimal(animal2);



        System.out.println(mapVisualizer.draw());
//        mapVisualizer.draw();

        System.out.println(animal1.getAnimalOrientation());
        map.move(animal1);

        System.out.println(animal2.getAnimalOrientation());
        map.move(animal2);
        System.out.println(mapVisualizer.draw());
    }
}
