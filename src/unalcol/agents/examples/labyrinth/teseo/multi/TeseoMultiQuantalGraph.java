package unalcol.agents.examples.labyrinth.teseo.multi;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import java.util.ArrayList;
import java.util.List;

public class TeseoMultiQuantalGraph extends MultiTeseoAgentProgram {

    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean AF, boolean AD, boolean AA, boolean AI) {
//    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        //-1: no hace nada
        //0: avanza
        //1: 1 giro a la derecha y avanza
        //2: 2 giros a la derecha y avanza
        //3: 3 giros a la derecha y avanza

        if (MT) return -1;  // encontrar la meta      

        //el nodo en donde se encuentra el agente no puede pertenecer a nodosPendientes
        if (nodosPendientes.contains(fila + "/" + columna)) {
            nodosPendientes.remove(fila + "/" + columna);
        }

        //crear nodo si no existe
        if (!grafo.containsVertex(fila + "/" + columna)) {
            grafo.addVertex(fila + "/" + columna);
        }

        int filaAnterior = fila;
        int columnaAnterior = columna;
        ArrayList<Integer> opciones = new ArrayList<>();

        //decisiones del agente
        if (!PF && decision(0)) {
            opciones.add(0);
        }
        if (!PD && decision(1)) {
            opciones.add(1);
        }
        if (!PA && decision(2)) {
            opciones.add(2);
        }
        if (!PI && decision(3)) {
            opciones.add(3);
        }

        // si no hay opciones de desplazamiento, se buscaon nodos pendientes
        if (opciones.isEmpty()) {
            //si no hay nodos pendientes, el agente se reinicia
            if (nodosPendientes.isEmpty()) {
                reiniciar();
                return -1;
            }

            // entre los nodos pendientes, buscar el nodo mas cercano
            //////////////////////////////////////////////////////////////////////////////////
            List<String> listaRutaCortaTemporal;
            List<String> listaRutaCorta = null;
            int longitudMinima = (int) Double.POSITIVE_INFINITY;
            int indice = -1;

            for (int a = 0; a < nodosPendientes.size(); a++) {
                try{
                    //en el caso de que el grafo no tenga problemas, busque la ruta corta
                    listaRutaCortaTemporal = rutaCorta.getPath(fila + "/" + columna, nodosPendientes.get(a));
                }catch(Exception e){
                    // si hay problemas con el grafo de cualquier tipo, el agente se reinicializa
                    reiniciar();
                    return -1;
                }
                if (listaRutaCortaTemporal.size() < longitudMinima) {
                    longitudMinima = listaRutaCortaTemporal.size();
                    indice = a;
                    listaRutaCorta = new ArrayList<>(listaRutaCortaTemporal);
                }
                listaRutaCortaTemporal.clear();
            }

            String nodoDestino = nodosPendientes.get(indice);
            //////////////////////////////////////////////////////////////////////////////////

            crearCamino(listaRutaCorta);
//            return accion(PF, PD, PA, PI, MT, AF, AD, AA, AI);
            return -1;
        }

        if (opciones.size() > 1) {
            nodosPendientes.add(fila + "/" + columna);
        } else {
            nodosPendientes.remove(fila + "/" + columna);
        }
        
        //entre las opciones disponibles, elige una de manera aleatoria
        int numeroAccion = opciones.get((int) (Math.random() * (opciones.size())));

        // si hay un agente enfrente, quedarse quieto
        // en el caso de que lo encuentre enfrente mas de 6 veces
        // el agente se reinicializa
        
        // NOTA: esto tal vez se pueda mejorar (ejemplo: buscar un nodo pendiente)
        if ((numeroAccion==0&&AF)||(numeroAccion==1&&AD)||(numeroAccion==2&&AA)||(numeroAccion==3&&AI)){ 
            if(contador==6){
                reiniciar();
            }
            contador++;
            return -1;
        }
        //si se mueve, cambiar orientacion
        if (0 <= numeroAccion && numeroAccion <= 3) {
            int salida[] = cambiarOrientacion(orientacion, numeroAccion);
            orientacion = salida[0];
            fila = salida[1];
            columna = salida[2];
        }

        //crear enlace o nodo si no existen
        crearEnlace(fila + "/" + columna, filaAnterior + "/" + columnaAnterior);

        return numeroAccion;
    }

    private Graph<String, String> grafo = new SparseMultigraph<>();
    private DijkstraShortestPath<String, String> rutaCorta = new DijkstraShortestPath(grafo);
    private ArrayList<String> nodosPendientes = new ArrayList<>();
    private int fila = 0;
    private int columna = 0;
    private int orientacion = 0;
    private int contador = 0;
    

    private void reiniciar(){
        fila = columna = orientacion = contador = 0;
        grafo= new SparseMultigraph<>();
        rutaCorta = new DijkstraShortestPath(grafo);
        nodosPendientes = new ArrayList<>();
        System.out.println("TeseoQuantal: memoria reiniciada");
    }
    private int[] cambiarOrientacion(int orientacion2, int giro) {
        int[] salida = {0, fila, columna};
        salida[0] = (orientacion2 + giro) % 4;
        if (salida[0] == 0) {         //si avanza al norte
            salida[1] = fila - 1;
        }
        if (salida[0] == 1) {         //si avanza al este
            salida[2] = columna + 1;
        }
        if (salida[0] == 2) {         //si avanza al sur
            salida[1] = fila + 1;
        }
        if (salida[0] == 3) {         //si avanza al oeste
            salida[2] = columna - 1;
        }
        return salida;
    }

    private void crearEnlace(String nodoPrincipal, String nodoTemporal) {
        if (!grafo.containsVertex(nodoPrincipal)) {
            grafo.addVertex(nodoPrincipal);
        }
        if (!grafo.containsVertex(nodoTemporal)) {
            grafo.addVertex(nodoTemporal);
            // ya que se crea un vertice, reinicializar el contador 
            // (porque el agente se esta moviendo)
            contador = 0;
        }

        String nombre = "(" + nodoTemporal + "&" + nodoPrincipal + ")";
        String nombre2 = "(" + nodoPrincipal + "&" + nodoTemporal + ")";

        if (!grafo.containsEdge(nombre) && !grafo.containsEdge(nombre2)) {
            grafo.addEdge(nombre, nodoTemporal, nodoPrincipal);
        }
    }

    private boolean decision(int giro) {
        //[1]: fila temporal    [2]: columna temporal
        int filColTemporal[] = cambiarOrientacion(orientacion, giro);
        if (!grafo.containsVertex(filColTemporal[1] + "/" + filColTemporal[2])) {
            return true;
        } else {
            crearEnlace(fila + "/" + columna, filColTemporal[1] + "/" + filColTemporal[2]);
            return false;
        }
    }

    private void crearCamino(List<String> listaRutaCorta) {
        String enlace;
        String[] arrayDeNodos;
        String nodo1;
        String nodo2;
        for (String listaRutaCorta1 : listaRutaCorta) {
            enlace = listaRutaCorta1;
            arrayDeNodos = enlace.split("&");
            nodo1 = arrayDeNodos[0].substring(1, arrayDeNodos[0].length());
            nodo2 = arrayDeNodos[1].substring(0, arrayDeNodos[1].length() - 1);
            grafo.removeEdge(enlace);
            grafo.removeVertex(nodo1);
            grafo.removeVertex(nodo2);
        }
    }
    
}
