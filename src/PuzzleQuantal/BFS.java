package PuzzleQuantal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BFS {

    private static Map<String, Integer> repetidos = new HashMap<>();
    private static Queue<String> listaTableros = new LinkedList<>();
    private static String tablero = Puzzle.getTablero();
    private static Map<String, String> historial = new HashMap<>();
    private static ArrayList<Integer> instrucciones = new ArrayList<>();

    public static void recorridoBFS() {

        agregar(tablero, null, 0);
        int a;
        //mientras que la cola de tableros no este vacia, analizar que 
        //movimientos se pueden realizar en el tablero (arriba, abajo, izq., 
        //der.). Por ultimo se elimina el tablero de la cola
        while (listaTableros.peek() != null) {
            a = listaTableros.peek().indexOf("0");
            arriba(listaTableros.peek(), a);
            abajo(listaTableros.peek(), a);
            izquierda(listaTableros.peek(), a);
            derecha(listaTableros.remove(), a);
            verificar();
        }
        if (historial==null)    System.out.println("no hay solucion");
    }

    //agrega tableros en la cola listaTableros, mientras no sean repetidos
    private static void agregar(String siguiente, String anterior, int n) {
        if (!repetidos.containsKey(siguiente)) {
            repetidos.put(siguiente, n);
            listaTableros.add(siguiente);
            historial.put(siguiente, anterior);
        }
    }

    //funciones que revisan que movimientos se pueden realizar en el tablero
    private static void arriba(String actual, int a) {
        if (a > 2) {
            String siguienteTablero = actual.substring(0, a - 3) + "0" + actual.substring(a - 2, a)
                    + actual.charAt(a - 3) + actual.substring(a + 1);
            salida(actual, siguienteTablero);
        }
    }

    private static void abajo(String actual, int a) {
        if (a < 6) {
            String siguienteTablero = actual.substring(0, a) + actual.substring(a + 3, a + 4)
                    + actual.substring(a + 1, a + 3) + "0" + actual.substring(a + 4);
            salida(actual, siguienteTablero);
        }
    }

    private static void izquierda(String actual, int a) {
        if (a != 0 && a != 3 && a != 6) {
            String siguienteTablero = actual.substring(0, a - 1) + "0" + actual.charAt(a - 1)
                    + actual.substring(a + 1);
            salida(actual, siguienteTablero);
        }
    }

    private static void derecha(String actual, int a) {
        if (a != 2 && a != 5 && a != 8) {
            String siguienteTablero = actual.substring(0, a) + actual.charAt(a + 1) + "0"
                    + actual.substring(a + 2);
            salida(actual, siguienteTablero);
        }
    }

    private static void salida(String anterior, String siguiente) {
        agregar(siguiente, anterior, repetidos.get(anterior) + 1);
//        Puzzle.imprimirTablero(s);
        
    }

    private static void verificar(){
        if (listaTableros.contains("123456780")) {
            System.out.println("la solucion existe en el nivel " + repetidos.get("123456780") + " del arbol");
            instrucciones("123456780");
            listaTableros.clear();

        }
    }
    //cuando haya encontrado la solucion, analizar por medio del historial que
    //movimientos se deben seguir para resolver el puzzle
    private static void instrucciones(String ruta) {
        while (ruta != null) {
            int a = ruta.indexOf("0");
            ruta = historial.get(ruta);
            if (ruta == null) {
                break;
            }
//            Puzzle.imprimirTablero(ruta);
            int b = ruta.indexOf("0");

            if (a == b + 3) {
                instrucciones.add(2);       //arriba
            }
            if (a == b - 3) {
                instrucciones.add(0);       //abajo
            }
            if (a == b + 1) {
                instrucciones.add(1);       //izquierda
            }
            if (a == b - 1) {
                instrucciones.add(3);       //derecha
            }
        }
        Collections.reverse(instrucciones);
        traductor();
    }

    private static void traductor() {
        System.out.println("instrucciones:");
        for (int a : instrucciones) {
            if (a == 0) {
                System.out.println("arriba");
            } else if (a == 1) {
                System.out.println("derecha");
            } else if (a == 2) {
                System.out.println("abajo");
            } else if (a == 3) {
                System.out.println("izquierda");
            }
        }
    }
}
