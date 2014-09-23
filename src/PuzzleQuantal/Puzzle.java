package PuzzleQuantal;
//problema  8-puzzle

import static PuzzleQuantal.BFS.recorridoBFS;
import static PuzzleQuantal.AStar.recorridoH;


public class Puzzle {

//    private static String tablero = "102754863";
    private static String tablero = "152704863";

    public static String getTablero() {
        return tablero;
    }

    public static void main(String[] args) {
        System.out.println("tablero inicial:");
        imprimirTablero(tablero);

        System.out.println("\ntablero ordenado por BFS");
        recorridoBFS();
        
        System.out.println("\ntablero ordenado por A*");
        recorridoH();
    }

    public static void imprimirTablero(String tablero) {
        char[] array = tablero.toCharArray();
        int cont = 0;

        System.out.println("-------");
        for (int fil = 0; fil < 3; fil++) {
            System.out.print("|" + array[cont] + "|" + array[cont + 1] + "|" + array[cont + 2] + "|");
            cont += 3;
            System.out.println("\n-------");
        }
    }
}
