/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.simple;

import java.util.HashMap;

/**
 *
 * @author Sneyder G
 */
public class TeseoSimpleQuantalHashMap extends SimpleTeseoAgentProgram {

    private InternalState currentState;
    private HashMap<Node, Node[]> memory;

    public TeseoSimpleQuantalHashMap() {
        currentState = currentState.getInstance();
        memory = new HashMap<>();
    }

    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        if (MT) {
            return -1;
        }
        if (!PI) {
            return 3;
        }
        if (!PF) {
            return 0;
        }
        if (!PD) {
            return 1;
        }
        return 2;
    }

    private class InternalState {

        private InternalState instance;

        private Point currentPosition;
        private int orientation;

        private InternalState() {
            currentPosition = new Point();
            orientation = 0;
        }

        public InternalState getInstance() {
            if (instance == null) {
                instance = new InternalState();
            }
            return instance;
        }

        private void setOrientation(int rotationNumber) {
            orientation = (orientation + rotationNumber) % 4;
        }

        private void setLocation(int east, int north) {
            currentPosition.setLocation(east, north);
        }

        public void modifyCurrentState(int rotationNumber) {
            setOrientation(rotationNumber);

            int eastTemp = currentPosition.getEast();
            int northTemp = currentPosition.getNorth();

            switch (orientation) {
                case 0: {
                    northTemp += 1;
                    break;
                }
                case 1: {
                    eastTemp += 1;
                    break;
                }
                case 2: {
                    northTemp -= 1;
                    break;
                }
                case 3: {
                    eastTemp -= 1;
                    break;
                }
            }
            setLocation(eastTemp, northTemp);
        }
    }

    private class Point {

        private int east;
        private int north;

        public Point() {
            east = 0;
            north = 0;
        }

        public Point(int east_, int north_) {
            east = east_;
            north = north_;
        }

        public void setLocation(int east_, int north_) {
            east = east_;
            north = north_;
        }

        public int getEast() {
            return east;
        }

        public int getNorth() {
            return north;
        }

    }

    private class Node extends Point {

        private boolean marked;

        public Node(int east, int north, boolean marked_) {
            super(east, north);
            marked = marked_;
        }

        @Override
        public boolean equals(final Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Point)) {
                return false;
            }
            final Point point = (Point) object;
            return (this.getEast() == point.getEast()) && (this.getNorth() == point.getNorth());
        }
    }
}
