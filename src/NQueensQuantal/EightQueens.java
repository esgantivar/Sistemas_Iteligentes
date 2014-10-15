/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NQueensQuantal;

import NQueensQuantal.GeneticAlgorithm.*;

/**
 *
 * @author Sneyder G
 */
public class EightQueens {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        byte nQueens = 40;
        short nPoblation = 5000;
        Poblation p = new Poblation(nQueens, nPoblation);
        //System.out.println(p.toString());
        System.out.println("avg fitness: " + p.averageFitness());

        for (int i = 0; i < 200; i++) {
            System.out.print("Generation: "+(i+2)+"\t");
            p.nextGeneration();
            //System.out.println(p.toString());
            System.out.println("avg fitness: " + p.averageFitness());

        }

    }

}
