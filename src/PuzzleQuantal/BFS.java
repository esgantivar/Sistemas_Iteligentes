package PuzzleQuantal;

import static PuzzleQuantal.Puzzle.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class BFS {

    private static final ArrayList<int[][]> estadosRecorridos = new ArrayList<>();
    private static Queue<int[][]> opciones = new LinkedList<>();
    private static int[][] tablero = getTablero();

    public static int[][] recorridoBFS() {

//        tablero = getTablero();
        estadosRecorridos.add(deepCopyIntMatrix(tablero));
        int[] coordenadas;
        int filaVacio;
        int columnaVacio;
        int[][] tableroTemporal;
        while (!estaOrdenado(tablero)) {
            coordenadas = buscarVacio();
            filaVacio = coordenadas[0];
            columnaVacio = coordenadas[1];
            if (coordenadaValida(filaVacio + 1, columnaVacio)) {
                tableroTemporal = tableroTemporal(filaVacio, columnaVacio, filaVacio + 1, columnaVacio);
                if (!estadosRecorridos.contains(deepCopyIntMatrix(tableroTemporal))) {
                    opciones.add(deepCopyIntMatrix(tableroTemporal));
                }
            }
            if (coordenadaValida(filaVacio, columnaVacio + 1)) {
                tableroTemporal = tableroTemporal(filaVacio, columnaVacio, filaVacio, columnaVacio + 1);
                if (!estadosRecorridos.contains(deepCopyIntMatrix(tableroTemporal))) {
                    opciones.add(deepCopyIntMatrix(tableroTemporal));
                }
            }
            if (coordenadaValida(filaVacio - 1, columnaVacio)) {
                tableroTemporal = tableroTemporal(filaVacio, columnaVacio, filaVacio - 1, columnaVacio);
                if (!estadosRecorridos.contains(deepCopyIntMatrix(tableroTemporal))) {
                    opciones.add(deepCopyIntMatrix(tableroTemporal));
                }
            }
            if (coordenadaValida(filaVacio, columnaVacio - 1)) {
                tableroTemporal = tableroTemporal(filaVacio, columnaVacio, filaVacio, columnaVacio - 1);
                if (!estadosRecorridos.contains(deepCopyIntMatrix(tableroTemporal))) {
                    opciones.add(deepCopyIntMatrix(tableroTemporal));
                }
            }

            tablero = deepCopyIntMatrix(opciones.poll());
            imprimirTablero(tablero);
            estadosRecorridos.add(deepCopyIntMatrix(tablero));
        }

        return tablero;
    }
    
//    private static boolean compararArray(ArrayList<int[][]> a1,int[][] a2){
//        for (int[][] h : a1) {
//            
//        }
//        return true;
//    }

    private static int[] buscarVacio() {
        int[] resultado = new int[2];
        for (int fil = 0; fil < tablero.length; fil++) {
            for (int col = 0; col < tablero[fil].length; col++) {
                if (tablero[fil][col] == 0) {
                    resultado[0] = fil;
                    resultado[1] = col;
                    return resultado;
                }
            }
        }
        return null;
    }

    private static boolean coordenadaValida(int fila, int columna) {
        return (fila >= 0 && columna >= 0 && fila <= 2 && columna <= 2);
    }

    private static int[][] tableroTemporal(int filOrigen, int colOrigen, int filDestino, int colDestino) {
        int[][] tableroTemporal = deepCopyIntMatrix(tablero);
        tableroTemporal[filOrigen][colOrigen] = tableroTemporal[filDestino][colDestino];
        tableroTemporal[filDestino][colDestino] = 0;
        return tableroTemporal;
    }
    
    //clonar arreglo 2 dimensiones (Gracias Adam)
    public static int[][] deepCopyIntMatrix(int[][] input) {
        if (input == null) {
            return null;
        }
        int[][] result = new int[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
    }
}