package unalcol.agents.examples.labyrinth.teseo.simple.DataStructure;

/**
 *
 * @author Quantal Team
 */
public class InternalState {

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
