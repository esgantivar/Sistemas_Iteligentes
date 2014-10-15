package NQueensQuantal.GeneticAlgorithm;

import java.util.ArrayList;

/**
 *
 * @author Sneyder G
 */
public class Genome implements Comparable<Genome> {

    private final Queen[] queens;
    protected int fitNess;

    public Genome(byte nQueens) {
        queens = new Queen[nQueens];
        generateGenome(nQueens);
    }

    public Genome(Queen[] _queens) {
        queens = new Queen[_queens.length];
        System.arraycopy(_queens, 0, queens, 0, _queens.length);
        evaluateFitness();
    }

    public void generateGenome(byte nQueens) {
        byte value = 0;
        ArrayList<Byte> options = new ArrayList<>(nQueens);
        for (int i = 1; i <= nQueens; i++) {
            options.add((byte) i);
        }

        while (value < nQueens) {
            queens[value] = new Queen((byte) (value + 1), options);
            value++;
        }
        evaluateFitness();
    }

    public int evaluateFitness() {
        fitNess = 0;
        for (Queen q : queens) {
            for (Queen q1 : queens) {
                if (!q.equals(q1) && q.evaluateThreat(q1)) {
                    fitNess++;
                }
            }
        }
        return fitNess;
    }

    @Override
    public String toString() {
        String s = "Genome: [" + queens[0].y;
        for (int i = 1; i < (queens.length - 1); i++) {
            s = s + "," + queens[i].y;
        }
        s = s + "," + queens[queens.length - 1].y + "]";

        return s + " -> FitNess: " + evaluateFitness();

    }

    public String printTable() {
        byte n = (byte) queens.length;
        String t = "Table:\n";
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == queens[j].y) {
                    t += "Q ";
                } else {
                    t += "- ";
                }
            }
            t += "\n";
        }
        return t + "FitNess: " + evaluateFitness();
    }

    public Genome[] cruzar(Genome g) {
        int index = (int) ((queens.length * 0.8) * Math.random());
        int n = index + (int) (queens.length * 0.2);

        //System.out.println("index: " + index);
        Queen[] son = new Queen[queens.length];
        Genome[] sons = new Genome[2];

        for (int i = 0; i <= n; i++) {
            son[i] = new Queen(queens[i]);
        }
        for (int i = n + 1; i < queens.length; i++) {
            son[i] = new Queen(g.queens[i]);
        }
        sons[0] = new Genome(son);

        for (int i = 0; i <= n; i++) {
            son[i] = new Queen(g.queens[i]);
        }
        for (int i = n + 1; i < queens.length; i++) {
            son[i] = new Queen(queens[i]);
        }
        sons[1] = new Genome(son);
        for (Genome genome : sons) {
            genome.mute();
        }
        return sons;
    }

    public void mute() {
        int index = (int) (queens.length * Math.random());
        byte value = (byte) (queens.length * Math.random() + 1);
        queens[index].y = value;
    }

    @Override
    public int compareTo(Genome o) {
        return (fitNess < o.fitNess) ? -1 : ((fitNess == o.fitNess) ? 0 : 1);
    }
}
