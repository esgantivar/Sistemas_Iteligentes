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

    public SimulatedAnnealing(double temperaturaInicial, double temperaturaFinal, double factorEnfriamiento){
        this.temperaturaFinal = temperaturaFinal;
        temperatura = temperaturaInicial;
        this.factorEnfriamiento = factorEnfriamiento;
    }
    
    public boolean funcionProbabilidad(double temperatura, double delta) {
        if (delta < 0) return true;
        
        // distribucion de Boltzmann
        double C = Math.exp(-delta / temperatura);
        double R = Math.random();
       
//        System.out.print(R < C);
//        System.out.println(" C:"+C+" R:"+R+"\ntemperatura:"+temperatura+" delta:"+delta);
        return R < C;
    }

    public void calcularSimulatedAnnealing(byte nQueens){        
        Tablero tablero = new Tablero((byte) nQueens);
        Tablero nuevoTablero;
                
        while (temperatura > temperaturaFinal) {
            
            nuevoTablero = (Tablero) tablero.clone();
            
            //calcular los valores de los costos y actualizar la solucion optima

//            System.out.println("tableroN1:");
//            System.out.println(nuevoTablero.printTable());
            
            nuevoTablero.cambiar(temperatura, temperaturaFinal);
            int conflictos = tablero.evaluarConflictos();
            
//            System.out.println("tableroN2:");
//            System.out.println(nuevoTablero.printTable());
            
            // si no hay conflictos se ha terminado el problema
            if (conflictos==0) {
                System.out.println("solucion encontrada");
                break;
            }            
            
            //ERROR: la clonacion de objetos no es profunda al calcular delta
            //esto genera que siempre sea igual a 0
            int delta = nuevoTablero.evaluarConflictos() - conflictos;
            
            System.out.println(delta);
//            System.out.println("conf t:"+conflictos+", conf tn:"+(conflictos-delta));
//            System.out.println("tablero:");
//            System.out.println(tablero.printTable());
//            System.out.println("tablero nuevo:");
//            System.out.println(nuevoTablero.printTable());
            
            if(funcionProbabilidad(temperatura, delta)){
                tablero = (Tablero) nuevoTablero.clone();
            }
//            System.out.println("tablero:");
//            System.out.println(tablero.printTable());
            temperatura = temperatura * factorEnfriamiento;
//            System.out.println(temperatura);
        }
    }
    
}
