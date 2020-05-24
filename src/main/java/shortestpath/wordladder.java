package shortestpath;
import java.util.*;

public class wordladder {
    private Map<Long, HashSet<Long>> adjacencyMatrix;
    int wordSize;

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        adjacencyMatrix = new HashMap<>();

        int allWordsSize = wordList.size() + 1;
        long[] allWords = new long[allWordsSize];
        int allWordsIndex = 0;
        for (String word : wordList) {
            allWords[allWordsIndex] = hash(word);
            allWordsIndex++;
        }

        wordSize = beginWord.length();
        for (int i = 0; i < allWordsSize; i++) {
            long firstWord = allWords[i];
            for (int k = i + 1; k < allWordsSize; k++) {
                long secondWord = allWords[k];
                if (distance(firstWord, secondWord, wordSize) <= 1) {
                    HashSet<Long> firstWordHashSet = adjacencyMatrix.getOrDefault(firstWord, new HashSet<Long>());
                    firstWordHashSet.add(secondWord);
                    adjacencyMatrix.put(firstWord, firstWordHashSet);

                    HashSet<Long> secondWordHashSet = adjacencyMatrix.getOrDefault(secondWord, new HashSet<Long>());
                    secondWordHashSet.add(firstWord);
                    adjacencyMatrix.put(secondWord, secondWordHashSet);
                }
            }
        }

        long firstWordAlias = Long.MAX_VALUE;
        long firstWord = hash(beginWord);
        for (int k = 0; k < allWordsSize; k++) {
            long secondWord = allWords[k];
            if (distance(firstWord, secondWord, wordSize) == 1) {
                HashSet<Long> firstWordHashSet = adjacencyMatrix.getOrDefault(firstWordAlias, new HashSet<Long>());
                firstWordHashSet.add(secondWord);
                adjacencyMatrix.put(firstWordAlias, firstWordHashSet);
            }
        }

        int result = bfs(firstWordAlias, hash(endWord));

        return result == Integer.MAX_VALUE ? 0 : result;
    }

    private int bfs(long startNode, long expectedNode) {
        Queue<Pair<Long, Integer>> toVisit = new LinkedList<>();
        Set<Long> visited = new HashSet<>();
        toVisit.add(new Pair(startNode, 1));
        while (!toVisit.isEmpty()) {
            Pair<Long, Integer> currentWordPair = toVisit.remove();
            Long currentWord = currentWordPair.getKey();
            int currendDepth = currentWordPair.getValue();
            visited.add(currentWord);
            Set<Long> children = adjacencyMatrix.getOrDefault(currentWord, new HashSet<Long>());
            if (expectedNode == currentWord) {
                return currendDepth;
            }

            for (Long child : children) {
                if (!visited.contains(child)) {
                    toVisit.add(new Pair(child, currendDepth + 1));
                }
            }
        }
        return 0;
    }

    private int distance(long hashWord1, long hashWord2, int wordSize) {
        if (hashWord1 == 3720324 && hashWord2 == 3720649) {
            int a = 1;
        }
        int diff = 0;
        for (int i = 0; i < wordSize; i++) {
            short unpackkthCharWord1 = unpack(i, hashWord1);
            short unpackkthCharWord2 = unpack(i, hashWord2);
            if (unpackkthCharWord1 != unpackkthCharWord2) diff++;
        }
        return diff;
    }

    private long hash(String word) {
        int size = word.length();

        long hash = 0;
        short kth = 0;

        for (char t : word.toCharArray()) {
            hash = hash + pack(compress(t), kth);
            kth++;
        }

        return hash;
    }

    private long pack(short compressedChar, short kth) {
        return ((long) compressedChar) * (long) Math.pow(26, kth);
    }

    private short unpack(int kth, long hash) {
        return (short) ((hash / (long) Math.pow(26, kth)) % 26);
    }

    private short compress(char uncompressedAlphabeticChar) {
        return (short) (uncompressedAlphabeticChar - 97);
    }

    private char uncompress(short compressedAlphabeticChar) {
        return (char) (compressedAlphabeticChar + 97);
    }

}
