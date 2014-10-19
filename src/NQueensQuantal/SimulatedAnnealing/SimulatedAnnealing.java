// calculo por templado simulado
package NQueensQuantal.SimulatedAnnealing;

/**
 *
 * @author Cesar Galvis
 */
public class SimulatedAnnealing {

    private final double temperaturaFinal;
    private final double factorEnfriamiento;
    private double temperatura;

    public SimulatedAnnealing(double temperaturaInicial, double temperaturaFinal, double factorEnfriamiento) {
        this.temperaturaFinal = temperaturaFinal;
        temperatura = temperaturaInicial;
        this.factorEnfriamiento = factorEnfriamiento;
    }

    public boolean funcionTemplado(double temperatura, double delta) {
        if (delta < 0) {
            return true;
        }

        // distribucion de Boltzmann
        double C = Math.exp(-delta / temperatura);
        double R = Math.random();

        return R < C;
    }

    public void calcularSimulatedAnnealing(byte nQueens) {
        Tablero tablero = new Tablero((byte) nQueens);

        int iteraciones = 0;
        while (temperatura > temperaturaFinal) {

            //calcular los valores de los costos y actualizar la solucion optima
            
//            System.out.println("tableroN1:");
//            System.out.println(tablero.printTable());
                        
            //calcular el numero de conflictos del tablero
            int conflictos = tablero.evaluarConflictos();

            // si no hay conflictos se ha terminado el problema
            if (conflictos == 0) {
                System.out.println("solucion encontrada, iteraciones: "+iteraciones);
                break;
            }
            
            //cambiar el tablero actual y calcular los conflictos del nuevo
            int columna = tablero.columnaAleatoria();
            byte fila = tablero.filaAleatoria(columna);
            byte filaOriginal = tablero.filaOriginal(columna);
            tablero.cambiar(fila, columna);
            int conflictos2 = tablero.evaluarConflictos();
            
            
//            System.out.println("tableroN2:");
//            System.out.println(tablero.printTable());

            int delta = conflictos2 - conflictos;

//            System.out.println("conf t:"+conflictos+", conf tn:"+(conflictos2)+", delta:"+delta);

            if (!funcionTemplado(temperatura, delta)) {
                tablero.cambiar(filaOriginal, columna);
            }
            temperatura = temperatura * factorEnfriamiento;
            iteraciones++;
//            System.out.println(temperatura);
        }
    }

}
