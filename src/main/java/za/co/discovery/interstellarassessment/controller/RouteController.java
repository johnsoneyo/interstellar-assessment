package za.co.discovery.interstellarassessment.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.interstellarassessment.dto.RouteDto;
import za.co.discovery.interstellarassessment.service.RouteService;

import javax.validation.Valid;

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

}
