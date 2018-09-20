/*
 * GraphEntropy:cluster
 * 
 * Date Created: Nov/03/2017
 * Author:
 *   -Justin Ritter
 */
package GraphEntropy;

import java.util.*;

public class Cluster {
    private List<Vertex> clust;
    private List<Edge> mapping;
    private double entropy = -1;

    public Cluster() {
        mapping = new ArrayList<>();
        clust = new ArrayList<>();
    }

    public Cluster(Collection<Vertex> vertices, Collection<Edge> edges) {
        mapping = new ArrayList<>();
        clust = new ArrayList<>();

        addAll_edge(edges);
        addAll_clust(vertices);
    }

    public Cluster(Cluster c) {
        mapping = new ArrayList<>();
        clust = new ArrayList<>();
        mapping.addAll(c.mapping);
        clust.addAll(c.getCluster());
        entropy = c.entropy;
    }

    public boolean addAll_clust(Collection<Vertex> collect) {
        boolean ret = clust.addAll(collect);
        calc_ClustEntropy();
        return ret;
    }

    public boolean addAll_edge(Collection<Edge> collect) {
        return mapping.addAll(collect);

    }

    public int size() {
        return getCluster().size();
    }

    public boolean add(Vertex a) {
        boolean ret = clust.add(a);
        calc_ClustEntropy();
        return ret;
    }

    public boolean remove(Vertex a) {
        boolean ret = clust.remove(a);
        calc_ClustEntropy();
        return ret;
    }

    public List<Vertex> getCluster() {
        return clust;
    }

    public double getEntropy() {
        return entropy;
    }

    public void setEntropy(double ent) {
        entropy = ent;
    }

    public boolean isEmpty() {
        return clust.isEmpty();
    }

    public void pruneVertexes() {
        List<Vertex> pristine = new ArrayList<>();
        pristine.addAll(clust);

        for(Vertex v : pristine) {
            if(test_removal(v)) {
                remove(v);
            }
        }
    }

    public void addOutterVertex() {
        List<Vertex> outters = new ArrayList<>();
        outters.addAll(findOutter());

        for(Vertex v: outters) {
            if(test_addition(v)) {
                add(v);
            }
        }
    }

    private double prob_inner(Vertex vert) {
        int innerEdges = calc_innerEdge(vert);
        return innerEdges/(double)vert.getDegree();
    }

    private double prob_outter(Vertex vert) {
        double outterEdge = vert.getDegree() - calc_innerEdge(vert);
        return outterEdge/(double)vert.getDegree();
    }

    private void calc_ClustEntropy() {
        if(entropy == -1){
            for(Vertex v : clust) {
                calc_VertexEntropy(v);
            }
        }

        double calcEntrop = 0.0;
        for(Vertex v : clust) {
            calcEntrop += v.getEntropy();
        }
        setEntropy(calcEntrop);
    }

    private void calc_VertexEntropy(Vertex vert) {
        double inProb = prob_inner(vert);
        double outProb = prob_outter(vert);

        if(outProb > 0.0) {
            double calculatedEntropy = -(inProb * log2(inProb)) -
                    outProb * log2(outProb);
            vert.setEntropy(calculatedEntropy);
        } else {
            vert.setEntropy(0.0);
        }
    }

    private int calc_innerEdge(Vertex vert) {
        int innerEdges = 0;
        for(Vertex inner : clust) {
            Edge currEdge = new Edge(inner, vert);
            if(mapping.contains(currEdge)) {
                innerEdges += 1;
            }
        }
        return innerEdges;
    }

    private boolean test_removal(Vertex vert) {
        Cluster testClust = new Cluster(this);
        if(testClust.remove(vert)) {
            return testClust.getEntropy() < getEntropy();
        }
        return false;
    }

    private boolean test_addition(Vertex vert) {
        Cluster testClust = new Cluster(this);
        if(testClust.add(vert)) {
            return testClust.getEntropy() < getEntropy();
        }
        return false;
    }

    private Collection<Vertex> findOutter() {
        Set<Vertex> outter = new HashSet<>();

        for(Vertex v : clust) {
            for (Edge e : mapping) {
                if (e.contains(v)) {
                    if (v.equals(e.get1()) && !clust.contains(e.get2())) {
                        outter.add(e.get2());
                    } else if (v.equals(e.get2()) && !clust.contains(e.get1())) {
                        outter.add(e.get1());
                    }
                }
            }
        }
        return outter;
    }

    private double log2(double d) {
        return Math.log(d)/Math.log(2);
    }

    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Cluster)) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        return (this.size() != ((Cluster) obj).size()) &&
                this.getCluster().equals(((Cluster)obj).getCluster());
    }

    public int hashCode() {
        int prime = 17;
        return prime + getCluster().hashCode();
    }

    public String toString() {
        String[] outStr = {String.valueOf(clust.size()) + ": {"};
        final int[] i = {0};
        clust.forEach((k)->{
            if(i[0] < clust.size()-1) {
                outStr[0] += k.getName() + " ";
            } else {
                outStr[0] += k.getName();
            }
            i[0]++;
        });
        outStr[0] += "}";
        return outStr[0];
    }

}
