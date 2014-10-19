/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NQueensQuantal;

import NQueensQuantal.SimulatedAnnealing.SimulatedAnnealing;


/**
 *
 * @author Cesar Galvis
 */
public class EightQueensSA {

    public static void main(String[] args) {
        // parametros templado simulado
        double temperaturaInicial = 45;
        double temperaturaFinal = 0; 
        double factorEnfriamiento = 0.6;
        byte nQueens = 100;
        SimulatedAnnealing sA = new SimulatedAnnealing(temperaturaInicial, temperaturaFinal, factorEnfriamiento);
        
        System.out.println("numero de reinas: "+nQueens);
        System.out.println("ejecutando templado simulado...");
        sA.calcularSimulatedAnnealing(nQueens);
        System.out.println("programa terminado");
    }
}
