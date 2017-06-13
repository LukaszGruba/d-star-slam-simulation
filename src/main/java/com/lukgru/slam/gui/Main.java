package com.lukgru.slam.gui;

import com.lukgru.slam.robot.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineJoin;

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
    private boolean simulationStarted = false;

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
        redrawWorld();
    }

    private void drawObject(Color color, int x, int y) {
        GraphicsContext gc = worldCanvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRoundRect(x, y, 7, 7, 2, 2);
    }

    private void addRobot(Position p) {
        simulation.addRobot(p);
        simulation.getRobot().ifPresent(r -> r.init(simulation.getSimulationMap()));
        this.canvasMode = ADD_OBSTACLE;
        redrawWorld();
    }

    @FXML
    public void worldCanvasDraw(MouseEvent me) {
        if (canvasMode == ADD_OBSTACLE) {
            Position position = fromPixels(me.getX(), me.getY());
            addObstacle(position);
        }
    }

    @FXML
    public void resetSimulation() {
        worldCanvas.getGraphicsContext2D().clearRect(0, 0, worldCanvas.getWidth(), worldCanvas.getHeight());
        observedCanvas.getGraphicsContext2D().clearRect(0, 0, observedCanvas.getWidth(), observedCanvas.getHeight());
        this.simulation = new Simulation();
        initialize();
    }

    private void addObstacle(Position position) {
        simulation.addObstacle(position);
        redrawWorld();
    }

    private void redrawWorld() {
        worldCanvas.getGraphicsContext2D().clearRect(0, 0, worldCanvas.getWidth(), worldCanvas.getHeight());
        simulation.getSimulationMap().getObjects().forEach(o -> {
            if (o.getType().equals(MapObject.MapObjectType.OBSTACLE)) {
                drawObstacleOnCanvas(o.getPosition());
            } else if (o.getType().equals(MapObject.MapObjectType.GOAL)) {
                GraphicsContext gc = worldCanvas.getGraphicsContext2D();
                gc.setFill(Color.RED);
                gc.fillOval(o.getPosition().getX() * 7, o.getPosition().getY() * 7, 7, 7);
            }
        });
        simulation.getRobot().map(Robot::getPosition)
                .ifPresent(p -> {
                    GraphicsContext gc = worldCanvas.getGraphicsContext2D();
                    gc.setFill(Color.BLUE);
                    gc.fillOval(p.getX() * 7, p.getY() * 7, 7, 7);
                });
    }

    private void drawObstacleOnCanvas(Position p) {
        drawObject(Color.GREEN, p.getX() * 7, p.getY() * 7);
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
        this.simulationStarted = true;
    }

    @FXML
    public void addGoal() {
        this.canvasMode = ADD_GOAL;
    }

    @FXML
    public void nextStep() {
        if (!simulationStarted) {
            startSimulation();
        }
        Position p = simulation.nextStep();
        List<Position> currentRoute = simulation.getCurrentRoute();

        drawObservedMap(simulation.getObservedMap());
        drawRoute(currentRoute);

        drawObject(Color.BLACK, p.getX() * 7, p.getY() * 7);
        redrawWorld();
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
        gc.beginPath();
        gc.setStroke(Color.ORANGE);
        gc.setLineWidth(3.0);
        gc.setLineJoin(StrokeLineJoin.ROUND);
        currentRoute.stream()
                .skip(1)
                .forEach(p -> {
                    int x = p.getX() * 7;
                    int y = p.getY() * 7;
                    gc.lineTo(x, y);
                });
        gc.stroke();
        gc.closePath();
    }

    public void playSimulation(MouseEvent mouseEvent) {

    }
}
