package tarjans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CriticalConnectionsNetworkClean {

    public ArrayList<Integer>[] graph;
    public List<List<Integer>> res;
    int[] visitedTimes;
    int[] lowTimes;
    int time;

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {

        buildList(n, connections);

        visitedTimes = new int[n];
        lowTimes = new int[n];

        res = new ArrayList<>();

        boolean[] visited = new boolean[n];

        time = 0;

        dfsOnGraph(visited, 0, -1);

        return res;
    }

    private void dfsOnGraph(boolean[] visited, int currNode, int parentNode) {

        visited[currNode] = true;

        visitedTimes[currNode] = time;
        lowTimes[currNode] = time;
        time++;

        for (int neig : graph[currNode]) {
            if (neig == parentNode) continue;
            if (!visited[neig]) {
                dfsOnGraph(visited, neig, currNode);
                lowTimes[currNode] = Math.min(lowTimes[currNode], lowTimes[neig]);
                if (visitedTimes[currNode] < lowTimes[neig]) {
                    res.add(Arrays.asList(currNode, neig));
                }
            } else {
                lowTimes[currNode] = Math.min(lowTimes[currNode], visitedTimes[neig]);
            }
        }


    }

    private void buildList(int noOfNodes, List<List<Integer>> connections) {

        graph = new ArrayList[noOfNodes];

        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        for (List<Integer> conn : connections) {

            int fNode = conn.get(0);
            int sNode = conn.get(1);

            graph[fNode].add(sNode);
            graph[sNode].add(fNode);

        }
    }
}
