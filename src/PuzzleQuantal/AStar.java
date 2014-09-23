//implementacion del algoritmo A* para resolver el 8-puzzle
//con calculo de distancia Manhattan
package PuzzleQuantal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Cesar Galvis
 */
public class AStar {
    
    private static Map<String, Integer> repetidos = new HashMap<>();
    private static Queue<String> listaTableros = new LinkedList<>();
    private static String tablero = Puzzle.getTablero();
    private static Map<String, String> historial = new HashMap<>();
    private static ArrayList<Integer> instrucciones = new ArrayList<>();
    private static Map<String, Integer> opciones = new HashMap<>();
    
    public static void recorridoH() {

        repetidos.put(tablero, 0);
        listaTableros.add(tablero);
        historial.put(tablero, null);
        
        int a;
        String anterior;
        //mientras que la cola de tableros no este vacia, analizar que 
        //movimientos se pueden realizar en el tablero (arriba, abajo, izq., 
        //der.). Por ultimo se elimina el tablero de la cola
        while (listaTableros.peek() != null) {
            a = listaTableros.peek().indexOf("0");
            anterior=listaTableros.peek();
            arriba(listaTableros.peek(), a);
            abajo(listaTableros.peek(), a);
            izquierda(listaTableros.peek(), a);
            derecha(listaTableros.remove(), a);
                        
            //ordenar opciones de mayor a menor costo de A* y agregarlos
            // en la cola de tableros:
            Map<Integer, String> map = sortByValues((HashMap) opciones);
            Set set2 = map.entrySet();
            Iterator iterator2 = set2.iterator();
            while(iterator2.hasNext()) {
                 Map.Entry me2 = (Map.Entry)iterator2.next();
                listaTableros.add(me2.getKey().toString());
                historial.put(me2.getKey().toString(), anterior);                
            } 
            opciones.clear();
            
            verificar();
        }
        if (historial==null)    System.out.println("no hay solucion");
    }

    //verificar que el tablero no sea repetido, en caso de que no sea
    //asi agregarlo en los repetidos y en la lista de opciones que se 
    //verificaran mas adelante con A*
    private static void agregar(String siguiente, String anterior, int n) {
        if (!repetidos.containsKey(siguiente)) {
            repetidos.put(siguiente, n);
            opciones.put(siguiente, h(siguiente));
        }
    }

    //funciones que revisan que movimientos se pueden realizar en el tablero
    private static void arriba(String actual, int a) {
        if (a > 2) {
            String siguiente = actual.substring(0, a - 3) + "0" + actual.substring(a - 2, a)
                    + actual.charAt(a - 3) + actual.substring(a + 1);
            agregar(siguiente, actual, repetidos.get(actual) + 1);
        }
    }

    private static void abajo(String actual, int a) {
        if (a < 6) {
            String siguiente = actual.substring(0, a) + actual.substring(a + 3, a + 4)
                    + actual.substring(a + 1, a + 3) + "0" + actual.substring(a + 4);
            agregar(siguiente, actual, repetidos.get(actual) + 1);
        }
    }

    private static void izquierda(String actual, int a) {
        if (a != 0 && a != 3 && a != 6) {
            String siguiente = actual.substring(0, a - 1) + "0" + actual.charAt(a - 1)
                    + actual.substring(a + 1);
            agregar(siguiente, actual, repetidos.get(actual) + 1);
        }
    }

    private static void derecha(String actual, int a) {
        if (a != 2 && a != 5 && a != 8) {
            String siguiente = actual.substring(0, a) + actual.charAt(a + 1) + "0"
                    + actual.substring(a + 2);
            agregar(siguiente, actual, repetidos.get(actual) + 1);
        }
    }

    private static void verificar(){
        if (listaTableros.contains("123456780")) {
            System.out.println("tableros revisados: "+repetidos.size());
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
    
    //calcular el costo estimado con distancia Manhattan - h(n)
    private static int h(String tablero) {
        int suma = 0;
        int posicion;
        int fila, columna, fila2, columna2;
        for (int a = 0; a < 8; a++) {
            //calcular el indice de la pieza del tablero actual
            posicion = tablero.indexOf(Integer.toString(a + 1));
            if ((a != posicion)) {
                fila = a / 3;
                columna = a % 3;
                fila2 = posicion / 3;
                columna2 = posicion % 3;
                suma += Math.abs(fila - fila2) + Math.abs(columna - columna2);
            }
        }
        return suma;
    }
    
    //funcion para ordenar opciones por valor en orden descendente
    //referencias:
    //http://beginnersbook.com/2013/12/how-to-sort-hashmap-in-java-by-keys-and-values/
    private static HashMap sortByValues(HashMap map) { 
       List list = new LinkedList(map.entrySet());
       Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
               return ((Comparable) ((Map.Entry) (o1)).getValue())
                  .compareTo(((Map.Entry) (o2)).getValue());
            }
       });

       HashMap sortedHashMap = new LinkedHashMap();
       for (Iterator it = list.iterator(); it.hasNext();) {
              Map.Entry entry = (Map.Entry) it.next();
              sortedHashMap.put(entry.getKey(), entry.getValue());
       } 
       return sortedHashMap;
  }
}
