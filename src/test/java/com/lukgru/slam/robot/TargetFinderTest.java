package com.lukgru.slam.robot;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Łukasz on 2017-06-12.
 */
public class TargetFinderTest {

    private TargetFinder targetFinder = new TargetFinder();

    @Test
    public void test() {
        //given
        ObservedMap observedMap = new ObservedMap();
        observedMap.update(asList(
                new MapObject(new Position(0, 12), MapObject.MapObjectType.GOAL)
        ));

        //when
        Position targetPosition = targetFinder.getTargetPosition(observedMap);

        //then
        assertThat(targetPosition)
                .isNotNull()
                .isEqualTo(new Position(0, 12));
    }

}