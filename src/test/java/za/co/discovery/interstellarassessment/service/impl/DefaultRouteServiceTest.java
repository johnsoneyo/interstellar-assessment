package za.co.discovery.interstellarassessment.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.discovery.interstellarassessment.bo.Planet;
import za.co.discovery.interstellarassessment.bo.RouteBo;
import za.co.discovery.interstellarassessment.dto.RouteDto;
import za.co.discovery.interstellarassessment.repository.RouteRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class DefaultRouteServiceTest {

    @InjectMocks
    DefaultRouteService defaultRouteService;
    @Mock
    RouteRepository repository;

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

}