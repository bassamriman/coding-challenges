package twopointers;

import common.Tuple;

import java.util.*;
import java.util.stream.Collectors;

public class TwoSum {
    public static void main(String[] args) {
        System.out.println("Hello");
        System.out.println(findTwoSum(Arrays.asList(-4, -1, -1, -1, 1, 1, 2), 0));
    }

    public static Set<Tuple<Integer, Integer>> findTwoSum(List<Integer> list, Integer targetSum) {
        List<Integer> sortedList = list.stream().sorted().collect(Collectors.toList());
        System.out.println(sortedList);
        return new HashSet<>(findTwoSumTail(0, sortedList.size() - 1, sortedList, targetSum));
    }

    public static List<Tuple<Integer, Integer>> findTwoSumTail(Integer startingLeftPointer,
                                                               Integer startingRightPointer,
                                                               List<Integer> sortedList,
                                                               Integer targetSum) {

        Queue<Tuple<Integer, Integer>> q = new LinkedList<>();
        q.add(Tuple.a(startingLeftPointer, startingRightPointer));
        List<Tuple<Integer, Integer>> result = new ArrayList<>();

        while (!q.isEmpty()) {
            Tuple<Integer, Integer> coordinates = q.remove();
            Integer leftPointer = coordinates.left();
            Integer rightPointer = coordinates.right();

            if (leftPointer.equals(rightPointer)) {
                break;
            } else {
                System.out.println("leftPointer: " + leftPointer);
                System.out.println("rightPointer: " + rightPointer);
                Integer leftPointerValue = sortedList.get(leftPointer);
                Integer rightPointerValue = sortedList.get(rightPointer);
                Integer sum = leftPointerValue + rightPointerValue;
                System.out.println("sum: " + sum);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
                if (sum.equals(targetSum)) {
                    result.add(Tuple.a(leftPointer, rightPointer));
                    q.add(Tuple.a(leftPointer + 1, rightPointer));
                    q.add(Tuple.a(leftPointer, rightPointer - 1));
                } else if (sum < targetSum) {
                    q.add(Tuple.a(leftPointer + 1, rightPointer));
                } else {
                    q.add(Tuple.a(leftPointer, rightPointer - 1));
                }
            }
        }
        return result;
    }
}