package com.lukgru.slam;

import java.util.Objects;

/**
 * Created by Łukasz on 2017-06-10.
 */
public class MapObject {

    private final Position position;
    private final MapObjectType type;

    public enum MapObjectType {
        OBSTACLE,
        CHECKPOINT,
        GOAL;
    }

    public MapObject(Position position, MapObjectType type) {
        this.position = position;
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public MapObjectType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapObject mapObject = (MapObject) o;
        return Objects.equals(position, mapObject.position) &&
                type == mapObject.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, type);
    }

    @Override
    public String toString() {
        return "MapObject{" +
                "position=" + position +
                ", type=" + type +
                '}';
    }
}
