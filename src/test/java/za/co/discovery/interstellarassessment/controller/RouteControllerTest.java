package za.co.discovery.interstellarassessment.controller;

import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import za.co.discovery.interstellarassessment.bo.Planet;
import za.co.discovery.interstellarassessment.bo.RouteBo;
import za.co.discovery.interstellarassessment.controller.advice.ErrorResponseAdvice;
import za.co.discovery.interstellarassessment.repository.RouteRepository;
import za.co.discovery.interstellarassessment.service.impl.DefaultRouteService;
import za.co.discovery.interstellarassessment.service.impl.DestinationRoutePatch;
import za.co.discovery.interstellarassessment.service.impl.DistanceRoutePatch;
import za.co.discovery.interstellarassessment.service.impl.OriginRoutePatch;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {
        RouteController.class, DefaultRouteService.class,
        ErrorResponseAdvice.class, DestinationRoutePatch.class,
        OriginRoutePatch.class, DistanceRoutePatch.class})
class RouteControllerTest {

    @MockBean
    RouteRepository repository;
    @Autowired
    MockMvc mockMvc;

    @SneakyThrows
    @Test
    void create_ShouldReturnCreate_WhenPayloadIsValid() {

        // Given
        byte[] data = Files.readAllBytes(Paths.get("src/test/resources/requests/valid-route.json"));

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/routes").contentType(MediaType.APPLICATION_JSON_VALUE).content(data))
                .andDo(print())
                .andExpect(status().isCreated());

        // Then
        verify(repository).save(any(RouteBo.class));
        verifyNoMoreInteractions(repository);
    }


    @SneakyThrows
    @Test
    void createShouldReturn400_WhenPayloadIsInvalid() {

        // Given
        byte[] data = Files.readAllBytes(Paths.get("src/test/resources/requests/invalid-route.json"));

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/routes").contentType(MediaType.APPLICATION_JSON_VALUE).content(data))
                .andDo(print())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].message", Matchers.is("destination is required")))
                .andExpect(status().isBadRequest());

        // Then
        verifyNoMoreInteractions(repository);
    }


    @SneakyThrows
    @Test
    void findAllShouldReturn200_WhenResultIsReturned() {

        // Given
        List<RouteBo> routes = List.of(new RouteBo(Planet.A, Planet.B, Double.valueOf(4.5)));
        when(repository.findAll()).thenReturn(routes);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/routes").accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(status().isOk());

        // Then
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @SneakyThrows
    @Test
    void findByIdShouldReturn404_WhenIdIsNotFoundReturned() {

        // Given
        Long routeId = 1L;

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/routes/" + routeId))
                .andDo(print())
                .andExpect(jsonPath("$.message", Matchers.is(String.format("route id %d not found", routeId))))
                .andExpect(status().isNotFound());

        // Then
        verify(repository).findById(routeId);
        verifyNoMoreInteractions(repository);
    }

    @SneakyThrows
    @Test
    void findByIdShouldReturn200_WhenResultIsReturned() {

        // Given
        Long routeId = 1L;
        RouteBo routeBo = new RouteBo(Planet.A, Planet.A_, Double.valueOf(34.0));
        when(repository.findById(routeId)).thenReturn(Optional.of(routeBo));

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/routes/" + routeId))
                .andDo(print()).andExpect(jsonPath("$", Matchers.is(Matchers.notNullValue())))
                .andExpect(jsonPath("$.origin", Matchers.is(Planet.A.name()))).andExpect(jsonPath("$.destination", Matchers.is(Planet.A_.name())))
                .andExpect(jsonPath("$.distance", Matchers.is(Double.valueOf(34.0))))
                .andExpect(status().isOk());

        // Then
        verify(repository).findById(routeId);
        verifyNoMoreInteractions(repository);
    }

    @SneakyThrows
    @Test
    void update_ShouldOnlyInteractWithOriginField_WhenPatchingRoute() {

        // Given
        Long routeId = 1L;
        RouteBo routeBo = spy(new RouteBo(Planet.A, Planet.A_, Double.valueOf(34.0)));
        when(repository.findById(routeId)).thenReturn(Optional.of(routeBo));
        byte[] data = Files.readAllBytes(Paths.get("src/test/resources/requests/update-route.json"));

        // When
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/routes/" + routeId).contentType(MediaType.APPLICATION_JSON_VALUE).content(data))
                .andDo(print())
                .andExpect(status().isOk());

        // Then
        verify(repository).findById(routeId);
        verify(repository).save(routeBo);
        verify(routeBo).setOrigin(Planet.C);
        verifyNoMoreInteractions(repository, routeBo);
    }

    @SneakyThrows
    @Test
    void update_ShouldThrow404NotFound_WhenRouteNotPresent() {

        // Given
        Long routeId = 1L;
        byte[] data = Files.readAllBytes(Paths.get("src/test/resources/requests/update-route.json"));

        // When
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/routes/" + routeId).contentType(MediaType.APPLICATION_JSON_VALUE).content(data))
                .andDo(print())
                .andExpect(jsonPath("$.message", Matchers.is(String.format("route id %d not found", routeId))))
                .andExpect(status().isNotFound());

        // Then
        verify(repository).findById(routeId);
        verifyNoMoreInteractions(repository);
    }

    @SneakyThrows
    @Test
    void delete_ShouldThrow404NotFound_WhenRouteNotPresent() {

        // Given
        Long routeId = 1L;

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/routes/" + routeId))
                .andDo(print())
                .andExpect(jsonPath("$.message", Matchers.is(String.format("route id %d not found", routeId))))
                .andExpect(status().isNotFound());

        // Then
        verify(repository).findById(routeId);
        verifyNoMoreInteractions(repository);
    }

    @SneakyThrows
    @Test
    void delete_ShouldReturn204_WhenRouteIsPresent() {

        // Given
        Long routeId = 1L;
        RouteBo routeBo = mock(RouteBo.class);
        when(repository.findById(routeId)).thenReturn(Optional.of(routeBo));

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/routes/" + routeId))
                .andDo(print())
                .andExpect(status().isNoContent());

        // Then
        verify(repository).findById(routeId);
        verify(repository).delete(routeBo);
        verifyNoMoreInteractions(repository);
    }


}