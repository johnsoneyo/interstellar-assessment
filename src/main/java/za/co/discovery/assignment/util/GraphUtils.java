package za.co.discovery.assignment.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import za.co.discovery.assignment.bo.Planet;
import za.co.discovery.assignment.exception.InvalidSourceConfigException;
import za.co.discovery.assignment.service.routegraph.RouteGraph;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GraphUtils {


    /**
     * @param graph  Prepopulated graph returned from cache
     * @param planet planet supplied to determine shortest path to source A
     * @return node selected by planet supplied
     * @throws InvalidSourceConfigException if planet supplied is not setup
     */
    public static RouteGraph.Node getNode(final RouteGraph graph, final Planet planet) throws InvalidSourceConfigException {
        List<RouteGraph.Node> nodes = new ArrayList<>(graph.getNodes());
        Integer planetIndex = nodes.indexOf(new RouteGraph.Node(planet));

        if (planetIndex >= 0) {
            return nodes.get(planetIndex);
        }
        throw new InvalidSourceConfigException(String.format("source %s not configured", planet));
    }
}
