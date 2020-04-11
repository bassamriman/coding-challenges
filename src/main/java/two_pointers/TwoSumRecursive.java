package two_pointers;

import common.Tuple;

import java.util.*;
import java.util.stream.Collectors;

public class TwoSumRecursive {
    public static void main(String[] args) {
        System.out.println("Hello");
        System.out.println(findTwoSum(Arrays.asList(-4, -1, -1, -1, 1, 1, 2), 0));
    }

    public static Set<Tuple<Integer, Integer>> findTwoSum(List<Integer> list, Integer targetSum) {
        List<Integer> sortedList = list.stream().sorted().collect(Collectors.toList());
        Integer leftPointer = 0;
        Integer rightPointer = list.size() - 1;
        System.out.println(sortedList);
        return new HashSet<>(findTwoSumTail(leftPointer, rightPointer, sortedList, targetSum));
    }

    public static List<Tuple<Integer, Integer>> findTwoSumTail(Integer leftPointer,
                                                               Integer rightPointer,
                                                               List<Integer> sortedList,
                                                               Integer targetSum) {
        if (leftPointer.equals(rightPointer)) {
            return new ArrayList<>();
        } else {
            System.out.println("leftPointer: " + leftPointer);
            System.out.println("rightPointer: " + rightPointer);
            Integer leftPointerValue = sortedList.get(leftPointer);
            Integer rightPointerValue = sortedList.get(rightPointer);
            Integer sum = leftPointerValue + rightPointerValue;
            System.out.println("sum: " + sum);
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
            if (sum.equals(targetSum)) {
                List<Tuple<Integer, Integer>> newResult = new ArrayList<>();
                newResult.add(Tuple.a(leftPointer, rightPointer));
                newResult.addAll(findTwoSumTail(leftPointer + 1, rightPointer, sortedList, targetSum));
                newResult.addAll(findTwoSumTail(leftPointer, rightPointer - 1, sortedList, targetSum));
                return newResult;
            } else if (sum < targetSum) {
                return findTwoSumTail(leftPointer + 1, rightPointer, sortedList, targetSum);
            } else {
                return findTwoSumTail(leftPointer, rightPointer - 1, sortedList, targetSum);
            }
        }
    }
}



/*
[-1, 0, 1, 2, -1, -4]


[-4, -1, -1, 0, 1, 2]
 /\                |
 sum = -2

[-4, -1, -1, 0, 1, 2]
     /\           |
 sum = 1

 [-4, -1, -1, 0, 1, 2]
      /\        |
 sum = 0
-------------------------------- 1
 [-4, -1, -1, 0, 1, 2]
          /\     |
 sum = 0
 ------------------------ 2
 [-4, -1, -1, 0, 1, 2]
             /\  |
 sum = 1

 [-4, -1, -1, 0, 1, 2]
             /|\
 sum = 0
 ------------------------ 2
  [-4, -1, -1, 0, 1, 2]
           /\  |
 sum = -1

 [-4, -1, -1, 0, 1, 2]
            /|\
 sum = 0

 -------------------------------- 1
 [-4, -1, -1, 0, 1, 2]
      /\      |
 sum = -1

[-4, -1, -1, 0, 1, 2]
         /\  |
 sum = -1

[-4, -1, -1, 0, 1, 2]
            /|\
 sum = 0




 [-4, -1, -1, -1 ,1, 1, 2]
      /\             |
 sum = 0


  [-4, -1, -1, -1 ,1, 1, 2]
           /\         |
 sum = 0

   [-4, -1, -1, -1 ,1, 1, 2]
                /\     |
 sum = 0



  [-4, -1, -1, -1 ,1, 1, 2]
        /\            |
 sum = 0


  [-4, -1, -1, -1 ,1, 1, 2]
       /\         |
 sum = 0

   [-4, -1, -1, -1 ,1, 1, 2]
                /\     |
 sum = 0
*/
