package PROJECT;

import java.util.*;

public class DijkstraRouting {
    static class Edge {
        int to, weight;
        Edge(int t, int w) { to = t; weight = w; }
    }

    public static void dijkstra(int start, List<List<Edge>> graph, int[] dist, int[] prev) {
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0], d = curr[1];

            if (d > dist[node]) continue;

            for (Edge edge : graph.get(node)) {
                int next = edge.to, weight = edge.weight;
                if (dist[next] > dist[node] + weight) {
                    dist[next] = dist[node] + weight;
                    prev[next] = node;
                    pq.offer(new int[]{next, dist[next]});
                }
            }
        }
    }

    public static List<Integer> getPath(int target, int[] prev) {
        List<Integer> path = new ArrayList<>();
        for (int at = target; at != -1; at = prev[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        int V = 6; // School + 5 villages
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < V; i++) graph.add(new ArrayList<>());

        // Sample village graph
        graph.get(0).add(new Edge(1, 4)); graph.get(1).add(new Edge(0, 4));
        graph.get(0).add(new Edge(2, 2)); graph.get(2).add(new Edge(0, 2));
        graph.get(1).add(new Edge(3, 3)); graph.get(3).add(new Edge(1, 3));
        graph.get(2).add(new Edge(3, 1)); graph.get(3).add(new Edge(2, 1));
        graph.get(2).add(new Edge(4, 7)); graph.get(4).add(new Edge(2, 7));
        graph.get(3).add(new Edge(5, 2)); graph.get(5).add(new Edge(3, 2));

        int[] dist = new int[V];
        int[] prev = new int[V];

        // 🔹 Print direct connections from school
        System.out.println("Direct distances from School to each Village:");
        for (int i = 1; i <= 5; i++) {
            boolean directFound = false;
            for (Edge edge : graph.get(0)) {
                if (edge.to == i) {
                    System.out.println("School -> Village " + i + ": " + edge.weight + " km");
                    directFound = true;
                    break;
                }
            }
            if (!directFound) {
                System.out.println("School -> Village " + i + ": No direct connection");
            }
        }

        System.out.println("\nRunning Dijkstra's Algorithm...\n");

        dijkstra(0, graph, dist, prev);

        System.out.println("Shortest distance and path from school to each village:");
        for (int i = 1; i < V; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                System.out.println("Village " + i + " is unreachable from school.");
            } else {
                List<Integer> path = getPath(i, prev);
                System.out.print("Village " + i + ": " + dist[i] + " km, Path: ");
                for (int j = 0; j < path.size(); j++) {
                    if (j > 0) System.out.print(" -> ");
                    System.out.print(path.get(j) == 0 ? "School" : "Village " + path.get(j));
                }
                System.out.println();
            }
        }
    }
}