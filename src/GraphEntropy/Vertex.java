/*
 * GraphEntropy:Vertex
 * 
 * Date Created: Nov/03/2017
 * Author:
 *   -Justin Ritter
 */
package GraphEntropy;

public class Vertex {
    private String name;
    private double entropy;
    private int degree;

    public Vertex() {
        setName("");
        setDegree(0);
        setEntropy(0.0);
    }

    public Vertex(String a) {
        setName(a);
        setDegree(0);
        setEntropy(0.0);
    }

    public Vertex(String a, int deg) {
        setName(a);
        setDegree(deg);
        setEntropy(0.0);
    }

    public String getName() {
        return name;
    }

    public double getEntropy() {
        return entropy;
    }

    public int getDegree() {
        return degree;
    }

    public void setName(String a) {
        name = a;
    }

    public void setEntropy(double ent) {
        entropy = ent;
    }

    public void setDegree(int deg) {
        degree = deg;
    }

    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Vertex)) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        return name.equals(((Vertex)obj).getName());
    }

    public int hashCode() {
        int prime = 17;
        int hash = 1;
        hash *= prime + ((name.isEmpty()) ? 0 : name.hashCode());
        return hash;
    }

    public String toString() {
        return name + ":" + degree + ":" + entropy;
    }

}
