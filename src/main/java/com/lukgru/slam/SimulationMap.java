package com.lukgru.slam;

import java.util.Set;

/**
 * Created by Łukasz on 2017-06-10.
 */
public class SimulationMap {

    private final Set<MapObject> objects;

    public SimulationMap(Set<MapObject> objects) {
        this.objects = objects;
    }

    public Set<MapObject> getObjects() {
        return objects;
    }
}
