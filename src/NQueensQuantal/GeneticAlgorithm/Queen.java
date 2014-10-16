
package NQueensQuantal.GeneticAlgorithm;

import java.util.ArrayList;

/**
 *
 * @author Sneyder G
 */
public class Queen{
    protected byte x;
    protected byte y;

    public byte getY() {
        return y;
    }

    public void setY(byte y) {
        this.y = y;
    }

    public Queen(byte _x, ArrayList<Byte> nQueens){
        x = _x;
        int index=(int) (nQueens.size() * Math.random());
        y = nQueens.remove(index);
    }
    
    public Queen(Queen q){
        x = q.x;
        y = q.y;
    }

    public boolean evaluateThreat(Queen q){
        
        if(y == q.y || x == q.x) return true;
        boolean s1 = ((x-y)==(q.x-q.y))||((x+y)==(q.x+q.y));
        boolean s2 = ((y-q.y)==(x-q.x))||((y-q.y)==(q.x-x));
        return s1 && s2;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Queen)) {
            return false;
        }
        final Queen queen = (Queen) object;
        return (this.x== queen.x) && (this.y == queen.y);
    }    
}
