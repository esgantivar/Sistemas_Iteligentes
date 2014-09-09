package PuzzleQuantal;

import static PuzzleQuantal.BFS.*;
import java.util.Arrays;

//problema  8-puzzle

public class Puzzle {

//    private static int[][] tablero = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private static int[][] tablero = {{8, 4, 6}, {2, 0, 7}, {5, 1, 3}};
    private static final int[][] ordenado = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
//    private static int tablero = 846207513;
//    private static final int ordenado = 123456780;

    public static int[][] getTablero() {
        return tablero;
    }
    
    public static void main(String[] args) {
        //tablero inicial
        System.out.println("tablero inicial:");
        imprimirTablero(tablero);

        System.out.println("tablero ordenado por BFS");
        imprimirTablero(recorridoBFS());
    }
    
    public static void imprimirTablero(int[][] tablero) {
        for (int fil = 0; fil < tablero.length; fil++) {
            System.out.println("---------");
            for (int col = 0; col < tablero[fil].length; col++) {
                System.out.print ("|"+tablero[fil][col]+"|");
            }
            System.out.println();
        }        
        System.out.println("---------");
    }

    //mejorar esta funcion para que imprima el tablero
//    public static void imprimirTablero(int tablero) {
//        System.out.println(tablero);
//    }
    
    public static boolean estaOrdenado(int[][] tablero) {
        return (Arrays.deepEquals(tablero, ordenado));
    }
//    public static boolean estaOrdenado(int tablero) {
//        return (tablero == ordenado);
//    }
}
