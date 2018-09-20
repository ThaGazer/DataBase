package K_means;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Justin Ritter on 9/25/2017.
 */
public class Kmeans {

    private static final int TOTALTIMEPOINTS = 12;
    private static final int TOTALCLUSTERS = 10;
    private static int ITERATIONS = 100;
    private static File fname = new File("assignment3_input.txt");

    private static List<Point> allGenes = new ArrayList<>();
    private static List<Cluster> allClusters = new ArrayList<>();


    public static void main(String[] args) throws FileNotFoundException {

        Scanner scn = new Scanner(fname);
        int totGene = 1;

        /*reads in all gene points and stores them in the allGenes collection*/
        while(scn.hasNext()) {
            List<Double> geneTPoints = new ArrayList<>();

            for(int i = 0; i < TOTALTIMEPOINTS; i++) {
                geneTPoints.add(scn.nextDouble());
            }
            allGenes.add(new Point(totGene, geneTPoints));

            totGene++;
        }
        scn.close();

        /*creates 10 clusters*/
        for(int i = 0; i < TOTALCLUSTERS; i++) {
            allClusters.add(new Cluster());
        }

        /*initial gene split: 50 genes to every cluster*/
        int geneCount = 0;
        int clusterIndex = 0;
        for(Point p : allGenes) {
            if(geneCount < 50) {
                allClusters.get(clusterIndex).addPoint(p);
                geneCount++;
            } else {
                geneCount = 1;
                clusterIndex++;
                allClusters.get(clusterIndex).addPoint(p);
            }
        }
        calcClusterMean();

        /*loops until the clusters don't change*/
        while(ITERATIONS > 0/*!allClusters.equals(preClusters)*/) {

            /*finds if there is a closer cluster for every
            point in the current cluster*/
            int i = 0;
            for (Cluster c : allClusters) {
                for(int j = 0; j < c.getClusters().size(); j++) {
                    Point p = c.getClusters().get(j);
                    int closestIndex = findClosestCluster(p);
                    if (closestIndex != i) {
                        c.removePoint(p);
                        allClusters.get(closestIndex).addPoint(p);
                    }
                }
                i++;
            }

            /*recalculates mean of the clusters*/
            calcClusterMean();

            ITERATIONS--;
        }

        /*prints the clusters*/
        printAllClusters();
    }

    /**
     * calculates the new mean for all the clusters
     */
    public static void calcClusterMean() {
        for(Cluster c : allClusters) {
            c.calcMean();
        }
    }

    /**
     * finds the closest cluster to a gene
     * @param p the gene
     * @return the index of the closest cluster
     */
    public static int findClosestCluster(Point p) {
        int closestIndex = 0;
        double minDist = p.distanceFrom(allClusters.get(0).getCentroid());

        int i = 0;
        for(Cluster c : allClusters) {
            double curDist = p.distanceFrom(c.getCentroid());
            if(curDist < minDist) {
                closestIndex = i;
                minDist = curDist;
            }
            i++;
        }

        return closestIndex;
    }

    /**
     * Prints all 10 clusters according to the print specifications
     */
    public static void printAllClusters() {
        int minSizeDex = minSize();
        System.out.print(allClusters.get(minSizeDex)
                .getClusters().size() + ": [");
        allClusters.get(minSizeDex).printCluster();
        System.out.println("]");

        for(int i = 0; i < 9; i++) {
            minSizeDex = nextMinSize(minSizeDex);
            System.out.print(allClusters.get(minSizeDex)
                    .getClusters().size() + ": [");
            allClusters.get(minSizeDex).printCluster();
            System.out.println("]");
        }
    }

    /**
     * finds the smallest Size of all the clusters
     * and returns that clusters index
     * @return the min index
     */
    public static int minSize() {
        int minIndex = 0;
        int clustIndex = 0;
        int minSize = allClusters.get(0).getClusters().size();

        for(Cluster c : allClusters) {
            if(c.getClusters().size() < minSize) {
                minIndex = clustIndex;
                minSize = c.getClusters().size();
            }
            clustIndex++;
        }

        return minIndex;
    }

    /**
     * Finds the next smallest size of all the clusters
     * @param preMin the previous minimum index
     * @return the next min index
     */
    public static int nextMinSize(int preMin) {
        int clustIndex = 0;
        int minClustIndex = 0;
        int minSize = Integer.MAX_VALUE;
        int preMinSize = allClusters.get(preMin).getClusters().size();

        for(Cluster c : allClusters) {
            int currSize = c.getClusters().size();

            if(currSize > preMinSize && currSize < minSize && clustIndex != preMin) {
                minSize = c.getClusters().size();
                minClustIndex = clustIndex;
            }
            clustIndex++;
        }

        return minClustIndex;
    }
}
