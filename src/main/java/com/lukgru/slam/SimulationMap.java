package com.lukgru.slam;

import io.vavr.Tuple2;
import io.vavr.collection.Stream;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Łukasz on 2017-06-10.
 */
public class SimulationMap {

    private final Set<MapObject> objects;
    private final Map<Position, MapObject> objectsByPosition;

    public SimulationMap(Set<MapObject> objects) {
        this.objects = objects;
        this.objectsByPosition = Stream.ofAll(objects).toJavaMap(o -> new Tuple2<>(o.getPosition(), o));
    }

    public Set<MapObject> getObjects() {
        return objects;
    }

    public Optional<MapObject> getAt(Position position) {
        return Optional.ofNullable(objectsByPosition.get(position));
    }
}
