package dbscan;

import jdk.nashorn.internal.objects.NativeUint8Array;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Justin Ritter on 9/25/2017.
 */
public class Cluster {
    List<Point> clusters;

    /**
     * constructs a cluster object
     */
    public Cluster() {
        clusters = new ArrayList<>();
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
     * add a collection of Points to a cluster
     * @param p
     */
    public void addAllPoint(List<Point> p) {
        if(p != null) {
            clusters.addAll(p);
        } else {
            throw new NullPointerException("Empty list of points");
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
