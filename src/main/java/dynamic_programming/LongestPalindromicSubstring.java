package dynamic_programming;

import java.util.Arrays;

public class LongestPalindromicSubstring {
    char[] sChars;
    int[] subStringCache;
    int size;

    public String longestPalindrome(String s) {
        sChars = s.toCharArray();
        size = sChars.length;
        initCache();
        int result = longestPalindromeRecCached(0, size - 1);
        if (result != -1) return toString(unpackToStart(result), unpackToEnd(result));
        return "";
    }

    private int longestPalindromeRecCached(int start, int end) {
        int result;
        if (size == 0 || start > end) {
            result = -1;
        } else {
            int compressedKey = compressToKey(pack(start, end));
            subStringCache[compressedKey] =
                    subStringCache[compressedKey] != -1 ? subStringCache[compressedKey] : longestPalindromeRec(start, end);
            result = subStringCache[compressedKey];
        }
        return result;
    }

    private int longestPalindromeRec(int start, int end) {
        int result;
        if (size == 0 || start > end) {
            result = -1;
        } else {
            if (isPalindrome(start, end)) {
                result = pack(start, end);
            } else {
                int lss = longestPalindromeRecCached(start, end - 1);
                int rss = longestPalindromeRecCached(start + 1, end);
                if (lss != -1 && rss != -1) {
                    result = length(lss) > length(rss) ? lss : rss;
                } else if (lss == -1 && rss != -1) {
                    result = rss;
                } else result = lss;
            }
        }
        return result;
    }

    private boolean isPalindrome(int start, int end) {
        if (size == 0) return false;
        if (start > end) return false;

        int startIndex = start;
        int endIndex = end;

        while (startIndex <= endIndex) {
            if (sChars[startIndex] != sChars[endIndex]) {
                return false;
            }
            ++startIndex;
            --endIndex;
        }

        return true;
    }

    private String toString(int start, int end) {
        return new String(Arrays.copyOfRange(sChars, start, end + 1)); //Highlight
    }

    private void initCache() {
        int nOfSubstring = size * (size + 1) / 2;
        subStringCache = new int[nOfSubstring];
        Arrays.fill(subStringCache, -1); //Highlight
    }

    private int compressToKey(int pack) {
        int start = unpackToStart(pack);
        int end = unpackToEnd(pack);
        return start * size - (start * (start - 1)) / 2 + end - start; //Highlight
    }

    private int pack(int start, int end) {
        return start * size + end;
    }

    private int unpackToStart(int key) {
        return key / size; //Highlight
    }

    private int unpackToEnd(int key) {
        return key % size; //Highlight
    }

    private int length(int key) {
        return unpackToEnd(key) - unpackToStart(key) + 1;
    }
}
