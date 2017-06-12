package com.lukgru.slam.robot;

import java.util.Collection;
import java.util.List;

/**
 * Created by Łukasz on 2017-06-10.
 */
public class Robot {
    private final RoutePlanner routePlanner;
    private final WorldScanner scanner;
    private final TargetFinder targetFinder;

    private final Position initialPosition;
    private ObservedMap observedMap;
    private Position position;
    private Position currentTarget = null;
    private SimulationMap world;
    private List<Position> route;

    public Robot(Position initialPosition) {
        this.routePlanner = new RoutePlanner();
        this.scanner = new WorldScanner();
        this.targetFinder = new TargetFinder();

        this.initialPosition = initialPosition;
        this.position = initialPosition;
    }

//    public void start(final SimulationMap world) {
//        ObservedMap observedMap = new ObservedMap();
//        Position position = initialPosition;
//
//        Position currentTarget = null;
//        while (!isAtTarget(position, currentTarget)) {
//            System.out.println(position);
//            Collection<MapObject> currentlyVisibleObstacles = scanner.scan(position, world);
//            observedMap.update(currentlyVisibleObstacles);
////            currentTarget = targetFinder.getTargetPosition(observedMap);
//            currentTarget = targetCheat(world);
//            List<Position> route = routePlanner.planRoute(position, currentTarget, observedMap);
//            Position nextPosition = route.get(1);
//
//            //moveToNextPosition
//            position = nextPosition;
//        }
//    }

    public void init(final SimulationMap world) {
        this.world = world;
        observedMap = new ObservedMap();
        position = initialPosition;
        currentTarget = null;
    }

    public Position nextStep() {
        System.out.println(position);
        Collection<MapObject> currentlyVisibleObstacles = scanner.scan(position, world);
        observedMap.update(currentlyVisibleObstacles);
//            currentTarget = targetFinder.getTargetPosition(observedMap);
        currentTarget = targetCheat(world);
        route = routePlanner.planRoute(position, currentTarget, observedMap);
        Position nextPosition = route.get(1);

        //moveToNextPosition
        position = nextPosition;
        return isAtTarget(position, currentTarget) ? null : position;
    }

    private boolean isAtTarget(Position position, Position currentTarget) {
        return position.equals(currentTarget);
    }

    public List<Position> getRoute() {
        return route;
    }

    private Position targetCheat(SimulationMap world) {
        return world.getObjects().stream().filter(o -> o.getType().equals(MapObject.MapObjectType.GOAL)).findFirst().orElseThrow(() -> new IllegalStateException("no goal found")).getPosition();
    }
}
