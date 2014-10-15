/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NQueensQuantal.GeneticAlgorithm;

public class QueenQuickSort {

    public static <T extends Comparable<? super T>> void qsort(T[] array) {
        qsort(array, 0, array.length - 1);
    }

    static <T extends Comparable<? super T>> void qsort(T[] array, int izq0, int dech0) {
        int izq = izq0;
        int dech = dech0 + 1;
        T pivote, aux;
        pivote = array[izq0];
        do {
            do {
                izq++;
            } while (izq <= dech0 && array[izq].compareTo(pivote) < 0);
            do {
                dech--;
            } while (array[dech].compareTo(pivote) > 0);
            if (izq < dech) {
                aux = array[izq];
                array[izq] = array[dech];
                array[dech] = aux;
            }
        } while (izq <= dech);
        aux = array[izq0];
        array[izq0] = array[dech];
        array[dech] = aux;
        if (izq0 < dech) {
            qsort(array, izq0, dech);
        }
        if (izq < dech0) {
            qsort(array, izq, dech0);
        }
    }
}
