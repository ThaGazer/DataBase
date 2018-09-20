package Agglomerative;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Justin Ritter on 9/25/2017.
 */
public class Cluster {
    private List<Point> clusters;

    /**
     * constructs a cluster object
     */
    public Cluster() {
        clusters = new ArrayList<>();
    }

    /**
     * gets the cluster
     * @return a Agglomerative.Cluster
     */
    public List<Point> getClusters() {
        return clusters;
    }

    /**
     * adds a Agglomerative.Point a into the cluster
     * @param p Agglomerative.Point to be added
     */
    public void addPoint(Point p) {
        if(p != null) {
            clusters.add(p);
        } else {
            throw new NullPointerException("Empty point");
        }
    }

    public void addAll(Cluster c) {
        clusters.addAll(c.getClusters());
    }

    /**
     * removes a Agglomerative.Point from the cluster
     * @param p Agglomerative.Point to be removed
     */
    public void removePoint(Point p) {
        if(p != null) {
            clusters.remove(p);
        } else {
            throw new NullPointerException("Empty point");
        }
    }

    /**
     * clears out the cluster of all objects
     */
    public void removeAll() {
        clusters.clear();
    }

    /**
     * calculates the average distance of all points in a cluster to another
     */
    public Double avgLinkDistance(Cluster c) {
        double distSum = 0.0d;
        int clustersSize = getClusters().size()*c.getClusters().size();

        if(!this.equals(c)) {
            for (Point op : this.getClusters()) {
                for (Point p : c.getClusters()) {
                    distSum += p.distanceFrom(op);
                }
            }

            return clustersSize != 0 ? roundUp(distSum / clustersSize) : 0.0d;
        } else {
            return 0.0d;
        }
    }

    /**
     * checks if two clusters are equal
     * @param c cluster to compare to
     * @return if the clusters are equal
     */
    public boolean equals(Cluster c) {
        List<Point> left = getClusters();
        List<Point> right = c.getClusters();

        if(this == c) {
            return true;
        }
        if(getClusters().size() != c.getClusters().size()) {
            return false;
        }
        for(int i = 0; i < left.size(); i++) {
            if(!left.get(i).equals(right.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * the comparator to tell sort how to order. In this case increasing order
     */
    public static Comparator<Point> geneIdDEC =
            Comparator.comparingInt(Point::getID);

    /**
     * prints a cluster
     */
    public void printCluster(PrintWriter pw) {
        boolean firstPrint = true;
        clusters.sort(geneIdDEC);

        for(Point p : getClusters()) {
            if(firstPrint) {
                pw.print(p.getID());
                firstPrint = false;
            } else {
                pw.print(", " + p.getID());
            }
        }
    }

    /**
     * Rounds a double to the hundredths place
     * @param dec double to be rounded
     * @return the rounded double
     */
    private double roundUp(Double dec) {
        dec *= 100;
        dec = (double) Math.round(dec);
        dec /= 100;

        return dec;
    }
}
