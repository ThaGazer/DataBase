/*
 * GraphEntropy:GraphEntropy
 * 
 * Date Created: Nov/02/2017
 * Author:
 *   -Justin Ritter
 */
package GraphEntropy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import static java.lang.System.exit;

public class GraphEntropy {

    private static final String errorFileFormatting =
            "file not formatted correctly";
    private static final String errorFileOpen = "Could not open file: ";

    private static Set<Edge> allEdge_set;
    private static Set<Vertex> allVertex_set;
    private static List<Vertex> allVertex_list;
    private static Set<Cluster> finalClust_set;
    private static List<Cluster> finalClust_list;
    private static List<Vertex> prevCluster;


    public static void main(String[] args) {
        allEdge_set = new HashSet<>();
        finalClust_set = new TreeSet<>(new clusterComparator_size());
        finalClust_list = new ArrayList<>();
        allVertex_list = new ArrayList<>();
        allVertex_set = new HashSet<>();
        prevCluster = new ArrayList<>();

        if(args.length < 1) {
            System.err.println("Usage: <filename>");
            exit(-1);
        }
        String fname = args[0];

        try(Scanner scn = new Scanner(new File(fname))) {
            while(scn.hasNextLine()) {
                String[] lineSplit = scn.nextLine().split("\\s");

                if(lineSplit.length < 2) {
                    throw new IllegalArgumentException(errorFileFormatting);
                }
                allEdge_set.add(new Edge
                        (new Vertex(lineSplit[0]), new Vertex(lineSplit[1])));
                allVertex_set.add(new Vertex(lineSplit[0]));
                allVertex_set.add(new Vertex(lineSplit[1]));
            }
        } catch(FileNotFoundException fnfe) {
            System.out.println(errorFileOpen + fnfe.getMessage());
        }

        calculateAllVertexDegree();

        allVertex_list.addAll(allVertex_set);
        while(allVertex_list.size() > prevCluster.size()) {
            Vertex nextVert = nextDegree_max();

            Cluster nextClust = new Cluster
                    (findNeighbors(nextVert), allEdge_set);
            nextClust.pruneVertexes();
            nextClust.addOutterVertex();

            if(nextClust.isEmpty()) {
                nextClust.add(nextVert);
            }

            if (!finalClust_list.add(nextClust)) {
                System.out.println("failed to add: " + nextClust);
            }
            prevCluster.addAll(nextClust.getCluster());
        }

        finalClust_set.addAll(finalClust_list);
        System.out.println(finalClust_set.size());
/*        for(Cluster v : finalClust_set) {
            if(v.size() > 2) {
                System.out.println(v.toString());
            }
        }*/
    }

    private static void calculateAllVertexDegree() {
        for(Vertex v : allVertex_set) {
            for(Edge e : allEdge_set) {
                if(e.contains(v)) {
                    v.setDegree(v.getDegree() + 1);
                }
            }
        }
    }

    private static Vertex nextDegree_max() {
        Vertex maxDegree = allVertex_list.get(0);

        for(Vertex v : allVertex_list) {
            if(v.getDegree() >= maxDegree.getDegree() &&
                    !prevCluster.contains(v)) {
                if(v.getDegree() == maxDegree.getDegree() &&
                        v.getName().compareTo(maxDegree.getName()) > 0) {
                    maxDegree = v;

                } else {
                    maxDegree = v;
                }
            }
        }
        return maxDegree;
    }

    private static Set<Vertex> findNeighbors(Vertex a) {
        Set<Vertex> neighbors = new HashSet<>();

        for(Edge e : allEdge_set) {
            if (e.contains(a)) {
                if(allVertex_list.contains(e.get1())) {
                    neighbors.add(allVertex_list.get
                            (allVertex_list.indexOf(e.get1())));
                }
                if(allVertex_list.contains(e.get2())) {
                    neighbors.add(allVertex_list.get
                            (allVertex_list.indexOf(e.get2())));
                }
            }
        }
        return neighbors;
    }

    public static class clusterComparator_size implements Comparator<Cluster> {

        @Override
        public int compare(Cluster o1, Cluster o2) {
            if(o1.size() < o2.size()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
