
package unalcol.agents.examples.labyrinth.teseo.simple.DataStructure;

/**
 *
 * @author Sneyder G
 */
public class Point {

    protected int east;
    protected int north;

    public Point() {
        east = 0;
        north = 0;        
    }
    public Point(Point p) {
        east =  p.east;
        north = p.north;        
    }

    public Point(int _east, int _north) {
        east = _east;
        north = _north;
    }

    
    public void setLocation(int _east, int _north) {
        east = _east;
        north = _north;
    }

    public int getEast() {
        return east;
    }

    public int getNorth() {
        return north;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Point)) {
            return false;
        }
        final Point point = (Point) object;
        return (this.east== point.east) && (this.north == point.north);
    }

    @Override
    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(Double.valueOf(east));
        bits ^= java.lang.Double.doubleToLongBits((double) north) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }
    @Override
    public String toString(){
        return "East: "+east+" North: "+north;
    }

}
