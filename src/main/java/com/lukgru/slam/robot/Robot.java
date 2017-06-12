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

    public Robot(Position initialPosition) {
        this.routePlanner = new RoutePlanner();
        this.scanner = new WorldScanner();
        this.targetFinder = new TargetFinder();

        this.initialPosition = initialPosition;
    }

    public void start(final SimulationMap world) {
        ObservedMap observedMap = new ObservedMap();
        Position position = initialPosition;

        Position currentTarget = null;
        while (!isAtTarget(position, currentTarget)) {
            System.out.println(position);
            Collection<MapObject> currentlyVisibleObstacles = scanner.scan(position, world);
            observedMap.update(currentlyVisibleObstacles);
            currentTarget = targetFinder.getTargetPosition(observedMap);
            List<Position> route = routePlanner.planRoute(position, currentTarget, observedMap);
            Position nextPosition = route.get(1);

            //moveToNextPosition
            position = nextPosition;
        }
    }

    private boolean isAtTarget(Position position, Position currentTarget) {
        return position.equals(currentTarget);
    }
}
