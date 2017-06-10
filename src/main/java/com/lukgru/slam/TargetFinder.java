package com.lukgru.slam;

/**
 * Created by Łukasz on 2017-06-10.
 */
public class TargetFinder {

    public Position getTargetPosition(ObservedMap observedMap) {
        MapObject target = observedMap.getObservedObjects().values().stream()
                .filter(object -> object.getType().equals(MapObject.MapObjectType.GOAL))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No goal defined."));
        return target.getPosition();
    }

}
