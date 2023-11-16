package za.co.discovery.interstellarassessment.service.routegraph;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.discovery.interstellarassessment.bo.Planet;
import za.co.discovery.interstellarassessment.dto.RouteDto;
import za.co.discovery.interstellarassessment.exception.RouteException;
import za.co.discovery.interstellarassessment.service.RouteService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteGraphServiceTest {

    @InjectMocks
    RouteGraphService routeGraphService;
    @Mock
    RouteService routeService;
    List<RouteDto> routes;

    @BeforeEach
    @SneakyThrows
    void setup() {

        routes = new ArrayList<>();
        LinkedList<String> lines = readFileToList("src/test/resources/data/route.csv");
        lines.stream()
                .forEach(line -> {
                    if (!line.startsWith("ROUTE_ID")) {
                        String[] splittedLine = line.split(",");
                        routes.add(new RouteDto(Planet.valueOf(splittedLine[1]), Planet.valueOf(splittedLine[2]), Double.valueOf(splittedLine[3])));
                    }
                });
    }


    @DisplayName("test shortest path should to A (Earth) from U should return A,C,F,J,R,P with a distance of 39.59")
    @Test
    void get() {

        // Given
        when(routeService.findAll()).thenReturn(routes);

        // When
        RouteGraph routeGraph = routeGraphService.get();

        // Then
        Assertions.assertThat(routeGraph.getNodes())
                .isNotNull()
                .filteredOn("planet", Planet.U)
                .extracting("planet", "shortestPath", "distance")
                .contains(tuple(Planet.U, new LinkedList<>(List.of(new RouteGraph.Node(Planet.A),
                        new RouteGraph.Node(Planet.C), new RouteGraph.Node(Planet.F),
                        new RouteGraph.Node(Planet.J), new RouteGraph.Node(Planet.R),
                        new RouteGraph.Node(Planet.P))), 39.59));
    }


    @Test
    void getShouldThrowRouteException_WhenConfigurationRouteIsNotDefined() {

        Assertions.assertThatThrownBy(() -> routeGraphService.get())
                .isInstanceOf(RouteException.class)
                .hasMessage("configured routes not defined");
    }


    private static LinkedList readFileToList(String fileName) throws IOException {
        LinkedList<String> result;
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            result = lines.collect(Collectors.toCollection(LinkedList::new));
        }
        return result;
    }
}