package unalcol.agents.examples.labyrinth.teseo.simple.DataStructure;

/**
 *
 * @author Quantal Team
 */
public class Node extends Point {

    private boolean marked;

    public Node(int east, int north, boolean _marked) {
        super(east, north);
        marked = _marked;
    }

    @Override
    public boolean equals(final Object object) {
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "East: "+this.east+" North: "+this.north;
    }

}
