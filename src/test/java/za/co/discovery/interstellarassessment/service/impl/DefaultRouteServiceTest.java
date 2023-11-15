package za.co.discovery.interstellarassessment.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.discovery.interstellarassessment.bo.Planet;
import za.co.discovery.interstellarassessment.bo.RouteBo;
import za.co.discovery.interstellarassessment.dto.RouteDto;
import za.co.discovery.interstellarassessment.dto.UpdateRouteDto;
import za.co.discovery.interstellarassessment.exception.RouteNotFoundException;
import za.co.discovery.interstellarassessment.repository.RouteRepository;
import za.co.discovery.interstellarassessment.service.RouteService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests written here mimic test done in RouteControllerTest
 * The difference here is webmvc tests actually tests serialization and deserialization and unit tests
 * While the unit uses mocks quite heavily ignoring serialization issues happening at the web layer
 * Both are important !!!
 */
@ExtendWith(MockitoExtension.class)
class DefaultRouteServiceTest {

    RouteService defaultRouteService;

    RouteRepository repository;

    DestinationRoutePatch routePatch;

    @BeforeEach
    void setup () {
        repository = mock(RouteRepository.class);
        routePatch = mock(DestinationRoutePatch.class);
        defaultRouteService = new DefaultRouteService(repository, List.of(routePatch));
    }

    @DisplayName("create should save with repository when called")
    @Test
    void create() {

        // Given
        RouteDto routeDto = new RouteDto(Planet.A, Planet.B, Double.valueOf(0.45));

        // When
        defaultRouteService.create(routeDto);

        // Then
        verify(repository).save(any(RouteBo.class));
        verifyNoMoreInteractions(repository);
    }

    @DisplayName("find should return all route entries")
    @Test
    void findAll() {

        // Given
        Planet origin = Planet.A;
        Planet destination = Planet.B;
        Double distance = Double.valueOf(0.45);
        RouteBo routeDto = new RouteBo(origin, destination, distance);
        when(repository.findAll()).thenReturn(List.of(routeDto));

        // When
        List<RouteDto> list = defaultRouteService.findAll();

        // Then
        Assertions.assertThat(list)
                .isNotEmpty()
                .extracting("origin", "destination", "distance")
                .contains(tuple(origin, destination, distance));
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }


    @Test
    void findById_ShouldThrowRouteNotFoundException_WhenRouteNotPresent() {

        // Given
        Long routeId = 1L;

        // When
        Assertions.assertThatThrownBy(() -> defaultRouteService.findById(routeId))
                .isInstanceOf(RouteNotFoundException.class)
                .hasMessage(String.format("route id %d not found", routeId));

        // Then
        verify(repository).findById(routeId);
        verifyNoMoreInteractions(repository);
    }



    @Test
    void findById_ShouldReturnRoute_WhenRoutePresent() {

        // Given
        Long routeId = 1L;
        RouteBo route = mock(RouteBo.class);
        when(repository.findById(routeId)).thenReturn(Optional.of(route));

        // When
        RouteDto routeDto = defaultRouteService.findById(routeId);

        // Then
        Assertions.assertThat(routeDto)
                        .isNotNull();
        verify(repository).findById(routeId);
        verifyNoMoreInteractions(repository);
    }


    @Test
    void update() {

        // Given
        Long routeId = 1L;
        UpdateRouteDto updateRouteDto = mock(UpdateRouteDto.class);
        RouteBo route = mock(RouteBo.class);
        when(repository.findById(routeId)).thenReturn(Optional.of(route));

        // When
        defaultRouteService.update(routeId, updateRouteDto);

        // Then
        verify(repository).save(route);
        verify(routePatch).patch(updateRouteDto, route);
        verifyNoMoreInteractions(updateRouteDto, route, repository, routePatch);
    }

}