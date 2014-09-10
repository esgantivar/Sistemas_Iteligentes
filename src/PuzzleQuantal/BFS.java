package PuzzleQuantal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BFS {

    private static Map<String,Integer> tablerosRepetidos = new HashMap<>();
    private static Queue<String> listaDeTableros = new LinkedList<>();
    private static String tablero = Puzzle.getTablero();
    private static Map<String,String> stateHistory = new HashMap<String,String>();

    public static void recorridoBFS() {
        
        agregar(tablero,null,0);
        
        while(listaDeTableros.peek()!=null){            
            arriba(listaDeTableros.peek());
            abajo(listaDeTableros.peek());
            izquierda(listaDeTableros.peek());
            derecha(listaDeTableros.remove());
        }
        System.out.println("no hay solucion");
    }
    
    private static void agregar(String siguiente,String anterior,int n){
        if(!tablerosRepetidos.containsKey(siguiente)){
            tablerosRepetidos.put(siguiente,n);
            listaDeTableros.add(siguiente);
            stateHistory.put(siguiente,anterior);
        }
    }
    
    private static void arriba(String actual){
        int a = actual.indexOf("0");
        if(a>2){
            String siguienteTablero = actual.substring(0,a-3)+"0"+actual.substring(a-2,a)+actual.charAt(a-3)+actual.substring(a+1);
            salida(actual,siguienteTablero);
        }
    }
    private static void abajo(String actual){
        int a = actual.indexOf("0");
        if(a<6){
            String siguienteTablero = actual.substring(0,a)+actual.substring(a+3,a+4)+actual.substring(a+1,a+3)+"0"+actual.substring(a+4);
            salida(actual,siguienteTablero);
        }
    }
    private static void izquierda(String actual){
        int a = actual.indexOf("0");
        if(a!=0 && a!=3 && a!=6){
            String siguienteTablero = actual.substring(0,a-1)+"0"+actual.charAt(a-1)+actual.substring(a+1);
            salida(actual,siguienteTablero);
        }
    }
    private static void derecha(String actual){
        int a = actual.indexOf("0");
        if(a!=2 && a!=5 && a!=8){
            String siguienteTablero = actual.substring(0,a)+actual.charAt(a+1)+"0"+actual.substring(a+2);
            salida(actual,siguienteTablero);
        }
    }
    
    private static void salida(String anterior,String siguiente){
        agregar(siguiente,anterior,tablerosRepetidos.get(anterior)+1);
//        Puzzle.imprimirTablero(s);
        if(siguiente.equals("123456780")) {
            System.out.println("la solucion existe en el nivel "+tablerosRepetidos.get(siguiente)+" del arbol");
            String ruta = siguiente;
            while (ruta != null) {
                System.out.println(ruta + " at " + tablerosRepetidos.get(ruta));
                ruta = stateHistory.get(ruta);
            }
            System.exit(0);
        }
    }
    
}