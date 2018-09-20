package dbscan;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Point {

    private static final int TIMEPOINTSIZE = 12;

    private int gID;
    private List<Double> timePoints;

    /**
     * constructs a Point where the id is 0 and the time points are all 0.0
     */
    public Point() {
        gID = 0;
        timePoints = new ArrayList<>(TIMEPOINTSIZE);

        for(int i = 0; i < TIMEPOINTSIZE; i++) {
            timePoints.add(0.0d);
        }
    }

    /**
     * Constructs a Point with an id and a predefined list of time points
     * @param id id of the Point
     * @param tPoint time points of the Point
     */
    public Point(Integer id, List<Double> tPoint) {
        if(id != null && tPoint != null) {
            setID(id);
            timePoints = new ArrayList<>();
            timePoints.addAll(tPoint);
        } else {
            throw new NullPointerException("Empty attributes");
        }
    }

    /**
     * gets the id of the Point
     * @return gID
     */
    public int getID() {
        return gID;
    }

    /**
     * sets the id of the Point
     * @param id id to set
     */
    public void setID(int id) {
        gID = id;
    }

    /**
     * gets the list of time points
     * @return the list of time points
     */
    public List<Double> getTimePoints() {
        return timePoints;
    }

    /**
     * sets a time point specified by index
     * @param index time point to change
     * @param val val to change to
     */
    public void setTimePoint(int index, double val) {
        timePoints.set(index, val);
    }

    /**
     * calculates the distance from one Point to another
     * @param a second Point for the distance formula
     * @return the distance between to points
     */
    public double distanceFrom(Point a) {
        double sum = 0.0;

        for(int i = 0; i < TIMEPOINTSIZE; i++) {
            sum += abs(a.getTimePoints().get(i) - getTimePoints().get(i));
        }

        return sum;
    }
}
