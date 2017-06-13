package com.lukgru.slam.robot;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Created by Łukasz on 2017-06-10.
 */
public class Simulation {

    private SimulationMap simulationMap = new SimulationMap(new HashSet<>());
    private Robot robot;

    public void start() {
        robot.init(simulationMap);
    }

    public Position nextStep() {
        return robot.nextStep();
    }

    public Optional<Robot> getRobot() {
        return Optional.ofNullable(robot);
    }

    public List<Position> getCurrentRoute() {
        return robot.getRoute();
    }

    public SimulationMap getSimulationMap() {
        return simulationMap;
    }

    public ObservedMap getObservedMap() {
        return robot.getObservedMap();
    }

    public void addObstacle(int x, int y) {
        MapObject obstacle = new MapObject(new Position(x, y), MapObject.MapObjectType.OBSTACLE);
        simulationMap.addObject(obstacle);
    }

    public void addObstacle(Position p) {
        addObstacle(p.getX(), p.getY());
    }

    public void addRobot(Position position) {
        this.robot = new Robot(position);
    }

    public void addGoal(Position position) {
        MapObject obstacle = new MapObject(position, MapObject.MapObjectType.GOAL);
        simulationMap.getObjects().removeIf(o -> o.getType().equals(MapObject.MapObjectType.GOAL));
        simulationMap.addObject(obstacle);
    }
}
