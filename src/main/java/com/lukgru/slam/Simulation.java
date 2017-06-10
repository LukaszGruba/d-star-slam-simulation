package com.lukgru.slam;

/**
 * Created by Łukasz on 2017-06-10.
 */
public class Simulation {

    public void start() {
        SimulationMap simulationMap = createMap();
        RoutePlanner routePlanner = new RoutePlanner();
        WorldScanner scanner = new WorldScanner();
        TargetFinder targetFinder = new TargetFinder();
        Robot robot = new Robot(simulationMap, routePlanner, scanner, targetFinder);
        robot.start();
    }

    private SimulationMap createMap() {
        throw new UnsupportedOperationException("not implemented yet");
    }

}
