package za.co.discovery.assignment.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import za.co.discovery.assignment.bo.Planet;
import za.co.discovery.assignment.exception.InvalidSourceConfigException;
import za.co.discovery.assignment.gen.GetShortestPathRequest;
import za.co.discovery.assignment.gen.GetShortestPathResponse;
import za.co.discovery.assignment.service.routegraph.RouteGraph;
import za.co.discovery.assignment.service.routegraph.RouteGraphService;
import za.co.discovery.assignment.util.GraphUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class exposes the webservice to query shortest path to source by {@link Planet}
 */
@RequiredArgsConstructor
@Endpoint
public class RouteEndpoint {

    private final RouteGraphService graphService;

    public static final String NAMESPACE_URI = "http://assignment.discovery.co.za/gen";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getShortestPathRequest")
    @ResponsePayload
    public GetShortestPathResponse getShortestPath(@RequestPayload GetShortestPathRequest request) {
        Planet planet = Planet.valueOf(request.getPlanet());

        final GetShortestPathResponse response = new GetShortestPathResponse();
        RouteGraph graph = graphService.getGraph().get("webservice");
        RouteGraph.Node node = GraphUtils.getNode(graph, planet);

        response.setDistance(node.getDistance());
        node.getShortestPath().stream()
                .map(RouteGraph.Node::getPlanet)
                .map(Planet::name)
                .forEach(response.getPaths()::add);

        return response;
    }

    /**
     * @param graph  Prepopulated graph returned from cache
     * @param planet planet supplied to determine shortest path to source A
     * @return node selected by planet supplied
     * @throws InvalidSourceConfigException if planet supplied is not setup
     */
    private RouteGraph.Node getNode(final RouteGraph graph, final Planet planet) throws InvalidSourceConfigException {
        List<RouteGraph.Node> nodes = new ArrayList<>(graph.getNodes());
        Integer planetIndex = nodes.indexOf(new RouteGraph.Node(planet));

        if (planetIndex >= 0) {
            return nodes.get(planetIndex);
        }
        throw new InvalidSourceConfigException(String.format("source %s not configured", planet));
    }
}