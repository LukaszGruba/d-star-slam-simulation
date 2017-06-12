package com.lukgru.slam;


import com.lukgru.slam.robot.MapObject;
import com.lukgru.slam.robot.Position;
import com.lukgru.slam.robot.SimulationMap;
import com.lukgru.slam.robot.WorldScanner;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Łukasz on 2017-06-11.
 */
public class WorldScannerTest {

    private WorldScanner worldScanner = new WorldScanner();
    private SimulationMap world;

    @Before
    public void initWorld() {
        Set<MapObject> objects = new HashSet<>();
        objects.add(object(-2,2));
        objects.add(object(-2,1));
        objects.add(object(-2,0));
        objects.add(object(-2,-1));

        objects.add(object(0,3));

        objects.add(object(-1,6));
        objects.add(object(0,6));
        objects.add(object(1,6));

        objects.add(object(2, -3));

        objects.add(object(3, -2));

        objects.add(object(4, -3));

        objects.add(object(3, -5));

        objects.add(object(2,8));
        objects.add(object(3,8));
        objects.add(object(4,8));
        objects.add(object(5,8));
        objects.add(object(5,7));
        objects.add(object(5,6));
        objects.add(object(5,5));
        objects.add(object(5,4));
        objects.add(object(5,3));
        objects.add(object(5,2));
        objects.add(object(6,5));
        objects.add(object(7,5));
        world = new SimulationMap(objects);
    }

    @Test
    public void scenario1() {
        //given
        Position position = new Position(3, 9);

        //when
        Collection<MapObject> detectedObjects = worldScanner.scan(position, world);

        //then
        assertThat(detectedObjects).containsOnly(
                object(2,8),
                object(3,8),
                object(4,8),
                object(5,8)
        );
    }

    @Test
    public void scenario2() {
        //given
        Position position = new Position(0, 0);

        //when
        Collection<MapObject> detectedObjects = worldScanner.scan(position, world);

        //then
        assertThat(detectedObjects).containsOnly(
                object(-2,2),
                object(-2,1),
                object(-2,0),
                object(-2,-1),
                object(0,3),
                object(-1,6),
                object(1,6),
                object(2,-3),
                object(3,-2),
                object(2,8),
                object(3,8),
                object(4,8),
                object(5,7),
                object(5,6),
                object(5,5),
                object(5,4),
                object(5,3),
                object(5,2)
        );
    }

    private MapObject object(int x, int y) {
        return new MapObject(new Position(x, y), MapObject.MapObjectType.OBSTACLE);
    }
}