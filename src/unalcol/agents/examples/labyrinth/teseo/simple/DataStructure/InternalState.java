package unalcol.agents.examples.labyrinth.teseo.simple.DataStructure;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Quantal Team
 */
public class InternalState {

    private static InternalState instance;
    private Point currentPosition;
    private ArrayList<Point> pointsVisited;
    private Node currentNode = null;
    private Link currentLink = null;
    private int orientation;
    private LinkedList<Node> steps;
    private ArrayList<Integer> options;
    private Graph<Node, Link> memory;
    private Transformer<Link, Double> wtTransformer;
    private DijkstraShortestPath<Node, Link> alg;
    private ArrayList<Node> nodePending;

    private InternalState() {
        currentPosition = new Point();
        nodePending = new ArrayList<>();
        steps = new LinkedList<>();
        options = new ArrayList<>();
        orientation = 0;
        currentLink = new Link();
        pointsVisited = new ArrayList<>();
        memory = new DirectedSparseGraph<>();
        wtTransformer = new Transformer<Link, Double>() {
            @Override
            public Double transform(Link link) {
                return (double) link.getWeight();
            }
        };
        alg = new DijkstraShortestPath(memory, wtTransformer);
    }

    public static InternalState getInstance() {
        if (instance == null) {
            instance = new InternalState();
        }
        return instance;
    }

    private void setOrientation(int rotationNumber) {
        orientation = (orientation + rotationNumber) % 4;
    }

    private void setLocation(int east, int north) {
        currentPosition.setLocation(east, north);
    }

    private boolean alreadyVisited(int rotation) {
        int tempOrientation = (orientation + rotation) % 4;

        int eastTemp = currentPosition.getEast();
        int northTemp = currentPosition.getNorth();

        switch (tempOrientation) {
            case 0: {
                northTemp += 1;
                break;
            }
            case 1: {
                eastTemp += 1;
                break;
            }
            case 2: {
                northTemp -= 1;
                break;
            }
            case 3: {
                eastTemp -= 1;
                break;
            }
        }
        return !pointsVisited.contains(new Point(eastTemp, northTemp));
    }

    private void modifyCurrentState(int rotationNumber) {
        setOrientation(rotationNumber);

        int eastTemp = currentPosition.getEast();
        int northTemp = currentPosition.getNorth();

        switch (orientation) {
            case 0: {
                northTemp += 1;
                break;
            }
            case 1: {
                eastTemp += 1;
                break;
            }
            case 2: {
                northTemp -= 1;
                break;
            }
            case 3: {
                eastTemp -= 1;
                break;
            }
        }
        setLocation(eastTemp, northTemp);
    }

    public Point getLocation() {
        return currentPosition;
    }

    public void setCurrentNode(Node _currentNode) {
        currentNode = _currentNode;
    }

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int addPoint(boolean[] _Perception) {
        if (!pointsVisited.contains(currentPosition)) {
            pointsVisited.add(new Point(currentPosition));
        }

        /*     El Grafo esta vacio        */
        if (memory.getVertexCount() == 0) {
            addNode(_Perception);
            int choice = simpleChoiceStep(_Perception);
            modifyCurrentState(choice);
            return choice;
        }
        /*--------------------------------*/

        int paths = 0;

        for (boolean b : _Perception) {
            if (!b) {
                paths += 1;
            }
        }
        //Si la celda tiene mas de dos caminos es un nodo, si no es un paso
        if (paths > 2) {
            if (!new Node(currentPosition).equals(currentNode)) {
                addNode(_Perception);
            }else{
                currentLink.clearSteps();
                addStep(_Perception);
            }
        } else {
            if (currentLink.havePredecessor()) {
                addStep(_Perception);
            }
        }

        stepOptions(_Perception);
        //el conjunto de opciones no es vacio, escoge una opcion de forma aleatoria
        if (!options.isEmpty()) {
            int rotation = options.remove((int) (Math.random() * options.size()));
            if (options.size() >= 1) {
                nodePending.add(currentNode);
            }
            options.clear();
            modifyCurrentState(rotation);
            return rotation;

        } else { //No hay opciones de movimiento a celdas no exploradas

            if (!memory.containsVertex(new Node(currentPosition))) {
                if (steps.isEmpty()) {
                    steps = new LinkedList(currentLink.getInverseSteps().getSteps());
                    currentLink.clearSteps();
                }
                steps.remove(new Node(currentPosition, _Perception, orientation));

                Node nodeTemp = steps.getFirst();
                steps.remove(nodeTemp);
                return interpreteStep(nodeTemp);
            } else {
                //el agente ya se encuentra en un nodo, ahora calcular la distancia mas corta
                System.out.println("ñero llegue a un nodo");
            }

        }

        return -1;
    }

    private void stepOptions(boolean[] _Perception) {

        if (!_Perception[0] && alreadyVisited(0)) {
            options.add(0);
        }
        if (!_Perception[1] && alreadyVisited(1)) {
            options.add(1);
        }
        if (!_Perception[2] && alreadyVisited(2)) {
            options.add(2);
        }
        if (!_Perception[3] && alreadyVisited(3)) {
            options.add(3);
        }
    }

    private int simpleChoiceStep(boolean[] _Perception) {
        if (!_Perception[0]) {
            return 0;
        } else if (!_Perception[1]) {
            return 1;
        } else if (!_Perception[3]) {
            return 3;
        } else if (!_Perception[2]) {
            return 2;
        }
        return -1;
    }

    private void addNode(boolean[] _Perception) {
        Node tempNode = new Node(currentPosition, _Perception, orientation);
        if (!memory.containsVertex(tempNode)) {
            memory.addVertex(tempNode);
        }

        currentLink.addStep(tempNode);
        for (Node b : currentLink.getSteps()) {
            System.out.println(b.toString());
        }
        System.out.println("************************");
        currentNode = new Node(tempNode);

        if (currentLink.havePredecessor()) {
            memory.addEdge(new Link(currentLink), new Node(currentNode), new Node(tempNode), EdgeType.DIRECTED);
            memory.addEdge(new Link(currentLink.getInverseSteps()), new Node(tempNode), new Node(currentNode), EdgeType.DIRECTED);
            currentLink.clearSteps();
        }
        currentLink.addPredecessor(tempNode);
    }

    private void addStep(boolean[] _Perception) {
        currentLink.addStep(new Node(currentPosition, _Perception, orientation));
    }

    private int interpreteStep(Node node) {
        int east = node.east - currentPosition.east;
        int north = node.north - currentPosition.north;
        int rotation = 0;

        if (east == 0 && north == 1) {
            rotation = 0;
        } else if (east == 1 && north == 0) {
            rotation = 1;
        } else if (east == 0 && north == -1) {
            rotation = 2;
        } else if (east == -1 && north == 0) {
            rotation = 3;
        }
        return (rotation + orientation) % 4;
    }

    public void print(boolean[] _p) {
        System.out.println("current: " + currentPosition);
        System.out.println("memory: " + memory.toString());
        /*System.out.print("perception: ");
         System.out.print("PF: "+_p[0]);
         System.out.print(" PD: "+_p[1]);
         System.out.print(" PA: "+_p[2]);
         System.out.print(" PI: "+_p[3]+"\n");*/

    }
}
