package two_pointers;

import common.Tuple;

import java.util.*;
import java.util.stream.Collectors;

public class TwoSumLoop {

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, ArrayList<Integer>> orignalIndex = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            Integer valueAtIndex = nums[i];
            ArrayList<Integer> existingBucket = orignalIndex.get(valueAtIndex);
            if(existingBucket != null){
                existingBucket.add(i);
            }else{
                ArrayList<Integer> newBucket = new ArrayList<>();
                newBucket.add(i);
                orignalIndex.put(valueAtIndex, newBucket);
            }

        }

        Arrays.sort(nums);
        int emptyResult[] = {};
        int i = 0;
        int y = nums.length - 1;
        while(i < nums.length && y >= 0){
            if(i == y){
                return emptyResult;
            }else{
                int sum = nums[i] + nums[y];
                if(sum > target){
                    y--;
                }else if (sum < target){
                    i++;
                }else {
                    if(nums[i] == nums[y]){
                        ArrayList<Integer> bucket = orignalIndex.get(nums[i]);
                        int result[] = {bucket.get(0), bucket.get(1)};
                        return result;
                    }else{
                        ArrayList<Integer> bucket = orignalIndex.get(nums[i]);
                        ArrayList<Integer> bucket2 = orignalIndex.get(nums[y]);
                        int result[] = {bucket.get(0), bucket2.get(0)};
                        return result;
                    }
                }
            }
        }
        return emptyResult;
    }
}