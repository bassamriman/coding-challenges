package twopointers;

import common.Triple;
import common.Tuple;

import java.util.*;
import java.util.stream.Collectors;

public class ThreeSum {
    public static void main(String[] args) {
        System.out.println("Hello");
        System.out.println(findThreeSum(Arrays.asList(-3, -1, -1, -1, 1, 1, 2), 0));
    }

    public static Set<Triple<Integer, Integer, Integer>> findThreeSum(List<Integer> list, Integer targetSum) {
        List<Integer> sortedList = list.stream().sorted().collect(Collectors.toList());
        System.out.println(sortedList);
        return new HashSet<>(findThreeSumTail(sortedList, targetSum));
    }

    public static List<Triple<Integer, Integer, Integer>> findThreeSumTail(List<Integer> sortedList,
                                                                           Integer targetSum) {

        Queue<Tuple<Integer, Integer>> q = new LinkedList<>();
        List<Triple<Integer, Integer, Integer>> result = new ArrayList<>();
        for (int firstPointer = 0; firstPointer < sortedList.size(); firstPointer++) {
            Integer startingSecondPointer = Math.min(firstPointer + 1, sortedList.size() - 1);
            Integer startingThirdPointer = sortedList.size() - 1;
            q.add(Tuple.a(startingSecondPointer, startingThirdPointer));

            while (!q.isEmpty()) {
                System.out.println("q: " + q);
                Tuple<Integer, Integer> coordinates = q.remove();
                Integer secondPointer = coordinates.left();
                Integer thirdPointer = coordinates.right();
                System.out.println("firstPointer: " + firstPointer);
                System.out.println("secondPointer: " + secondPointer);
                System.out.println("thirdPointer: " + thirdPointer);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
                if (!secondPointer.equals(thirdPointer)) {
                    Integer firstPointerValue = sortedList.get(firstPointer);
                    Integer secondPointerValue = sortedList.get(secondPointer);
                    Integer thirdPointerValue = sortedList.get(thirdPointer);
                    Integer sum = firstPointerValue + secondPointerValue + thirdPointerValue;
                    System.out.println("sum: " + sum);
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
                    if (sum.equals(targetSum)) {
                        result.add(Triple.a(firstPointer, secondPointer, thirdPointer));
                        q.add(Tuple.a(secondPointer + 1, thirdPointer));
                        q.add(Tuple.a(secondPointer, thirdPointer - 1));
                    } else if (sum < targetSum) {
                        q.add(Tuple.a(secondPointer + 1, thirdPointer));
                    } else {
                        q.add(Tuple.a(secondPointer, thirdPointer - 1));
                    }
                }
            }
        }

        return result;
    }
}