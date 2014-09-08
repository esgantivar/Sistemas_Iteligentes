package PuzzleQuantal;

//problema  8-puzzle
public class Puzzle {

//    private int[][] tablero = new int[2][2];
    private static int[][] tablero = {{8, 4, 6}, {0, 2, 7}, {5, 1, 3}};
    private static final int[][] ordenado = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    public static void main(String[] args) {
        //tablero inicial
        System.out.println("tablero inicial:");
        imprimirTablero();

        System.out.println("tablero ordenado por BFS");
        
    }

    public static void imprimirTablero() {
        for (int fil = 0; fil < tablero.length; fil++) {
            System.out.println("---------");
            for (int col = 0; col < tablero[fil].length; col++) {
                System.out.print ("|"+tablero[fil][col]+"|");
            }
            System.out.println();
        }        
        System.out.println("---------");
    }

    public static boolean estaOrdenado() {
        return (tablero == ordenado);
    }

}
