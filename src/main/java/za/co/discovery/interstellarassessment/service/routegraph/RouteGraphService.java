package za.co.discovery.interstellarassessment.service.routegraph;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.co.discovery.interstellarassessment.bo.Planet;
import za.co.discovery.interstellarassessment.dto.RouteDto;
import za.co.discovery.interstellarassessment.exception.RouteException;
import za.co.discovery.interstellarassessment.service.RouteService;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * This class contains only one public method {@link RouteGraphService#get()} which returns  a mapping of all nodes in the graph to source node {@link Planet#A}
 */
@RequiredArgsConstructor
@Service
public class RouteGraphService {

    private final RouteService routeService;

    /**
     * @return returns a graph of all established shortest paths to a source {@link Planet#A}
     * @throws za.co.discovery.interstellarassessment.exception.InvalidSourceConfigException when source node is not configured
     * @throws za.co.discovery.interstellarassessment.exception.InvalidSourceConfigException when configured route list is empty
     */
    public RouteGraph get() {

        final List<RouteGraph.Node> nodes = EnumSet.allOf(Planet.class)
                .stream()
                .map(RouteGraph.Node::new)
                .collect(Collectors.toList());

        List<RouteDto> configuredRoutes = routeService.findAll();
        if(!configuredRoutes.isEmpty()) {
            routeService.findAll()
                    .forEach(defineNodeEdges(nodes));

            RouteGraph routeGraph = RouteGraph.getInstance();
            nodes.forEach(routeGraph::addNode);

            Integer sourceIndex = nodes.indexOf(new RouteGraph.Node(Planet.A));
            if (sourceIndex >= 0) {
                return calculateShortestPathFromSource(routeGraph, nodes.get(sourceIndex));
            }
            throw new RouteException("source route not configured in nodes");
        }

        throw new RouteException("configured routes not defined");
    }

    /**
     * This function defines the nodes to edges by looking up an object through their index in the node list
     *
     * @param nodes receives nodes {@link RouteGraph.Node}
     * @return returns a consumer function
     * @see RouteDto
     * @see RouteGraph.Node
     */
    private Consumer<RouteDto> defineNodeEdges(List<RouteGraph.Node> nodes) {
        return routeDto -> {

            Integer originIndex = nodes.indexOf(new RouteGraph.Node(routeDto.getOrigin()));
            Integer destinationIndex = nodes.indexOf(new RouteGraph.Node(routeDto.getDestination()));

            if (originIndex >= 0 && destinationIndex >= 0) {
                RouteGraph.Node originNode = nodes.get(originIndex);
                RouteGraph.Node destinationNode = nodes.get(destinationIndex);
                originNode.createEdge(destinationNode, routeDto.getDistance());
            }
        };
    }

    /**
     * @param routeGraph
     * @param source
     * @return
     */
    private RouteGraph calculateShortestPathFromSource(RouteGraph routeGraph, RouteGraph.Node source) {

        source.setDistance(0.0);

        Set<RouteGraph.Node> settledNodes = new HashSet<>();
        Set<RouteGraph.Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (!unsettledNodes.isEmpty()) {
            RouteGraph.Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<RouteGraph.Node, Double> adjacencyPair : currentNode.getNearbyNodes().entrySet()) {
                RouteGraph.Node adjacentNode = adjacencyPair.getKey();
                Double edgeWeigh = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return routeGraph;
    }

    /**
     * @param evaluationNode
     * @param edgeWeigh
     * @param sourceNode
     */
    private void calculateMinimumDistance(RouteGraph.Node evaluationNode, Double edgeWeigh, RouteGraph.Node sourceNode) {
        Double sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<RouteGraph.Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    /**
     * @param unsettledNodes
     * @return
     */
    private RouteGraph.Node getLowestDistanceNode(Set<RouteGraph.Node> unsettledNodes) {
        RouteGraph.Node lowestDistanceNode = null;
        Double lowestDistance = Double.MAX_VALUE;
        for (RouteGraph.Node node : unsettledNodes) {
            Double nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

}
