/*
 * GraphEntropy:Edge
 * 
 * Date Created: Nov/03/2017
 * Author:
 *   -Justin Ritter
 */
package GraphEntropy;

public class Edge {
    private Vertex v1;
    private Vertex v2;

    public Edge(Vertex a, Vertex b) {
        set1(a);
        set2(b);
    }

    public Vertex get1() {
        return v1;
    }

    public Vertex get2() {
        return v2;
    }

    public void set1(Vertex a) {
        v1 = a;
    }

    public void set2(Vertex a) {
        v2 = a;
    }

    public boolean contains(Vertex a) {
        return (v1.equals(a) || v2.equals(a));
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Edge)) {
            return false;
        }
        if(obj == this) {
            return true;
        }

        return (v1.equals(((Edge) obj).get1()) &&
                v2.equals(((Edge) obj).get2())) ||
            (v1.equals(((Edge) obj).get2()) && v2.equals(((Edge) obj).get1()));
    }

    public int hashCode() {
        int prime = 17;
        int hash = 1;
        hash *= prime + (v1.hashCode());
        hash *= prime + (v2.hashCode());
        return hash;
    }

    public String toString() {
        return v1 + " " + v2;
    }
}
