package za.co.discovery.assignment.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.assignment.bo.Planet;
import za.co.discovery.assignment.service.routegraph.RouteGraph;
import za.co.discovery.assignment.service.routegraph.RouteGraphService;
import za.co.discovery.assignment.util.GraphUtils;

import java.util.LinkedList;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/shortest-path")
@RequiredArgsConstructor
public class RouteGraphController {

    private final RouteGraphService routeGraphService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{planet}")
    ShortestPathDto getShortestPath(@PathVariable Planet planet) {
        RouteGraph.Node node = GraphUtils.getNode(routeGraphService.getGraph().get("rest"), planet);

        return new ShortestPathDto(
                node.getShortestPath().stream().map(RouteGraph.Node::getPlanet)
                        .collect(Collectors.toCollection(LinkedList::new)), node.getDistance());
    }


    @AllArgsConstructor
    @Getter
    static class ShortestPathDto {

        LinkedList<Planet> path;
        Double distance;

    }
}
