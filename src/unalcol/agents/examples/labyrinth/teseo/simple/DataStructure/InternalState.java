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
    Scanner sc =  new Scanner(System.in);
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
        System.out.println("currentP: " + currentPosition.toString());
        if (!pointsVisited.contains(currentPosition)) {
            pointsVisited.add(new Point(currentPosition));
        }

        /*-----El Grafo esta vacio--------*/
        if (memory.getVertexCount() == 0) {
            addNode(_Perception);
            int choice = simpleChoiceStep(_Perception);
            modifyCurrentState(choice);
            return choice;
        }
        /*--------------------------------*/

        /*Si la celda tiene mas de dos caminos es un nodo, si no es un paso*/
        if (countPaths(_Perception) > 2) {
            addNode(_Perception);
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
                nodePending.add(new Node(currentNode));
            }
            modifyCurrentState(rotation);
            return rotation;

        } else { //No hay opciones de movimiento a celdas no exploradas
            if(isNode()){
                System.out.println("calcular Dijstra");
                
                sc.next();
            }else{
                /*El agente esta en un paso*/
                if(steps.isEmpty()){
                     steps = new LinkedList(currentLink.getInverseSteps().getSteps());
                     steps.removeFirst();
                     currentLink.clearSteps();
                }
                System.out.println("+++++++++++++++++++");
                for (Node b : steps) {
                    System.out.println(b.toString());
                }
                System.out.println("+++++++++++++++++++");
                
                int rotation = interpreteStep(steps.removeFirst());
                modifyCurrentState(rotation);
                
                
                return rotation;
            }

        }

        return -1;
    }

    private void stepOptions(boolean[] _Perception) {
        options.clear();
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
        
        //System.out.println("Paths: "+countPaths(_Perception));
        //System.out.println("agregar nodo: "+tempNode.toString()+"\nLink: \n"+currentLink.toString());

        if (currentLink.havePredecessor()) {
            currentLink.addStep(tempNode);
            memory.addEdge(new Link(currentLink), new Node(currentLink.getSource()), new Node(currentLink.getTarget()), EdgeType.DIRECTED);
            memory.addEdge(new Link(currentLink.getInverseSteps()), new Node(currentLink.getInverseSteps().getSource()), new Node(currentLink.getInverseSteps().getTarget()), EdgeType.DIRECTED);
            currentLink.resetLink();
        }
        currentNode = new Node(tempNode);
        currentLink.addPredecessor(new Node(currentNode));
    }

    private void addStep(boolean[] _Perception) {
        currentLink.addStep(new Node(currentPosition, _Perception, orientation));
    }

    private int interpreteStep(Node node) {
        int east = (node.east+100) - (currentPosition.east+100);
        int north = (node.north+100) - (currentPosition.north+100);
        int rotation = 0;
        boolean entro = false;

        if (east == 0 && north == 1) {
            rotation = 0;
            entro=true;
        } else if (east == 1 && north == 0) {
            entro=true;
            rotation = 1;
        } else if (east == 0 && north == -1) {
            entro=true;
            rotation = 2;
        } else if (east == -1 && north == 0) {
            entro=true;
            rotation = 3;
        }
        if((rotation-orientation)<0){
            rotation = rotation-orientation +4;
        }else{
            rotation = rotation-orientation;
        }
        
        return rotation;
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

    private int countPaths(boolean[] _Perception) {
        int paths = 0;
        for (boolean b : _Perception) {
            if (!b) {
                paths += 1;
            }
        }
        return paths;
    }
    
    private boolean isNode(){
        return memory.containsVertex(new Node(currentPosition));
    }
}
