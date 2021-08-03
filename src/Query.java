/*
 * by LB
 * in Harbin Institute of Technology
 *
 * 2021 Spring Semester
 */

import java.util.List;

public class Query {

    private static int IO;

    public static void query()
    {
        System.out.println("\n\nchunk size: " + Lab3.chunkSize);
        double res;
        int Cost_DirectlyScan;
        for(int i = 1; i <= 20; i++)
        {
            switch (i)
            {
                case 1:
                    res = getMean("PRECTOT", 3000, 5000);
                    Cost_DirectlyScan = Cost_DirectlyScan(3000, 5000);
                    break;
                case 2:
                    res = getVariance("PRECTOT", 52000, 55000);
                    Cost_DirectlyScan = Cost_DirectlyScan(52000, 55000);
                    break;
                case 3:
                    res = getMean("TS", 120550, 125555);
                    Cost_DirectlyScan = Cost_DirectlyScan(120550, 125555);
                    break;
                case 4:
                    res = getVariance("TS", 789000, 800000);
                    Cost_DirectlyScan = Cost_DirectlyScan(789000, 800000);
                    break;
                case 5:
                    res = getMean("PRECTOT", 10000, 500000);
                    Cost_DirectlyScan = Cost_DirectlyScan(10000, 500000);
                    break;
                case 6:
                    res = getVariance("PRECTOT", 122000, 780000);
                    Cost_DirectlyScan = Cost_DirectlyScan(122000, 780000);
                    break;
                case 7:
                    res = getMean("TS", 207830, 605600);
                    Cost_DirectlyScan = Cost_DirectlyScan(207830, 605600);
                    break;
                case 8:
                    res = getVariance("TS", 170000, 950000);
                    Cost_DirectlyScan = Cost_DirectlyScan(170000, 950000);
                    break;
                case 9:
                    res = getMean("PRECTOT", 352000, 425000);
                    Cost_DirectlyScan = Cost_DirectlyScan(352000, 425000);
                    break;
                case 10:
                    res = getVariance("TS", 480000, 657900);
                    Cost_DirectlyScan = Cost_DirectlyScan(480000, 657900);
                    break;
                case 11:
                    res = getMean("PRECTOT", 10322, 56800);
                    Cost_DirectlyScan = Cost_DirectlyScan(10322, 56800);
                    break;
                case 12:
                    res = getVariance("PRECTOT", 35, 50);
                    Cost_DirectlyScan = Cost_DirectlyScan(35, 50);
                    break;
                case 13:
                    res = getMean("TS", 120, 130);
                    Cost_DirectlyScan = Cost_DirectlyScan(120, 130);
                    break;
                case 14:
                    res = getVariance("TS", 1, 1048576);
                    Cost_DirectlyScan = Cost_DirectlyScan(1, 1048576);
                    break;
                case 15:
                    res = getMean("PRECTOT", 897500, 899000);
                    Cost_DirectlyScan = Cost_DirectlyScan(897500, 899000);
                    break;
                case 16:
                    res = getVariance("PRECTOT", 89000, 1000000);
                    Cost_DirectlyScan = Cost_DirectlyScan(89000, 1000000);
                    break;
                case 17:
                    res = getMean("TS", 37000, 370000);
                    Cost_DirectlyScan = Cost_DirectlyScan(37000, 370000);
                    break;
                case 18:
                    res = getVariance("TS", 9900, 10500);
                    Cost_DirectlyScan = Cost_DirectlyScan(9900, 10500);
                    break;
                case 19:
                    res = getMean("PRECTOT", 896000, 900000);
                    Cost_DirectlyScan = Cost_DirectlyScan(896000, 900000);
                    break;
                case 20:
                    res = getVariance("TS", 15, 3750);
                    Cost_DirectlyScan = Cost_DirectlyScan(15, 3750);
                    break;
                default:
                    throw new RuntimeException();
            }
//            System.out.println("query result " + i + ": " + res);
            System.out.print("query " + i + "    IO number: " + IO + "    directly scan I/O number: " + Cost_DirectlyScan);
            if(Cost_DirectlyScan < IO)
            {
                System.out.print("    better to scan directly");
            }
            System.out.println();
        }
    }




    /**
     * Calculate the I/O number of the query in [recordLeft, recordRight]
     *
     * @param recordLeft left range of the query
     * @param recordRight right range of the query
     * @return the I/O number
     */
    private static int Cost_DirectlyScan(int recordLeft, int recordRight)
    {
        return (int)Math.ceil((double)(recordRight - recordLeft + 1) / Lab3.memorySize);
    }


    /**
     * check if the field is legal
     *
     * @param field the field to check
     */
    private static void checkArgument(String field)
    {
        if(!field.equals("PRECTOT") && !field.equals("TS"))
            throw new IllegalArgumentException("field need to be \"PRECTOT\" or \"TS\"");
    }

