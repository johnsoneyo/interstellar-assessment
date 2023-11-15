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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = { RouteController.class, DefaultRouteService.class, ErrorResponseAdvice.class})
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
        List<RouteBo>routes = List.of(new RouteBo(Planet.A, Planet.B, Double.valueOf(4.5)));
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
    void findByIdShouldReturn404_WhenResultIsReturned() {

        // Given
        Long routeId = 1L;

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/routes/"+routeId).accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$.message", Matchers.is(String.format("route id %d not found", routeId))))
                .andExpect(status().isNotFound());

        // Then
        verify(repository).findById(routeId);
        verifyNoMoreInteractions(repository);
    }



}