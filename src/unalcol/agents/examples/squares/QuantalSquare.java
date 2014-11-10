/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.squares;

import java.util.ArrayList;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author Cesar Galvis
 */
public class QuantalSquare implements AgentProgram {

    protected String color;

    protected static final int LEFT = 1;
    protected static final int TOP = 2;
    protected static final int RIGHT = 4;
    protected static final int BOTTOM = 8;
    protected static final int WHITE = 16;

    public QuantalSquare(String color) {
        this.color = color;
    }

    @Override
    public Action compute(Percept p) {
        long time = (long) (200 * Math.random());
        try {
            Thread.sleep(time);
        } catch (Exception e) {
        }
        if (p.getAttribute(Squares.TURN).equals(color)) {
            int size = Integer.parseInt((String) p.getAttribute(Squares.SIZE));
            int i = 0;
            int j = 0;

            //esto deberia retornar la accion, corregir mas adelante
            decision(size, p);
            
            //hay que cambiar esto
            Vector<String> v = new Vector();
            while (v.size() == 0) {
                i = (int) (size * Math.random());
                j = (int) (size * Math.random());
                if (((String) p.getAttribute(i + ":" + j + ":" + Squares.LEFT)).equals(Squares.FALSE)) {
                    v.add(Squares.LEFT);
                }
                if (((String) p.getAttribute(i + ":" + j + ":" + Squares.TOP)).equals(Squares.FALSE)) {
                    v.add(Squares.TOP);
                }
                if (((String) p.getAttribute(i + ":" + j + ":" + Squares.BOTTOM)).equals(Squares.FALSE)) {
                    v.add(Squares.BOTTOM);
                }
                if (((String) p.getAttribute(i + ":" + j + ":" + Squares.RIGHT)).equals(Squares.FALSE)) {
                    v.add(Squares.RIGHT);
                }
            }
            return new Action(i + ":" + j + ":" + v.get((int) (Math.random() * v.size())));
        }
        return new Action(Squares.PASS);
    }

    @Override
    public void init() {
    }

    //tomar una decision de una jugada teniendo en cuenta el algoritmo minimax
    public Action decision(int size, Percept p) {
        Action mejorAccion = null;
        Board tablero = crearTablero(size, p);
        
        ArrayList<Board> hijos = buscarHijos(tablero,size);
        
        System.out.println(hijos);
//        for (Board tablero2 : hijos) {
//            impVal(tablero2.values);
//        }
        return mejorAccion;
    }
    
    //crea un tablero en base a las percepciones actuales del agente
    public Board crearTablero(int size, Percept p) {
        Board tablero = new Board(size);

        for (int a = 0; a < size; a++) {
            for (int b = 0; b < size; b++) {
                if (((String) p.getAttribute(a + ":" + b + ":" + Squares.COLOR)).equals(Squares.SPACE)) {
                    if (((String) p.getAttribute(a + ":" + b + ":" + Squares.LEFT)).equals(Squares.TRUE)) {
                        tablero.values[a][b] |= LEFT;
                    }
                    if (((String) p.getAttribute(a + ":" + b + ":" + Squares.TOP)).equals(Squares.TRUE)) {
                        tablero.values[a][b] |= TOP;
                    }
                    if (((String) p.getAttribute(a + ":" + b + ":" + Squares.RIGHT)).equals(Squares.TRUE)) {
                        tablero.values[a][b] |= RIGHT;
                    }
                    if (((String) p.getAttribute(a + ":" + b + ":" + Squares.BOTTOM)).equals(Squares.TRUE)) {
                        tablero.values[a][b] |= BOTTOM;
                    }
                } else {
                    if (((String) p.getAttribute(a + ":" + b + ":" + Squares.WHITE)).equals(Squares.WHITE)) {
                        tablero.values[a][b] = WHITE;
                    } else {
                        tablero.values[a][b] = 15;
                    }
                }
            }
        }

        /*CUIDADO! creo que aquí hay un error del profe, no se cuentan los cuadros de
         color "WHITE", por lo que se hizo la funcion white_count en esta clase*/
//        System.out.println("white: "+white_count(tablero, size)+", black: "+tablero.black_count());
//        System.out.println("white: "+tablero.white_count()+", black: "+tablero.black_count());
        return tablero;
    }

