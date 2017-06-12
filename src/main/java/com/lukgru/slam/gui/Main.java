package com.lukgru.slam.gui;

import com.lukgru.slam.robot.MapObject;
import com.lukgru.slam.robot.ObservedMap;
import com.lukgru.slam.robot.Position;
import com.lukgru.slam.robot.Simulation;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static com.lukgru.slam.gui.CanvasMode.*;
import static com.lukgru.slam.gui.Utils.fromPixels;

/**
 * Created by Łukasz on 2017-06-12.
 */
public class Main {

    @FXML
    public AnchorPane ap;
    @FXML
    public Canvas worldCanvas;
    @FXML
    public Canvas observedCanvas;
    @FXML
    public Button startButton;
    @FXML
    public Button stopButton;
    @FXML
    public Button resetButton;

    private Simulation simulation = new Simulation();
    private CanvasMode canvasMode = ADD_OBSTACLE;

    @FXML
    public void initialize() {
        List<Position> initialObstacles = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            initialObstacles.add(new Position(0, i));
            initialObstacles.add(new Position(99, i));
            initialObstacles.add(new Position(i, 0));
            initialObstacles.add(new Position(i, 99));
        }
        initialObstacles.forEach(this::addObstacle);
    }

    @FXML
    public void worldCanvasClicked(MouseEvent me) {
        Position position = fromPixels(me.getX(), me.getY());
        if (canvasMode == ADD_ROBOT) {
            addRobot(position);
        } else if (canvasMode == ADD_OBSTACLE) {
            addObstacle(position);
        } else if (canvasMode == ADD_GOAL) {
            addGoal(position);
        }
    }

    private void addGoal(Position p) {
        simulation.addGoal(p);
        this.canvasMode = ADD_OBSTACLE;

        GraphicsContext gc = worldCanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillRoundRect(p.getX() * 7, p.getY() * 7, 7, 7, 2, 2);
    }

    private void addRobot(Position p) {
        simulation.addRobot(p);
        this.canvasMode = ADD_OBSTACLE;

        GraphicsContext gc = worldCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.fillRoundRect(p.getX() * 7, p.getY() * 7, 7, 7, 2, 2);
    }

    @FXML
    public void worldCanvasDraw(MouseEvent me) {
        Position position = fromPixels(me.getX(), me.getY());
        addObstacle(position);
    }

    @FXML
    public void resetSimulation() {
        worldCanvas.getGraphicsContext2D().clearRect(0, 0, worldCanvas.getWidth(), worldCanvas.getHeight());
        observedCanvas.getGraphicsContext2D().clearRect(0, 0, observedCanvas.getWidth(), observedCanvas.getHeight());
        this.simulation = new Simulation();
        initialize();
    }

    private void addObstacle(Position position) {
        drawObstacleOnCanvas(position);
        simulation.addObstacle(position);
    }

    private void drawObstacleOnCanvas(Position p) {
        GraphicsContext gc = worldCanvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(p.getX() * 7, p.getY() * 7, 7, 7, 2, 2);
    }

    @FXML
    public void addRobot() {
        this.canvasMode = ADD_ROBOT;
    }

    @FXML
    public void startSimulation() {
        simulation.start();

        ap.getScene().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.RIGHT)) {
                nextStep();
            }
        });
    }

    @FXML
    public void addGoal() {
        this.canvasMode = ADD_GOAL;
    }

    @FXML
    public void nextStep() {
        Position p = simulation.nextStep();
        List<Position> currentRoute = simulation.getCurrentRoute();

        drawObservedMap(simulation.getObservedMap());
        drawRoute(currentRoute);

        GraphicsContext gc = worldCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRoundRect(p.getX() * 7, p.getY() * 7, 7, 7, 2, 2);
    }

    private void drawObservedMap(ObservedMap observedMap) {
        GraphicsContext gc = observedCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, observedCanvas.getWidth(), observedCanvas.getHeight());
        gc.setFill(Color.GREEN);
        observedMap.getObservedObjects()
                .values()
                .stream()
                .map(MapObject::getPosition)
                .forEach(p -> gc.fillRoundRect(p.getX() * 7, p.getY() * 7, 7, 7, 2, 2));
    }

    private void drawRoute(List<Position> currentRoute) {
        GraphicsContext gc = observedCanvas.getGraphicsContext2D();
        gc.setFill(Color.GREY);
        currentRoute.forEach(p -> gc.fillRoundRect(p.getX() * 7, p.getY() * 7, 7, 7, 2, 2));
    }
}
