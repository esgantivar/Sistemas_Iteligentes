package PuzzleQuantal;
//problema  8-puzzle

import static PuzzleQuantal.BFS.recorridoBFS;


public class Puzzle {

    private static String tablero = "506712483";

    public static String getTablero() {
        return tablero;
    }

    public static void main(String[] args) {
        System.out.println("tablero inicial:");
        imprimirTablero(tablero);

        System.out.println("tablero ordenado por BFS");
        recorridoBFS();
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
