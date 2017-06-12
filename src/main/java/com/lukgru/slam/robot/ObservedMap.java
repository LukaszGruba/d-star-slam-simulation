package com.lukgru.slam.robot;

import io.vavr.Tuple2;
import io.vavr.collection.Stream;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Łukasz on 2017-06-10.
 */
public class ObservedMap {

    private Map<Position, MapObject> observedObjects = new HashMap<>();

    public void update(Collection<MapObject> objects) {
        Map<Position, MapObject> map = Stream.ofAll(objects).toJavaMap(o -> new Tuple2<>(o.getPosition(), o));
        observedObjects.putAll(map);
    }

    public Map<Position, MapObject> getObservedObjects() {
        return observedObjects;
    }
}
