package com.lukgru.slam;

import com.lukgru.slam.dstar.DStarLite;
import com.lukgru.slam.dstar.State;

import java.util.List;
import java.util.Random;

/**
 * Created by Lukasz on 08.06.2017.
 */
public class Main {

    public static void main(String[] args) {
        Random r = new Random();

        //Create pathfinder
        DStarLite pf = new DStarLite();
        //set start and goal nodes
        pf.init(0,0,1000,1000);
        //set impassable nodes
        for (int i = 0; i < 20000; i++) {
            pf.updateCell(r.nextInt(1000), r.nextInt(1000), -1);
        }

        System.out.println("map initialised");

        //perform the pathfinding
        pf.replan();

        System.out.println("path found");

        //get and print the path
        pf.getPath()
                .forEach(i -> System.out.println("x: " + i.x + " y: " + i.y));
    }
}
