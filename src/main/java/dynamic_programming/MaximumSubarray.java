package dynamic_programming;

import java.util.Arrays;

public class MaximumSubarray {
    final private int MIN_INT = -2147483646;
    private int CACHE_SIZE;

    private int[] nums;
    private int size;

    private int[] maxRightAdjacentSubarrayCache;
    private int[] maxLeftAdjacentSubarrayCache;

    public int maxSubArray(int[] nums) {

        this.nums = nums;
        this.size = nums.length;

        CACHE_SIZE = size * (size + 1) / 2;

        maxRightAdjacentSubarrayCache = new int[CACHE_SIZE];
        maxLeftAdjacentSubarrayCache = new int[CACHE_SIZE];

        Arrays.fill(maxRightAdjacentSubarrayCache, MIN_INT);
        Arrays.fill(maxLeftAdjacentSubarrayCache, MIN_INT);


        int result = findMaxSubarray();
        return result;
    }

    private int findMaxSubarray() {
        int max = 0;
        for (int i = 0; i < size; i++) {
            int maxRightAdjacentSubarray = i + 1 <= size - 1 ? maxRightAdjacentSubarrayCached(i + 1, size - 1) : MIN_INT;
            int maxLeftAdjacentSubarray = 0 <= i - 1 ? maxLeftAdjacentSubarrayCached(0, i - 1) : MIN_INT;
            max = Math.max(max, Math.max(nums[i], Math.max(maxRightAdjacentSubarray + nums[i], maxLeftAdjacentSubarray + nums[i])));
        }
        return max;
    }


    private int maxRightAdjacentSubarrayCached(int left, int right) {
        checkArgument(left <= right, "Left should be less or equal to right.");
        checkArgument(left >= 0, "Left should big bigger then 0 index.");
        checkArgument(right < size, "Right should be smaller the nums array size");

        int cacheResult = maxRightAdjacentSubarrayCache[indexInCache(left, right)];
        int result = cacheResult;
        if (cacheResult == MIN_INT) {
            result = maxRightAdjacentSubarray(left, right);
            maxRightAdjacentSubarrayCache[indexInCache(left, right)] = result;
        }
        return result;
    }

    private int maxRightAdjacentSubarray(int left, int right) {
        checkArgument(left <= right, "Left should be less or equal to right.");
        checkArgument(left >= 0, "Left should big bigger then 0 index.");
        checkArgument(right < size, "Right should be smaller the nums array size");

        int result = MIN_INT;
        if (right > left) {
            int maxRightAdjacentSubarray = maxLeftAdjacentSubarray(left, right - 1);
            result = Math.max(maxRightAdjacentSubarray + nums[right], maxRightAdjacentSubarray);
        } else {
            result = nums[right];
        }
        return result;
    }

    private int maxLeftAdjacentSubarrayCached(int left, int right) {
        checkArgument(left <= right, "Left should be less or equal to right.");
        checkArgument(left >= 0, "Left should big bigger then 0 index.");
        checkArgument(right < size, "Right should be smaller the nums array size");

        int cacheResult = maxLeftAdjacentSubarrayCache[indexInCache(left, right)];
        int result = cacheResult;
        if (cacheResult == MIN_INT) {
            result = maxLeftAdjacentSubarray(left, right);
            maxLeftAdjacentSubarrayCache[indexInCache(left, right)] = result;
        }
        return result;
    }

    private int maxLeftAdjacentSubarray(int left, int right) {
        checkArgument(left <= right, "Left should be less or equal to right.");
        checkArgument(left >= 0, "Left should big bigger then 0 index.");
        checkArgument(right < size, "Right should be smaller the nums array size");

        int result = MIN_INT;
        if (left < right) {
            int maxLeftAdjacentSubarray = maxLeftAdjacentSubarray(left + 1, right);
            result = Math.max(nums[left] + maxLeftAdjacentSubarray, maxLeftAdjacentSubarray);
        } else if (left == right) {
            result = nums[left];
        }
        return result;
    }

    private int indexInCache(int left, int right) {
        checkArgument(right < size, "Right should be smaller the nums array size");
        checkArgument(left < size, "Left should be smaller the nums array size");

        return right * size - ((right - 1) * (right) / 2) + left - right;
    }

    private void checkArgument(boolean condition, String msg) {
        if (!condition) {
            throw new IllegalArgumentException(msg);
        }
    }
}
