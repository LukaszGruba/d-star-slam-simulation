package com.lukgru.slam;

import com.lukgru.slam.dstar.DStarLite;
import com.lukgru.slam.dstar.State;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Łukasz on 2017-06-10.
 */
public class RoutePlanner {

    public List<Position> planRoute(Position position, Position target, ObservedMap map) {
        DStarLite dStar = new DStarLite();
        dStar.init(position.getX(), position.getY(), target.getX(), target.getY());
        map.getObservedObjects().values().stream()
                .filter(object -> object.getType().equals(MapObject.MapObjectType.OBSTACLE))
                .forEach(obstacle -> dStar.updateCell(obstacle.getPosition().getX(), obstacle.getPosition().getY(), -1));
        dStar.replan();
        List<State> path = dStar.getPath();
        return path.stream().map(state -> new Position(state.x, state.y)).collect(Collectors.toList());
    }
}
