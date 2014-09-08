/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.simple;

/**
 *
 * @author Sneyder G
 */
public class TeseoSimpleQuantalHashMap extends SimpleTeseoAgentProgram {

    public TeseoSimpleQuantalHashMap() {}
    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        if (MT) return -1;
        if (!PI) return 3;
        if (!PF) return 0;
        if (!PD) return 1;
        return 2;
    }
    
}
