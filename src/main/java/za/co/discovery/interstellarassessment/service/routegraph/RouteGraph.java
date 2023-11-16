package za.co.discovery.interstellarassessment.service.routegraph;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.discovery.interstellarassessment.bo.Planet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RouteGraph {

    private static RouteGraph INSTANCE;
    private Set<Node> nodes = new HashSet<>();

    public static RouteGraph getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RouteGraph();
        }
        return INSTANCE;
    }

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }


    /**
     * This implementation of Node {@link Object#equals} and {@link java.util.Objects#hashCode(Object)} by planet is important
     * in looking up objects in a list as used in {@link RouteGraphService#get()}* from {@link java.util.List#indexOf(Object)}
     */
    @Getter
    @Setter
    @EqualsAndHashCode(of = {"planet"})
    public static class Node {

        private Planet planet;
        private LinkedList<RouteGraph.Node> shortestPath = new LinkedList<>();
        private Double distance = Double.MAX_VALUE;
        private Map<RouteGraph.Node, Double> nearbyNodes = new HashMap<>();

        public Node(Planet planet) {
            this.planet = planet;
        }

        /**
         * Creates an edge with {@link za.co.discovery.interstellarassessment.service.routegraph.RouteGraph.Node}
         *
         * @param destination
         * @param distance
         */
        public void createEdge(RouteGraph.Node destination, Double distance) {
            nearbyNodes.put(destination, distance);
        }


    }


}