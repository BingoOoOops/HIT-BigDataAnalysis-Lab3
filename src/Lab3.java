/*
 * by LB
 * in Harbin Institute of Technology
 *
 * 2021 Spring Semester
 */


import java.io.*;
import java.util.*;

public class Lab3 {

    public static int chunkSize;
    public static final int memorySize = 1000;
    private static final String dataPath = "./data/data.csv";

    public static Map<String, List<Double>> originalData = new HashMap<String, List<Double>>(){{
        put("PRECTOT", new ArrayList<>());
        put("TS", new ArrayList<>());
    }};

    public static Map<String, List<Double>> basicAggregates = new HashMap<String, List<Double>>(){{
        put("PRECTOT_sum", new ArrayList<>());
        put("PRECTOT_sumOfSquare", new ArrayList<>());
        put("TS_sum", new ArrayList<>());
        put("TS_sumOfSquare", new ArrayList<>());
    }};
    public static Map<String, SegmentTree> segmentTrees = new HashMap<>();


    /**
     * read data file and convert to chunks of basic aggregates
     */
    private static void initializeData() throws IOException {
        BufferedReader br = new BufferedReader(
            new InputStreamReader(
                new FileInputStream(dataPath)
            )
        );
        String line;
        boolean isFirstLine = true;
        int couLine = 0;

        double PRECTOT_Sum = 0;
        double PRECTOT_sumOfSquare = 0;
        double TS_Sum = 0;
        double TS_sumOfSquare = 0;

//        double debugAllPETCTOT = 0;

        while((line = br.readLine()) != null)
        {
            //first line of data.csv is names of fields
            if(isFirstLine)
            {
                isFirstLine = false;
                continue;
            }

            couLine++;
            String[] fields = line.split(",");

//            System.out.println(fields[2].trim() + "    " + fields[11].trim());

            double itPRECTOT = Double.parseDouble(fields[2].trim());
            double itTS = Double.parseDouble(fields[11].trim());

            originalData.get("PRECTOT").add(itPRECTOT);
            originalData.get("TS").add(itTS);

//            debugAllPETCTOT += itPRECTOT;

            PRECTOT_Sum += itPRECTOT;
            PRECTOT_sumOfSquare += Math.pow(itPRECTOT, 2);
            TS_Sum += itTS;
            TS_sumOfSquare += Math.pow(itTS, 2);

            if(couLine % chunkSize == 0)
            {
                basicAggregates.get("PRECTOT_sum").add(PRECTOT_Sum);
                basicAggregates.get("PRECTOT_sumOfSquare").add(PRECTOT_sumOfSquare);
                basicAggregates.get("TS_sum").add(TS_Sum);
                basicAggregates.get("TS_sumOfSquare").add(TS_sumOfSquare);

                PRECTOT_Sum = 0;
                PRECTOT_sumOfSquare = 0;
                TS_Sum = 0;
                TS_sumOfSquare = 0;
            }
        }

//        System.out.println("debugAllPETCTOT: " + debugAllPETCTOT);

//        for(int i = 0; i < 12; i++)
//        {
//            System.out.println(
//                    basicAggregates.get("PRECTOT_mean").get(i) + " " +
//                    basicAggregates.get("PRECTOT_meanOfSquare").get(i) + "    " +
//                    basicAggregates.get("TS_mean").get(i) + " " +
//                    basicAggregates.get("TS_meanOfSquare").get(i));
//        }
    }

    /**
     * generate segment tree on every aggregate of every field
     */
    private static void generateSegmentTrees() {
        for(String itAggregate : basicAggregates.keySet())
        {
//            System.out.println("\n\n" + itAggregate);
            List<Double> chunks = basicAggregates.get(itAggregate);
            SegmentTree st = new SegmentTree(chunks);
            segmentTrees.put(itAggregate, st);

//            System.out.println("\nquery result 1: " + st.query(0, chunks.size() - 1));
//            System.out.println("\nquery result 2: " + st.query(0, (chunks.size() - 1) / 2));
//            System.out.println("\nquery result 3: " + st.query((chunks.size() - 1) / 2, chunks.size() - 1));
        }
    }

    public static void main(String[] args) throws IOException {
        List<Integer> chunksizeList = Arrays.asList(50, 200, 1000, 500000, 1443, 2885);
        for(int itChunksize : chunksizeList)
        {
            chunkSize = itChunksize;
            initializeData();
            generateSegmentTrees();
            Query.query();
        }
    }
}
