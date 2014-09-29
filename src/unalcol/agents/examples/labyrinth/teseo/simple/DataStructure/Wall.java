/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.simple.DataStructure;

/**
 *
 * @author Sneyder G
 */
public class Wall {

    private boolean northWall;
    private boolean southWall;
    private boolean eastWall;
    private boolean weastWall;

    public Wall(boolean[] _Perception, int _orientation) {
        northWall = _Perception[(_orientation + 0) % 4];
        eastWall = _Perception[(_orientation + 1) % 4];
        southWall = _Perception[(_orientation + 2) % 4];
        weastWall = _Perception[(_orientation + 3) % 4];
    }

    public boolean isNorthWall() {
        return northWall;
    }

    public boolean isSouthWall() {
        return southWall;
    }

    public boolean isEastWall() {
        return eastWall;
    }

    public boolean isWeastWall() {
        return weastWall;
    }
}
