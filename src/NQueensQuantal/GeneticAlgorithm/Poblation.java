/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NQueensQuantal.GeneticAlgorithm;

/**
 *
 * @author Sneyder G
 */
public class Poblation {

    private byte nQueens;
    private short nPoblation;
    private Genome[] poblation;
    
    public Poblation(byte _nQueens, short _nPoblation) {
        nQueens = _nQueens;
        nPoblation = _nPoblation;
         poblation = new Genome[nPoblation];
        for (int i = 0; i < nPoblation; i++) {
            poblation[i] = new Genome(nQueens);
        }
        QueenQuickSort.qsort(poblation);
    }
    
    public String averageFitness(){
        double avg = 0;
        for (Genome genome : poblation) {
            avg += genome.evaluateFitness();
        }
        avg = avg / poblation.length;
        return "avg fitness: "+ avg + " betterfitness: "+poblation[0].fitNess;
    }
    
    public void nextGeneration(){
        int n = (int)poblation.length/2;
        if(n%2 != 0) n-=1;
        
        int m = n;
        n = n/2;
        // a partir de m la poblacion sera cambiada por los nuevos hijos
        //n sera el numero de padres
        int father=0, mother=1;
        Genome[] gs;
        while(n > 0){
            //System.out.println("f: "+father+" m: "+mother+" h: "+m);
            gs = poblation[father].cruzar(poblation[mother]);
            //System.out.println("gs"+gs.length);
            poblation[m] = gs[0];
            poblation[m+1] = gs[1];
            m+=2;
            father +=2;
            mother = father + 1;
            //System.out.println("f: "+father+" m: "+mother+" h: "+m);
            n --;
        }
        QueenQuickSort.qsort(poblation);
    }
    
    @Override
    public String toString(){
        if(poblation.length==0){
            return "Dont Exists Poblation";
        }
        String s = "Size Poblation: "+poblation.length+", Better Fitness: "+poblation[0].fitNess+"\n";
        for (Genome genome : poblation) {
            s+=genome.toString()+"\n";
        }
        return s;
    }

}
