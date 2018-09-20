package K_means;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Justin Ritter on 9/25/2017.
 */
public class Cluster {
    Point centroid;
    List<Point> clusters;

    /**
     * constructs a cluster object
     */
    public Cluster() {
        centroid = new Point();
        clusters = new ArrayList<>();
    }

    /**
     * gets the centroid of the cluster
     * @return a Point
     */
    public Point getCentroid() {
        return centroid;
    }

    /**
     * gets the cluster
     * @return a Cluster
     */
    public List<Point> getClusters() {
        return clusters;
    }

    /**
     * adds a Point a into the cluster
     * @param p Point to be added
     */
    public void addPoint(Point p) {
        if(p != null) {
            clusters.add(p);
        } else {
            throw new NullPointerException("Empty point");
        }
    }

    /**
     * removes a Point from the cluster
     * @param p Point to be removed
     */
    public void removePoint(Point p) {
        if(p != null) {
            clusters.remove(p);
        } else {
            throw new NullPointerException("Empty point");
        }
    }

    /**
     * calculates the mean of the cluster
     */
    public void calcMean() {
        Point newMeanPoint = new Point();

        /*if the cluster is empty set centroid to empty object*/
        if(!clusters.isEmpty()) {
            /*add all time points in cluster*/
            for (Point p : clusters) {
                int i = 0;
                for (Double d : p.getTimePoints()) {
                    double centPoint = newMeanPoint.getTimePoints().get(i);
                    newMeanPoint.setTimePoint(i, (centPoint + d));
                    i++;
                }
            }

            /*calculate the average of all the time points*/
            int i = 0;
            for (Double d : newMeanPoint.getTimePoints()) {
                Double avg = roundUp((d / getClusters().size()));
                newMeanPoint.setTimePoint(i, avg);
                i++;
            }
        }

        /*set new mean for cluster*/
        centroid = newMeanPoint;
    }

    /**
     * the comparator to tell sort how to order. In this case increasing order
     */
    public static Comparator<Point> geneIdDEC =
            Comparator.comparingInt(Point::getID);

    /**
     * prints a cluster
     */
    public void printCluster() {
        boolean firstPrint = true;
        clusters.sort(geneIdDEC);

        for(Point p : getClusters()) {
            if(firstPrint) {
                System.out.print(p.getID());
                firstPrint = false;
            } else {
                System.out.print(", " + p.getID());
            }
        }
    }

    /**
     * Rounds a double to the hundredths place
     * @param dec double to be rounded
     * @return the rounded double
     */
    public double roundUp(Double dec) {
        dec *= 100;
        dec = (double) Math.round(dec);
        dec /= 100;

        return dec;
    }
}
