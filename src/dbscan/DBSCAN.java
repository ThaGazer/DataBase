package dbscan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class DBSCAN {

    private static final String FILENAME = "assignment3_ground_truth.txt";
    private static final int TOTALTIMEPOINTS = 12;

    private static List<Point> allPoints;
    private static List<Cluster> finCluster;

    private static int minPnt;
    private static double epsilon;


    public DBSCAN(int mPnt, double eps) {
        minPnt = mPnt;
        epsilon = eps;
        allPoints = new ArrayList<>();
        finCluster = new ArrayList<>();
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.err.println("Usage: <minPoints> <epsilon");
            exit(1);
        }

        File fileName;

/*        Scanner scn = new Scanner(System.in);
        do {
            System.out.println("Enter file name to read: ");
            fileName = new File(scn.nextLine());

            if(!fileName.exists()) {
                System.out.println("Error: could not find file");
            }
        } while(fileName.exists() && fileName.isFile());
        scn.close();*/

        fileName = new File(FILENAME);

        //reading from file
        try (Scanner scn = new Scanner(fileName)) {
            int itemCount = 0;

            while(scn.hasNextLine()) {
                List<Double> timeInc = new ArrayList<>();
                for(int i = 0; i < TOTALTIMEPOINTS; i++) {
                    timeInc.add(scn.nextDouble());
                }
                allPoints.add(new Point(itemCount, timeInc));
                itemCount++;
            }
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        for(Point p : allPoints) {
            if(isNoise(p)) {

            }
            findAll_DensityReachable(p);
        }
    }

    /**
     * find all points within a collection that are
     * density reachable from a single point.
     * Returns a collection of all the points found
     * @param p point to find for
     */
    private static List<Point> findAll_DensityReachable(Point p) {
        List<Point> newClust = new ArrayList<>();

        return newClust;
    }

    /**
     * check whether this point can reach any other points
     * @param p point to check
     */
    private static boolean isNoise(Point p) {
        boolean noise = false;
        for(int i = 0; i < allPoints.size() && !noise; i++) {
            Point currP = allPoints.get(i);

            if(p.distanceFrom(currP) <= epsilon) {
                noise = true;
            }
        }

        return noise;
    }
}