    /**get mean of fields between [recordLeft, recordRight]
     *
     * @param field target field
     * @param recordLeft left range of query
     * @param recordRight right range of query
     * @return final mean
     */
    public static double getMean(String field, int recordLeft, int recordRight)
    {
        checkArgument(field);
        IO = 0;

        String aggregateMean;
        if(field.equals("PRECTOT"))
        {
            aggregateMean = "PRECTOT_sum";
        }
        else {
            aggregateMean = "TS_sum";
        }
        return getMeanOfField(field, aggregateMean, recordLeft, recordRight);
    }


    /**get variance of fields between [recordLeft, recordRight]
     *
     * @param field target field
     * @param recordLeft left range of query
     * @param recordRight right range of query
     * @return final variance
     */
    public static double getVariance(String field, int recordLeft, int recordRight)
    {
        checkArgument(field);
        IO = 0;

        String aggregateMean, aggregateSquareMean;
        if(field.equals("PRECTOT"))
        {
            aggregateMean = "PRECTOT_sum";
            aggregateSquareMean = "PRECTOT_sumOfSquare";
        }
        else {
            aggregateMean = "TS_sum";
            aggregateSquareMean = "TS_sumOfSquare";
        }
        double mean = getMeanOfField(field, aggregateMean, recordLeft, recordRight);
        double meanOfSquare = getMeanOfField(field, aggregateSquareMean, recordLeft, recordRight);

//        System.out.println("\nmean = " + mean);
//        System.out.println("mean of square = " + meanOfSquare);

        return meanOfSquare - Math.pow(mean, 2);
    }


    /**Get mean of field between recordLeft and recordRight
     *
     * @param field target field
     * @param aggregateName indicate the way to operate fields
     * @param recordLeft left range of query
     * @param recordRight right range of query
     * @return aggregate value, mean of fields or mean of square of fields
     */
    public static double getMeanOfField(String field, String aggregateName, int recordLeft, int recordRight)
    {
//        checkArgument(field);
//
//        IO = 0;
        int chunkLeft = recordLeft / Lab3.chunkSize + 1;
        int chunkRight = recordRight / Lab3.chunkSize - 1;

//        String aggregateName;
//        if(field.equals("PRECTOT"))
//        {
//            aggregateName = "PRECTOT_sum";
//        }
//        else {
//            aggregateName = "TS_sum";
//        }

        double sum = 0;
        if(chunkLeft <= chunkRight)
        {
            //get query result from segment tree
            SegmentTree segmentTree = Lab3.segmentTrees.get(aggregateName);
            sum = segmentTree.query(chunkLeft, chunkRight);

//            System.out.println("from tree: sum = " + sum);

            //scan original data directly to get the result of residual range part
            sum += scanOriginalData(field, aggregateName, recordLeft,  chunkLeft * Lab3.chunkSize);
            sum += scanOriginalData(field, aggregateName, chunkRight * Lab3.chunkSize + 1, recordRight + 1);

//            System.out.println("add residual part: sum = " + sum);

            //count the I/O number in this query
            IO = segmentTree.get_IO_Number();
            IO += (int)Math.ceil((double)(chunkLeft * Lab3.chunkSize - recordLeft) / Lab3.memorySize);
            IO += (int)Math.ceil((double)(recordRight - (chunkRight + 1) * Lab3.chunkSize + 1) / Lab3.memorySize);
        }
        //query range does not contain any complete chunk
        else {
            IO = (int)Math.ceil((double)(recordRight - recordLeft + 1) / Lab3.memorySize);
            sum += scanOriginalData(field, aggregateName, recordLeft,  recordRight + 1);
//            for(int i = recordLeft; i <= recordRight; i++)
//            {
//                sum += originalData.get(i);
//            }
        }
//        System.out.println("final: sum = " + sum);
//        System.out.println();
        return sum / (recordRight - recordLeft + 1);
    }


    /**Directly scan original data to get sum or sum of square of field of fields in [recordLeft, recordRight)
     *
     * @param field target field
     * @param aggregateName indicate the way to operate fields
     * @param recordLeft left range of query
     * @param recordRight right range of query
     * @return aggregate value, sum of fields or sum of square of fields
     */
    private static double scanOriginalData(String field, String aggregateName, int recordLeft, int recordRight)
    {
        double sum = 0;
        List<Double> originalData = Lab3.originalData.get(field);
        if(aggregateName.equals("PRECTOT_sum") || aggregateName.equals("TS_sum"))
        {
            for(int i = recordLeft; i < recordRight; i++)
            {
                sum += originalData.get(i);
            }
        }
        else {
            for(int i = recordLeft; i < recordRight; i++)
            {
//                System.out.println("originalData.get(" + i + ") = " + originalData.get(i));
                sum += Math.pow(originalData.get(i), 2);
//                System.out.println("sum = " + sum);
            }
        }
        return sum;
    }
}

