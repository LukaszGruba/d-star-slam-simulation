package com.lukgru.slam.robot;

import java.util.Optional;

/**
 * Created by Łukasz on 2017-06-10.
 */
public class TargetFinder {

    public Position getTargetPosition(ObservedMap observedMap) {
        MapObject goal = observedMap.getObservedObjects().values().stream()
                .filter(object -> object.getType().equals(MapObject.MapObjectType.GOAL))
                .findFirst()
                .orElse(temporaryGoal().orElseThrow(() -> new IllegalStateException("No goal found.")));
        return goal.getPosition();
    }

    private Optional<MapObject> temporaryGoal() {
        //TODO: handle situation when no goal is visible -> then it should return some new goal
        return Optional.empty();
    }

}
