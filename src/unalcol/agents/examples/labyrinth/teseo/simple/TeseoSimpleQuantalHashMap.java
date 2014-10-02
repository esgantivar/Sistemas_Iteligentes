/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.simple;

import java.util.Scanner;
import unalcol.agents.examples.labyrinth.teseo.simple.DataStructure.*;


/**
 *
 * @author Sneyder G
 */
public class TeseoSimpleQuantalHashMap extends SimpleTeseoAgentProgram {

    private InternalState currentState;

    //private HashMap<Node, Node[]> memory;
    public TeseoSimpleQuantalHashMap() {
        currentState = InternalState.getInstance();
        currentState.setCurrentPosition(new Point());
    }

    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        //Si encuentra el objetivo, 
        if (MT) {
            return -1;
        }
        boolean[] p = {PF, PD, PA, PI};
        int s = currentState.addPoint(p);
        return s;
    }
}
