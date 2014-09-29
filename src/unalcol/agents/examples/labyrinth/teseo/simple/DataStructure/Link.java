/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.simple.DataStructure;

import java.util.LinkedList;

/**
 *
 * @author Sneyder G
 */
public class Link {

    private LinkedList<Node> steps;
    private boolean havePredecessor = false;

    public Link(Node node) {
        steps = new LinkedList<>();
        havePredecessor = true;
        steps.addLast(node);
    }

    public Link() {
        steps = new LinkedList<>();
    }

    public void addPredecessor(Node prede) {
        havePredecessor = true;
        steps.clear();
        addStep(prede);
    }

    public boolean havePredecessor() {
        return havePredecessor;
    }

    public int getWeight() {
        return steps.size() - 1;
    }

    public void addStep(Node node) {
            steps.addLast(node);
    }

    public void clearSteps() {
        steps.clear();
    }

    public Link getInverseSteps() {
        Link inverseLink = new Link();
        for (Node node : steps) {
            inverseLink.steps.addFirst(node);
        }
        inverseLink.havePredecessor = true;
        return inverseLink;
    }
    
    public Node getSource(){
        return steps.getFirst();
    }
    
    public Node getTarget(){
        return steps.getLast();
    }
    public LinkedList getSteps(){
        return steps;
    }

    @Override
    public String toString() {
        return "From: " + steps.getFirst() + ", To: " + steps.getLast();
    }
}
