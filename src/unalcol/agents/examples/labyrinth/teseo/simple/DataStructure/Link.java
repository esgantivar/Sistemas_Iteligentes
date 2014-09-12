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

    private int weight;
    private LinkedList<Node> steps;

    public Link(Node node) {
        weight = 0;
        steps = new LinkedList<>();
        steps.addLast(node);
    }

    public int getWeight() {
        return weight;
    }
    
    public void addStep(Node node){
        weight += 1;
        steps.addLast(node);
    }
    
    public String toString(){
        return "From: " + steps.getFirst()+", To: "+steps.getLast();
    }
}
