package PROJECT;

import java.util.*;

public class GreedyRouting {
    static class Village {
        int id, students;
        Village(int id, int students) {
            this.id = id;
            this.students = students;
        }
    }

    public static void greedyRoute(int[][] dist, Village[] villages, int busCapacity) {
        int n = dist.length;
        boolean[] visited = new boolean[n];
        visited[0] = true;

        int totalStudents = 0;
        for (int i = 1; i < n; i++) {
            totalStudents += villages[i].students;
        }

        System.out.println("Total number of students to be picked up: " + totalStudents + "\n");

        int totalDistance = 0;
        int current = 0;
        int load = 0;
        int trips = 0;
        List<Integer> route = new ArrayList<>();
        route.add(0);

        while (true) {
            int nearest = -1;
            int minDist = Integer.MAX_VALUE;

            for (int i = 1; i < n; i++) {
                if (!visited[i] && dist[current][i] < minDist && load + villages[i].students <= busCapacity) {
                    nearest = i;
                    minDist = dist[current][i];
                }
            }

            if (nearest == -1) {
                if (current != 0) {
                    totalDistance += dist[current][0];
                    route.add(0);
                    System.out.println("Bus " + (++trips) + " route: " + formatRoute(route));
                    System.out.println("  Students picked up: " + load + ", Distance: " + getTripDistance(route, dist) + " km\n");
                    route.clear();
                    route.add(0);
                    current = 0;
                    load = 0;
                }

                boolean allVisited = true;
                for (int i = 1; i < n; i++) {
                    if (!visited[i]) {
                        allVisited = false;
                        break;
                    }
                }

                if (allVisited) break;
                else continue;
            }

            totalDistance += dist[current][nearest];
            route.add(nearest);
            load += villages[nearest].students;
            visited[nearest] = true;
            current = nearest;
        }

        if (current != 0) {
            totalDistance += dist[current][0];
            route.add(0);
            System.out.println("Bus " + (++trips) + " route: " + formatRoute(route));
            System.out.println("  Students picked up: " + load + ", Distance: " + getTripDistance(route, dist) + " km\n");
        }

        System.out.println("All students picked up in " + trips + " trips.");
        System.out.println("Total distance traveled: " + totalDistance + " km.");
    }

    static String formatRoute(List<Integer> route) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < route.size(); i++) {
            if (i > 0) sb.append(" -> ");
            sb.append(route.get(i) == 0 ? "School" : "Village " + route.get(i));
        }
        return sb.toString();
    }

    static int getTripDistance(List<Integer> route, int[][] dist) {
        int total = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            total += dist[route.get(i)][route.get(i + 1)];
        }
        return total;
    }

    public static void main(String[] args) {
        int INF = 10;

        int[][] dist = {
            {0,   4,   2, INF, INF, INF},
            {4,   0, INF,   3, INF, INF},
            {2, INF,   0,   1,   7, INF},
            {INF, 3,   1,   0, INF,   2},
            {INF, INF, 7, INF,   0, INF},
            {INF, INF, INF, 2, INF,   0}
        };

        Village[] villages = new Village[] {
            new Village(0, 0),  // School
            new Village(1, 5),
            new Village(2, 4),
            new Village(3, 6),
            new Village(4, 2),
            new Village(5, 3)
        };

        int busCapacity = 9;
        greedyRoute(dist, villages, busCapacity);
    }
}