package unalcol.agents.examples.labyrinth.teseo.simple.DataStructure;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Quantal Team
 */
public class InternalState {

    private InternalState instance;
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
                return (double)link.getWeight();
            }
        };
        alg = new DijkstraShortestPath(memory, wtTransformer);
    }

    public InternalState getInstance() {
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
        pointsVisited.add(currentPosition);
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
            if (b) {
                paths += 1;
            }
        }

        stepOptions(_Perception);

        if (!options.isEmpty()) {
            if (paths > 2) {
                addNode(_Perception);
            } else {
                if (currentLink.havePredecessor()) {
                    addStep(_Perception);
                }
            }
            int rotation = options.remove((int) (Math.random() * options.size()));
            
            if (options.size() > 1) {
                nodePending.add(currentNode);
            }
            modifyCurrentState(rotation);
            return rotation;
        } else {
            
            addStep(_Perception);
            steps = currentLink.getInverseSteps().getSteps();
            steps.remove(new Node(currentPosition, _Perception, orientation));
            
            
            /*Calculo de la distancia mas corta*/
            int shortestDistance = (int) Double.POSITIVE_INFINITY;
            List<Link> shortestPath ;
            Node nearestNode;
            
            for (Node node : nodePending) {
                int tempDist = (int) alg.getDistance(currentNode, node);
                if(tempDist<shortestDistance){
                    shortestDistance=tempDist;
                    shortestPath=new ArrayList<>(alg.getPath(currentNode, node));
                    nearestNode=node;
                }
            }
            

        }

        return -1;
    }

    private void stepOptions(boolean[] _Perception) {

        if (_Perception[0] && alreadyVisited(0)) {
            options.add(0);
        }
        if (_Perception[1] && alreadyVisited(1)) {
            options.add(1);
        }
        if (_Perception[2] && alreadyVisited(2)) {
            options.add(2);
        }
        if (_Perception[3] && alreadyVisited(3)) {
            options.add(3);
        }
    }

    private int simpleChoiceStep(boolean[] _Perception) {
        if (_Perception[0]) {
            return 0;
        } else if (_Perception[1]) {
            return 1;
        } else if (_Perception[3]) {
            return 3;
        } else if (_Perception[2]) {
            return 2;
        }
        return -1;
    }

    private void addNode(boolean[] _Perception) {
        Node tempNode = new Node(currentPosition, _Perception, orientation);
        memory.addVertex(tempNode);
        currentLink.addStep(tempNode);

        if (currentLink.havePredecessor()) {
            memory.addEdge(currentLink, currentNode, tempNode, EdgeType.DIRECTED);
            currentNode = new Node(tempNode);
            memory.addEdge(currentLink.getInverseSteps(), tempNode, currentNode, EdgeType.DIRECTED);
            currentLink.clearSteps();
        }
        currentLink.addPredecessor(tempNode);
    }

    private void addStep(boolean[] _Perception) {
        currentLink.addStep(new Node(currentPosition, _Perception, orientation));
    }

}
