package com.lukgru.slam.robot;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Łukasz on 2017-06-10.
 */
public class WorldScanner {

    private static final double ANGLE_STEP = Math.PI / 180;
    private static final double SIGHT_MAX_DISTANCE = 50.0;

    public Collection<MapObject> scan(Position position, SimulationMap world) {
        Set<MapObject> visibleObjects = new HashSet<>();
        double angle = 0.0;
        while (Double.compare(angle, 2 * Math.PI) < 0) {
            nearestObjectFromDirection(world, position, angle)
                    .ifPresent(visibleObjects::add);
            angle += ANGLE_STEP;
        }
        return visibleObjects;
    }

    private Optional<MapObject> nearestObjectFromDirection(SimulationMap world, Position position, double angle) {
        int currentX = position.getX();
        int currentY = position.getY();
        for (double distance = 0; distance < SIGHT_MAX_DISTANCE; distance = distance + 0.01) {
            int x = (int) Math.round(distance * Math.cos(angle));
            int y = (int) Math.round(distance * Math.sin(angle));
            Optional<MapObject> object = world.getAt(new Position(currentX + x, currentY + y));
            if (object.isPresent()) {
                return object;
            }
        }
        return Optional.empty();
    }

}
