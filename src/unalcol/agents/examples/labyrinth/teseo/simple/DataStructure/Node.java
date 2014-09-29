package unalcol.agents.examples.labyrinth.teseo.simple.DataStructure;

/**
 *
 * @author Quantal Team
 */
public class Node extends Point {

    private boolean marked;
    private Wall wall;

    public Node(int east, int north, boolean _marked) {
        super(east, north);
        marked = _marked;
    }
    
    public Node(int east, int north, boolean[] _perception,int _orientarion) {
        super(east, north);
        marked=false;
        wall= new Wall(_perception,_orientarion);
    }
    
    public Node(Point p, boolean[] _perception,int _orientarion) {
        super(p.east, p.north);
        marked=false;
        wall= new Wall(_perception,_orientarion);
    }
    
    public Node(Point p){
        super(p.east, p.north);
        marked = false;
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
        return "(East: "+this.east+" North: "+this.north+")";
    }

}
