package com.lukgru.slam;

import com.lukgru.slam.dstar.DStarLite;

/**
 * Created by Lukasz on 08.06.2017.
 */
public class Main {

    /**
     * TODO: general algorithm:
     * 1. set position as 0,0
     * 2. detect visible obstacles
     * 3. update map
     * 4. replan route
     * 5. move one step
     * 6. if at goal -> end, else goto 2
     */

    //TODO: at the beginning try to find goal, if not present then make some assumption that it can be behind obstacle so go to the nearest edge of obstacle and then

    //TODO: remember all of the found checkpoint positions

    public static void main(String[] args) {
        DStarLite pf = new DStarLite();
        pf.init(0, 0, 0, 10);
        for (int i = -3; i < 10; i++) {
            pf.updateCell(i, 3, -1);
        }

        pf.replan();
        printPath(pf);

        System.out.println("moved to -4, 3");
        pf.updateStart(-4, 3);
        for (int i = 0; i < 20; i++) {
            pf.updateCell(-3, 3 + i, -1);
        }
        System.out.println("adding obstacles");
        pf.replan();
        System.out.println("replanning");
        printPath(pf);
    }

    private static void printPath(DStarLite pf) {
        pf.getPath()
                .forEach(i -> System.out.println("x: " + i.x + " y: " + i.y));
    }
}
