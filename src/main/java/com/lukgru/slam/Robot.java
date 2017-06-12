package com.lukgru.slam;

import java.util.Collection;
import java.util.List;

/**
 * Created by Łukasz on 2017-06-10.
 */
public class Robot {
    private final RoutePlanner routePlanner;
    private final WorldScanner scanner;
    private final TargetFinder targetFinder;

    public Robot(RoutePlanner routePlanner, WorldScanner scanner, TargetFinder targetFinder) {
        this.routePlanner = routePlanner;
        this.scanner = scanner;
        this.targetFinder = targetFinder;
    }

    public void start(final SimulationMap world) {
        ObservedMap observedMap = new ObservedMap();
        Position position = new Position(0, 0);

        Position currentTarget = targetFinder.getTargetPosition(observedMap);
        while (isAtTarget(position, currentTarget)) {
            Collection<MapObject> currentlyVisibleObstacles = scanner.scan(position, world);
            observedMap.update(currentlyVisibleObstacles);
            currentTarget = targetFinder.getTargetPosition(observedMap);
            List<Position> route = routePlanner.planRoute(position, currentTarget, observedMap);
            Position nextPosition = route.remove(0);

            //moveToNextPosition
            position = nextPosition;
        }
    }

    private boolean isAtTarget(Position position, Position currentTarget) {
        return position.equals(currentTarget);
    }
}
