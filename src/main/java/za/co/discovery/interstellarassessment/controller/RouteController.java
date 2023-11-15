package za.co.discovery.interstellarassessment.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.interstellarassessment.dto.RouteDto;
import za.co.discovery.interstellarassessment.dto.UpdateRouteDto;
import za.co.discovery.interstellarassessment.service.RouteService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void create(@RequestBody @Valid final RouteDto routeDto) {
        routeService.create(routeDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<RouteDto> findAll() {
        return routeService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{routeId}")
    RouteDto findById(@PathVariable Long routeId) {
        return routeService.findById(routeId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{routeId}")
    void update(@PathVariable Long routeId, @RequestBody UpdateRouteDto routeDto) {
        routeService.update(routeId, routeDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{routeId}")
    void delete(@PathVariable Long routeId) {
        routeService.delete(routeId);
    }
}