    //retorna la copia de un tablero ingresado como parametro
    public Board clonarTablero(Board tabl, int size) {
        Board tablero = new Board(size);

        for (int a = 0; a < size; a++) {
            for (int b = 0; b < size; b++) {
                tablero.values[a][b] = tabl.values[a][b];
            }
        }
        return tablero;
    }

    //cuenta los cuadros blancos del tablero (el del profe parece que no funciona)
    public int white_count(Board tablero, int size) {
        int count = 0;
        for (int a = 0; a < size; a++) {
            for (int b = 0; b < size; b++) {
                if (tablero.values[a][b] == WHITE) {
                    count++;
                }
            }
        }
        return count;
    }

    //busca posibles jugadas en un estado (tablero) y las retorna en forma
    //de arraylist
    public ArrayList<Board> buscarHijos(Board tablero,int size) {
        ArrayList<Board> listaTableros = new ArrayList<>();
        
        for (int a = 0; a < size; a++) {
            for (int b = 0; b < size; b++) {
                if (tablero.values[a][b]<15) {
                    if ((tablero.values[a][b] & LEFT)!=LEFT) {
                        proceso(tablero, listaTableros, LEFT, a, b, size);
                    }
                    if ((tablero.values[a][b] & TOP)!=TOP) {
                        proceso(tablero, listaTableros, TOP, a, b, size);
                    }
                    if ((tablero.values[a][b] & RIGHT)!=RIGHT) {
                        proceso(tablero, listaTableros, RIGHT, a, b, size);
                    }
                    if ((tablero.values[a][b] & BOTTOM)!=BOTTOM) {
                        proceso(tablero, listaTableros, BOTTOM, a, b, size);
                    }
                }
            }
        }
        return listaTableros;
    }

    //verifica si se puede hacer una jugada ficticia y verifica si no hay jugadas iguales
    public void proceso(Board tablero, ArrayList<Board> listaTableros, int direccion, int fil, int col, int size) {
        Board tablTemp = clonarTablero(tablero,size);
        //falta agregar el cambio de turnos, no siempre va a ser TRUE como dice aqui
        tablTemp.play(true, fil, col, direccion);
        if (!duplicado(listaTableros, tablTemp.values)) {
            listaTableros.add(tablTemp);
        }
    }

    //imprime una matriz de valores
    public void impVal(int[][] val) {
        for (int[] val1 : val) {
            for (int b = 0; b < val.length; b++) {
                System.out.print(val1[b] + " ");
            }
            System.out.println();
        }
        System.out.println("------------------");
    }

    //verifica si un tablero no esta repetido en un arraylist
    public boolean duplicado(ArrayList<Board> listaTableros, int[][] values2) {
        int cont = 0;
        for (Board tabl : listaTableros) {
            for (int a = 0; a < values2.length; a++) {
                for (int b = 0; b < values2.length; b++) {
                    if (tabl.values[a][b] == values2[a][b]) {
                        cont++;
                    }
                }
            }
            if (cont == Math.pow(values2.length, 2)) {
                return true;
            }
            cont = 0;
        }
        return false;
    }

    //verifica que el tablero este lleno (el del profe parece no funcionar por 
    //no leer los cuadros blancos)
    public boolean full(Board tablero, int size) {
        return white_count(tablero, size) + tablero.black_count() == Math.pow(size, 2);
    }

    //retorna el numero de casillas del color activo
    //HAY QUE ARREGLAR ESTO!
    public int resultado(Board tablero,int size){
        if(color.equals(Squares.WHITE))
            return white_count(tablero, size);
        return tablero.black_count();
    }
//    public int minValue(Board tablero, Percept p) {
//        int size = tablero.values.length;
//        if (full(tablero, size)) {
//            if (color.equals(Squares.WHITE)) {
//                return tablero.black_count();
//            }
//            return white_count(tablero, size);
//        }
//        return 0;
//    }
//
//    public int maxValue(Board tablero, Percept p) {
//        int size = tablero.values.length;
//        if (full(tablero, size)) {
//            if (color.equals(Squares.WHITE)) {
//                return white_count(tablero, size);
//            }
//            return tablero.black_count();
//        }
//        return 0;
//    }
}
