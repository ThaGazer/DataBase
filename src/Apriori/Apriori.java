package Apriori;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Apriori {

    public static void main(String[] args) throws FileNotFoundException {

        List<String[]> itemSet = new ArrayList<>();
        Map<String, Double> itemSuppFinal = new HashMap<>();
        Map<String, Double> itemSuppL1;
        Map<String, Double> itemSuppLn;
        int totItemCount;
        double minSupp = .035;

        Scanner scn = new Scanner(new File("dataSet1"));


        while(scn.hasNext()) {
            //grabas a line and splits every word by a space
            String[] splitbuf = scn.nextLine().split("\\s");
            splitbuf = Arrays.copyOfRange(splitbuf, 1, splitbuf.length);
            itemSet.add(splitbuf);
        }
        scn.close();

        totItemCount = findItemCount(itemSet);
        itemSuppLn = findItemSupp(itemSet, totItemCount);
        pruneItemSet(itemSuppLn, minSupp);

        while(itemSuppLn.size() > 1) {
            itemSuppLn = joinItemSet(itemSet, itemSuppLn, totItemCount);
            itemSuppFinal.putAll(pruneItemSet(itemSuppLn, minSupp));
        }

        printaMap(itemSuppFinal);

    }

    /*private static boolean hasStr(Map<String, Double> a, String[] b) {
        final boolean[] tf = {false};

        if(!a.isEmpty()) {
            a.forEach((k, v) -> {
                if(!tf[0]) {
                    for (int i = 0; i < k.length; i++) {
                        if (k[i].equals(b[i])) {
                            tf[0] = true;
                        }
                    }
                }
            });
        }
        else {
            tf[0] = false;
        }

        return tf[0];
    }*/

    private static int findItemCount(List<String[]> a) {
        int cnt = 0;

        for(String[] i : a) {
            for(String ignored : i) {
                cnt++;
            }
            cnt--;
        }

        return cnt;
    }

    private static Map<String, Double> findItemSupp(List<String[]> a, int itemTotal) {
        Map<String, Double> cntHolder = new HashMap<>();

        for (String[] i : a) {
            for (String j : i) {
                if(cntHolder.containsKey(j)) {
                    cntHolder.put(j, cntHolder.get(j) + 1);
                }
                else {
                    cntHolder.put(j, 1.0);
                }
            }
        }

        cntHolder.forEach((k, v)->{
            double percentSupp = v/itemTotal;
            cntHolder.put(k, percentSupp);
        });

        return cntHolder;
    }

    private static Map<String, Double> pruneItemSet(Map<String, Double> a, Double minSupp) {
        Map<String, Double> builder = new HashMap<>();
        List<String> rmBuilder = new ArrayList<>();

        a.forEach((k,v)->{
            if(v < minSupp) {
                rmBuilder.add(k);
            }
            else {
                builder.put(k, v);
            }
        });

        rmBuilder.forEach(a::remove);

        return builder;
    }

    private static Map<String, Double> joinItemSet(List<String[]> itemSet, Map<String, Double> a, int itemTotal) {
        Map<String, Double> builder = new HashMap<>();

        a.forEach((k,v)->a.forEach((l, w)->{
            String[] delimStr = l.split("\\s");
            for(String i : delimStr) {
                if(k.contains(i)) {
                    String newStr = k + " " + i;
                }
            }
        }));

        builder.forEach((k,v)->{
            itemSet.forEach(l->{
                if(Arrays.asList(l).contains(k)) {
                    builder.put(k, v + 1);
                }
            });

            double percentSupp = v/itemTotal;
            builder.put(k, percentSupp);
        });

        return builder;
    }

    private static void printaMap(Map<String, Double> a) {
        while(!a.isEmpty()) {
            List<String> rmBuilder = new ArrayList<>();

            int curLvl = 2;
            a.forEach((k,v)->{
                if(k.length() == curLvl) {
                    System.out.println("[" + k + "] " + v + "% support");
                    rmBuilder.add(k);
                }
            });

            rmBuilder.forEach(a::remove);
        }
    }
}
