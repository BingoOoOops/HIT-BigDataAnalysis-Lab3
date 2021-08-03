/*
 * by LB
 * in Harbin Institute of Technology
 *
 * 2021 Spring Semester
 */

import java.util.List;

public class SegmentTree {
    private Double[] tree;
    private List<Double> chunks;
    private int num_nodeUse = 0;//the number of nodes used in the last query

    /**
     * build a segment tree using chunks
     *
     * @param chunks chunks of aggregate value
     */
    public SegmentTree(List<Double> chunks) {
        int num_chunks = chunks.size();
        tree = new Double[num_chunks * 4];
        this.chunks = chunks;
        generateTree(0, 0, num_chunks - 1);

//        int cou = 0;
//        int j = 1;
//        for(int i = 0; i < 7; i++)
//        {
//            cou++;
//            System.out.print(tree[i] + "  ");
//            if(cou % j == 0)
//            {
//                cou = 0;
//                j *= 2;
//                System.out.println();
//            }
//        }
    }

    /**
     * build a sub segment tree storing aggregate values of chunks[left ~ right] with currentNode as the root
     *
     * @param currentNode the root node of subtree
     * @param leftRange left range the subtree represent
     * @param rightRange right range the subtree represent
     */
    private void generateTree(int currentNode, int leftRange, int rightRange) {
        if(leftRange == rightRange)
        {
            tree[currentNode] = chunks.get(leftRange);
            return;
        }
        int mid = (leftRange + rightRange) / 2;
        int leftChild = 2 * currentNode + 1;
        int rightChild = 2 * currentNode + 2;

        generateTree(leftChild, leftRange, mid);
        generateTree(rightChild, mid + 1, rightRange);

        tree[currentNode] = tree[leftChild] + tree[rightChild];
    }

    /**get the sum of aggregate values of chunks[queryLeft ~ queryRight]
     *
     * @param queryLeft left range of query
     * @param queryRight right range of query
     * @return sum of aggregate values
     */
    public Double query(int queryLeft, int queryRight)
    {
        num_nodeUse = 0;
        if(!(0 <= queryLeft && queryLeft <= queryRight && queryRight <= chunks.size() - 1))
        {
            throw new IllegalArgumentException();
        }
        return query(0, 0, chunks.size() - 1, queryLeft, queryRight);
    }

    /**get the sum of aggregate values of chunks[start ~ end]
     *
     * @param currentNode root of sub segment tree
     * @param start left range the tree represent
     * @param end right range the tree represent
     * @param queryLeft left range of query
     * @param queryRight right range of query
     * @return sum of aggregate values
     */
    private Double query(int currentNode, int start, int end, int queryLeft, int queryRight)
    {
        if(start > queryRight || end < queryLeft)
        {
            return 0.;
        }
        if(queryLeft <= start && end <= queryRight)
        {
            num_nodeUse++;
            return tree[currentNode];
        }
        int mid = (start + end) / 2;
        int leftChild = 2 * currentNode + 1;
        int rightChild = 2 * currentNode + 2;

        Double sumLeft = query(leftChild, start, mid, queryLeft, queryRight);
        Double sumRight = query(rightChild, mid + 1, end, queryLeft, queryRight);
        return sumLeft + sumRight;
    }

    /**
     * Get I/O number of nodes in segment tree read in the last query.
     *
     * @return I/O number
     */
    public int get_IO_Number()
    {
        return num_nodeUse;
    }
}
