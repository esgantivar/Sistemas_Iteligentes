/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NQueensQuantal.SimulatedAnnealing;

import NQueensQuantal.GeneticAlgorithm.Queen;
import java.util.ArrayList;

/**
 *
 * @author Cesar Galvis
 */
public class Tablero implements Cloneable{
    private Queen[] queens;

    public void setQueens(Queen[] queens) {
        this.queens = queens;
    }
    protected int conflicto;
    
    public Tablero(byte nQueens) {
        queens = new Queen[nQueens];
        generarTablero(nQueens);
    }
    
    private void generarTablero(byte nQueens) {
        byte value = 0;
        ArrayList<Byte> options = new ArrayList<>(nQueens);
        for (int i = 1; i <= nQueens; i++) {
            options.add((byte) i);
        }

        while (value < nQueens) {
            queens[value] = new Queen((byte) (value + 1), options);
            value++;
        }
        evaluarConflictos();
    }
    
    public int evaluarConflictos() {
        conflicto = 0;
        for (Queen q : queens) {
            for (Queen q1 : queens) {
                if (!q.equals(q1) && q.evaluateThreat(q1)) {
                    conflicto++;
                }
            }
        }
        return conflicto;
    }
    
    public void cambiar(double temperatura, double temperaturaFinal) {
//        int rango = ((int) (temperatura/temperaturaFinal))*queens.length;
        int index = (int) (queens.length * Math.random());
        
//        byte value = (byte) (rango * Math.random());
//        if (value<0){ 
//            value = (byte) (value*(-1));
//        }
//        
//        queens[index].setY(value);
//        evaluarConflictos();
        byte value = (byte) (queens.length * Math.random() + 1);
        queens[index].setY(value);
    }
    
    public String printTable() {
        byte n = (byte) queens.length;
        String t = "Table:\n";
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == queens[j].getY()) {
                    t += "Q ";
                } else {
                    t += "- ";
                }
            }
            t += "\n";
        }
        return t + "conflictos: " + evaluarConflictos();
    }
    
    @Override
    public Object clone(){
        Object clone = null;
        try
        {
            clone = super.clone();
        } 
        catch(CloneNotSupportedException e)
        {
            // No deberia ocurrir
        }
        ((Tablero)clone).setQueens((Queen[])queens.clone());
        return clone;
    }
}
