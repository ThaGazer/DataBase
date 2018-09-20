package Agglomerative;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Justin Ritter on 10/5/2017.
 */
public class Agglo {
    private static final int TOTALTIMEPOINTS = 12;

    private static List<Point> allGene = new ArrayList<>();
    private static List<Cluster> allClusters = new ArrayList<>();
    private static List<Cluster> finalCluster = new ArrayList<>();
    private String filename;

    public static void main(String[] args) {
        try {
            Scanner scn = new Scanner(new File("assignment4_input.txt"));

            int geneCount = 0;
            while(scn.hasNext()) {
                List<Double> geneTpoint = new ArrayList<>();

                for(int i = 0; i < TOTALTIMEPOINTS; i++) {
                    geneTpoint.add(scn.nextDouble());
                }
                allGene.add(new Point(geneCount+1, geneTpoint));
                geneCount++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: \"dataSet.txt\"");
        }

        /*print test*/
        //printGene(allGene);

        /*creates a new cluster for all genes*/
        for(Point p : allGene) {
            Cluster tmpClust = new Cluster();
            tmpClust.addPoint(p);
            allClusters.add(tmpClust);
        }
        finalCluster.addAll(allClusters);

        while(finalCluster.size() > 1) {
            /*join clusters*/
            join(finalCluster);
            /*print clusters*/
            if(finalCluster.size() == 50) {
                printSumAllClusters();
            }
            try {
                PrintWriter pw;
                String filename;

                if (finalCluster.size() == 50) {
                    filename = "output_50.txt";
                    pw = new PrintWriter(new File(filename));

                    printAllClusters(pw);
                } else if (finalCluster.size() == 30) {
                    filename = "output_30.txt";
                    pw = new PrintWriter(new File(filename));

                    printAllClusters(pw);
                } else if (finalCluster.size() == 10) {
                    filename = "output_10.txt";
                    pw = new PrintWriter(new File(filename));

                    printAllClusters(pw);
                }
            } catch(FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static void join(List<Cluster> cluster) {
        Cluster minA = cluster.get(0);
        Cluster minB = cluster.get(1);
        double minDist = minA.avgLinkDistance(minB);

        for(Cluster c1 : cluster) {
            for(Cluster c2: cluster) {
                double currDist = c1.avgLinkDistance(c2);
                if(currDist != 0.0d && currDist < minDist) {
                    minA = c1;
                    minB = c2;
                    minDist = currDist;
                }
            }
        }

        cluster.remove(minA);
        cluster.remove(minB);
        minA.addAll(minB);
        cluster.add(minA);
    }

    /**
     * prints all genes in a list
     * @param gene list of genes
     */
    public static void printGene(List<Point> gene) {
        for(Point p : gene) {
            boolean firstPrint = true;
            System.out.print(p.getID() + ": [");
            for(Double d : p.getTimePoints()) {
                if(firstPrint) {
                    System.out.print(d);
                    firstPrint = false;
                } else {
                    System.out.print(" " + d);
                }
            }
            System.out.println("]");
        }
    }

    /**
     * Prints all 10 clusters according to the print specifications
     */
    public static void printAllClusters(PrintWriter pw) {
        for(Cluster c : finalCluster) {
            pw.print(c.getClusters().size() + " [");
            c.printCluster(pw);
            pw.println("]");

        }
        pw.flush();
    }

    public static void printSumAllClusters() {
        Map<Integer, Integer> clusterCount = new TreeMap<>();

        for(Cluster c : finalCluster) {
            int  clustSize = c.getClusters().size();
            if(clusterCount.containsKey(clustSize)) {
                clusterCount.put(clustSize, clusterCount.get(clustSize)+1);
            } else {
                clusterCount.put(clustSize, 1);
            }
        }

        clusterCount.forEach((k, v) -> {
            System.out.println("Size: " + k + " " + v + " clusters");
        });
    }

}
