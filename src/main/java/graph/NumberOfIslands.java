package graph;

import java.util.LinkedList;
import java.util.Queue;

public class NumberOfIslands {
    private char[][] map;
    private int width;
    private int height;
    private int totalSize;

    public int numIslands(char[][] grid) {
        if (grid.length < 1) return 0;
        if (grid[0].length < 1) return 0;

        map = grid;
        height = map.length;
        width = map[0].length;
        totalSize = width * height;

        int islandCount = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int packedCoord = packCoord(x, y);
                if (isLand(packedCoord)) {
                    ++islandCount;
                    bfs(packedCoord);
                }
            }
        }
        return islandCount;
    }

    private void bfs(int startCoord) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startCoord);
        while (!queue.isEmpty()) {

            int maybeLand = queue.remove();

            if (isLand(maybeLand)) {

                explore(maybeLand);

                int topNeighbor = topNeighbor(maybeLand);
                int botNeighbor = botNeighbor(maybeLand);
                int leftNeighbor = leftNeighbor(maybeLand);
                int rightNeighbor = rightNeighbor(maybeLand);

                if (topNeighbor >= 0) queue.add(topNeighbor);
                if (botNeighbor >= 0) queue.add(botNeighbor);
                if (leftNeighbor >= 0) queue.add(leftNeighbor);
                if (rightNeighbor >= 0) queue.add(rightNeighbor);
            }
        }
    }

    private void explore(int packedLandCoord) {
        int x = unpackX(packedLandCoord);
        int y = unpackY(packedLandCoord);
        map[y][x] = '0';
    }

    private boolean isLand(int packedCoord) {
        return map[unpackY(packedCoord)][unpackX(packedCoord)] == '1';
    }

    private int topNeighbor(int packedCoord) {
        int newPackedCoord = packedCoord - width;
        return newPackedCoord >= 0 ? newPackedCoord : -1;
    }

    private int botNeighbor(int packedCoord) {
        int newPackedCoord = packedCoord + width;
        return newPackedCoord < totalSize ? newPackedCoord : -1;
    }

    private int leftNeighbor(int packedCoord) {
        int x = unpackX(packedCoord) - 1;
        return x >= 0 ? packCoord(x, unpackY(packedCoord)) : -1;
    }

    private int rightNeighbor(int packedCoord) {
        int x = unpackX(packedCoord) + 1;
        return x < width ? packCoord(x, unpackY(packedCoord)) : -1;
    }

    private int packCoord(int x, int y) {
        return x + y * width;
    }

    private int unpackX(int packedCoord) {
        return packedCoord % width;
    }

    private int unpackY(int packedCoord) {
        return packedCoord / width;
    }
}
